# github-code-with-quarkus

This project is a REST API that integrates with GitHub to fetch repositories and their branches using Quarkus, the Supersonic Subatomic Java Framework.

## Technologies Used

- **Java 17** – Programming language
- **Quarkus** – Framework for building Java applications
- **RESTEasy Reactive** – Implementation of JAX-RS for RESTful services
- **MicroProfile REST Client** – HTTP client for external API communication
- **Jackson** – JSON serialization and deserialization
- **JUnit 5** – Testing framework
- **RestAssured** – For API testing
- **Maven** – Dependency and build management
- **GitHub API** – External API integration

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Token Configuration

To interact with the GitHub API, you need to provide a personal access token. Add your GitHub token to the `application.properties` file as follows:

```properties script
github.token=your_personal_access_token
```
Make sure to replace `your_personal_access_token` with your actual GitHub token. You can create a new token in your GitHub account settings under "Developer settings" → "Personal access tokens".
## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/github-code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

