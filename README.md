# Bynder Java SDK

The main goal of this SDK is to speed up the integration of Bynder customers who use JAVA. Making it easier to connect to the Bynder API (http://docs.bynder.apiary.io) and executing requests on it.

## Current status

At the moment this JAVA SDK provides a default library with the following methods defined in the class <b>com.getbynder.sdk.BynderApiService</b>:

```java
public UserAccessData login(final String username, final String password);

public Map<String, String> getRequestToken();

public Map<String, String> getAccessToken(final String requestTokenKey, final String requestTokenSecret);

public List<Category> getCategories();

public List<Tag> getTags();

public Map<String, Metaproperty> getMetaproperties();

public List<MediaAsset> getMediaAssets(final String type, final String keyword, final Integer limit, final Integer offset, final String propertyOptionId);

public MediaAsset getMediaAssetById(final String id, final Boolean versions);

public List<MediaAsset> getImageAssets(final String keyword, final Integer limit, final Integer offset);

public List<MediaAsset> getImageAssets(final String keyword, final Integer limit, final Integer page, final List<String> propertyOptionIds);

public List<MediaAsset> getImageAssetsByMetapropertyId(final String propertyOptionId);

public int getImageAssetsTotal();

public int getImageAssetsTotal(final String keyword, final List<String> propertyOptionIds);

public int setMediaAssetProperties(final String id, final String name, final String description, final String copyright, final Boolean archive, final String datePublished);

public int addMetapropertyToAsset(final String assetId, final String metapropertyId, final String... optionsIds);
```

## Installation

Components used to install and run the project:
* Java JDK (version 1.7.0_80)
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
Before executing any request to the Bynder API, it is necessary to instantiate the class <b>BynderApiService</b>.

The following example shows how to use the <b>BynderApiServiceBuilder</b> to construct a <b>BynderApiService</b> instance:
```java
BynderApiService bynderApiService = new BynderApiServiceBuilder()
    .setBaseUrl("https://example.getbynder.com/api/")
    .setConsumerKey("your consumer key")
    .setConsumerSecret("your consumer secret")
    .setAccessTokenKey("your access token key")
    .setAccessTokenSecret("your access token secret")
    .create();
```

It is also possible to construct a <b>BynderApiService</b> instance with login, in case you don't have access token for your environment:
```java
BynderApiService bynderApiService = new BynderApiServiceBuilder()
    .setBaseUrl("https://example.getbynder.com/api/")
    .setConsumerKey("your consumer key")
    .setConsumerSecret("your consumer secret")
    .setRequestTokenKey("your request token key")
    .setRequestTokenSecret("your request token secret")
    .createWithLogin("your username", "your password");
```

<b>Important:</b> The order of invocation of the setter methods does not matter, but they are all mandatory.

After instantiating the <b>BynderApiService</b> class successfully it is possible to call any of the methods listed in the section <b>Current Status</b>. Example:

```java
Map<String, Metaproperty> metaproperties = bynderApiService.getMetaproperties();
```

## Running the integration tests

In order to be able to run the integration tests against the Bynder API you need to create a new properties file called "secret.properties" like the one shown below. Where your Bynder API base url shall have this structure: <i>https://&#91;accountdomain&#93;/api/</i>
```bash
$ vi bynder-java-sdk/src/main/resources/secret.properties

# bynder api base url
BASE_URL=<your bynder api base url>

# bynder api login credentials
USERNAME=<your username>
PASSWORD=<your password>

# bynder api request tokens
REQUEST_TOKEN_KEY=<your request token key>
REQUEST_TOKEN_SECRET=<your request token secret>

# bynder api access tokens
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
<b>Note:</b> Before the integration tests are executed, an instance of the <b>BynderApiService</b> class will be created using the base url, credentials and tokens defined in the "secret.properties" file.

After running this command, if everything is working fine, you should get a similar output as the one shown below, telling you all the tests ran successfully.

<b>Important:</b> It can also happen that some tests are skipped and in that case the reason why they were skipped it will be printed in the console.
```bash
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.getbynder.sdk.BynderApiServiceBuilderIT
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.589 sec - in com.getbynder.sdk.BynderApiServiceBuilderIT
Running com.getbynder.sdk.BynderApiServiceIT
Tests run: 20, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 36.498 sec - in com.getbynder.sdk.BynderApiServiceIT

Results :

Tests run: 23, Failures: 0, Errors: 0, Skipped: 0

```
