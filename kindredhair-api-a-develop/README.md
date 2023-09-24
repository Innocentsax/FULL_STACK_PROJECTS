
## Kindredhair API
This project serves as the backend for Kindredhair application.

### Starting the application
To run the app on your local machine<br />
First export the environment variables by running `export $(cat .env | xargs)`.<br /><br />
Ensure you have postgres running on your local machine.<br />
Create a database with the name `kindredhair`. <br />

Run `./mvnw spring-boot:run` in your terminal.<br />
Or click the appropriate button to run a java application in your favorite text editor.<br />
Open [http://localhost:9000](http://localhost:9000) to view it in the browser or whatever PORT you specified.
