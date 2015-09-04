#!/bin/bash

docker stop sample-drools
docker rm sample-drools
docker run -d -m 2048M --name sample-drools \
    -v $(pwd)/target:/opt/sample \
    -v /home/pedroxs/work/examples/shipping-rules/target/:/opt/rules:ro \
    tifayuki/java:8 java -jar /opt/sample/drools-jar-consumer-0.0.1-SNAPSHOT.jar
