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
