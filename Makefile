all:
	 mvn package spring-boot:repackage
	 docker compose build

run:
	docker compose up
