java -jar yourApp.jar [JVM_OPTIONS]

#### For local host access only JVM_OPTIONS are:
```shell
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.port=9010
-Dcom.sun.management.jmxremote.rmi.port=9010
-Dcom.sun.management.jmxremote.host=127.0.0.1
-Dcom.sun.management.jmxremote.local.only=true
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false
```

#### For full remote access JVM_OPTIONS are:
```shell
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.port=9010
-Dcom.sun.management.jmxremote.rmi.port=9010
-Dcom.sun.management.jmxremote.host=192.168.5.121 #remote host IP to access outside
-Dcom.sun.management.jmxremote.local.only=false
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false
```

#### With Security :
```shell
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.port=9010
-Dcom.sun.management.jmxremote.ssl=true
-Dcom.sun.management.jmxremote.authenticate=true
-Dcom.sun.management.jmxremote.access.file=jmxremote.access
-Dcom.sun.management.jmxremote.password.file=jmxremote.password

// files
jmxremote.access
monitorRole   readonly
controlRole   readwrite

jmxremote.password
monitorRole   monitorpassword
controlRole   controlpassword
```

##### Prefer IPv4 over IPv6:
```shell
-Djava.net.preferIPv4Stack=true
```

Example:
```shell
java -jar MyJar.jar -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.rmi.port=9010 -Dcom.sun.management.jmxremote.local.only=true -Dcom.sun.management.jmxremote.host=127.0.0.1 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.net.preferIPv4Stack=true
```
