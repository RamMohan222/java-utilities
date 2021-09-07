Exception while configuring SonarQube in eclipse.
By default eclipse doesn't use the system keystore, it uses it's own. For example, here is the location of the keystore that should get the cert:

```java
unable to execute request: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certificate path
```

## C:\Users\****youruser****\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_15.0.2.v20210201-0955\jre\lib\security\cacerts
