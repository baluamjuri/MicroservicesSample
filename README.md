# UserCRUDEurekaZuulCloudConfigSpringRestJpaOAuthJWTMysql

## This is a Spring cloud microservices project, providing rest end-points for User CRUD
### Technical stack:
    1. Java8
    2. MySQL
    3. Spring Boot(REST, JPA)
    4. Spring cloud(Eureka, Zuul, Config)
    5. oAuth2 with JWT
    6. Lombok
    7. Maven

### Setup:
    1. Import as Maven project in Eclipse/STS
    2. Lombok setup in Eclipse/STS
        https://howtodoinjava.com/automation/lombok-eclipse-installation-examples/
        Note: you can find the jar in .m2 repository, once build is successfull
    3. Postman or any other app to test the rest API services
    4. Mysql database
    
### How to run:
    1. Go to auth-center -> /resources/auth-center.yml
        Change the database url with appropriate schema(Here in this project, it is 'test'), username and password
        Do the same for user-service -> /resources/user-service.yml
    2. Run config-service (Cloud config) as spring boot application/java applicaiton
    3. Run discovery-service (Eureka)
    4. Make sure the database(test) up and run
        Run auth-center (Authorization server)
        As the hibernate 'ddl-auto' is 'update', the tables will generate automatically for the first time
    5. Run user-service (Resource server)
    6. Run api-gateway (Zuul)
    7. Go to the database and execute all the insert queries provided (auth-center\src\main\resources\data.sql)
 
### Test:
    1. You need an access token to access User resource(protected resource)
       For access token you need to send credentials to auth-center through zuul gatewaay.
       As we are using oAuth2 **password** grant type, the required details should sent as follows in Postman:
         1) Request type: POST
         2) Url: http://localhost:8888/gateway/auth/oauth/token
         3) Authorization
            Type: Basic Auth
            username: springdeveloper123
            password: test
         4) Body(x-www-form-urlencoded)
            'grant_type' : 'password'
            'username' : 'john'
            'password' : 'johh'
            
            Reference: https://s3.amazonaws.com/postman-static-getpostman-com/postman-docs/WS-preview-request.png
                       https://www.codeproject.com/KB/webservices/1090252/Token.jpg
         5) Send the request
            You will get an access token
         
    2. Using this access token you can send request to user-service resource through zuul gateway
       Open Postman
         1) Request type: GET
         2) Url: http://localhost:8888/gateway/userservice/users
         3) Headers
            Authorization : bearer <access token>
         4) Send the request
            You will get the list of users 
   
## References:
### For oAuth2 (Priya K)
   * [oAuth2 SSO](https://www.youtube.com/watch?v=xEuEP-4IipI&index=5&t=0s&list=PL5PqGfirOcL-kuCpAQ6yhpqQOjSGAWHw8)
   * [oAuth2 Resource server](https://www.youtube.com/watch?v=TV8HRGALKt4&index=4&t=0s&list=PL5PqGfirOcL-kuCpAQ6yhpqQOjSGAWHw8)
   * [oAuth2 Authorization server](https://www.youtube.com/watch?v=TcU0xBIhqBg&index=3&t=0s&list=PL5PqGfirOcL-kuCpAQ6yhpqQOjSGAWHw8)
   * [oAuth2 Rest Template](https://www.youtube.com/watch?v=_x5EDG8jow4&index=2&t=0s&list=PL5PqGfirOcL-kuCpAQ6yhpqQOjSGAWHw8)
   
### For cloud config (Priya K)
   * [Config at classpath#1](https://www.youtube.com/watch?v=OytqzGq9hmE)
   * [Config at classpath#2](https://www.youtube.com/watch?v=UDLNmiuSqBI) 
   * [Config at classpath#3](https://www.youtube.com/watch?v=C6coAVlLFec)
   * [Config at git](https://www.youtube.com/watch?v=MoaF_G4qYW4)
   * [Config client](https://www.youtube.com/watch?v=IOEHV-7gdpI)
   * [Config security](https://www.youtube.com/watch?v=P7H0DPzepE0)
        
### For Eureka
   * https://www.baeldung.com/spring-cloud-netflix-eureka
   * https://spring.io/guides/gs/service-registration-and-discovery/
### For zuul
   * https://howtodoinjava.com/spring-cloud/spring-cloud-api-gateway-zuul/
### To create a CRUD REST API
   * http://www.springboottutorial.com/spring-boot-crud-rest-service-with-jpa-hibernate    
