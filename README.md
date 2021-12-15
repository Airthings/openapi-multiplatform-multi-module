# OpenAPI KMM demo

This repo is used to demonstrate using
[OpenAPI Generator](https://github.com/OpenAPITools/openapi-generator) in a KMM project where the
generated code is contained in a child Gradle module.

## Usage

For demonstration and testing purposes, this project contains the OpenAPI Generator repo as a
submodule. To clone the repo with an updated submodule, run the following:
```
git clone --recursive git@github.com:Airthings/openapi-multiplatform-multi-module.git
```

Update the OpenAPI generated KMM code by running:
```
(cd openapi-generator && ./mvnw clean package)
(cd openapi-generator && ./bin/generate-samples.sh bin/configs/kotlin-multiplatform.yaml -- --additional-properties=omitGradlePluginVersions=true)
```

Validate that the app compiles by running the Android app from Android Studio.

The iOS app runs as well but it currently fails to load a pet name due to KMM memory model related
issues.

<!--
#### Updating the OpenAPI Generator

```
(cd openapi-generator && ./mvnw package -DskipTests=true)
```

#### Testing Generated Code

```
(cd openapi-generator && ./mvnw integration-test -f samples/client/petstore/kotlin-multiplatform/pom.xml)
```

--!>
