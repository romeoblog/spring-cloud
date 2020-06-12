#!/bin/bash
#<!--
#  ~  Copyright 2019 https://github.com/romeoblog/spring-cloud.git Group.
#  ~
#  ~  Licensed under the Apache License, Version 2.0 (the "License");
#  ~  you may not use this file except in compliance with the License.
#  ~  You may obtain a copy of the License at
#  ~
#  ~       http://www.apache.org/licenses/LICENSE-2.0
#  ~
#  ~  Unless required by applicable law or agreed to in writing, software
#  ~  distributed under the License is distributed on an "AS IS" BASIS,
#  ~  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  ~  See the License for the specific language governing permissions and
#  ~  limitations under the License.
#  -->
SpringBoot=$2

if [ "$1" = "" ];
then
    echo -e "\033[0;31m 未输入操作名 \033[0m  \033[0;34m {start|stop|restart|status} \033[0m"
    exit 1
fi

if [ "$SpringBoot" = "" ];
then
    echo -e "\033[0;31m 未输入应用名 \033[0m"
    exit 1
fi

function start()
{
    count=`ps -ef |grep java|grep $SpringBoot|grep -v grep|wc -l`
    if [ $count != 0 ];then
        echo "$SpringBoot is running..."
    else
        echo "Start $SpringBoot success..."
        nohup java -jar $SpringBoot > /dev/null 2>&1 &
    fi
}

function stop()
{
    echo "Stop $SpringBoot"
    boot_id=`ps -ef |grep java|grep $SpringBoot|grep -v grep|awk '{print $2}'`
    count=`ps -ef |grep java|grep $SpringBoot|grep -v grep|wc -l`

    if [ $count != 0 ];then
        kill $boot_id
        count=`ps -ef |grep java|grep $SpringBoot|grep -v grep|wc -l`

        boot_id=`ps -ef |grep java|grep $SpringBoot|grep -v grep|awk '{print $2}'`
        kill -9 $boot_id
    fi
}

function restart()
{
    stop
    sleep 2
    start
}

function status()
{
    count=`ps -ef |grep java|grep $SpringBoot|grep -v grep|wc -l`
    if [ $count != 0 ];then
        echo "$SpringBoot is running..."
    else
        echo "$SpringBoot is not running..."
    fi
}

case $1 in
    start)
    start;;
    stop)
    stop;;
    restart)
    restart;;
    status)
    status;;
    *)

    echo -e "\033[0;31m Usage: \033[0m  \033[0;34m sh  $0  {start|stop|restart|status}  {SpringBootJarName} \033[0m
\033[0;31m Example: \033[0m
      \033[0;33m sh  $0  start esmart-test.jar \033[0m"
esac