# Bynder Java SDK

![Tests](https://github.com/Bynder/bynder-java-sdk/workflows/Tests/badge.svg)
![Release](https://github.com/Bynder/bynder-java-sdk/workflows/Release/badge.svg)
[![Coverage Status](https://coveralls.io/repos/github/Bynder/bynder-java-sdk/badge.svg?branch=master)](https://coveralls.io/github/Bynder/bynder-java-sdk?branch=master)
![Maven Central](https://img.shields.io/maven-central/v/com.bynder/bynder-java-sdk)

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

Observable<Token> getAccessToken(final String code);

Observable<Token> refreshAccessToken();
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

### Using latest release

The most recent release is Bynder Java SDK 2.2.27.1.

- API Docs: http://www.javadoc.io/doc/com.bynder/bynder-java-sdk/2.2.27.1

To add a dependency on the SDK using Maven, use the following:

```xml
<dependency>
  <groupId>com.bynder</groupId>
  <artifactId>bynder-java-sdk</artifactId>
  <version>2.2.27.1</version>
</dependency>
```

To add a dependency using Gradle:

```
dependencies {
  implementation 'com.bynder:bynder-java-sdk:2.2.27.1'
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

### Sample Files Functionality Testing

Classes within `sample` contain code to execute corresponding functionalities. The purpose is to demonstrate how methods 
are called and provide a convenient method to execute functions.

Within `src/main/resources` create an `app.properties` file. This file will be referenced from sample files. 

Make sure all values are populated correctly before running sample files.

Example `app.properties` file content:

```java
# permanent token if using permanent token auth
PERMANENT_TOKEN=<your auth permanent token>
# portal url
BASE_URL=https://portal.bynder.com
# OAuth info
REDIRECT_URI=https://google.com
CLIENT_ID=<your OAuth2 client id>
CLIENT_SECRET=<your OAuth2 client secret>
# media id for info
MEDIA_ID_FOR_INFO=5B8357A7-5DEB-4BC7-9CFBDEE1ECE120A9
# media id for renaming
MEDIA_ID_FOR_RENAME=5B8357A7-5DEB-4BC7-9CFBDEE1ECE120A9
# media id for removal
MEDIA_ID_FOR_REMOVAL=946A1800-6298-4201-AEB8F2830B07400E
# collection id to get info for
GET_COLLECTION_INFO_ID=615F03BB-D986-4786-B2C085D2F0718230
# collection id to share
SHARE_COLLECTION_ID=615F03BB-D986-4786-B2C085D2F0718230
# recipient to receive shared collection
COLLECTION_SHARE_RECIPIENT=recipient@mail.com
# media id to add to collection
ADD_MEDIA_TO_COLLECTION_MEDIA_ID=C078E8EE-C13A-4DA5-86EC8D6F335364EB
# collection id for media to be added to
ADD_MEDIA_TO_COLLECTION_COLLECTION_ID=615F03BB-D986-4786-B2C085D2F0718230
# collection id to remove asset from
REMOVE_FROM_COLLECTION_ID=615F03BB-D986-4786-B2C085D2F0718230
# media id to remove from collection
REMOVE_MEDIA_ID_FROM_COLLECTION=C078E8EE-C13A-4DA5-86EC8D6F335364EB
# media id used for creating asset usage
MEDIA_ID_FOR_ASSET_USAGE=C078E8EE-C13A-4DA5-86EC8D6F335364EB
# integration id used for asset usage
INTEGRATION_ID_FOR_ASSET_USAGE=0191a303-9d99-433e-ada4-d244f37e1d7d
```
Within each sample file, OAuth credentials are read in from `app.properties`. 
This will prompt the browser to open to retrieve an access code and then redirected to the redirect URI. 
Access code is then provided to terminal prompt to retrieve an access token for API calls afterward.


#### Maven

Make sure `mvn` CLI is installed.

From root directory, dependencies can be installed from `pom.xml` using command:
```bash
mvn clean install -Dgpg.skip -Dmaven.javadoc.skip
```

#### Brands Sample

Execute `BrandsSample.java` file with command

```bash
mvn compile exec:java -Dexec.mainClass=com.bynder.sdk.sample.BrandsSample
```

Methods Used:
* getBrands()

#### Collections Sample

Execute `CollectionsSample.java` file with command

```bash
mvn compile exec:java -Dexec.mainClass=com.bynder.sdk.sample.CollectionsSample
```

Methods Used:
* getCollections(collectionQuery)
* getCollectionInfo(collectionInfoQuery)
* createCollection(createCollectionQuery)
* shareCollection(collectionShareQuery)
* addMediaToCollection(collectionAddMediaQuery)
* getCollectionMediaIds(addMediaCollectionInfoQuery)
* removeMediaFromCollection(collectionRemoveMediaQuery)

#### Media Sample

Execute `MediaSample.java` file with command

```bash
mvn compile exec:java -Dexec.mainClass=com.bynder.sdk.sample.MediaSample
```

Methods Used:

* getMediaList(mediaQuery)
* getMediaInfo(mediaInfoQuery)
* getMediaDownloadUrl(mediaDownloadQuery)
* modifyMedia(modifyQuery)
* getMediaInfo(mediaInfoQueryRename)
* deleteMedia(mediaDeleteQuery)
* getDerivatives()

#### Metaproperties Sample

Execute `MetapropertiesSample.java` file with command

```bash
mvn compile exec:java -Dexec.mainClass=com.bynder.sdk.sample.MetapropertiesSample
```

Methods Used:
* getMetaproperties(metapropertyQuery)

#### Smart Filters Sample

Execute `SmartFiltersSample.java` file with command

```bash
mvn compile exec:java -Dexec.mainClass=com.bynder.sdk.sample.SmartFiltersSample
```

Methods Used:
* getSmartfilters()
* smartFilter.getMetaproperties()
* smartFilter.getLabels()

#### Tags Sample

Execute `TagsSample.java` file with command

```bash
mvn compile exec:java -Dexec.mainClass=com.bynder.sdk.sample.TagsSample
```

Methods Used:
* getTags()

#### Upload Sample

Execute `UploadSample.java` file with command

```bash
mvn compile exec:java -Dexec.mainClass=com.bynder.sdk.sample.UploadSample
```

Methods Used:
* uploadFile(uploadQuery)

#### Usage Sample

Execute `UsageSample.java` file with command

```bash
mvn compile exec:java -Dexec.mainClass=com.bynder.sdk.sample.UsageSample
```
Methods Used:
* createUsage(usageCreateQuery)
* getUsage(usageQuery)
* deleteUsage(usageDeleteQuery)
