# JEE (micro) service for store catalog data

This is a JEE implementation to store catalog data: language dependant dictionary of codes, e.g. languages, countries, states or otherenumerations.

## Prerequisites to develop locally and run the project ##
- Maven 3
- A JEE7 compliant application server. In detail, we need:
  - JPA 2.0
  - EJB 3.2
  - JAX-RS 2.0
- A relational database supported by the JPA implementation. For development *PostgresQL 9.4* was used. Others databases may work also with slight adoptions to [persistence.xml](CatalogService/src/main/resources/META-INF/persistence.xml).

## Build ##
```
mvn package
```