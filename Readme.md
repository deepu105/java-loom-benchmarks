# Java Loom benchmarks

Benchmarks for Java Project Loom Virtual threads vs Platform Threads with JDK (openjdk 19 2022-09-20)

Setup instructions for benchmark:

## Clone the project

```bash
git clone https://github.com/deepu105/java-loom-benchmarks.git
```

## Setup Java 19 EA build using SDK man

```bash
sdk install java 19.ea.36-open
sdk use java 19.ea.36-open
```

## Build and run using Maven

```bash
mvn clean verify
java -jar target/benchmarks.jar
```

## Typical Results

These results vary because they were ran on a developer machine with other services running. Also, according to the JMH
docs, to avoid `blackholes` (methods that return void). e.g. If you call a method and return void the JVM will optimize
by removing dead code. To ensure the code isn't removed the method returns teh current time.

Below are some results (smaller numbers are better).

### Linux (JDK 19)

```text
Benchmark                             Mode  Cnt  Score   Error  Units
LoomBenchmark.platformThreadPerTask  thrpt    5  0.362 ± 0.079  ops/s
LoomBenchmark.platformThreadPool     thrpt    5  0.528 ± 0.067  ops/s
LoomBenchmark.virtualThreadPerTask   thrpt    5  1.843 ± 0.093  ops/s
LoomBenchmark.platformThreadPerTask   avgt    5  5.600 ± 0.768   s/op
LoomBenchmark.platformThreadPool      avgt    5  3.887 ± 0.717   s/op
LoomBenchmark.virtualThreadPerTask    avgt    5  1.098 ± 0.020   s/op
```
