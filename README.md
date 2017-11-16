# IIN Range Project

This is a very detailed description regarding IinRangeService project.

## Building

Application is buildable with Maven.
Run the following to create a jar file with dependencies:

```
mvn clean compile assembly:single
```

## Running

### Demo

Run without parameters to see a demo:

```
java -jar IinRangeService-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Single parameter

Run with a single parameter to see whether a single card is supported:

```
java -jar IinRangeService-1.0-SNAPSHOT-jar-with-dependencies.jar 53999999
```

### List of parameters

Run with a list of parameters to see whether each card in the list is supported:

```
java -jar IinRangeService-1.0-SNAPSHOT-jar-with-dependencies.jar 53999999 535810000 10000000
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Krzysztof Jastrzebski** - *ALL work*
