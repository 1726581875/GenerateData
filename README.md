# GenerateData

一个简单数据测试数据制造工具，作用是用于制造大量测试数据。目前只支持MySql数据库。

### 简单使用

1、拉取代码

```
git@github.com:1726581875/GenerateData.git
```



2、更改配置文件 config.properties

```
url=jdbc:mysql://${ip}:${端口}/${数据库名}?characterEncoding=UTF-8&serverTimezone=GMT%2B8
username=${你的用户名}
password=${你的密码}
driver=com.mysql.cj.jdbc.Driver
```



3、zhangyu.fool.generate.object包下新增数据库类对应实体类，并且使用注解说明生成规则

参考：zhangyu.fool.generate.object.FoolDatabase实体类



4、在MainRunner类main方法调用MySqlRunner方法生成数据

例如：fool_database表生成5000行数据

```
    public static void main(String[] args) {
        MySqlRunner mySqlRunner = new MySqlRunner();
        mySqlRunner.toRun(FoolDatabase.class, 5000);
    }
```

