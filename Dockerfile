# Build stage build the jar with all our resources
FROM maven:3.5-jdk-8 as builder
#FROM maven:3.5-jdk-7 as builder

VOLUME /tmp
WORKDIR /tmp
ADD . .
RUN mvn clean package

# Package & run
FROM java:8
#FROM java:7

# copy only the built jar and nothing else
COPY --from=builder /tmp/target/*-SNAPSHOT.jar /memory-consumer.jar
WORKDIR /
CMD java $JVM_OPTS -jar memory-consumer.jar
