compose-up:
	mvn clean install
	docker-compose up

compose-down:
	docker-compose down

dev:
	./mvnw, spring-boot:run, -Dspring-boot.run.profiles=dev

prod:
	./mvnw, spring-boot:run, -Dspring-boot.run.profiles=prod

test:
	./mvnw test -Dspring.profiles.active=test