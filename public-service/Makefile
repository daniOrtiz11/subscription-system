# Copyright 2021 Daniel Ortiz @daniOrtiz11 (https://github.com/daniOrtiz11). All rights reserved.

.PHONY: help
help: ## Show this help.
	@echo Welcome to Public API service!
	@echo Prerequisites:
	@echo 1. At least Java 11
	@echo 2. Maven
	@echo 3. Docker
	@echo 4. Unix based os
	@echo Options:
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'

.PHONY: public-service-up
backend-challenge-up: ## Command to run the application.
		cp resources/dev_config.yaml src/main/resources/config.yaml
		mvn clean install -DskipTests
		docker-compose -f docker-compose.yml  up --build

.PHONY: public-service-down
backend-challenge-down: ## Command to shut down the application.
		docker-compose -f docker-compose.yml  down

.PHONY: public-service-up-test
backend-challenge-test: ## Command to test the application.
    mvn clean test
