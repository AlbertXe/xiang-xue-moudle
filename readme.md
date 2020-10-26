运行命令java -jar springcloud-order-1.0-SNAPSHOT.jar --server.port=8085

打包运行jar 
 <plugin>
                <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <mainClass>com.OrderApp</mainClass>
                </configuration>
            </plugin>