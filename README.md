### This is the Java Spring Boot test task for Fujitsu (Movie rental API)
The application uses the traditional application architecture:

**Controller** -> **Service** -> **Repository** 

In addition, there are some service classes that are used for reading/writing data from/to JSON and YAML files.

---
In ``application.properties`` it's possible to define whether the application should use JSON or YAML as a database.

It's also possible to define it in ``.config(boolean useYaml)`` method in the database service classes (useful for testing).

The JSON and YAML files which act as the databases are located in ``src/main/resources/databases/json/*`` and ``src/main/resources/databases/yaml/*``.