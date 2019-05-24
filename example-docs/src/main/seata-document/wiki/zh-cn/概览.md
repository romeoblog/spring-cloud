Fescar 是 **阿里巴巴** 开源的 **分布式事务**中间件，以 **高效** 并且对业务 **0 侵入** 的方式，解决 **微服务** 场景下面临的分布式事务问题。

# 1. 什么是微服务化带来的分布式事务问题？

首先，设想一个传统的单体应用（Monolithic App），通过 3 个 Module，在同一个数据源上更新数据来完成一项业务。

很自然的，整个业务过程的数据一致性由本地事务来保证。

![Monolithic architecture](https://upload-images.jianshu.io/upload_images/4420767-f3cc9912798e8d1f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

随着业务需求和架构的变化，单体应用被拆分为微服务：原来的 3 个 Module 被拆分为 3 个独立的服务，分别使用独立的数据源（[Pattern: Database per service](http://microservices.io/patterns/data/database-per-service.html)）。业务过程将由 3 个服务的调用来完成。

![Microservices architecture](https://upload-images.jianshu.io/upload_images/4420767-e1982baac66726b5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

此时，每一个服务内部的数据一致性仍由本地事务来保证。而整个业务层面的全局数据一致性要如何保障呢？这就是微服务架构下面临的，典型的分布式事务需求：我们需要一个分布式事务的解决方案保障业务全局的数据一致性。

![Fescar Solution](https://upload-images.jianshu.io/upload_images/4420767-2525b9e4b6bc21c6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 2. Fescar 的发展历程

阿里是国内最早一批进行应用分布式（微服务化）改造的企业，所以很早就遇到微服务架构下的分布式事务问题。

2014 年，阿里中间件团队发布 **TXC（Taobao Transaction Constructor）**，为集团内应用提供分布式事务服务。

2016 年，TXC 经过产品化改造，以 **GTS（Global Transaction Service）** 的身份登陆阿里云，成为当时业界唯一一款云上分布式事务产品，在阿云里的公有云、专有云解决方案中，开始服务于众多外部客户。

2019 年起，基于 TXC 和 GTS 的技术积累，阿里中间件团队发起了开源项目 **Fescar（Fast & EaSy Commit And Rollback, FESCAR）**，和社区一起建设这个分布式事务解决方案。

TXC/GTS/Fescar 一脉相承，为解决微服务架构下的分布式事务问题交出了一份与众不同的答卷。

## 2.1 设计初衷

高速增长的互联网时代，**快速试错** 的能力对业务来说是至关重要的：

- 一方面，不应该因为技术架构上的微服务化和分布式事务支持的引入，给业务层面带来额外的研发负担。
- 另一方面，引入分布式事务支持的业务应该基本保持在同一量级上的性能表现，不能因为事务机制显著拖慢业务。

基于这两点，我们设计之初的最重要的考量就在于：

- **对业务无侵入：** 这里的 **侵入** 是指，因为分布式事务这个技术问题的制约，要求应用在业务层面进行设计和改造。这种设计和改造往往会给应用带来很高的研发和维护成本。我们希望把分布式事务问题在 **中间件** 这个层次解决掉，不要求应用在业务层面做额外的工作。
- **高性能：** 引入分布式事务的保障，必然会有额外的开销，引起性能的下降。我们希望把分布式事务引入的性能损耗降到非常低的水平，让应用不因为分布式事务的引入导致业务的可用性受影响。

## 2.2 既有的解决方案为什么不满足？

既有的分布式事务解决方案按照对业务侵入性分为两类，即：对业务无侵入的和对业务有侵入的。

### 业务无侵入的方案

既有的主流分布式事务解决方案中，对业务无侵入的只有基于 XA 的方案，但应用 XA 方案存在 3 个方面的问题：

1. 要求数据库提供对 XA 的支持。如果遇到不支持 XA（或支持得不好，比如 MySQL 5.7 以前的版本）的数据库，则不能使用。
2. 受协议本身的约束，事务资源（数据记录、数据库连接）的锁定周期长。长周期的资源锁定从业务层面来看，往往是不必要的，而因为事务资源的管理器是数据库本身，应用层无法插手。这样形成的局面就是，基于 XA 的应用往往性能会比较差，而且很难优化。
3. 已经落地的基于 XA 的分布式解决方案，都依托于重量级的应用服务器（Tuxedo/WebLogic/WebSphere 等)，这是不适用于微服务架构的。

### 侵入业务的方案

实际上，最初分布式事务只有 XA 这个唯一方案。XA 是完备的，但在实践过程中，由于种种原因（包含但不限于上面提到的 3 点）往往不得不放弃，转而从业务层面着手来解决分布式事务问题。比如：

- 基于可靠消息的最终一致性方案
- TCC
- Saga 

都属于这一类。这些方案的具体机制在这里不做展开，网上这方面的论述文章非常多。总之，这些方案都要求在应用的业务层面把分布式事务技术约束考虑到设计中，通常每一个服务都需要设计实现正向和反向的幂等接口。这样的设计约束，往往会导致很高的研发和维护成本。

## 2.3 理想的方案应该是什么样子？

不可否认，侵入业务的分布式事务方案都经过大量实践验证，能有效解决问题，在各行各业的业务应用系统中起着重要作用。但回到原点来思考，这些方案的采用实际上都是 **迫于无奈**。设想，如果基于 XA 的方案能够不那么 **重**，并且能保证业务的性能需求，相信不会有人愿意把分布式事务问题拿到业务层面来解决。

一个理想的分布式事务解决方案应该：像使用 **本地事务** 一样简单，业务逻辑只关注业务层面的需求，不需要考虑事务机制上的约束。

# 3. 原理和设计

我们要设计一个对业务无侵入的方案，所以从业务无侵入的 XA 方案来思考：

是否可以在 XA 的基础上演进，解决掉 XA 方案面临的问题呢？

### 3.1 如何定义一个分布式事务？

首先，很自然的，我们可以把一个分布式事务理解成一个包含了若干 **分支事务** 的 **全局事务**。**全局事务** 的职责是协调其下管辖的 **分支事务** 达成一致，要么一起成功提交，要么一起失败回滚。此外，通常 **分支事务** 本身就是一个满足 ACID 的 **本地事务**。这是我们对分布式事务结构的基本认识，与 XA 是一致的。

![Global & Branch Transaction](https://upload-images.jianshu.io/upload_images/4420767-8ae056b6bc8752f0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

其次，与 XA 的模型类似，我们定义 3 个组件来协议分布式事务的处理过程。

![FESCAR Model](https://upload-images.jianshu.io/upload_images/4420767-158f1e2289406eea.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- **Transaction Coordinator (TC)：** 事务协调器，维护全局事务的运行状态，负责协调并驱动全局事务的提交或回滚。
- **Transaction Manager (TM)：** 控制全局事务的边界，负责开启一个全局事务，并最终发起全局提交或全局回滚的决议。
- **Resource Manager (RM)：** 控制分支事务，负责分支注册、状态汇报，并接收事务协调器的指令，驱动分支（本地）事务的提交和回滚。

一个典型的分布式事务过程：

1. TM 向 TC 申请开启一个全局事务，全局事务创建成功并生成一个全局唯一的 XID。
2. XID 在微服务调用链路的上下文中传播。
3. RM 向 TC 注册分支事务，将其纳入 XID 对应全局事务的管辖。 
4. TM 向 TC 发起针对 XID 的全局提交或回滚决议。
5. TC 调度 XID 下管辖的全部分支事务完成提交或回滚请求。

![Architecture](https://upload-images.jianshu.io/upload_images/4420767-494a54d402b2d354.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

至此，Fescar 的协议机制总体上看与 XA 是一致的。

### 3.2 与 XA 的差别在什么地方？

#### 架构层次

![RM in Architecture](https://upload-images.jianshu.io/upload_images/4420767-a4c9e542fec10c39.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

XA 方案的 RM 实际上是在数据库层，RM 本质上就是数据库自身（通过提供支持 XA 的驱动程序来供应用使用）。

而 Fescar 的 RM 是以二方包的形式作为中间件层部署在应用程序这一侧的，不依赖与数据库本身对协议的支持，当然也不需要数据库支持 XA 协议。这点对于微服务化的架构来说是非常重要的：应用层不需要为本地事务和分布式事务两类不同场景来适配两套不同的数据库驱动。

这个设计，剥离了分布式事务方案对数据库在 **协议支持** 上的要求。

#### 两阶段提交

先来看一下 XA 的 2PC 过程。

![XA-2PC](https://upload-images.jianshu.io/upload_images/4420767-6d2dc1354c7317e8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

无论 Phase2 的决议是 commit 还是 rollback，事务性资源的锁都要保持到 Phase2 完成才释放。

设想一个正常运行的业务，大概率是 90% 以上的事务最终应该是成功提交的，我们是否可以在 Phase1 就将本地事务提交呢？这样 90% 以上的情况下，可以省去 Phase2 持锁的时间，整体提高效率。

![Seata-2PC](https://upload-images.jianshu.io/upload_images/4420767-f2b3a01d38167bdf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 分支事务中数据的 **本地锁** 由本地事务管理，在分支事务 Phase1 结束时释放。
- 同时，随着本地事务结束，**连接** 也得以释放。
- 分支事务中数据的 **全局锁** 在事务协调器侧管理，在决议 Phase2 全局提交时，全局锁马上可以释放。只有在决议全局回滚的情况下，**全局锁** 才被持有至分支的 Phase2 结束。

这个设计，极大地减少了分支事务对资源（数据和连接）的锁定时间，给整体并发和吞吐的提升提供了基础。

当然，你肯定会问：Phase1 即提交的情况下，Phase2 如何回滚呢？

## 3.3 分支事务如何提交和回滚？

首先，应用需要使用 Fescar 的 JDBC 数据源代理，也就是 Fescar 的 RM。

![Data Source Proxy](https://upload-images.jianshu.io/upload_images/4420767-e812bdf8e6ee8b4a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**Phase1：**

Fescar 的 JDBC 数据源代理通过对业务 SQL 的解析，把业务数据在更新前后的数据镜像组织成回滚日志，利用 **本地事务** 的 ACID 特性，将业务数据的更新和回滚日志的写入在同一个 **本地事务** 中提交。

这样，可以保证：任何提交的业务数据的更新一定有相应的回滚日志存在。

![Branch Transaction with UNDO LOG](https://upload-images.jianshu.io/upload_images/4420767-37fe46ac70573cfe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


基于这样的机制，分支的本地事务便可以在全局事务的 Phase1 提交，马上释放本地事务锁定的资源。

**Phase2：**

- 如果决议是全局提交，此时分支事务此时已经完成提交，不需要同步协调处理（只需要异步清理回滚日志），Phase2 可以非常快速地完成。

![Global Commit](https://upload-images.jianshu.io/upload_images/4420767-1c7a65ba1cdceb5f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- 如果决议是全局回滚，RM 收到协调器发来的回滚请求，通过 XID 和 Branch ID 找到相应的回滚日志记录，通过回滚记录生成反向的更新 SQL 并执行，以完成分支的回滚。

![Global Rollback](https://upload-images.jianshu.io/upload_images/4420767-627bafaf92c13005.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 3.4 事务传播机制

XID 是一个全局事务的唯一标识，事务传播机制要做的就是把 XID 在服务调用链路中传递下去，并绑定到服务的事务上下文中，这样，服务链路中的数据库更新操作，就都会向该 XID 代表的全局事务注册分支，纳入同一个全局事务的管辖。

基于这个机制，Fescar 是可以支持任何微服务 RPC 框架的。只要在特定框架中找到可以透明传播 XID 的机制即可，比如，Dubbo 的 Filter + RpcContext。

对应到 Java EE 规范和 Spring 定义的事务传播属性，Fescar 的支持如下：

- **PROPAGATION_REQUIRED：** 默认支持
- **PROPAGATION_SUPPORTS：** 默认支持
- PROPAGATION_MANDATORY：应用通过 API 来实现
- PROPAGATION_REQUIRES_NEW：应用通过 API 来实现
- PROPAGATION_NOT_SUPPORTED：应用通过 API 来实现
- PROPAGATION_NEVER：应用通过 API 来实现
- PROPAGATION_NESTED：不支持

## 3.5 隔离性

全局事务的隔离性是建立在分支事务的本地隔离级别基础之上的。

在数据库本地隔离级别 **读已提交** 或以上的前提下，Fescar 设计了由事务协调器维护的 **全局写排他锁**，来保证事务间的 **写隔离**，将全局事务默认定义在 **读未提交** 的隔离级别上。

我们对隔离级别的共识是：微服务场景产生的分布式事务，绝大部分应用在 **读已提交** 的隔离级别下工作是没有问题的。而实际上，这当中又有绝大多数的应用场景，实际上工作在 **读未提交** 的隔离级别下同样没有问题。

在极端场景下，应用如果需要达到全局的 **读已提交**，Fescar 也提供了相应的机制来达到目的。默认，Fescar 是工作在 **读未提交** 的隔离级别下，保证绝大多数场景的高效性。

![Isolation](https://upload-images.jianshu.io/upload_images/4420767-c31988ca4daec2dd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

事务的 ACID 属性在 Fescar 中的体现是一个比较复杂的话题，我们会有专门的文章来深入分析，这里不做进一步展开。


# 4. 适用场景分析

前文所述的 Fescar 的核心原理中有一个 **重要前提**：分支事务中涉及的资源，**必须** 是支持 **ACID 事务**的 **关系型数据库**。分支的提交和回滚机制，都依赖于本地事务的保障。所以，如果应用使用的数据库是不支持事务的，或根本不是关系型数据库，就不适用。

另外，目前 Fescar 的实现还存在一些局限，比如：事务隔离级别最高支持到 **读已提交** 的水平，SQL 的解析还不能涵盖全部的语法等。

为了覆盖 Fescar 原生机制暂时不能支持应用场景，我们定义了另外一种工作模式。

上面介绍的 Fescar 原生工作模式称为 AT（Automatic Transaction）模式，这种模式是对业务无侵入的。与之相应的另外一种工作模式称为 MT（Manual Transaction）模式，这种模式下，分支事务需要应用自己来定义业务本身及提交和回滚的逻辑。



## 4.1 分支的基本行为模式

作为全局事务一部分的分支事务，除本身的业务逻辑外，都包含 4 个与协调器交互的行为：

- **分支注册：** 在分支事务的数据操作进行之前，需要向协调器注册，把即将进行的分支事务数据操作，纳入一个已经开启的全局事务的管理中去，在分支注册成功后，才可以进行数据操作。
- **状态上报：** 在分支事务的数据操作完成后，需要向事务协调器上报其执行结果。
- **分支提交**：响应协调器发出的分支事务提交的请求，完成分支提交。
- **分支回滚**：响应协调器发出的分支事务回滚的请求，完成分支回滚。


![How does RM talk to TC](https://upload-images.jianshu.io/upload_images/4420767-de4ebd2e967ca641.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 4.2 AT 模式分支的行为模式

业务逻辑不需要关注事务机制，分支与全局事务的交互过程自动进行。

![AT branch](https://upload-images.jianshu.io/upload_images/4420767-93e528feff502597.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 4.3 MT 模式分支的行为模式

业务逻辑需要被分解为 Prepare/Commit/Rollback 3 部分，形成一个 MT 分支，加入全局事务。

![MT branch](https://upload-images.jianshu.io/upload_images/4420767-7d4de205558a35b2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

MT 模式一方面是 AT 模式的补充。另外，更重要的价值在于，通过 MT 模式可以把众多非事务性资源纳入全局事务的管理中。

## 4.4 混合模式

因为 AT 和 MT 模式的分支从根本上行为模式是一致的，所以可以完全兼容，即，一个全局事务中，可以同时存在 AT 和 MT 的分支。这样就可以达到全面覆盖业务场景的目的：AT 模式可以支持的，使用 AT 模式；AT 模式暂时支持不了的，用 MT 模式来替代。另外，自然的，MT 模式管理的非事务性资源也可以和支持事务的关系型数据库资源一起，纳入同一个分布式事务的管理中。

## 4.5 应用场景的远景

回到我们设计的初衷：一个理想的分布式事务解决方案是不应该侵入业务的。MT 模式是在 AT 模式暂时不能完全覆盖所有场景的情况下，一个比较自然的补充方案。我们希望通过 AT 模式的不断演进增强，逐步扩大所支持的场景，MT 模式逐步收敛。未来，我们会纳入对 XA 的原生支持，用 XA 这种无侵入的方式来覆盖 AT 模式无法触达的场景。

![Roadmap of Transaction Mode](https://upload-images.jianshu.io/upload_images/4420767-56c21f500c3b40b6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



# 5. 扩展点

## 5.1 微服务框架的支持

事务上下文在微服务间的传播需要根据微服务框架本身的机制，订制最优的，对应用层透明的解决方案。有兴趣在这方面共建的开发者可以参考内置的对 Dubbo 的支持方案，来实现对其他微服务框架的支持。

## 5.2 所支持的数据库类型

因为 AT 涉及 SQL 的解析，所以在不同类型的数据库上工作，会有一些特定的适配。有兴趣在这方面共建的开发者可以参考内置的对 MySQL 的支持方案，来实现对其他数据库的支持。

## 5.3 配置和服务注册发现

支持接入不同的配置和服务注册发现解决方案。比如：Nacos、Eureka、ZooKeeper 等。

## 5.4 MT 模式的场景拓展

MT 模式的一个重要作用就是，可以把非关系型数据库的资源，通过 MT 模式分支的包装，纳入到全局事务的管辖中来。比如，Redis、HBase、RocketMQ 的事务消息等。有兴趣在这方面共建的开发者可以在这里贡献一系列相关生态的适配方案。

## 5.5 事务协调器的分布式高可用方案

针对不同场景，支持不同的方式作为事务协调器 Server 端的高可用方案。比如，针对事务状态的持久化，可以是基于文件的实现方案，也可以是基于数据库的实现方案；集群间的状态同步，可以是基于 RPC 通信的方案，也可以是基于高可用 KV 存储的方案。

# 6. Roadmap

## Lanscape

![Landscape](https://upload-images.jianshu.io/upload_images/4420767-9500ecbd9a6a865b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



**The green** part is already open sourced, **the yellow** part will open source by Alibaba/AntFinancial, **the blue** part we want co-building with out community:

- Developers can refer to Seata implementation of MySQL support if you want to support different databases transaction
- Developers can refer to Seata implementation of Dubbo support if you want to support different microservices
- Developers can refer to Seata implementation of TCC support if you want to support different data source(such as MQ, NoSQL)
- Developers can refer to Seata implementation of TCC support if you want to support different data source(such as MQ, NoSQL)
- Developers can easily support configuration/registry services with just a little work
- **The blue** part is warmly welcome you, join it and contribute excellent solution
- We will support XA which is the standard of distributed transaction in our product roadmap

## Roadmap


### v0.1.0

- Microservice framework support: Dubbo
- Database support: MySQL
- Spring AOP annotation Support
- Transaction coordinator: Stand-alone Server


### v0.5.x

- Rename Fescar to Seata
- Microservice framework support: SOFA, Spring Cloud
- Support TCC(Try Confirm Cancel) transaction mode
- Dynamic configuration
- Services discovery

### v0.6.x

- Transaction coordinator: Cluster Server with HA

### v0.8.x

- Promethus support
- Management console: Monitor, Deployment, Upgrating, etc.

### v1.0.0

- Production ready

### v1.5.x

- Database support: Oracle, PostgreSQL, OceanBase
- Optimization of conflict datas
- Independent of Spring Annotation
- Multiple source of data transaction support: MessageQueue(RocketMQ), HBase, Redis, etc.

### v2.0.0

- XA transaction mode support


当然，项目迭代演进的过程，我们最重视的是社区的声音，路线图会和社区充分交流及时进行调整。

# 7. 相关链接

- [FESCAR on GitHub](https://github.com/alibaba/fescar)
- [GTS on Aliyun](https://help.aliyun.com/product/48444.html?spm=5176.doc55547.3.1.Gg1hcs)