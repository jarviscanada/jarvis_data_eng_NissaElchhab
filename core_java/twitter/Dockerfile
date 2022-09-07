FROM openjdk:8-alpine
COPY target/TwitterCLI*.jar /usr/local/app/twitter_cli/lib/twittercli.jar
ENTRYPOINT ["java","-jar","/usr/local/app/twitter_cli/lib/twittercli.jar"]