# Example using MS Build of OpenJDK image directly
FROM mcr.microsoft.com/openjdk/jdk:11-ubuntu

LABEL maintainer="lipe.ferreira1609@hotmail.com"

ENV LANG C.UTF-8

CMD java -jar /app/app.jar

# Continue with your application deployment
RUN mkdir /opt/app

ADD build/libs/beerstore*SNAPSHOT.jar /opt/app/app.jar

CMD ["java", "-jar", "/opt/app/app.jar"]