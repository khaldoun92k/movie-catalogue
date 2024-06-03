 
# Requirements
Here is a list of requirements/acceptance criteria for the IMDB application (also serves as check list).

|       | Requirement/Acceptance criteria                                                                                                                                                        | Status | 
|-------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------|
| 1     | Domain model for data types Film and User created along with the corresponding database layout. Any additions to the data model for enhanced functionality are completely up to you!   | X      |
| 2     | User can register themselves in the application (with username and password).                                                                                                          | X      |
| 3     | A Film can be rated on a scale of 1-5 by users.                                                                                                                                        | X      |
| 4     | The web frontend should give new users the means to register themselves, and returning users the ability to login.                                                                     | X      |
| 5     | Logged-in users can search for films by name, or see a list of all films.                                                                                                              | X      |
| 6     | A film can also be rated by a logged-in user; the current average user rating of each film should be displayed.                                                                        | X      |
| 7     | The backend should be based on Spring Boot and JPA; the database can be freely chosen.                                                                                                 | X      |
| 8     | The RESTful API should meet the typical norms of REST.                                                                                                                                 | X      |
| 9     | Users receive recommendations on movies based on previous ratings, genres, and directors.Timing constraints                                                                            | X      |

Choose your focus wisely. Delivering complete functionality with high standard over full functionality with low quality is preferred.

# Installation
## IDE installation/Run
On first installation:

> mvn spring-boot:run -Pprod (run maven goal to install npm and node)

Afterward to run:

> mvn spring-boot:run 
## Docker Image/Container run
**requirement: Docker Desktop installed** 

Create docker image (fat image which contain the backend and the frontend) via spring boot packet builder: 

> mvn spring-boot:build-image -P prod -DskipTests

Run the image on a container locally: 

> docker run -d -p 8080:8080 --name movie1 ghcr.io/khaldoun92k/movie-catalogue/movie-catalogue:latest
 


