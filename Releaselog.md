# 版本发布日志

## v5.1.0 

### 升级功能特性
* 新增支持 NoSQL 分布式文件存储：MongDB 数据存储。
* 新增支持从 Mysql -> ElasticSearch 全量同步，采用 Logstash 实现方案。
* 新增支持Canal基于MySQL高性能数据增量同步，消息可选择 Kafka 或 RabbitMQ 作为消息缓冲，方案：Mysql  ->  Canal  ->  消息中间件  ->  ElasticSearch。
* 新增支持 Apache Quartz任务调度。
* 升级分布式事物 Seata 版本至 0.8.0。
* 升级配置中心 Nacos 版本至 1.1.3。
* 完善优化相关文档。

### 修复和优化
* 删除pom.xml一些无效代码。
* 其他修复和优化。

### 下版本支持功能
* 支持全局Docker化，支持Docker-Compose 一键部署。
* 支持分布式NoSQL数据存储：Cassandra。
* 支持分布式调度解决方案：Elastic-Job。
* 支持唯品会开源的一个分布式任务调度平台：Saturn (任务调度系统)。
* 支持Seata达生产级别。

2019.09.28

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

## v4.3.4 

### 功能特性
* 升级Spring Cloud版本为Greenwich.SR2
* 升级并支持Alibaba Seata分布式事务版本为0.7.1
* 提供服务端口和Docker端口号映射规则表。
### 修复和优化
* 调整默认日志输入路径：${user.home}/logs/example}，可通过${LOG_HOME}进行自定义。
* 修复可能存在内存异常的情况。
* 文档内容调整。

2019.07.29

## v4.3.3

### 功能特性
* 升级Alibaba Nacos版本为1.1.0
* 添加服务端口和Docker端口号映射规则表，项目已按照规格表设计调整。（文档后续整理）
* 支持日志文件目录自定义。
* 删除example-turbine模块。
* 集成Docker管理，后续引入docker-compose，形成脚手架项目。
* 支持 dockerfile-maven 管理。
### 修复和优化
* 优化Maven打包机制，引入spring-boot-maven-plugin 插件
* 支持日志文件目录自定义
* Token拦截器调整优化，引入动态配置刷新注解: @RefreshScope
* 文档内容调整。

2019.07.06

## v4.3.2

### 功能特性
* 集成支持Apache RocketMQ。
* 提供消息测试案例：批量消息、严格顺序消息、过滤条件消息、延时消息以及简单消息案例。
### 修复和优化
* 优化一些代码规范

2019.07.06

## v4.3.1

### 功能特性
* 重写认证服务checkToken检查信息，自定义token合法校验。
* 新增相关基础栗子模型：nacos-sample、seata-eureka-sample、seata-ha-sample、seata-nacos-sample、seata-sample、seata-tcc-sample、springboot-sample等基础模块模型。（还未提供具体栗子）
### 修复和优化
* 优化和调整代码相关的规范和制约。
* 校验token有效性统一归纳认证服管理。
* 文档内容修改：新增相关栗子模块模型。
* 其他优化和调整。

2019.06.24

## v4.3.0

### 功能特性
* 项目架构核心配置模块包结构调整，使其更加规范化和见名之意。
* 升级Nacos至1.0.1版本。
* 全面支持应用的动态服务发现、配置管理和服务及集群管理等。
* 新增Github项目自定义配置主题，对应配置文件：_config.yml
* 预留TCC模式
### 修复和优化
* 修改文档相关内容，添加许可证和个人相关信息。

2019.06.23

## v4.2.1

### 功能特性
* 升级Swagger-models至1.5.22，解决NumberFormatException问题。
* Druid连接池控制台，提供默认账号密码：root、root。
* 新增基于Mybatis-Plus 栗子模块，提供基本CRUD规范和操作。
* 添加个人信息和版权信息
### 修复和优化
* 数据实体Base基础包结构调整，由platform调整至entity。

2019.06.20

## v4.2.0

### 功能特性
* 支持Token合法有效验证、支持单点登陆。
* 认证服务集成支持Seata分布式事务。
* 添加相关认证初始化Sql脚本。
### 修复和优化
* 完善文档相关说明。

2019.06.14

## v4.1.0

### 功能特性
* 分布式事务配置项由File配置更改Nacos配置。
* 重构鉴权认证服务。
* 授权模式支持两种模式：密码模式和授权码模式。
* 支持Token存储方式：JdbcTokenStore、RedisTokenStore、JwtTokenStore(推荐方式)
* 支持自定义生成token携带的信息。
### 修复和优化
* 网关动态路由优化。
* 调整Feign默认的请求连接和超时时间：10000,60000（单位：ms）
* 其他优化和调整。

2019.06.08

## v4.0.0

### 功能特性
* 支持Eureka和Nacos服务发现与注册两种方式。
* 支持服务链路跟踪，采用Skywalking框架方案。
> VM环境配置
```
-javaagent:/Users/zhengweilu/Documents/projects/private/spring-cloud/example-skywalking-agent/skywalking-agent.jar
-Dskywalking.agent.service_name=example-business-service
-Dskywalking.collector.backend_service=localhost:11800
```
* 集成支持Seata分布式事务集群模式：server分离状态，水平扩张能力强等特点。
* 支持SpingCloud Feign日志自定义扩展功能。
* 升级jackson版本至2.9.9。
* 新增测试模块：example-test
* 新增Skywalking相关文档，也可以前往官方查看相关文档。

### 修复和优化
* 调整异常信息和日志相关格式，使其日志打印更加清晰明朗。
* 全局异常处理添加内部异常和外部异常等方式：InternalApiException/ApiException
* 调整Feign默认连接超时时间：60000ms
* 调整.gitignore对jar不过滤，允许提交。
* 其他修复和优化

2019.06.05

## v3.0.0

### 功能特性
* 支持Sentine相关l功能：流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性。
* 支持Sentinel生产环境的使用：支持规则管理及推送动态扩张数据源，规则中心统一推送，客户端通过注册监听器的方式时刻监听变化。
* 支持网关流控： 新增支持Spring Cloud Gateway 的适配模块。
* 新增模块服务：API接口统一管理模块。
### 修复和优化

* 优化gson工具类：避免重复地builder.create，缓存gson对象。
* 其他修复和优化

2019.05.31