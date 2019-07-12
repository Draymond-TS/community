## Draymond 社区

## 技术
前端：Bootstrap+Thymeleaf  
后端：Spring Web+mybatis+Github OAuth+MySQL



## 工具
Flyway
Lombok
Postman

## 脚本
```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```


##问题解决
1.mybatis-generator导入失败
```java
//导入一下内容（缺一不可）
<dependencies>
    <dependency>
           <groupId>org.mybatis.generator</groupId>
           <artifactId>mybatis-generator-core</artifactId>
           <version>1.3.2</version>
    </dependency>
     <dependency>
           <groupId>mysql</groupId>
           <artifactId>mysql-connector-java</artifactId>
           <version>5.1.12</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
               <groupId>org.mybatis.generator</groupId>
               <artifactId>mybatis-generator-maven-plugin</artifactId>
               <version>1.3.2</version>
               <dependencies>
                     <dependency>
                          <groupId>mysql</groupId>
                         <artifactId>mysql-connector-java</artifactId>
                        <version>5.1.12</version>
                    </dependency>
               </dependencies>
        </plugin>
    </plugins>
</build>

```


2.JS文件导入失败
```html
//区分type的不同
<script src="" type="application/javascript"></script>
<script src="" type="text/javascript"></script>
```

