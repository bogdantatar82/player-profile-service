# Start docker
mvn clean install -DskipTests -Dspring.cloud.contract.verifier.skip=true
export SERVICE_IMAGE=hunus/player-profile-service:latest
docker-compose -f docker-compose.yml --profile jooq-generate up --detach
sleep 30

# Generate JOOQ classes
mvn -DskipTests -Dspring.cloud.contract.verifier.skip=true org.jooq:jooq-codegen-maven:3.15.5:generate

# Stop docker
docker-compose -f docker-compose.yml down
