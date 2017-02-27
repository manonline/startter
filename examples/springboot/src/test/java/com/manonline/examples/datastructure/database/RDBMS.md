## Back to Basics

### Data Structure
#### Array
The two-dimensional array is the simplest data structure. A table can be seen as an array. For example:

#### Tree

#### B+ Tree

#### Hash Table

## Overview

### The core components:
- The process manager: Many databases have a pool of processes/threads that needs to be managed. Moreover, in order to gain nanoseconds, some modern databases use their own threads instead of the Operating System threads.
- The network manager: Network I/O is a big issue, especially for distributed databases. That’s why some databases have their own manager.
- File system manager: Disk I/O is the first bottleneck of a database. Having a manager that will perfectly handle the Operating System file system or even replace it is important.
- The memory manager: To avoid the disk I/O penalty a large quantity of ram is required. But if you handle a large amount of memory, you need an efficient memory manager. Especially when you have many queries using memory at the same time.
- Security Manager: for managing the authentication and the authorizations of the users
- Client manager: for managing the client connections
- ...

### Tools
- Backup manager: for saving and restoring a database.
- Recovery manager: for restarting the database in a coherent state after a crash
- Monitor manager: for logging the activity of the database and providing tools to monitor a database
- Administration manager: for storing metadata (like the names and the structures of the tables) and providing tools to manage databases, schemas, tablespaces, …
- ...

### Query Manager
- Query parser: to check if a query is valid
- Query rewriter: to pre-optimize a query
- Query optimizer: to optimize a query
- Query executor: to compile and execute a query

### Data Manager 
- Transaction manager: to handle transactions
- Cache manager: to put data in memory before using them and put data in memory before writing them on disk
- Data access manager: to access data on disk

### Queries
How queries are executed : Client Manager -> Query Manager -> Data Manager

## Client Manager
The client manager is the part that handles the communications with the client. The client can be a (web) server or an end-user/end-application. The client manager provides different ways to access the database through a set of well-known APIs: JDBC, ODBC, OLE-DB ... ; It can also provide proprietary database access APIs.

When you connect to a database:
- The manager first checks your **authentication** (your login and password) and then checks if you have the **authorizations**  to use the database. These access rights are set by your DBA.
- Then, it checks if there is a **process (or a thread) available** to manage your query.
- It also checks if the database if not under heavy load.
- It can wait a moment to get the required resources. If this **wait** reaches a **timeout**, it closes the connection and gives a readable error message.
- Then it sends your **query to the query manager** and your query is processed
- Since the query processing is not an “all or nothing” thing, as soon as it gets data from the query manager, it **stores the partial results in a buffer and start sending them** to you.
- In case of problem, it stops the connection, gives you a readable explanation and releases the resources.

## Query Manager
- the query is first **parsed** to see if it’s valid
- it’s then **rewritten** to remove useless operations and add some pre-optimizations
- it’s then **optimized** to improve the performances and transformed into an execution and **data access plan**.
= then the plan is **compiled**
- at last, it’s **executed**

### Query Parser 
- Syntax check
- Database object validation 
- If the tables exist
- If the fields of the tables exist
- If the operations for the types of the fields are possible (for example you can’t compare an integer with a string, you can’t use a substring() function on an integer)
- Authorization check 

### Query Rewritter 
- to pre-optimize the query
- to avoid unnecessary operations
- to help the optimizer to find the best possible solution
#### Optimziation
- View merging: If you’re using a view in your query, the view is transformed with the SQL code of the view.
- Subquery flattening: Having subqueries is very difficult to optimize so the rewriter will try to modify a query with a subquery to remove the subquery.
- Removal of unnecessary operators: For example if you use a DISTINCT whereas you have a UNIQUE constraint that prevents the data from being non-unique, the DISTINCT keyword is removed.
- Redundant join elimination: If you have twice the same join condition because one join condition is hidden in a view or if by transitivity there is a useless join, it’s removed.
- Constant arithmetic evaluation: If you write something that requires a calculus, then it’s computed once during the rewriting. For example WHERE AGE > 10+2 is transformed into WHERE AGE > 12 and TODATE(“some date”) is transformed into the date in the datetime format
- (Advanced) Partition Pruning: If you’re using a partitioned table, the rewriter is able to find what partitions to use.
- (Advanced) Materialized view rewrite: If you have a materialized view that matches a subset of the predicates in your query, the rewriter checks if the view is up to date and modifies the query to use the materialized view instead of the raw tables.
- (Advanced) Custom rules: If you have custom rules to modify a query (like Oracle policies), then the rewriter executes these rules
- (Advanced) Olap transformations: analytical/windowing functions, star joins, rollup … are also transformed (but I’m not sure if it’s done by the rewriter or the optimizer, since both processes are very close it must depends on the database).

