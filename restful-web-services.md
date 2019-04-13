# RESTful Web Services

## Tools
1. Spring Boot (Java-based framework used to create micro-service)
2. [Spring Tool Suite (STS) 3](https://spring.io/tools3/sts/all) as IDE

## Steps
1. Make a Spring Boot project by `File > New > Spring Starter Project`
   ![ss2](/img/ss2.png)
   Then, select **Web** as dependencies
2. This is the **Main java** file by default
    ```java
    package com.mocatfrio.webapp;

    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;

    @SpringBootApplication
    public class BookManagementApplication {

        public static void main(String[] args) {
            SpringApplication.run(BookManagementApplication.class, args);
        }

    }
    ```
3. Update the **pom.xml** file by this dependencies:
   * spring-web
   * spring-boot-starter
   * spring-boot-starter-tomcat
   * spring-bind
   * jackson-databind
4. Add a basic REST code to the application. Remove the `@SpringBootApplication` annotation and add annotations that will help the class to act as a web service class, that is:
    ```java
    @Configuration
    @EnableAutoConfiguration
    @ComponentScan
    @Controller
    ```
    So, the **Main java** file will look like this:
    ```java
    package com.mocatfrio.webapp;

    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.stereotype.Controller;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.ResponseBody;
    @Configuration
    @EnableAutoConfiguration
    @ComponentScan
    @Controller

    public class BookManagementApplication {
        // simple method to return a string as our basic web service method
        @ResponseBody
        @RequestMapping("/")
        public String sayAloha() {
            return "Aloha";
        }

        public static void main(String[] args) {
            SpringApplication.run(BookManagementApplication.class, args);
        }
    }
    ```
    > Note: Let's say, we forget to update the pom.xml, Maven can updating pom.xml file itself by right click on the project > Maven > Update Project
5. Run the project by `Run As... > Spring Boot App`
6. Check through the browser `http://localhost:8080/`
   ![ss3](/img/ss3.png)
7. To change the port number, we can configure it on `src/main/resources/application.properties`. For example, `server.port = 1998`
   ![ss4](/img/ss4.png)

## Full Code

[RESTful Web Services](/restful-web-services)