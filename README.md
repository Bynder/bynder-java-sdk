# Bynder Java SDK

The main goal of this SDK is to speed up the integration of Bynder customers who use JAVA. Making it easier to connect to the Bynder API (http://docs.bynder.apiary.io/) and executing requests on it.

## Current status

At the moment this SDK provides a default library with the following methods:

#### Login
```java
User login(String username, String password);

Map<String, String> getRequestToken();

Map<String, String> getAccessToken();

String getAuthoriseUrl(final String callbackUrl);

void logout();
```

#### Asset Bank Manager
```java
Observable<Response<List<Brand>>> getBrands();

Observable<Response<List<Tag>>> getTags();

Observable<Response<Map<String, Metaproperty>>> getMetaproperties(MetapropertyQuery metapropertyQuery);

Observable<Response<List<Media>>> getMediaList(MediaQuery mediaQuery);

Observable<Response<Media>> getMediaInfo(MediaInfoQuery mediaInfoQuery);

Observable<Response<DownloadUrl>> getMediaDownloadUrl(MediaDownloadQuery mediaDownloadQuery);

Observable<Response<Void>> setMediaProperties(MediaPropertiesQuery mediaPropertiesQuery);

Observable<Response<Void>> addMetapropertyToMedia(AddMetapropertyToMediaQuery addMetapropertyToMediaQuery);

Response<Void> uploadFile(UploadQuery uploadQuery) throws BynderUploadException, InterruptedException;
```

## Installation

Components used to install and run the project:
* Java JDK (version 1.8.0_60)
* Apache Maven 3.3.3

**Important:** Don't forget to define the environmental variables for Java and Maven!

### Installation steps
Clone the repository:
```bash
$ git clone git@github.com:Bynder/bynder-java-sdk.git
```

Build the project from its root with the following Maven command:
```bash
$ mvn clean install
```

This command tells Maven to build all the modules and to install it in the local repository. At this point all the integrations tests will be skipped.

## How does it work
Before executing any request to the Bynder API, it is necessary to instantiate the class **BynderService**.

The following example shows how to use the **BynderServiceImpl.create** static method to create an instance of **BynderService** using the **Settings** object as parameter:
```java
BynderService bynderService = BynderServiceImpl.create(new Settings("https://example.getbynder.com/api/",
                                                                    "your consumer key",
                                                                    "your consumer secret",
                                                                    "your access token key",
                                                                    "your access token secret"));
```

After instantiating the **BynderService** class successfully it is possible to call any of the methods listed in the section **Current Status**. Example:

#### Reactive way to get the Observable
```java
Observable<Response<List<Tag>>> observable = bynderService.getTags();
```

#### Synchronous way to wait for the Observable to complete and emit the single item
```java
Response<List<Tag>> response = bynderService.getTags().blockingSingle();
```
