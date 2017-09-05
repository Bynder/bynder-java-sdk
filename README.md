# Bynder Java SDK

The main goal of this SDK is to speed up the integration of Bynder customers who use JAVA. Making it easier to connect to the Bynder API (http://docs.bynder.apiary.io/) and executing requests on it.

## Current status

At the moment this SDK provides a default library with the following methods:

#### Login
```java
Observable<User> login(String username, String password);

Observable<String> getRequestToken();

URL getAuthoriseUrl(final String callbackUrl);

Observable<String> getAccessToken();

void logout();

Observable<Response<List<Derivative>>> getDerivatives();
```

#### Asset Bank Service
```java
Observable<Response<List<Brand>>> getBrands();

Observable<Response<List<Tag>>> getTags();

Observable<Response<Map<String, Metaproperty>>> getMetaproperties(MetapropertyQuery metapropertyQuery);

Observable<Response<List<Media>>> getMediaList(MediaQuery mediaQuery);

Observable<Response<Media>> getMediaInfo(MediaInfoQuery mediaInfoQuery);

Observable<Response<Void>> setMediaProperties(MediaPropertiesQuery mediaPropertiesQuery);

Observable<Response<Void>> deleteMedia(MediaDeleteQuery mediaDeleteQuery);

Observable<Response<DownloadUrl>> getMediaDownloadUrl(MediaDownloadQuery mediaDownloadQuery);

Observable<Response<Usage>> createUsage(UsageCreateQuery usageCreateQuery);

Observable<Response<List<Usage>>> getUsage(UsageQuery usageQuery);

Observable<Response<Void>> deleteUsage(UsageDeleteQuery usageDeleteQuery);

Observable<SaveMediaResponse> uploadFile(UploadQuery uploadQuery);

Observable<UploadProgress> uploadFileWithProgress(UploadQuery uploadQuery);
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

### Using lastest release
The most recent release is Bynder Java SDK 1.0.5, released Aug 27, 2017.
- API Docs: http://www.javadoc.io/doc/com.bynder/bynder-java-sdk/1.0.5

To add a dependency on the SDK using Maven, use the following:
```xml
<dependency>
  <groupId>com.bynder</groupId>
  <artifactId>bynder-java-sdk</artifactId>
  <version>1.0.5</version>
</dependency>
```
To add a dependency using Gradle:
```
dependencies {
  compile 'com.bynder:bynder-java-sdk:1.0.5'
}
```

### Using source code
Components used to install and run the project:
* Java JDK (version 1.8.0_60)
* Apache Maven 3.3.3

**Important:** Don't forget to define the environmental variables for Java and Maven!

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
Before executing any request to the Bynder API, it is necessary to instantiate the class **BynderService**.

The following example shows how to use the **BynderServiceImpl.create(Settings settings)** static method to create an instance of **BynderService** using the **Settings** object as parameter:
```java
BynderService bynderService = BynderServiceImpl.create(new Settings("https://example.bynder.com",
                                                                    "consumer key",
                                                                    "consumer secret",
                                                                    "token",
                                                                    "token secret"));
```

If you need to configure extra HTTP connection settings like SSL context (to allow the implementation of mutual SSL), timeouts and custom interceptor, create an instance of **HttpConnectionSettings** and add it to the **Settings** constructor:
```java
HttpConnectionSettings httpConnectionSettings = new HttpConnectionSettings(sslContext,
                                                                           trustManager,
                                                                           customInterceptor,
                                                                           readTimeoutSeconds,
                                                                           connectTimeoutSeconds,
                                                                           retryOnConnectionFailure);

BynderService bynderService = BynderServiceImpl.create(new Settings("https://example.bynder.com",
                                                                    "consumer key",
                                                                    "consumer secret",
                                                                    "token",
                                                                    "token secret",
                                                                    httpConnectionSettings));
```

After instantiating the **BynderService** class successfully it is possible to call any of the methods listed in the section **Current Status**. Example:

#### Reactive way to get the Observable
```java
// Get an instance of the asset bank service to perform Bynder Asset Bank operations.
AssetBankService assetBankService = bynderService.getAssetBankService();

// Get all tags (request without query)
Observable<Response<List<Tag>>> tagsObservable = assetBankService.getTags();

// Get media (request with query)
Observable<Response<List<Media>>> mediaObservable = assetBankService.getMediaList(new MediaQuery().setType(MediaType.IMAGE).setLimit(100).setPage(1));
```

#### Synchronous way to wait for the Observable to complete and emit the single item
```java
// Get an instance of the asset bank service to perform Bynder Asset Bank operations.
AssetBankService assetBankService = bynderService.getAssetBankService();

// Get all tags (request without query)
Response<List<Tag>> tagsResponse = assetBankService.getTags().blockingSingle();

// Get media (request with query)
Response<List<Media>> mediaResponse = assetBankService.getMediaList(new MediaQuery().setType(MediaType.IMAGE).setLimit(100).setPage(1)).blockingSingle();
```
