# About the project
- A REST API for apartment ads
- Modularized with Gradle
- It has two parts, one using Tomcat, and using the Spring ecosystem

# Project part with Tomcat
- Created Gradle script to build .war file and deploy it into the CATALINA_HOME folder
- Used Pooling design pattern to manage Database connections
- Implemented Factory design pattern to create an array based DAO for prod profile, and a database DAO for prod profile
- Session based authentication + authentication filter
- Simple Handlebars server side rendering

# Project part with Spring
- Has three profiles: one using in memory DAO, one using DAO created with JDBC and Reflection API, one using DAO created with Hibernate
- Used Reflection API to create the same needed CRUD functions for different domain models, following the DRY principles
- Different Gradle config files for different profiles
- Used DTOs with ORM to preserve the compatibility of the API, if the domain models change
- Implemented exception controllers

**Note**: this was a university project, <a href="https://gitlab.com/ubb-idde/labs/2022/lab-gvim2021">this</a> is the original link.
