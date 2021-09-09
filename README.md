# URL-Shortener

## Overview
This application is developed using Spring (Java 8) and PostgreSQL database.
It is deployed on AWS Elastic BeanStalk and is accessible via the link: https://smolurl.net/

## Set Up (local)
1. Clone the code to your local machine.
2. Ensure that your JAVA_HOME environment has been set and pointing to your your Java JDK. 
  MacOS example (Terminal, vim ~/.bash_profile): 
    ```
    export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.7.jdk/Contents/Home
    ```
3. Ensure that you have maven installed (eg. run mvn -v does not return an error and shows you a version of your maven). 
   Else, set up mvn (MacOS)
   ```
   brew install maven
   ```
4. Ensure that you have postgresql installed. (eg. run psql on terminal)
    Else, set up postgresql (MacOS) via terminal below. Alternatively, you can download it from their site at https://www.postgresql.org/download/
    ```
    brew install postgresql
    ```
5. Open the source code and ensure that the JDK is set up for the project in your IDE.
6. At the root directory, run `maven clean install` to download all dependencies and build the executable JAR.
7. To run the application locally, update the user and password of your postgres DB in application.properties and create a Database table called `urlshortener` in your local postgres. 
    Example: 
    ```
    CREATE DATABASE urlshortener;
    ```
8. Then, run the application using `java -jar target/urlshortener-0.0.1-SNAPSHOT.jar`.
9. Finally, you can access the app at http://localhost:5000/

