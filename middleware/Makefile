# Copyright 2021 Daniel Ortiz @daniOrtiz11 (https://github.com/daniOrtiz11). All rights reserved.

.PHONY: help
help: ## Show this help.
	@echo Welcome to Middleware service!
	@echo Prerequisites:
	@echo 1. At least Java 11
	@echo 2. Maven
	@echo 3. Docker
	@echo 4. Unix based os
	@echo Options:
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'

.PHONY: middleware-up
middleware-up: ## Command to run the application.
		cp resources/dev_config.yaml src/main/resources/config.yaml
		mvn clean install -DskipTests
		docker-compose -f docker-compose_standalone.yml  up --build

.PHONY: middleware-down
middleware-down: ## Command to shut down the application.
		docker-compose -f docker-compose_standalone.yml  down

.PHONY: middleware-test
middleware-test: ## Command to test the application.
		docker-compose -f build_acceptance/docker-compose_acceptance.yml  down
		docker-compose -f build_acceptance/docker-compose_acceptance.yml  up --build --abort-on-container-exit
