# ElasticSearch

## RESTful API
### Format
``` curl -i -X<VERB> '<PROTOCOL>://<HOST>:<PORT>/<PATH>?<QUERY_STRING>' -d '<BODY>' ```
- VERB : The appropriate HTTP method or verb: GET, POST, PUT, HEAD, or DELETE.
- PROTOCOL : Either http or https (if you have an https proxy in front of Elasticsearch.)
- HOST : The hostname of any node in your Elasticsearch cluster, or localhost for a node on your local machine.
- PORT : The port running the Elasticsearch HTTP service, which defaults to 9200.
- PATH : API Endpoint (for example _count will return the number of documents in the cluster). Path may contain multiple components, such as _cluster/stats or _nodes/stats/jvm
- QUERY_STRING : Any optional query-string parameters (for example ?pretty will pretty-print the JSON response to make it easier to read.)
- BODY : A JSON-encoded request body (if the request needs one.)
- i : To display response header

### Create
#### Using Our Own ID
```PUT /{index}/{type}/{id} -d '<BODY>' ```

##### To Ensure Only New 
```PUT /{index}/{type}/{id}?op_type=create -d '<BODY>' ```

OR 

```PUT /{index}/{type}/{id}/_create -d '<BODY>' ```

#### Autogenerating ID
```POST /{index}/{type}```

instead of using the PUT verb (“store this document at this URL”), we use the POST verb (“store this document under this URL”).

### Check Existance
```HEAD /{index}/{type}/{id}```

### Get
- Everything 

```GET /{index}/{type}/{id}```
- Source Only

```GET /{index}/{type}/{id}/_source```
- Selected Fields

```GET /{index}/{type}/{id}?_source=field1,field2```

- Get Multiple Doc

```GET /_mget 
{
    "docs" : [
        {
            "_index" : value,
            "_type" :  value,
            "_id" :    value
        },
        {
            "_index" : value,
            "_type" :  value,
            "_id" :    value,
            "_source" : value
        }
        ...
    ]
}
```

### Update
Documents in Elasticsearch are immutable; we cannot change them. Instead, if we need to update an existing document, we reindex or replace it;
- Whole Update

```PUT /{index}/{type}/{id}```

- Partical Update

Document
```
POST /{index}/{type}/{id}/_update 
{
    "doc" : {
        key1 : value1
        key2 : value2
    }
}
```

Script
```
POST /{index}/{type}/{id}/_update 
{
   "script" : "ctx._source.{...}",
   "params" : {
        key : value
   }
   "upsert" : {
        key : value
   }
}
```

### Delete

```DELETE /{index}/{type}/{id}```

### Bulk Change

```
{ action: { metadata }}\n
{ request body        }\n
{ action: { metadata }}\n
{ request body        }\n
...

```

Example

```

{ "delete": { "_index": "website", "_type": "blog", "_id": "123" }}
{ "create":  { "_index": "website", "_type": "blog", "_id": "123" }}
{ "title":    "My first blog post" }

```

## Search
### Empty Search

```GET /_search?timeout={number}ms```

The timed_out value tells us whether the query timed out. By default, search requests do not time out. If low response times are more important to you than complete results, you can specify a timeout as 10 or 10ms (10 milliseconds), or 1s (1 second)

### Multi-Index/Type

- /_search : Search all types in all indices
- /gb/_search : Search all types in the gb index
- /gb,us/_search : Search all types in the gb and us indices
- /g*,u*/_search : Search all types in any indices beginning with g or beginning with u
- /gb/user/_search : Search type user in the gb index
- /gb,us/user,tweet/_search : Search types user and tweet in the gb and us indices
- /_all/user,tweet/_search : Search types user and tweet in all indices

### Pagination

- size : Indicates the number of results that should be returned, defaults to 10
- from : Indicates the number of initial results that should be skipped, defaults to 0

``` GET /_search?size=5&from=5 ```

### Search Lite

```GET /_all/tweet/_search?q=tweet:elasticsearch```

OR : _all Fields

```GET /_all/tweet/_search?q=elasticsearch```

## Cluster
### Health

```GET /_cluster/health```

### Create Index

```PUT /{index}```


## Document
Often, we use the terms object and document interchangeably. However, there is a distinction. An object is just a JSON object—similar to what is known as a hash, hashmap, dictionary, or associative array. Objects may contain other objects. In Elasticsearch, the term document has a specific meaning. It refers to the top-level, or root object that is serialized into JSON and stored in Elasticsearch under a unique ID.

- _index : Where the document lives. An index is a collection of documents that should be grouped together for a common reason. For example, you may store all your products in a products index, while all your sales transactions go in sales. Although it is possible to store unrelated data together in a single index, it is often considered an anti-pattern.
- _type : The class of object that the document represents. Data may be grouped loosely together in an index, but often there are sub-partitions inside that data which may be useful to explicitly define. For example, all your products may go inside a single index. But you have different categories of products, such as "electronics", "kitchen" and "lawn-care".
- _id : The unique identifier for the document. When creating a new document, you can either provide your own _id or let Elasticsearch generate one for you.

