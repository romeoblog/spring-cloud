# 关于es索引新建和切换操作

## 1. 通过kibana的dev tools 工具新建索引以及mapping映射，下例：

​	新建一个索引名为：new_test_product的索引，举例具体mapping映射如下：

``` DSL
PUT new_test_product
{
  "settings": {
    "analysis": {
      "analyzer": {
        "ik": {
          "tokenizer": "ik_max_word"（指定分词器）
        }
      }
    },
    "index.mapping.single_type": true（用于设置该索引只能拥有一个type）
  },
  "mappings": {
    "product_type_pid_operation": {（指定type名称为：product_type_pid_operation）
      "dynamic": true,
      "properties": {
        "activetime": {
          "type": "date",
          "format": "yyyy-MM-dd HH:mm:ss"
        },
        "brandName": {
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word"
        },
        "brandid": {
          "type": "integer",
          "store": true
        },
        "categoryName": {
          "type": "keyword"
        },
        "categoryid": {
          "type": "integer",
          "store": true
        },
        "fcategoryName": {
          "type": "keyword"
        },
        "fcategoryid": {
          "type": "integer",
          "store": true
        },
        "fdMproId": {
          "type": "integer",
          "store": true
        },
        "fdMproName": {
          "type": "text",
          "fields": {
            "keyword": {
              "type": "keyword"
            }
          }, 
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word"
        },
        "fdMproPic": {
          "type": "keyword",
          "store": true
        },
        "fdMproPreSell": {
          "type": "keyword",
          "store": true
        },
        "fdMproPrice": {
          "type": "double",
          "store": true
        },
        "fdMproSalenum": {
          "type": "integer",
          "store": true
        },
        "fdMskuId": {
          "type": "integer",
          "store": true
        },
        "fdMskuTitle": {
          "type": "text",
          "store": true,
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "fields": {
              "keyword": {
                "type": "keyword",
                "ignore_above": 256
              }
            }
        },
        "fdSalenum": {
          "type": "integer",
          "store": true
        },
        "label": {
          "type": "keyword",
          "store": true
        },
        "mcmpyid": {
          "type": "integer",
          "store": true
        },
        "mcmpyname": {
          "type": "text",
          "store": true,
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word",
          "fields": {
              "keyword": {
                "type": "keyword",
                "ignore_above": 256
              }
            }
        },
        "mproType": {
          "type": "integer",
          "store": true
        },
        "mproprice": {
          "type": "double",
          "store": true
        },
        "remark": {
          "type": "text",
          "fields": {
              "keyword": {
                "type": "keyword",
                "ignore_above": 256
              }
            }
        },
        "scategoryName": {
          "type": "keyword"
        },
        "scategoryid": {
          "type": "integer",
          "store": true
        },
        "shopcategoryid": {
          "type": "integer",
          "store": true
        },
        "tcPic": {
          "type": "keyword",
          "store": true
        },
        "thirdLogoUrl": {
          "type": "keyword",
          "store": true
        },
        "oneOperationId": {
          "type": "integer",
          "store": true
        },
        "operationName": {
          "type": "text",
          "store": true
        },
        "twoOperationId": {
          "type": "integer",
          "store": true
        },
        "twoOperationName": {
          "type": "text",
          "store": true
        },
        "operationId": {
          "type": "integer",
          "store": true
        },
        "thirdOperationName": {
          "type": "text",
          "store": true
        }
      }
    }
  }
}
```



## 2. 将旧的索引中的数据迁移到新建的索引中，并且指定索引的type名称。

​	鉴于旧索引数据量较大，用商品类型来分段迁移索引数据

``` json
POST _reindex
{
  "source": {
    "index": "product_pid_del",(旧的索引名称)
    "query": {（搜索条件）
      "match": {
        "mproType": 3
      }
    }
  },
  "dest": {
    "index": "new_test_product",（新的索引名称）
    "type":"product_type_pid_operation"（指定的type名称）
  }
}
```



## 3. 对新的索引设置别名，用于无缝切换es索引指向

``` json
POST /_aliases
{
    "actions": [
        { "remove": { "index": "product_pid_del", "alias": "product_pid_final" }},
        { "add":    { "index": "new_test_product", "alias": "product_pid_final" }}
    ]
}
```



## 4. 将旧的索引数据关闭

``` json
POST /product_pid_del/_close (关闭索引)
POST /product_pid_del/_open （打开索引）
```

