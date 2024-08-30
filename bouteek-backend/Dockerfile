# syntax=docker/dockerfile:1

FROM openjdk:22-slim AS deps

WORKDIR /build

COPY mvnw mvnw
RUN chmod 0755 mvnw
COPY .mvn/ .mvn/

RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests

FROM deps AS package

WORKDIR /build

COPY ./src src/
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar


FROM package AS extract

WORKDIR /build

RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

FROM openjdk:22-slim AS final

ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

# Copy the executable from the "package" stage.
#COPY --from=extract build/target/extracted/dependencies/ ./
#COPY --from=extract build/target/extracted/spring-boot-loader/ ./
#COPY --from=extract build/target/extracted/snapshot-dependencies/ ./
#COPY --from=extract build/target/extracted/application/ ./
COPY --from=package build/target/app.jar ./app.jar

EXPOSE 8080

#ENTRYPOINT ["ls"]
ENTRYPOINT [ "java", "--enable-preview", "-jar", "./app.jar" ]
