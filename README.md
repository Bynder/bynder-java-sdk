# Bynder Java SDK

![Tests](https://github.com/Bynder/bynder-java-sdk/workflows/Tests/badge.svg)
![Release](https://github.com/Bynder/bynder-java-sdk/workflows/Release/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/Bynder/bynder-java-sdk/badge.svg?branch=master)](https://coveralls.io/github/Bynder/bynder-java-sdk?branch=master)

The main goal of this SDK is to speed up the integration of Bynder customers who use Java, making it easier to connect to the Bynder API (http://docs.bynder.apiary.io/) and executing requests on it.

## Current status

At the moment this SDK provides a default library with the following methods:

#### Bynder Service

```java
OAuthService getOAuthService();

AssetService getAssetService();

CollectionService getCollectionService();

Observable<Response<List<Derivative>>> getDerivatives();
```

#### OAuth Service

```java
URL getAuthorizationUrl(final String state);

Single<Token> getAccessToken(final String code);

Single<Token> getClientCredentials();

Single<Token> refreshAccessToken();
```

#### Asset Bank Service

```java
Observable<Response<List<Brand>>> getBrands();

Observable<Response<List<Tag>>> getTags();

Observable<Response<Map<String, Metaproperty>>> getMetaproperties(MetapropertyQuery metapropertyQuery);

Observable<Response<List<Media>>> getMediaList(MediaQuery mediaQuery);

Observable<Response<Media>> getMediaInfo(MediaInfoQuery mediaInfoQuery);

Observable<Response<Void>> modifyMedia(MediaPropertiesQuery mediaPropertiesQuery);

Observable<Response<Void>> deleteMedia(MediaDeleteQuery mediaDeleteQuery);

Observable<Response<DownloadUrl>> getMediaDownloadUrl(MediaDownloadQuery mediaDownloadQuery);

Observable<Response<Usage>> createUsage(UsageCreateQuery usageCreateQuery);

Observable<Response<List<Usage>>> getUsage(UsageQuery usageQuery);

Observable<Response<Void>> deleteUsage(UsageDeleteQuery usageDeleteQuery);

Observable<Response<List<Smartfilter>>> getSmartfilters();

Single<SaveMediaResponse> uploadFile(NewAssetUploadQuery uploadQuery);

Single<SaveMediaResponse> uploadFile(ExistingAssetUploadQuery uploadQuery);
```

#### Collection Service

```java
Observable<Response<List<Collection>>> getCollections(CollectionQuery collectionQuery);

Observable<Response<Collection>> getCollectionInfo(CollectionInfoQuery collectionInfoQuery);

Observable<Response<Void>> createCollection(CollectionCreateQuery collectionCreateQuery);

Observable<Response<Void>> deleteCollection(CollectionInfoQuery collectionInfoQuery);

Observable<Response<List<String>>> getCollectionMediaIds(CollectionInfoQuery collectionInfoQuery);

Observable<Response<Void>> addMediaToCollection(CollectionAddMediaQuery collectionAddMediaQuery);

Observable<Response<Void>> removeMediaFromCollection(CollectionRemoveMediaQuery collectionRemoveMediaQuery);

Observable<Response<Void>> shareCollection(CollectionShareQuery collectionShareQuery);
```

## Installation

### Using latest release

The most recent release is Bynder Java SDK 2.2.7.

- API Docs: http://www.javadoc.io/doc/com.bynder/bynder-java-sdk/2.2.7

To add a dependency on the SDK using Maven, use the following:

```xml
<dependency>
  <groupId>com.bynder</groupId>
  <artifactId>bynder-java-sdk</artifactId>
  <version>2.2.7</version>
</dependency>
```

To add a dependency using Gradle:

```
dependencies {
  implementation 'com.bynder:bynder-java-sdk:2.2.7'
}
```

### Using source code

Components used to install and run the project:

- Java version 1.8.0_221
- Apache Maven 3.6.0

**Important:** Don't forget to define the environment variables for Java and Maven!

Clone the repository:

```bash
$ git clone git@github.com:Bynder/bynder-java-sdk.git
```

Build the project from its root with the following Maven command (skipping the GPG signing and Javadocs generation):

```bash
$ mvn clean install -Dgpg.skip -Dmaven.javadoc.skip
```

This command tells Maven to build all the modules and to install it in the local repository. At this point all the integrations tests will be skipped.

### Using ProGuard

If you are using ProGuard, remember to add the following lines to your ProGuard rules file.

```java
# Bynder Java SDK
-keep class com.bynder.sdk.model.** { *; }
-keep class com.bynder.sdk.query.** { *; }
```

## How does it work

Before executing any request to the Bynder API, it is necessary to instantiate the class **BynderClient**.

The following examples show how to use the **BynderClient.Builder.create(final Configuration configuration)** static method to create an instance of the **BynderClient** using the **Configuration** object as parameter.

### Instantiate BynderClient with a Permanent Token

```java
BynderClient bynderClient = BynderClient.Builder.create(new Configuration.Builder("Bynder portal base URL").setPermanentToken("Permanent token")).build());
```

### Instantiate BynderClient with a OAuth application settings

```java
BynderClient bynderClient = BynderClient.Builder.create(new Configuration.Builder("Bynder portal base URL").setOAuthSettings("Client id", "Client secret", "Redirect URI")).build());
```

After instantiating the **BynderClient** class successfully with your OAuth application settings the OAuth flow needs to be executed, using the methods from the **OAuthService**, in order to authorize the SDK client with Bynder and get an access token to perform the API requests.

To check how to execute the OAuth flow, please see [AppSample.java](src/main/java/com/bynder/sdk/sample/AppSample.java).

After the SDK client had been authorized successfully it is possible to call any of the methods listed in the section **Current Status**. Example:

#### Reactive way to get the Observable

```java
// Get an instance of the asset bank service to perform Bynder Asset Bank operations.
AssetService assetService = bynderClient.getAssetService();

// Get all tags (request without query)
Observable<Response<List<Tag>>> tagsObservable = assetService.getTags();

// Get media (request with query)
Observable<Response<List<Media>>> mediaObservable = assetService.getMediaList(new MediaQuery().setType(MediaType.IMAGE).setLimit(100).setPage(1));
```

#### Synchronous way to wait for the Observable to complete and emit the single item

```java
// Get an instance of the asset bank service to perform Bynder Asset Bank operations.
AssetService assetService = bynderClient.getAssetBankService();

// Get all tags (request without query)
Response<List<Tag>> tagsResponse = assetService.getTags().blockingSingle();

// Get media (request with query)
Response<List<Media>> mediaResponse = assetService.getMediaList(new MediaQuery().setType(MediaType.IMAGE).setLimit(100).setPage(1)).blockingSingle();
```
