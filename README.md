 
# Requirements
Here is a list of requirements/acceptance criteria for the IMDB application (also serves as check list).

|       | Requirement/Acceptance criteria                                                                                                                                                        | Status     | 
|-------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------|
| 1     | Domain model for data types Film and User created along with the corresponding database layout. Any additions to the data model for enhanced functionality are completely up to you!   |            |
| 2     | User can register themselves in the application (with username and password).                                                                                                          |            |
| 3     | A Film can be rated on a scale of 1-5 by users.                                                                                                                                        |            |
| 4     | The web frontend should give new users the means to register themselves, and returning users the ability to login.                                                                     |            |
| 5     | Logged-in users can search for films by name, or see a list of all films.                                                                                                              |            |
| 6     | A film can also be rated by a logged-in user; the current average user rating of each film should be displayed.                                                                        |            |
| 7     | The backend should be based on Spring Boot and JPA; the database can be freely chosen.                                                                                                 |            |
| 8     | The RESTful API should meet the typical norms of REST.                                                                                                                                 |            |
| 9     | Users receive recommendations on movies based on previous ratings, genres, and directors.Timing constraints                                                                            |            |

Choose your focus wisely. Delivering complete functionality with high standard over full functionality with low quality is preferred.

# Installation
## IDE installation/Run
On first installation run maven goal to install npm and node for the first time: "mvn spring-boot:run -Pprod"
Afterward to run: "mvn spring-boot:run"
## Docker Image/Container run
requirement: Docker Desktop installed
Create docker image via spring boot packet builder: mvn spring-boot:build-image -P prod -DskipTests
Run the image on a container locally: docker run -d -p 8080:8080 movie-catalogue:0.0.1-SNAPSHOT --name movie1 



# Miscellaneous
- CSRFs (Cross site request forgery) are typically conducted using malicious social engineering, such as an email or link that tricks the victim into sending a forged request to a server. As the unsuspecting user is authenticated by their application at the time of the attack, it’s impossible to distinguish a legitimate request from a forged one.

