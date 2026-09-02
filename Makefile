all:
	mvn clean package
	docker compose build

build:
	mvn clean package

run:
	docker compose up
