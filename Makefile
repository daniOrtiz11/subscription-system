# Copyright 2021 Daniel Ortiz @daniOrtiz11 (https://github.com/daniOrtiz11). All rights reserved.

.PHONY: help
help: ## Show this help.
	@echo Welcome to subscription-system service!
	@echo Prerequisites:
	@echo 1. At least Java 11
	@echo 2. Maven
	@echo 3. Docker
	@echo 4. Unix based os
	@echo Options:
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'

.PHONY: public-service-up
public-service-up: ## Command to run the application.
		cp public-service/resources/dev_config.yaml public-service/src/main/resources/config.yaml
		mvn clean install -f public-service/pom.xml -DskipTests
		docker-compose -f public-service/docker-compose.yml  up --build

.PHONY: public-service-down
public-service-down: ## Command to shut down the application.
		docker-compose -f public-service/docker-compose.yml  down

.PHONY: public-service-test
public-service-test: ## Command to test the application.
		mvn clean -f public-service/pom.xml test

.PHONY: middleware-up
middleware-up: ## Command to run the application.
		cp middleware/resources/dev_config.yaml middleware/src/main/resources/config.yaml
		mvn clean install -f middleware/pom.xml -DskipTests
		docker-compose -f middleware/docker-compose.yml  up --build

.PHONY: middleware-down
middleware-down: ## Command to shut down the application.
		docker-compose -f middleware/docker-compose.yml  down

.PHONY: middleware-test
middleware-test: ## Command to test the application.
		docker-compose -f middleware/build_acceptance/docker-compose_acceptance.yml  down
		docker-compose -f middleware/build_acceptance/docker-compose_acceptance.yml  up --build --abort-on-container-exit


.PHONY: email_notifier-up
email_notifier-up: ## Command to run the application.
		cp email_notifier/resources/dev_config.yaml email_notifier/src/main/resources/config.yaml
		mvn clean install -f email_notifier/pom.xml -DskipTests
		docker-compose -f email_notifier/docker-compose.yml  up --build

.PHONY: email_notifier-down
email_notifier-down: ## Command to shut down the application.
		docker-compose -f email_notifier/docker-compose.yml  down

.PHONY: kafka-up
kafka-up: ##up the local docker
	docker-compose -f kafka-mock/docker-compose.yml up

.PHONY: kafka-down
kafka-down: ##down the local docker
	docker-compose -f kafka-mock/docker-compose.yml down
