# maven-deploy-files

- Generate commands for deploying libraries to the Maven Server.

## Requirements

- Java 8

## How to use

- Run the command at the command prompt, passing the specified parameters as shown in the example below:

`java -jar maven-deploy-files-1.0.0.jar -repositoryPath "C:\\repository-test" -mvnDeplyRepositoryId "test" -mvnDeplyRepositoryUrl "http://localhost:8080/test"`

## Parameter documentation

- **repositoryPath** : maven directory path where the libraries are located to generate the "deploy:deploy-file" commands.

- **mvnDeplyRepositoryId** : destination repository ID.

- **mvnDeplyRepositoryUrl** : destination repository URL.