## Query Optimizer
### Statistics
### Optimization
All modern databases are using a Cost Based Optimization (or CBO) to optimize queries. a database optimizer computes their CPU cost, disk I/O cost and memory requirement. The difference between time complexity and CPU cost is that time cost is very approximate (it’s for lazy guys like me). For the CPU cost, I should count every operation like an addition, an “if statement”, a multiplication, an iteration … Moreover:
- Each high level code operation has a specific number of low level CPU operations.
- The cost of a CPU operation is not the same (in terms of CPU cycles) whether you’re using an Intel Core i7, an Intel Pentium 4, an AMD Opteron…. In other words it depends on the CPU architecture.
Keep in mind that the bottleneck is most of the time the disk I/O and not the CPU usage.

### Access Path
Before applying your join operators, you first need to get your data. Here is how you can get your data. Note: the real problem with all the access paths is the disk I/O.
- Full Scan : A full scan is simply the database reading a table or an index entirely. In terms of disk I/O, a table full scan is obviously more expensive than an index full scan.
- Range Scan : There are other types of scan like index range scan. It is used for example when you use a predicate like “WHERE AGE > 20 AND AGE <40”. for a range scan you don’t need to read the full index so it’s less expensive in terms of disk I/O than a full scan. 
- Unique Scan : If you only need one value from an index you can use the unique scan.

### Access by Row ID
Most of the time, if the database uses an index, it will have to look for the rows associated to the index. To do so it will use an access by row id. For example, if you do something like

<code> SELECT LASTNAME, FIRSTNAME from PERSON WHERE AGE = 28 </code>
If you have an index for person on column age, the optimizer will use the index to find all the persons who are 28 then it will ask for the associate rows in the table because the index only has information about the age and you want to know the lastname and the firstname.

But, if now you do something like
<code> SELECT TYPE_PERSON.CATEGORY from PERSON ,TYPE_PERSON WHERE PERSON.AGE = TYPE_PERSON.AGE </code>
The index on PERSON will be used to join with TYPE_PERSON but the table PERSON will not be accessed by row id since you’re not asking information on this table.

Though it works great for a few accesses, the real issue with this operation is the disk I/O. **If you need too many accesses by row id the database might choose a full scan.**

### Join
#### Nested Loop Join
- For each row in the outer relation
- You look at all the rows in the inner relation to see if there are rows that match
In term of disk I/O, for each of the N rows in the outer relation, the inner loop needs to read M rows from the inner relation. This algorithm needs to read N + N*M rows from disk. 
#### Inner Relation (Memory)
if the inner relation is small enough, you can put the relation in memory and just have M +N reads. With this modification, the inner relation must be the smallest one since it has more chance to fit in memory. In terms of time complexity it makes no difference but in terms of disk I/O it’s way better to read only once both relations.
#### Inner Relation (Index)
the inner relation can be replaced by an index, it will be better for the disk I/O.
#### Outer/Inner Relation (Batch)
- instead of reading both relation row by row,
- you read them bunch by bunch and keep 2 bunches of rows (from each relation) in memory,
- you compare the rows inside the two bunches and keep the rows that match,
- then you load new bunches from disk and compare them
- and so on until there are no bunches to load.
Each disk access gathers more data than the previous algorithm but it doesn’t matter since they’re sequential accesses (the real issue with mechanical disks is the time to get the first data).
