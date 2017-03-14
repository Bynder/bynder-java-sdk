# Bynder Java SDK

The main goal of this SDK is to speed up the integration of Bynder customers who use JAVA. Making it easier to connect to the Bynder API (http://docs.bynder.apiary.io/, http://docs.bynderapiv5.apiary.io/) and executing requests on it.

## Current status

At the moment this SDK provides a default library with the following methods:

#### Login
```java
User login(String username, String password);

void getRequestToken();

void getAccessToken();

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

void uploadFile(UploadQuery uploadQuery) throws BynderUploadException, IOException, InterruptedException, RuntimeException;
```

## Installation

Components used to install and run the project:
* Java JDK (version 1.8.0_60)
* Apache Maven 3.3.3

<b>Important:</b> Don't forget to define the environmental variables for Java and Maven!

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
Before executing any request to the Bynder API, it is necessary to instantiate the class <b>BynderService</b>.

The following example shows how to use the <b>BynderServiceImpl.create</b> static method to create an instance of <b>BynderService</b> using the <b>Settings</b> object as parameter:
```java
BynderService bynderService = BynderServiceImpl.create(new Settings("https://example.getbynder.com/api/",
                                                                    "your consumer key",
                                                                    "your consumer secret",
                                                                    "your access token key",
                                                                    "your access token secret"));
```

After instantiating the <b>BynderService</b> class successfully it is possible to call any of the methods listed in the section <b>Current Status</b>. Example:

#### Reactive way to get the Observable
```java
Observable<Response<List<Tag>>> observable = bynderService.getTags();
```

#### Synchronous way to wait for the Observable to complete and emit the single item
```java
Response<List<Tag>> response = bynderService.getTags().blockingSingle();
```

## Running the integration tests

In order to be able to run the integration tests against the Bynder API you need to create a new properties file called "app.properties" like the one shown below. Where your Bynder API base url shall have this structure: <i>https://&#91;accountdomain&#93;/api/</i>
```bash
$ vi bynder-java-sdk/src/main/resources/app.properties

# bynder api base url
BASE_URL=<your bynder api base url>

# bynder api tokens
CONSUMER_KEY=<your consumer key>
CONSUMER_SECRET=<your consumer secret>
ACCESS_TOKEN_KEY=<your access token key>
ACCESS_TOKEN_SECRET=<your access token secret>
```
<b>Important:</b> Don't forget to add this file to your .gitignore.

To run the integration tests you can execute the following Maven command in the project's root:
```bash
$ mvn verify
```
<b>Note:</b> Before the integration tests are executed, an instance of the <b>BynderService</b> class will be created using the base url, credentials and tokens defined in the "app.properties" file.

After running this command, if everything is working fine, you should get a similar output as the one shown below, telling you all the tests ran successfully.

<b>Important:</b> It can also happen that some tests are skipped and in that case the reason why they were skipped it will be printed in the console.
```bash
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.bynder.sdk.service.AssetBankManagerIT
Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 52.866 sec - in com.bynder.sdk.service.AssetBankManagerIT
Running com.bynder.sdk.service.BynderServiceIT
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.377 sec - in com.bynder.sdk.service.BynderServiceIT

Results :

Tests run: 10, Failures: 0, Errors: 0, Skipped: 0

```
