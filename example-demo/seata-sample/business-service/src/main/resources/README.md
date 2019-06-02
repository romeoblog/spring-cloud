**准备工作**

执行数据库脚本

启动fescar server 使用默认配置

启动启动business、storage、account、order 数据库默认连接127.0.0.1:3306，不同的注意修改

事务成功 GET 127.0.0.1:8084/purchase?userId=1001&commodityCode=2001&orderCount=1

事务回滚 GET 127.0.0.1:8084/purchase?userId=1001&commodityCode=2001&orderCount=5