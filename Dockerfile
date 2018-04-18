FROM java
MAINTAINER Manoel Rodrigo Farinha de Medeiros <manorfm@hotmail.com>

ENV DIR_BASE=/usr/app

RUN mkdir -p $DIR_BASE

ADD ./main/target/main-1.0.0.war $DIR_BASE/clock.war

WORKDIR $DIR_BASE
#RUN java -jar clock.war

CMD ["java", "-jar", "clock.war"]