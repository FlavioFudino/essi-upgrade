FROM public.ecr.aws/amazoncorretto/amazoncorretto:8

RUN yum install unzip -y
RUN curl -o daemon.zip https://s3.us-east-2.amazonaws.com/aws-xray-assets.us-east-2/xray-daemon/aws-xray-daemon-linux-3.x.zip && \
    unzip daemon.zip && cp xray /usr/bin/xray && rm daemon.zip

WORKDIR /app

ADD target/essi-client-0.0.1-CLIENT-SNAPSHOT.jar /app/app.jar
COPY entrypoint.sh /

EXPOSE 80
ENTRYPOINT ["/entrypoint.sh"]
