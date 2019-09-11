# Spring Cloud Mesh

该项目包含开发分布式应用微服务的必需组件，通过 Spring Cloud 架构模型 和相关 Spring Cloud 组件来开发分布式应用服务。

## 主要功能

* **服务限流降级**：支持 Feign、RestTemplate 和 Spring Cloud Gateway 等限流降级功能的接入，支持在运行时通过控制台实时修改限流降级规则。
* **服务注册与发现**：适配 Spring Cloud 服务注册与发现，支持两种配置：Eureka 和 Nacos（推荐）。
* **分布式配置管理**：支持分布式系统中的外部化配置，配置更改时自动刷新。支持 Nacos Config 作为统一配置。
* **分布式事务**：我们折中选择由 Alibaba 团队开发的 Seata 分布式事务解决方案，已集成支持 Seata 分布式事务方案，使用 @GlobalTransactional 注解，高效并且对业务零侵入地解决分布式事务问题。
* **安全加密支持**：支持对敏感数据不予暴露，采用不对称加密方式，支持对配置文件加密，提高项目的第一频道的安全性。
* **服务健康与监控**：用于管理和监控 Spring Cloud 的应用，提供简洁的可视化 WEB UI。
* **全链路监控**：是分布式监控系统，用于跟踪分布式服务之间的应用数据链路，分析处理延时，帮助我们改进系统的性能和定位故障。

## 组件

* **Oauth2 & Security**：基于 OAUTH2.0 统一认证授权的微服务基础架构，支持权限、角色等安全认证。

* **Sentinel**：监控服务，把流量作为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性。

* **Nacos**：提供用于存储配置和其他元数据的 key/value 存储，一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。

* **Nacos Config**：Nacos Config 是 Config Server 和 Client 的替代方案，项目很容易地使用 Nacos Config 来实现应用的外部化配置，实现统一配置与管理。

* **Eureka**：一种基于 REST 的服务，主要用于定位服务，以实现中间层服务器的负载平衡和故障转移。目前 Eureka 已停止维护，建议采用 Alibaba Nacos 替代方案。

* **Seata**：由 Alibaba 团队发起了开源项目，一个易于使用的高性能微服务分布式事务解决方案。

* **Gateway**：Spring Cloud Gateway 是 Spring Cloud 官方推出的第二代网关框架，取代Zuul网关。网关作为对外的门户，在微服务系统中有着非常作用，实现动态路由转发、权重路由、断路器、限流、鉴权和黑白名单等功能。

* **Zuul**：Spring Cloud Netflix Zuul 是由 Netflix 开源的 API 网关，目前还在继续维护。

* **Jasypt**：提供对配置文件安全加密和管理。

* **Sleuth & Zikpin**：提供全链路监控，跟踪分布式服务间的应用数据链路。（未支持）

* **Apache Skywalking**：提供全链路监控，跟踪分布式服务间的应用数据链路。（已支持）

### 组织结构

    spring-cloud
    ├── mesh-api -- openFeign 相关接口 api
    ├── mesh-auth-server -- 认证系统
    ├── mesh-boot-admin -- 服务健康与监控系统
    ├── mesh-common -- 工具类及通用代
    ├── mesh-docs -- 项目文档信息
    ├── mesh-demo -- 相关框架实例
    ├────── nacos-sample -- 栗子1
    ├────── seata-eureka-sample -- 栗子2
    ├────── seata-ha-sample -- 栗子3
    ├────── seata-nacos-sample -- 栗子4
    ├────── seata-sample -- 栗子5
    ├────── seata-tcc-sample -- 栗子6
    ├────── springboot-sample -- 栗子7
    ├── mesh-entity -- 数据层公共实体类
    ├── mesh-enums -- 枚举公共类
    ├── mesh-eureka -- Eureka 服务注册与发现组件
    ├── mesh-gateway -- SpringCloud Gateway 网关组件
    ├── mesh-gateway-zuul -- SpringCloud Zuul 网关组件
    ├── mesh-generator -- Mybatis 构建自动化
    ├── mesh-model -- 与前端交互视图实体相关，VO、DTO、BTO
    ├── mesh-modules -- 模块集
    ├────── mesh-async-elasticsearch
    ├────── mesh-elasticsearch -- 全文搜索引擎 Elasticsearch
    ├────── mesh-mongodb
    ├────── mesh-cassandra
    ├── mesh-core -- 服务层公共配置信息和组件初始化代码
    ├── mesh-service -- 独立服务父 POM
    ├────── mesh-business-service -- 独立业务服务1
    ├────── mesh-order-service -- 独立业务服务2
    ├────── mesh-storage-service -- 独立业务服务3
    ├────── mesh-user-service -- 独立业务服务4
    ├────── mesh-mybatis-service -- 独立业务服务5
    ├── mesh-skywalking-agent -- 提供全链路监控 agent
    ├── mesh-test -- 框架搭建时的相关测试代码
    ├── sql -- sql 执行语句
    └── style -- 框架代码规范约定

