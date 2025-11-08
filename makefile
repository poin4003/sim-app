-include .env

ifeq ($(OS),Windows_NT)
	MVN_CMD = mvnw.cmd
else
	MVN_CMD = ./mvnw
endif

# Start server
run:
	@echo "Starting server..."
	$(MVN_CMD) spring-boot:run

# Migration command
flyway_migrate:
	@echo "Running Flyway migration..."
	./mvnw.cmd flyway:migrate "-Dflyway.url=$(DB_URL)" "-Dflyway.user=$(DB_USER)" "-Dflyway.password=$(DB_PASSWORD)"

flyway_info:
	@echo "Checking migration status..."
	./mvnw.cmd flyway:info "-Dflyway.url=$(DB_URL)" "-Dflyway.user=$(DB_USER)" "-Dflyway.password=$(DB_PASSWORD)"

flyway_validate:
	@echo "Validating migrations..."
	./mvnw.cmd flyway:validate "-Dflyway.url=$(DB_URL)" "-Dflyway.user=$(DB_USER)" "-Dflyway.password=$(DB_PASSWORD)"

flyway_repair:
	@echo "Repairing schema history..."
	./mvnw.cmd flyway:repair "-Dflyway.url=$(DB_URL)" "-Dflyway.user=$(DB_USER)" "-Dflyway.password=$(DB_PASSWORD)"
