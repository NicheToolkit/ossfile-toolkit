## [ossfile-Toolkit](https://github.com/NicheToolkit/ossfile-toolkit)

[![GitHub License](https://img.shields.io/badge/license-Apache-blue.svg)](https://github.com/NicheToolkit/ossfile-toolkit/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.nichetoolkit/ossfile-toolkit-starter)](https://central.sonatype.com/search?smo=true&q=ossfile-toolkit-starter&namespace=io.github.nichetoolkit)
[![Nexus Release](https://img.shields.io/nexus/r/io.github.nichetoolkit/ossfile-toolkit-starter?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/content/repositories/releases/io/github/nichetoolkit/ossfile-toolkit-starter/)
[![Nexus Snapshot](https://img.shields.io/nexus/s/io.github.nichetoolkit/ossfile-toolkit-starter?server=https%3A%2F%2Fs01.oss.sonatype.org)](https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/nichetoolkit/ossfile-toolkit-starter/)
![Tests](https://github.com/NicheToolkit/ossfile-toolkit/workflows/Tests/badge.svg)

## Maven Central Repository

- [Maven Central Repository](https://search.maven.org/search?q=io.github.nichetoolkit)

- [Sonatype Central Repository](https://central.sonatype.com/search?q=io.github.nichetoolkit)

## Dependent & Environment

> [Spring Boot](https://spring.io/projects/spring-boot) 4.0.0.RELEASE\
> [Maven](https://maven.apache.org/) 3.6.3+\
> [JDK](https://www.oracle.com/java/technologies/downloads/#java17) 17\
> [PostgreSQL](https://www.postgresql.org/) 10.0+

## Wiki Reference

[Wiki Reference](https://github.com/NicheToolkit/ossfile-toolkit/wiki): https://github.com/NicheToolkit/ossfile-toolkit/wiki

## Instructions

### Maven Usages

#### ossfile-toolkit-core

* Maven (`pom.xml`)

```xml

<dependency>
    <groupId>io.github.nichetoolkit</groupId>
    <artifactId>ossfile-toolkit-core</artifactId>
    <version>4.1.2-SNAPSHOT</version>
</dependency>
```

#### ossfile-toolkit-minio

* Maven (`pom.xml`)

```xml

<dependency>
    <groupId>io.github.nichetoolkit</groupId>
    <artifactId>ossfile-toolkit-minio</artifactId>
    <version>4.1.2-SNAPSHOT</version>
</dependency>
```

#### ossfile-toolkit-aliyun

* Maven (`pom.xml`)

```xml

<dependency>
    <groupId>io.github.nichetoolkit</groupId>
    <artifactId>ossfile-toolkit-aliyun</artifactId>
    <version>4.1.2-SNAPSHOT</version>
</dependency>
```

#### ossfile-toolkit-amazon

* Maven (`pom.xml`)

```xml

<dependency>
    <groupId>io.github.nichetoolkit</groupId>
    <artifactId>ossfile-toolkit-amazon</artifactId>
    <version>4.1.2-SNAPSHOT</version>
</dependency>
```

### Configure Properties

#### ossfile configuration

* prefix

>
> nichetoolkit.ossfile
>

* values

|       value       |         type          | defaultValue |                       description                        |
|:-----------------:|:---------------------:|:------------:|:--------------------------------------------------------:|
|    `provider`     | `OssfileProviderType` |   `MINIO`    |       the provider type on oss file configuration.       |
|    `endpoint`     |       `String`        |              |         the endpoint on oss file configuration.          |
|   `access-key`    |       `String`        |              |        the access key on oss file configuration.         |
|   `secret-key`    |       `String`        |              |        the secret key on oss file configuration.         |
|     `region`      |       `String`        |              |          the region on oss file configuration.           |
|     `expire`      |        `Long`         |    `3600`    | the  credentials token expire on oss file configuration. |
|    `role-name`    |       `String`        |              |   the credentials role name on oss file configuration.   |
|    `role-arn`     |       `String`        |              |   the credentials role arn on oss file configuration.    |
|     `bucket`      |       `String`        |              |      the default bucket on oss file configuration.       |
|     `prefix`      |       `String`        |              |          the prefix on oss file configuration.           |
|   `bulk-prefix`   |       `String`        |    `bulk`    |          the prefix on oss file configuration.           |
| `preview-prefix`  |       `String`        |  `preview`   |      the preview prefix on oss file configuration.       |
| `image.compress`  |        `Long`         |   `102400`   |   the image compress size on oss image configuration.    |
|  `image.quality`  |       `Double`        |    `0.5`     |    the image quality size on oss image configuration.    |
|   `image.scale`   |       `Double`        |    `0.5`     |     the image scale size on oss image configuration.     |
| `allowed.origins` |      `String[]`       |              |      the allowed origins on oss file configuration.      |
| `allowed.headers` |      `String[]`       |              |      the allowed headers on oss file configuration.      |

* properties

```properties
nichetoolkit.ossfile.provider=MINIO
nichetoolkit.ossfile.endpoint=http://127.0.0.1:9000
nichetoolkit.ossfile.access-key=minioadmin
nichetoolkit.ossfile.secret-key=minioadmin
nichetoolkit.ossfile.region=us-east-1
nichetoolkit.ossfile.expire=3600
nichetoolkit.ossfile.role-name=
nichetoolkit.ossfile.role-arn=
nichetoolkit.ossfile.bucket=ossfile-bucket
nichetoolkit.ossfile.prefix=ossfile
nichetoolkit.ossfile.bulk-prefix=bulk
nichetoolkit.ossfile.preview-prefix=preview
nichetoolkit.ossfile.image.compress=102400
nichetoolkit.ossfile.image.quality=0.5
nichetoolkit.ossfile.image.scale=0.5
```

## Test Example

[ossfile-toolkit-example](https://github.com/NicheToolkit/ossfile-toolkit/tree/master/ossfile-toolkit-example)

## License

[Apache License](https://www.apache.org/licenses/LICENSE-2.0)

## Dependencies

[Rest-toolkit](https://github.com/NicheToolkit/rest-toolkit)

[Rice-toolkit](https://github.com/NicheToolkit/rice-toolkit)

[Mybatis-toolkit](https://github.com/NicheToolkit/mybatis-toolkit)

[Ossfile-toolkit](https://github.com/NicheToolkit/ossfile-toolkit)

[Spring Boot](https://github.com/spring-projects/spring-boot)
