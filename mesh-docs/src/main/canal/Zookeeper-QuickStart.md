##  环境版本
* 操作系统：CentOS release 6.6 (Final)
* java版本: jdk1.8
* zookeeper版本: zookeeper-3.4.11

##  一、 安装jdk
```
此处省略
```
##  二、 安装zookeeper
### 2.1 下载源码包，并解压
官网下载地址：`http://www.apache.org/dyn/closer.cgi/zookeeper`
```
wget http://mirror.olnevhost.net/pub/apache/zookeeper/zookeeper-3.4.11/zookeeper-3.4.11.tar.gz
tar zxvf zookeeper-3.4.11.tar.gz  
mv zookeeper-3.4.11 /usr/local/zookeeper
```
### 2.2 修改环境变量
编辑 /etc/profile 文件, 在文件末尾添加以下环境变量配置:
```
# ZooKeeper Env
export ZOOKEEPER_HOME=/usr/local/zookeeper
export PATH=$PATH:$ZOOKEEPER_HOME/bin
```
运行以下命令使环境变量生效:
` source /etc/profile `
### 2.3 重命名配置文件
初次使用 ZooKeeper 时,需要将$ZOOKEEPER_HOME/conf 目录下的 zoo_sample.cfg 重命名为 zoo.cfg, zoo.cfg 
```
mv  $ZOOKEEPER_HOME/conf/zoo_sample.cfg $ZOOKEEPER_HOME/conf/zoo.cfg
```
### 2.4 单机模式--修改配置文件
创建目录`/usr/local/zookeeper/data` 和`/usr/local/zookeeper/logs`
修改配置文件
```
tickTime=2000
initLimit=10
syncLimit=5
dataDir=/usr/local/zookeeper/data
dataLogDir=/usr/local/zookeeper/logs
clientPort=2181
```
如果是多节点,配置文件中尾部增加
```
server.1=192.168.1.110:2888:3888
server.2=192.168.1.111:2888:3888
server.3=192.168.1.112:2888:3888
```
同时,增加
```
#master
echo "1">/usr/local/zookeeper/data/myid

#slave1
echo "2">/usr/local/zookeeper/data/myid

#slave2
echo "3">/usr/local/zookeeper/data/myid

```
### 2.5 启动 ZooKeeper 服务
```
# cd /usr/local/zookeeper/zookeeper-3.4.11/bin
# ./zkServer.sh  start
ZooKeeper JMX enabled by default
Using config: /usr/local/zookeeper/zookeeper-3.4.11/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED

zkServer.sh  status
ZooKeeper JMX enabled by default
Using config: /usr/local/zookeeper/bin/../conf/zoo.cfg
Mode: follower
```
### 2.6 验证zooKeeper服务
服务启动完成后，可以使用 telnet 和 stat 命令验证服务器启动是否正常:
```
# telnet 127.0.0.1 2181
Trying 127.0.0.1...
Connected to 127.0.0.1.
Escape character is '^]'.
stat
Zookeeper version: 3.4.11-37e277162d567b55a07d1755f0b31c32e93c01a0, built on 11/01/2017 18:06 GMT
Clients:
/127.0.0.1:48430[0](queued=0,recved=1,sent=0)

Latency min/avg/max: 0/0/0
Received: 1
Sent: 0
Connections: 1
Outstanding: 0
Zxid: 0x0
Mode: standalone
Node count: 4
Connection closed by foreign host.
```
### 2.7 停止 ZooKeeper 服务
想要停止 ZooKeeper 服务, 可以使用如下命令:
```
# cd /usr/local/zookeeper/zookeeper-3.4.11/bin
# ./zkServer.sh  stop
ZooKeeper JMX enabled by default
Using config: /usr/local/zookeeper/zookeeper-3.4.11/bin/../conf/zoo.cfg
Stopping zookeeper ... STOPPED

```
## 三、zk ui安装 (选装，页面查看zk的数据)
### 拉取代码
```
#git clone https://github.com/DeemOpen/zkui.git
```
### 源码编译需要安装 maven
```
# wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
#cd zkui/
#yum install -y maven
#mvn clean install
```
### 修改配置文件默认值
```
#vim config.cfg
    serverPort=9090     #指定端口
    zkServer=192.168.1.110:2181
    sessionTimeout=300000
```
### 启动程序至后台

2.0-SNAPSHOT 会随软件的更新版本不同而不同，执行时请查看target 目录中真正生成的版本
```
 nohup java -jar target/zkui-2.0-SNAPSHOT-jar-with-dependencies.jar & 
```

### 用浏览器访问：
`http://192.168.2.110:9090/`