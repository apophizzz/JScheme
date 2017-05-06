
all:
	mvn clean test package

trigger-docker-build:
	curl -H "Content-Type: application/json" --data '{"source_type": "Branch", "source_name": "cont_passing"}' -X POST
	${DOCKER_TRIGGER_URL}

skipTests:
	mvn clean package -Dmaven.test.skip=true

docker: all
	docker build -t pkleindienst/jscheme:cont-passing .

docker-skipTests: skipTests
	docker build -t pkleindienst/jscheme:cont-passing .