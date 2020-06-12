#!/usr/bin/env bash
while getopts ":h:p:g:t:" opt
do
  case $opt in
  h)
    host=$OPTARG
    ;;
  p)
    port=$OPTARG
    ;;
  g)
    group=$OPTARG
    ;;
  t)
    tenant=$OPTARG
    ;;
  ?)
    echo "\033[31m USAGE OPTION: $0 [-h host] [-p port] [-g group] [-t tenant] \033[0m"
    exit 1
    ;;
  esac
done

if [[ -z ${host} ]]; then
    host=localhost
fi
if [[ -z ${port} ]]; then
    port=8848
fi
if [[ -z ${group} ]]; then
    group="SEATA_GROUP"
fi
if [[ -z ${tenant} ]]; then
    tenant=""
fi

nacosAddr=$host:$port
contentType="content-type:application/json;charset=UTF-8"

echo "set nacosAddr=$nacosAddr"
echo "set group=$group"

failCount=0
tempLog=$(mktemp -u)
function addConfig() {
  curl -X POST -H "${1}" "http://$2/nacos/v1/cs/configs?dataId=$3&group=$group&content=$4&tenant=$tenant" >"${tempLog}" 2>/dev/null
  if [[ -z $(cat "${tempLog}") ]]; then
    echo "\033[31m Please check the cluster status. \033[0m"
    exit 1
  fi
  if [[ $(cat "${tempLog}") =~ "true" ]]; then
    echo "Set $3=$4\033[32m successfully \033[0m"
  else
    echo "Set $3=$4\033[31m failure \033[0m"
    (( failCount++ ))
  fi
}

count=0
for line in $(cat $(dirname "$PWD")/nacos-config.txt); do
  (( count++ ))
	key=${line%%=*}
  value=${line#*=}
	addConfig "${contentType}" "${nacosAddr}" "${key}" "${value}"
done

echo "========================================================================="
echo " Complete initialization parameters, \033[32m total-count:$count \033[0m, \033[31m failure-count:$failCount \033[0m"
echo "========================================================================="

if [[ ${failCount} -eq 0 ]]; then
	echo "\033[32m Init nacos config finished, please start seata-server. \033[0m"
else
	echo "\033[31m init nacos config fail. \033[0m"
fi