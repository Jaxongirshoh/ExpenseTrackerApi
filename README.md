# ExpenseTracker API

A Spring Boot application for tracking expenses with Notion integration. 
## Technologies

- Java 21
- Spring Boot 3.4.1
- Spring Security
- Spring Data JPA
- PostgreSQL
- Lombok
- OpenAPI (Swagger UI)
- Apache POI (Excel support)
- Notion API integration

## Features

- Expense tracking and management
- User authentication and authorization
- Data validation
- API documentation with Swagger UI
- Excel file handling
- Notion database synchronization for transactions
- PostgreSQL database integration

## Prerequisites

- JDK 21
- Maven
- PostgreSQL database
- Notion API credentials (for transaction synchronization)

## Building the Project

To build the project, run:

```bash
mvn clean install
```

## Running the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:
`http://localhost:8080/swagger-ui.html`

## Database Configuration

The application uses PostgreSQL as its database. Make sure to configure your database connection in `application.properties` or `application.yml`.

## Notion Integration

This application includes functionality to synchronize transactions with a Notion database. To set up the Notion integration:

1. Set up a Notion integration and get the API key
2. Share your database with the integration
3. Configure the Notion API credentials in your application properties

## Development

The project uses Lombok for reducing boilerplate code. Make sure your IDE has Lombok plugin installed.

### Key Dependencies

- spring-boot-starter-data-jpa: JPA/Hibernate support
- spring-boot-starter-security: Security features
- spring-boot-starter-validation: Data validation
- spring-boot-starter-web: Web application support
- postgresql: PostgreSQL database driver
- springdoc-openapi: API documentation
- poi-ooxml: Excel file handling

## Testing

Run the tests using:

```bash
mvn test
```
🚧 Current Status: This project is in active development, and test coverage is written sometime but you can write test and run test with above script !

## Building for Production

The project includes GraalVM native image support and Hibernate bytecode enhancement for optimized performance.

To create a native image:

```bash
mvn -Pnative native:compile
```




## Contact
Project Maintainer [me](https://github.com/Jaxongirshoh) \
[my-telegram](https://t.me/chala_softwareengineer)\
Project Link: [https://github.com/Jaxongirshoh/warehouse](https://github.com/Jaxongirshoh/ExpenseTrackerApi)