## 服务端口规约
<table>
    <tr>
		<th colspan="3">网关服务（8080-8099）</th>
	<tr>
	<tr>
		<td>服务名称</td>
		<td>服务端口号</td>
		<td>Docker映射端口号</td>
	</tr>
	<tr>
		<td>mesh-gateway</td>
		<td>8080</td>
		<td>8080</td>
	</tr>
	<tr>
		<td>mesh-gateway-zuul</td>
		<td>8090</td>
		<td>8090</td>
	</tr>
	<tr>
		<th colspan="3">认证服务（8010-8019）</th>
	<tr>
	<tr>
		<td>服务名称</td>
		<td>服务端口号</td>
		<td>Docker映射端口号</td>
	</tr>
	<tr>
		<td>mesh-auth-server</td>
		<td>8010</td>
		<td>8010</td>
	</tr>
	<tr>
		<th colspan="3">监控服务（8020-8049）</th>
	<tr>
		<tr>
		<td>服务名称</td>
		<td>服务端口号</td>
		<td>Docker映射端口号</td>
	</tr>
	<tr>
		<td>mesh-boot-admin</td>
		<td>8020</td>
		<td>8020</td>
	</tr>
	<tr>
		<td>mesh-turbine</td>
		<td>8030</td>
		<td>8030</td>
	</tr>
	<tr>
		<td>apache skywalking</td>
		<td>8040</td>
		<td>8040</td>
	</tr>
	<tr>
		<th colspan="3">注册中心服务（8830-8860）</th>
	<tr>
	<tr>
		<td>服务名称</td>
		<td>服务端口号</td>
		<td>Docker映射端口号</td>
	</tr>
	<tr>
		<td>mesh-eureka</td>
		<td>8830</td>
		<td>8830</td>
	</tr>
	<tr>
		<td>alibaba nacos</td>
		<td>8848</td>
		<td>8848</td>
	</tr>
	<tr>
		<th colspan="3">分布式事务服务（8091-8099）</th>
	<tr>
	<tr>
		<td>服务名称</td>
		<td>服务端口号</td>
		<td>Docker映射端口号</td>
	</tr>
	<tr>
		<td>alibaba seata</td>
		<td>8091</td>
		<td>8091</td>
	</tr>
	<tr>
		<th colspan="3">消息中间件MQ服务（8050-8079）</th>
	<tr>
	<tr>
		<td>服务名称</td>
		<td>服务端口号</td>
		<td>Docker映射端口号</td>
	</tr>
	<tr>
        <td>roketmq-name-server</td>
        <td>9876</td>
        <td>9876</td>
    </tr>
    <tr>
        <td>roketmq-broker</td>
        <td>10909、10911</td>
        <td>10909、10911</td>
    </tr>
	<tr>
		<td>mesh-rocketmq-producer</td>
		<td>8050</td>
		<td>8050</td>
	</tr>
	<tr>
		<td>mesh-rocketmq-consumer</td>
		<td>8060</td>
		<td>8060</td>
	</tr>
	<tr>
		<td>mesh-rocketmq-console</td>
		<td>8070</td>
		<td>8070</td>
	</tr>
	<tr>
    	<th colspan="3">全文搜索引擎Elastisearch（8700-8709）</th>
    <tr>
    <tr>
        <td>服务名称</td>
        <td>服务端口号</td>
        <td>Docker映射端口号</td>
    </tr>
    <tr>
        <td>mesh-elastisearch</td>
        <td>8700</td>
        <td>8700</td>
    </tr>
	<tr>
		<th colspan="3">业务服务（8610-8699）</th>
	<tr>
	<tr>
		<td>服务名称</td>
		<td>服务端口号</td>
		<td>Docker映射端口号</td>
	</tr>
	<tr>
		<td>mesh-business-service</td>
		<td>8610</td>
		<td>8610</td>
	</tr>
	<tr>
		<td>mesh-order-service</td>
		<td>8620</td>
		<td>8620</td>
	</tr>
	<tr>
		<td>mesh-storage-service</td>
		<td>8630</td>
		<td>8630</td>
	</tr>
	<tr>
		<td>mesh-user-service</td>
		<td>8640</td>
		<td>8640</td>
	</tr>
	<tr>
		<td>mesh-mybatis-service</td>
		<td>8650</td>
		<td>8650</td>
	</tr>
	<tr>
        <th colspan="3">MongoDB（8710-8719）</th>
    <tr>
    <tr>
        <td>服务名称</td>
        <td>服务端口号</td>
        <td>Docker映射端口号</td>
    </tr>
    <tr>
        <td>mesh-elastisearch</td>
        <td>8710</td>
        <td>8710</td>
    </tr>
