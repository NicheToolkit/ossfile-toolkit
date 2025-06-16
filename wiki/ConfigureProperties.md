
# ossfile configuration

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
