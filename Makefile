
all:
	mvn clean test package

trigger-docker-build:
	curl -H "Content-Type: application/json" --data '{"docker_tag": "master"}' -X POST ${DOCKER_TRIGGER_URL}

skipTests:
	mvn clean package -Dmaven.test.skip=true

docker: all
	docker build -t pkleindienst/jscheme .

docker-skipTests: skipTests
	docker build -t pkleindienst/jscheme .