</table>

## 版本说明
#### 版本依赖关系
| 技术 | 说明  | 官网 | 
| ------------------ |-------------------|---------------------------------------------------------------------------------------|
| Spring Cloud       | 微服务架构框架      | [https://spring.io/projects/spring-cloud](https://spring.io/projects/spring-cloud)    | 
| Spring Boot        | SpringBoot 框架    | [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)  | 
| Netflix Eureka     | 服务注册与发现      | [https://github.com/Netflix/eureka](https://github.com/Netflix/eureka)            | 
| Alibaba Nacos      | 服务注册与发现      | [https://github.com/alibaba/nacos](https://github.com/alibaba/nacos)          | 
| Nacos Config       | 服务配置中心        | [https://github.com/alibaba/nacos](https://github.com/alibaba/nacos)             | 
| Netflix Eureka     | 服务注册与发现      | [https://github.com/Netflix/eureka](https://github.com/Netflix/eureka)            | 
| Gateway            | 服务网关           | [https://spring.io/projects/spring-cloud](https://spring.io/projects/spring-cloud)  | 
| Zull               | 服务网关           | [https://spring.io/projects/spring-cloud](https://spring.io/projects/spring-cloud)  | 
| Spring Security    | 认证和授权框架      | [https://spring.io/projects/spring-security](https://spring.io/projects/spring-security)  | 
| Oauth2.0           | 认证授权协议规范    | [https://oauth.net/2](https://oauth.net/2)                                             | 
| OpenFeign          | 服务之间调用解决方案 | [https://github.com/OpenFeign/feign](https://github.com/OpenFeign/feign)                | 
| Alibaba Sentinel   | 流量控制、熔断降级   | [https://github.com/alibaba/Sentinel](https://github.com/alibaba/Sentinel)             | 
| Alibaba Seata      | 分布式事务框架      | [https://github.com/seata/seata](https://github.com/seata/seata)                          | 
| Apache Skywalking  | 提供全链路监控      | [https://github.com/apache/skywalking](https://github.com/apache/skywalking)              | 
| Jasypt             | 安全加密和管理      | [https://github.com/ulisesbocchio/jasypt-spring-boot](https://github.com/ulisesbocchio/jasypt-spring-boot)    | 
| MyBatis            | ORM 框架           | [http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)          | 
| MyBatisPlus        | ORM 增强工具        | [https://mp.baomidou.com/](https://mp.baomidou.com/)           | 
| MyBatisGenerator   | 数据层代码生成       | [http://www.mybatis.org/generator/index.html](http://www.mybatis.org/generator/index.html)           | 
| PageHelper         | MyBatis 物理分页插件 | [http://git.oschina.net/free/Mybatis_PageHelper](http://git.oschina.net/free/Mybatis_PageHelper)        | 
| Swagger-UI         | 文档生产工具        | [https://github.com/swagger-api/swagger-ui](https://github.com/swagger-api/swagger-ui)               | 
| Redis              | 分布式缓存          |[https://redis.io/](https://redis.io/)                                       | 
| Druid              | 数据库连接池        |[https://github.com/alibaba/druid](https://github.com/alibaba/druid)          | 
| JWT                | JWT 登录支持       | [https://github.com/auth0/java-jwt](https://github.com/auth0/java-jwt)      | 
| logback            | 日志收集           | [https://github.com/logstash/logstash-logback-encoder](https://github.com/logstash/logstash-logback-encoder)    | 
| Lombok             | 简化对象封装工具    | [https://github.com/rzwitserloot/lombok](https://github.com/rzwitserloot/lombok)       | 

## 架构图

##### 系统架构图

![系统架构图](mesh-docs/src/main/asciidoc-zh/images/architectural-design.png)

##### 业务架构图
TDB

## 如何构建

* 项目采用 Spring Cloud Greenwich，最低支持 JDK 1.8。
* Maven：3.3.9+
* Mysql：5.7+
* Redis：3.2+

Spring Cloud 使用 Maven 来构建，最快的使用方式将本项目执行以下命令：

	./mvnw clean install

执行完毕后，项目将被安装到本地 Maven 仓库。

## 运行

启动各服务即可，访问各服务查看对应的配置文件，格式：

    spring.application.name:spring.port

参考文档 查看 [WIKI](https://github.com/romeoblog/spring-cloud/wiki) 

## 联系方式

#### 邮箱：
zheng_weilu@163.com

#### 微信：
![个人微信](mesh-docs/src/main/name.jpeg)

## 许可证

[Apache License 2.0](https://github.com/romeoblog/spring-cloud/blob/master/LICENSE)

Copyright 2019 https://github.com/romeoblog/spring-cloud.git Group.