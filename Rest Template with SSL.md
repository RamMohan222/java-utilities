## Generate Self-Signed Certificate

```shell
keytool -genkeypair -alias yourdomain -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore yourdomain.p12 -validity 3650 -storepass yourpassword
```

## Generate Self-Signed Certificate (for localhost)

If you are going to use this certificate on localhost then add the following -ext “SAN:c=DNS:localhost,IP:127.0.0.1”. So for localhost use, the complete command will look like this:
```shell
keytool -genkeypair -alias yourdomain -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore yourdomain.p12 -validity 3650 -storepass yourpassword -ext "SAN:c=DNS:localhost,IP:127.0.0.1"
```

* **genkeypair:** generates a key pair;
* **alias:** the alias name to access the item in a keystore file. Choose Your own alias name;
* **keyalg:** the cryptographic algorithm to generate the key pair;
* **keysize:** the size of the key;
* **storetype:** the type of keystore;
* **keystore:** the name of the keystore file;
* **validity:** the number of days this certificate should be valid;
* **storepass:** a password to access the keystore file. Choose your own password.

To verify the generated file and see if you can access its content with the provided Keystore password, use the following command.
```shell
keytool -list -v -storetype pkcs12 -keystore <YOUR KEYSTORE FILE NAME HERE>.p12
```

```properties
server.port=8443
server.ssl.enabled=true # default value is true if ssl is configured
server.ssl.key-store-type=PKCS12
server.ssl.key-store=classpath:yourdomain.p12
server.ssl.key-store-password=yourpassword
server.ssl.key-alias=yourdomain
```
If your Spring Boot application also uses Spring Security, you can configure it to accept only HTTPs requests. To do that add the following property.
```properties
security.require-ssl=true
```

```xml
<dependency>
      <groupId>org.apache.httpcomponents</groupId>
      <artifactId>httpclient</artifactId>
</dependency>
```

```java
package com.demo.sslrestclient;

import javax.net.ssl.SSLContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestTemplateSslClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateSslClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final String password = "yourpassword";
        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(ResourceUtils.getFile("classpath:yourdomain.p12"), password.toCharArray())
                .build();

        CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .build();
        
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(client);
        
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        
        String url = "https://localhost:8443/status/check";
        
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
        
        System.out.println("Result = " + response.getBody());
    }

}
```
