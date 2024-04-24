FROM tomcat:9.0.87-jdk11

COPY target/DAPP01Practica05-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps

RUN chmod 644 /usr/local/tomcat/webapps/DAPP01Practica05-0.0.1-SNAPSHOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
