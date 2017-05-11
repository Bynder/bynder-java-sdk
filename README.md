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
```

#### Asset Bank Service
```java
Observable<Response<List<Brand>>> getBrands();

Observable<Response<List<Tag>>> getTags();

Observable<Response<Map<String, Metaproperty>>> getMetaproperties(MetapropertyQuery metapropertyQuery);

Observable<Response<List<Media>>> getMediaList(MediaQuery mediaQuery);

Observable<Response<Media>> getMediaInfo(MediaInfoQuery mediaInfoQuery);

Observable<Response<DownloadUrl>> getMediaDownloadUrl(MediaDownloadQuery mediaDownloadQuery);

Observable<Response<Void>> setMediaProperties(MediaPropertiesQuery mediaPropertiesQuery);

Observable<SaveMediaResponse> uploadFile(UploadQuery uploadQuery);
```

## Installation

### Using lastest release
The most recent release is Bynder Java SDK 1.0.1, released May 11, 2017.
- API Docs: http://www.javadoc.io/doc/com.bynder/bynder-java-sdk/1.0.1

To add a dependency on the SDK using Maven, use the following:
```xml
<dependency>
  <groupId>com.bynder</groupId>
  <artifactId>bynder-java-sdk</artifactId>
  <version>1.0.1</version>
</dependency>
```
To add a dependency using Gradle:
```
dependencies {
  compile 'com.bynder:bynder-java-sdk:1.0.1'
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

Build the project from its root with the following Maven command (skipping the GPG signing):
```bash
$ mvn clean install -Dgpg.skip
```

This command tells Maven to build all the modules and to install it in the local repository. At this point all the integrations tests will be skipped.

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
