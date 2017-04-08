
all:
	mvn clean package

skipTests:
	mvn clean package -Dmaven.test.skip=true

docker: all
	docker build -t jscheme .

docker-skipTests: skipTests
	docker build -t jscheme .