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
cmd>java -cp <classpath for zip/jar files> Main
```


