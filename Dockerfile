#FROM tifayuki/java:8
FROM pedroxs/drools-rules:0.1.0

COPY target /opt/consumer

RUN cd /opt/consumer && ln -s drools-jar-consumer*.jar drools-jar-consumer.jar

EXPOSE 8080

CMD ["java", "-jar", "/opt/consumer/drools-jar-consumer.jar"]
