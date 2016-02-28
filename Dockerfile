FROM iron/java:1.8
MAINTAINER Jirka Penzes <jirkapenzes@gmail.com>

ENV VIRTUAL_HOST="www.jirkovocoffee.cz,jirkovocoffee.cz"

WORKDIR /web
COPY resources resources
COPY target/jirkovocoffee-0.1.0-SNAPSHOT-standalone.jar /web/

EXPOSE 8082

CMD ["java", "-jar", "jirkovocoffee-0.1.0-SNAPSHOT-standalone.jar"]
