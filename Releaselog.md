# 版本发布日志

## v5.0.0 

### 功能特性
* 项目由example更名为mesh，期望能跟从新一代的Service Mesh，成为基础微服务技术脚架新秀。
* 全面升级Alibaba Cloud至2.1.0.RELEASE，SpringCloud使用版本：Greenwich.SR2。
* RocketMQ支持注册中心。
* 支持全文搜索引擎技术框架：Elasticsearch（后续以多种方式实现Msql与Elasticearch数据同步）。
* 升级Guava至28.0-jre。
* 支持Alibaba fastjson。
* 升级SringBoot内部容器由tomcat更改为undertow。
* 新增Docker独立项目：[spring-cloud-docker](https://github.com/romeoblog/spring-cloud-docker)。
### 修复和优化
* 调整lombok作用域为provided。
* 删除parent-old。
* 其他修复和优化。

2019.08.19

# v4.3.4 

### 功能特性
* 升级Spring Cloud版本为Greenwich.SR2
* 升级并支持Alibaba Seata分布式事务版本为0.7.1
* 提供服务端口和Docker端口号映射规则表。
### 修复和优化
* 调整默认日志输入路径：${user.home}/logs/example}，可通过${LOG_HOME}进行自定义。
* 修复可能存在内存异常的情况。
* 文档内容调整。

2019.07.29