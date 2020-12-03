运行命令java -jar springcloud-order-1.0-SNAPSHOT.jar --server.port=8085
 java -jar springcloud-order-1.0-SNAPSHOT.jar --spring.profiles.active=8762


打包运行jar 
 <plugin>
                <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.OrderApp</mainClass>
                </configuration>
            </plugin>
            
高可用
host文件
C:\Windows\System32\drivers\etc  
127.0.0.1       localhost eureka8761 eureka8762       

#### redis     
jedis 链接需要 redis.conf protected-mode=no  关闭保护模式