# bisecurity-demo

Environment: 
  Java 17
  maven 3.6.3

Build:
mvn install 

Run: 
java -jar target/spring-boot-security-login-0.0.1-SNAPSHOT.jar

# Guide to the project 
The project use spring security, jwt, H2 to: 
1. Accouonts management: add/delete/update/get;
2. Resources access control management;
3. Authrization and authentications.
It is only a backend project, so Postman is recommended to use to do all the query to the restful APIs.

To view all the data in H2, H2 console: http://localhost:8080/h2-console/

1. Roles there are three roles defined: admin, user, moderator
   To begin with, we need to add roles for the system. End points for roles:
   List all roles: GET localhost:8080/api/role/listAll
   Add all roles: POST localhost:8080/api/role/addRoles
   ![image](https://github.com/Simonrocker6/bisecurity-demo/assets/17208248/524ae3ed-da60-451f-969c-10c378e39545)

   

2. We need to add accounts to proceed, signup account and signin to get the token for corresponding roles/permissions
  Sign up: POST localhost:8080/api/auth/signup
  Sign in: POST localhost:8080/api/auth/signin

    ![image](https://github.com/Simonrocker6/bisecurity-demo/assets/17208248/55a485ee-1cbb-40e0-80a4-e135f36d86bb)
    ![image](https://github.com/Simonrocker6/bisecurity-demo/assets/17208248/86fce193-dac1-477d-934d-f84739d4f390)

3. Because Alice has the admin roles, so she can access all resources for apis in TestController.
  Resources access control APIs: GET  localhost:8080/api/test/user, GET  localhost:8080/api/test/admin, GET  localhost:8080/api/test/mod
    
    ![image](https://github.com/Simonrocker6/bisecurity-demo/assets/17208248/b42c992d-f340-4daa-aa6b-af9c6acc8ca8)

4. With admin role, you can do listAll accounts, deleteAccount, update, and addAccount.
   ListAll: GET localhost:8080/api/account/listAll
   Add: POST localhost:8080/api/account/add
   Delete: POST localhost:8080/api/account/delete
   Update: Add: POST localhost:8080/api/account/update

  ![image](https://github.com/Simonrocker6/bisecurity-demo/assets/17208248/ba3ef853-af0b-4f94-9de6-7620ae332972)
  ![image](https://github.com/Simonrocker6/bisecurity-demo/assets/17208248/d69a12da-79bb-4352-98fc-02034c011d6c)
  

