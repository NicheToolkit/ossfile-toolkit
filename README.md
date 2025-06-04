## [Ossfile-Toolkit](https://github.com/NicheToolkit/Ossfile-toolkit)

[![GitHub License](https://img.shields.io/badge/license-Apache-blue.svg)](https://github.com/NicheToolkit/Ossfile-toolkit/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.nichetoolkit/ossfile-toolkit-starter)](https://central.sonatype.com/search?smo=true&q=Ossfile-toolkit-starter&namespace=io.github.nichetoolkit)
[![Nexus Release](https://img.shields.io/nexus/r/io.github.nichetoolkit/Ossfile-toolkit-starter?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/content/repositories/releases/io/github/nichetoolkit/Ossfile-toolkit-starter/)
[![Nexus Snapshot](https://img.shields.io/nexus/s/io.github.nichetoolkit/Ossfile-toolkit-starter?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/nichetoolkit/Ossfile-toolkit-starter/)
![Tests](https://github.com/NicheToolkit/Ossfile-toolkit/workflows/Tests/badge.svg)

## Maven Central Repository

- [Maven Central Repository](https://search.maven.org/search?q=io.github.nichetoolkit)

- [Sonatype Central Repository](https://central.sonatype.dev/search?q=io.github.nichetoolkit)

## Dependent & Environment

> [Spring Boot](https://spring.io/projects/spring-boot) 2.7.18.RELEASE\
> [Maven](https://maven.apache.org/) 3.6.3+\
> [JDK](https://www.oracle.com/java/technologies/downloads/#java8) 1.8\
> [PostgreSQL](https://www.postgresql.org/) 10.0+

## Wiki Reference

[Wiki Reference](https://github.com/NicheToolkit/Ossfile-toolkit/wiki): https://github.com/NicheToolkit/Ossfile-toolkit/wiki

## Instructions

### Maven Usages

#### ossfile-toolkit-core

* Maven (`pom.xml`)

```xml

<dependency>
    <groupId>io.github.nichetoolkit</groupId>
    <artifactId>ossfile-toolkit-core</artifactId>
    <version>1.1.1</version>
</dependency>
```

#### ossfile-toolkit-context

* Maven (`pom.xml`)

```xml

<dependency>
    <groupId>io.github.nichetoolkit</groupId>
    <artifactId>ossfile-toolkit-context</artifactId>
    <version>1.1.1</version>
</dependency>
```

#### ossfile-toolkit-starter

* Maven (`pom.xml`)

```xml

<dependency>
    <groupId>io.github.nichetoolkit</groupId>
    <artifactId>ossfile-toolkit-starter</artifactId>
    <version>1.1.1</version>
</dependency>
```

### Configure Properties

#### cache configuration

* prefix

>
> nichetoolkit.ossfile.cache
>

* values

|    value    |   type    | defaultValue |                          description                          |
|:-----------:|:---------:|:------------:|:-------------------------------------------------------------:|
| `init-size` | `Integer` |    `1024`    |   the initiate size of table cache on ossfile `sql` handle.   |
| `use-once`  | `Boolean` |   `false`    | the switch of table cache used once on ossfile `sql ` handle. |

* properties

```properties
nichetoolkit.ossfile.cache.init-size=1024
nichetoolkit.ossfile.cache.use-once=false
```

## Test Example

[Ossfile-toolkit-example](https://github.com/NicheToolkit/Ossfile-toolkit/tree/master/Ossfile-toolkit-example)

## License

[Apache License](https://www.apache.org/licenses/LICENSE-2.0)

## Dependencies

[Rest-toolkit](https://github.com/NicheToolkit/rest-toolkit)

[Rice-toolkit](https://github.com/NicheToolkit/rice-toolkit)

[ossfile-toolkit](https://github.com/NicheToolkit/ossfile-toolkit)

[Spring Boot](https://github.com/spring-projects/spring-boot)
