# JEE (micro) service for store catalog data

This is a JEE implementation of a service to store catalog data. By catalog data, we mean language dependant larger dictionaries of codes, e.g. languages, countries, states or other enumerations.

The service will return code/text (key/value) pairs for enumerations, where the text is available in different scopes, e.g. languages. An example for such a dictionary is  e.g.

```
[
 {"text":"Andorra","code":"AD"},
 {"text":"United Arab Emirates","code":"AE"},
 {"text":"Afghanistan","code":"AF"},
 {"text":"Antigua and Barbuda","code":"AG"},
 ...
 {"text":"Zambia","code":"ZM"},
 {"text":"Zimbabwe","code":"ZW"}
]
```

This is a subset of the ISO-3166-1-Alpha2 country codes, with their englisch name. The same list for the german version would be:

```
[
 {"text":"Andorra","code":"AD"},
 {"text":"Vereinigte Arabische Emirate","code":"AE"},
 {"text":"Afghanistan","code":"AF"},
 {"text":"Antigua und Barbuda","code":"AG"},
 ...
 {"text":"Sambia","code":"ZM"},
 {"text":"Simbabwe","code":"ZW"}
]
```

The above example are available using the URLS
- [http://catalogjee1.eu-gb.mybluemix.net/api/catalogs/iso-3166-1-alpha2/scope-list/en]
- [http://catalogjee1.eu-gb.mybluemix.net/api/catalogs/iso-3166-1-alpha2/scope-list/de]

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

## Goals of this project ##
- Providing language dependant dictionaries in JSON format for rich internet application. 

## TODOs ##
- Caching support for the service.
- An Angular module to fetch the dictionaries and cache them.
- A Java bean validation component, to check valid codes in JAX-RS endpoints (POST, PUT).
