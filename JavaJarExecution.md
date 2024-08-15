## Commands
To Execute Spring boot Jar.
```cmd
java -cp  C:\jar-path\your-jar-1.2.0.jar -Dloader.main=package-and-main class  -Dloader.path=external dependency jar path  org.springframework.boot.loader.PropertiesLauncher -Dspring.profiles.active=profile etc -default,test --spring.config.location=external properties file name
```
for more info [Spring Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config)

```shell
To Compile:
cmd>javac -cp <classpath for zip/jar files> Main.java
To Run:
cmd>java -cp <classpath for zip/jar files>; Main
```
**NOTE:** The **;** is used to add multiple lib locations.
```cmd
cmd> javac -cp ./* Main.java
cmd> java -cp ./*; Main
```
**To create a executable JAR from CLI**
```shell
cd src
javac com/example/*.java
echo "Manifest-Version: 1.0" > manifest.txt
echo "Main-Class: com.example.MainClass" >> manifest.txt
jar cfm MyApp.jar manifest.txt com/example/*.class
```
**To create a library JAR from CLI**
```shell
cd src
javac com/example/*.java
jar cf MyApp.jar com/example/*.class
(or)
jar cf MyApp.jar *.class
```
