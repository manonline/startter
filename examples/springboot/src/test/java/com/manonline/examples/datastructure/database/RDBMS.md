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
##### Inner Relation (Memory)
if the inner relation is small enough, you can put the relation in memory and just have M +N reads. With this modification, the inner relation must be the smallest one since it has more chance to fit in memory. In terms of time complexity it makes no difference but in terms of disk I/O it’s way better to read only once both relations.
##### Inner Relation (Index)
the inner relation can be replaced by an index, it will be better for the disk I/O.
##### Outer/Inner Relation (Batch)
- instead of reading both relation row by row,
- you read them bunch by bunch and keep 2 bunches of rows (from each relation) in memory,
- you compare the rows inside the two bunches and keep the rows that match,
- then you load new bunches from disk and compare them
- and so on until there are no bunches to load.
Each disk access gathers more data than the previous algorithm but it doesn’t matter since they’re sequential accesses (the real issue with mechanical disks is the time to get the first data).

#### Hash Join
The hash join is more complicated but gives a better cost than a nested loop join in many situations. The idea of the hash join is to:
- Get all elements from the inner relation
- Build an in-memory hash table
- Get all elements of the outer relation one by one
- Compute the hash of each element (with the hash function of the hash table) to find the associated bucket of the inner relation
- find if there is a match between the elements in the bucket and the element of the outer table

#### Merge Join
The merge join is the only join that produces a sorted result. Note: In this simplified merge join, there are no inner or outer tables; they both play the same role. But real implementations make a difference, for example, when dealing with duplicates. The merge join can be divided into of two steps:
- (Optional) Sort join operations: Both the inputs are sorted on the join key(s).
- Merge join operation: The sorted inputs are merged together.
##### Sort
##### Merge Join
This part is very similar to the merge operation of the merge sort we saw. But this time, instead of picking every element from both relations, we only pick the elements from both relations that are equals. Here is the idea:
- you compare both current elements in the 2 relations (current=first for the first time)
- if they’re equal, then you put both elements in the result and you go to the next element for both relations
- if not, you go to the next element for the relation with the lowest element (because the next element might match)
- and repeat 1,2,3 until you reach the last element of one of the relation.
This works because both relations are sorted and therefore you don’t need to “go back” in these relations.

### Which One is The Best
If there was a best type of joins, there wouldn’t be multiple types. This question is very difficult because many factors come into play like:
- The amount of free memory: without enough memory you can say goodbye to the powerful hash join (at least the full in-memory hash join)
- The size of the 2 data sets. For example if you have a big table with a very small one, a nested loop join will be faster than a hash join because the hash join has an expensive creation of hashes. If you have 2 very large tables the nested loop join will be very CPU expensive.
- The presence of indexes. With **2 B+Tree indexes** the smart choice seems to be the **merge join**
- If the result need to be sorted: Even if you’re working with unsorted data sets, you might want to use a costly merge join (with the sorts) because at the end the result will be sorted and you’ll be able to chain the result with another merge join (or maybe because the query asks implicitly/explicitly for a sorted result with an ORDER BY/GROUP BY/DISTINCT operation)
- If the relations are already sorted: In this case the merge join is the best candidate
- The type of joins you’re doing: is it an equijoin (i.e.: tableA.col1 = tableB.col2)? Is it an inner join, an outer join, a cartesian product or a self-join? Some joins can’t work in certain situations.
- The distribution of data. If the data on the join condition are skewed (For example you’re joining people on their last name but many people have the same), using a hash join will be a disaster because the hash function will create ill-distributed buckets.
- If you want the join to be executed by multiple threads/process

### Real Optimization
The real job of an optimizer is to find a good solution on a limited amount of time. On limited amount of time, a given order of joins, each join has 3 possibilities: HashJoin, MergeJoin, NestedJoin. for a given order of joins there are 34 possibilities. The join ordering is a permutation problem on a binary tree and there are (2*4)!/(4+1)! possible orders. For this very simplified problem, I end up with 34*(2*4)!/(4+1)! possibilities. In non-geek terms, it means 27 216 possible plans. If I now add the possibility for the merge join to take 0,1 or 2 B+Tree indexes, the number of possible plans becomes 210 000. Did I forget to mention that this query is VERY SIMPLE? Most of the time an optimizer doesn’t find the best solution but a “good” one.

- Dynamic Programming
- Greedy Algorithms
- Other Algorithms

### Query Plan Cache
Since the creation of a plan takes time, most databases store the plan into a query plan cache to avoid useless re-computations of the same query plan. It’s kind of a big topic since the database needs to know when to update the outdated plans. The idea is to put a threshold and if the statistics of a table have changed above this threshold then the query plan involving this table is purged from the cache.

### Query executor
At this stage we have an optimized execution plan. This plan is compiled to become an executable code. Then, if there are enough resources (memory, CPU) it is executed by the query executor. The operators in the plan (JOIN, SORT BY …) can be executed in a sequential or parallel way; it’s up to the executor. To get and write its data, the query executor interacts with the data manager, which is the next part of the article.

## Data Manager
At this step, the query manager is executing the query and needs the data from the tables and indexes. It asks the data manager to get the data, but there are 2 problems:
- Relational databases use a transactional model. So, you can’t get any data at any time because someone else might be using/modifying the data at the same time.
- Data retrieval is the slowest operation in a database, therefore the data manager needs to be smart enough to get and keep data in memory buffers.


