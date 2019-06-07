# 生成 jks 文件

配置 JwtTokenStore 时需要使用 jks 文件作为 Token 加密的秘钥。

jks 文件需要Java keytool工具，保证Java环境变量没问题，打开计算机终端，输入命令:

```
keytool -genkeypair -alias auth-jwt -validity 3650 -keyalg RSA -dname "CN=jwt,OU=jtw,O=jwt,L=zurich,S=zurich,C=CH" -keypass authServer -keystore auth-jwt.jks -storepass authServer
```

解释，-alias 选项为别名，-keypass 和 -storepass 为密码选项，-validity 为配置jks文件过期时间（单位：天）。

后续更新使用：
```
keytool -importkeystore -srckeystore auth-jwt.jks -destkeystore auth-jwt.jks -deststoretype pkcs12
```

获取的 jks 文件作为私钥，只允许 Uaa 服务持有，并用作加密 JWT 并放在resource目录下。

那么消费者对应的资源服务，是如何解密 JWT 的呢？这时就需要使用 jks 文件的公钥。获取 jks 文件的公钥命令如下：

```
keytool -list -rfc --keystore auth-jwt.jks | openssl x509 -inform pem -pubkey
```

输入密码authServer后，显示的信息很多，我们只提取 PUBLIC KEY，即如下所示：

```
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlkAvdkqgs/V/4DpLT/B7
20Mg1BicTGn9DJgpDPw0UQvEwdU+VQJIVplo8MXAn8Rwy1uWsVhkPl2gHdfWs+nG
bKY+rDFSCoZPKW4RIC4cGcR0megI8ByO+qyn9VLk6rpxTd0R9/FAXi2a6hYhEMh+
x3nVCnxOkNdFS4rZ9cIj3cfwz/S5SZFmQFVBqaGiqPcqObYYcib1IvhYva3T3+TY
Nii3Ru4aP0z1SyMlmdJ3qllgL39ObAunzkcstv5t+wYYyVJomDJz+GnC6zUPFVdM
dbyMRme9mDDxE+o4F8vA3QumdcHIMV0JzYl/vXkkZw6Zoe9tloCnrNW1uKivJMy5
eQIDAQAB
-----END PUBLIC KEY-----
```

新建一个 public.cert 文件，将上面的公钥信息复制到 public.cert 文件中并保存。
并将文件放到 消费者服务 等资源服务的resources目录下。


注意：Maven 在项目编译时，可能会将 jks 文件编译，导致 jks 文件乱码，最后不可用。需要在工程的 pom 文件中添加以下内容:

```$xslt
<plugin>
       <groupId>org.apache.maven.plugins</groupId>
       <artifactId>maven-resources-plugin</artifactId>
       <configuration>
             <nonFilteredFileExtensions>
                 <nonFilteredFileExtension>cert</nonFilteredFileExtension>
                 <nonFilteredFileExtension>jks</nonFilteredFileExtension>
              </nonFilteredFileExtensions>
        </configuration>
</plugin>
```