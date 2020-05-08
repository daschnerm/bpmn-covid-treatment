FROM anapsix/alpine-java
VOLUME /tmp
EXPOSE 8080
EXPOSE 8081
ADD ./target/ApplicationApprovalProcess.jar /app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENV APP_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar $APP_OPTS" ]