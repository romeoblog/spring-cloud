# Download and unzip Elasticsearch

1. https://www.elastic.co/downloads/elasticsearch

2. Run bin/elasticsearch (or bin\elasticsearch.bat on Windows)

3. Run curl http://localhost:9200/ or Invoke-RestMethod http://localhost:9200 with PowerShell

# Download and unzip Kibana

1. https://www.elastic.co/downloads/kibana

2. Open config/kibana.yml in an editor

3. Set elasticsearch.hosts to point at your Elasticsearch instance

4. Run bin/kibana (or bin\kibana.bat on Windows)

5. Point your browser at http://localhost:5601


# Installing Logstash

## To check your Java version, run the following command:
    
    java -version

## Installing from a Downloaded Binary

https://www.elastic.co/downloads/logstash

Download the Logstash installation file for your host environment—​TARG.GZ, DEB, ZIP, or RPM.

## Stashing Your First Event

```bash
    cd ${logstashHome}
    bin/logstash -e 'input { stdin { } } output { stdout {} }'
```
    
After starting Logstash, enter hello world at the command prompt:
 
```bash
    hello world
    2013-11-21T01:22:14.405+0000 0.0.0.0 hello world
```

# 2. Configuration Logstash

## Installing plugin logstash-input-jdbc

```bash
    bin/logstash-plugin logstash-input-jdb
    
    Installation successful.
```

## Installing plugin logstash-output-elasticsearch

```bash
    bin/logstash-plugin logstash-output-elasticsearch
    
    Installation successful.
```

## Downloaded Jdbc Driver Library

https://dev.mysql.com/downloads/connector/j/

mv mysql-connector-java-*.jar ${logstashHome}/config


# 3. Startup
```bash
    nohup bin/logstash -f config/jdbc.conf --config.reload.automatic  &
```

