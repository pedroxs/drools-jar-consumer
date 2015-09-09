# Drools with dynamic rules

## Tests run:

* [2000 rules and 2000 users in 15 minutes](http://pedroxs.github.io/drools-jar-consumer/results/droolsjarconsumersimulation-1441384352090/index.html)
* [1000 rules and 10000 users in 60 minutes](http://pedroxs.github.io/drools-jar-consumer/results/droolsjarconsumersimulation-1441390352399/index.html)
* [1000 rules and 10000 users in 6 hours](http://pedroxs.github.io/drools-jar-consumer/results/droolsjarconsumersimulation-1441772741753/index.html)


### Run with docker

From the root folder execute the following:

```
mvn clean package

docker run -d --name sample-drools -v $(pwd)/target:/opt/sample -v $(pwd)/src/main/resources/rules/:/opt/rules tifayuki/java:8 java -jar /opt/sample/drools-jar-consumer-0.0.1-SNAPSHOT.jar
```

Discover the container IP:

```
container_ip=`docker inspect --format '{{ .NetworkSettings.IPAddress }}' sample-drools`
```

Use curl to verify:

```
❯ curl "http://${container_ip}:8080/version" -w "\n"
shipping-rules-0.1.0.jar
❯ curl "http://${container_ip}:8080/message" -w "\n"
{"text":"Hello"}
❯ curl "http://${container_ip}:8080/rule?version=2" -w "\n"
Rule version 2 applied!
❯ curl "http://${container_ip}:8080/message" -w "\n"
{"text":"Hello new version!"}
```
