# ZadatakZaProgramiranje
## Description
This is a small project that displays a collection of movies and their genres. The movies can be filtered by name, adding date (from given starting date to given ending date) and genre.

## Access
### 1. Production
The production can be accesed on the url: https://zadatakzaprogramiranje-ui.onrender.com/home
Note: it is deployed on a https://render.com free instance and render sometimes shuts off inactive instances so before accessing website you can check if it is active by visiting the url https://zadatakzaprogramiranje-api.onrender.com/swagger-ui/index.html#/ and waiting for it to load.

### 2. Local
To run the application locally follow these steps
#### 1. Clone the git repo https://github.com/MarkoTunjic/ZadatakZaProgramiranje.git.
#### 2. Setup postgresql and an IDE like pgadmin
#### 3. Create a database named ZadatakZaProgramiranje
#### 4. In the file web-api/src/main/resources/application-develop.properties change the username and password to you postgresql username and password
#### 5. If you want run the scripts located web-api/src/main/resources/db. First the database.sql script then the inserts.sql. But empty tables will be created regardless of this step
#### 6.  Then using intellij or vscode or some similar IDE start up the web-api located in the web-api/ folder.
#### 7. Setup angular on you machine
#### 8. Lastly start the frontend by running npm install and npm run in the folder user-interface/

# Tech stack
## 1. Database
For the database development postgresql was used to create and run a relational databse. The used IDE was pgAdmin
## 2. Backend
For the backend development spring boot 3.1.4 was used with hibernate and JPA for database access. For testing JUnit, Mockito and a in memory H2 database were used
## 3. Frontend
For frontend development angular with angular material-ui were used. For e2e tests nightwatch.js was used
## 4. Deployment
For deployement of backend I used docker and frontend was deployed using the built in npm run build command which generates a static html+css+js site.
## 5. Cloud
For running the web app in production mode render cloud services were used specificaly for deploying a database instance, a docker wep api and a static frontend site
## 6. Code generation
For generating frontend fetch commands, towards the backend, the open-api configuration was used. So the backend was documented using Swagger and open-api standards. Next the openapi-genrator was used to generate typescript commands for communication. And those commands/classes were injected into the angular components


# Improvements (that could be done)
1. Use a database versioning software like flyway or liquibase
2. Frontend design :) and error handling
3. CRUD operations for create, update and delete + validation
4. User managment+authorisation+authentication using OAuth2/OpenID standard with a free provider like Auth0
5. Usage of design patterns like "strategy" and "template method" to reduce code duplication in tests (P.S didn't use it becouse I always had an error "stale component ID" when running tests)