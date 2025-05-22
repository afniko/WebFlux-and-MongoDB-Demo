FROM openjdk:11
MAINTAINER afniko@gmail.com
ADD build/libs/flux-demo-1.0-SNAPSHOT.jar /usr/src/flux-demo-1.0-SNAPSHOT.jar
WORKDIR usr/src

ENTRYPOINT ["java","-jar", "flux-demo-1.0-SNAPSHOT.jar"]
EXPOSE 8780
