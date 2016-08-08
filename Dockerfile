FROM java:8-jdk

RUN mkdir -p /home/thiago

# Copy project and change to workdir
COPY ./thiagosc /home/thiago
COPY ./build/libs/springmvc-portfolio-website-1.0.jar /home/thiago/app.jar
WORKDIR /home/thiago

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]
