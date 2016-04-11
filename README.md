# Bynder Java SDK

The main goal of this SDK is to speed up the integration of Bynder customers who use JAVA. Making it easier to connect to the Bynder API v4 (http://docs.bynder.apiary.io) and executing requests on it.

## Current status

At the moment this JAVA SDK provides a default library with the following methods defined in the class <b>com.getbynder.sdk.BynderService</b>:

```java
public UserAccessData login(final String username, final String password);

public Map<String, String> getRequestToken();

public Map<String, String> getAccessToken(final String requestToken, final String requestTokenSecret);

public List<Category> getCategories();

public List<Tag> getTags();

public List<MediaAsset> getAllImageAssets();

public List<MediaAsset> getImageAssets(final int limit, final int offset);

public List<MediaAsset> getImageAssetsByKeyword(final String keyword);

public List<MediaAsset> getImageAssetsByMetapropertyId(final String metapropertyId);

public int getImageAssetsTotal();

public List<MediaAsset> getAllMediaAssets();

public List<MediaAsset> getMediaAssets(final int limit, final int offset);

public MediaAsset getMediaAssetById(final String id, final Boolean includeVersions);

public int getAllMediaAssetsTotal();

public int setMediaAssetProperties(final MediaAsset mediaAsset);

public List<Metaproperty> getAllMetaproperties();

public int addMetapropertyToAsset(final String assetId, final String metapropertyId, final String... optionsIds);
```

## Installation

Components used to install and run the project:
* Java JDK (version 1.7.0_80)
* Apache Maven 3.3.3

<b>Important:</b> Don't forget to define the environmental variables for Java and Maven

### Installation steps
Clone the repository:
```bash
$ git clone git@github.com:Bynder/bynder-java-sdk.git
```
Access the "config.properties" file:
```bash
$ vi bynder-java-sdk/src/main/resources/config.properties
```

Change the <b>BASE_URL</b> property to your Bynder API v4 base URL.
<b>Example:</b> <i>https://&#91;accountdomain&#93;/api/v4/</i>

Create a new properties file called "secret.properties" with the following structure:
```bash
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
<b>Important:</b> You just need to fill the <b>USERNAME</b>, <b>PASSWORD</b>, <b>REQUEST_TOKEN_KEY</b> and <b>REQUEST_TOKEN_SECRET</b> properties if you need to login to the Bynder API in order get the access tokens. If you already possess the access tokens, just insert them in the <b>ACCESS_TOKEN_KEY</b> and <b>ACCESS_TOKEN_SECRET</b> properties. <b>Don't forget to add this file to .gitignore</b>.

Build the project from its root with the following Maven command:
```bash
$ mvn clean install -DskipTests
```
This command tells Maven to build all the modules and to install it in the local repository, skipping the tests.

## How does it work
Before executing any request to the Bynder API, it is necessary to instantiate the class <b>BynderService</b>.

For this purpose there are three different constructors you can use:
```java
public BynderService();

public BynderService(final String username, final String password);

public BynderService(final String baseUrl, final String username, final String password);
```
The first constructor, the one without parameters, it is supposed to be used when you don't need to login to the Bynder API because you already have the access tokens (explained previously). Meaning that in this case the properties <b>ACCESS_TOKEN_KEY</b> and <b>ACCESS_TOKEN_SECRET</b> of your "secret.properties" file were already filled in with those same access tokens.

The second constructor takes two String parameters: <b>username</b> and <b>password</b>. Gets the <b>BASE_URL</b> property from the "config.properties" file and stores it in an instance variable, calls the <b>getUserAccessData</b> method that receives the <b>username</b> and <b>password</b> as arguments and logins to the Bynder API to get the access tokens that are necessary to make the requests.

The third and last constructor takes three String parameters: <b>baseUrl</b>, <b>username</b> and <b>password</b>. Stores the <b>baseUrl</b> in an instance variable and calls the <b>getUserAccessData</b> method that receives the <b>username</b> and <b>password</b> as arguments and logins to the Bynder API to get the access tokens that are necessary to make the requests.

As shown above the access tokens that are retrieved by the Bynder API after a successful login are stored in the instance variable <b>userAccessData</b>.

Code example to instantiate the <b>BynderService</b> class using this constructor:
```java
BynderService bynderService = new BynderService("https://example.getbynder.com/api/v4/", "test", "12345");
```
In the example above the <b>BynderService</b> class is instantiated with the <b>baseUrl</b> "ht&#8203;tp://example.getbynder.com/api/v4/", <b>username</b> "test" and <b>password</b> "12345".

After instantiating the <b>BynderService</b> class successfully it is possible to call any of the methods listed in the section <b>Current Status</b>. Example:

```java
BynderService bynderService = new BynderService("https://example.getbynder.com/api/v4/", "test", "12345");

List<MediaAsset> allImageAssets = bynderService.getAllImageAssets();
```

## Running the tests
To run the integration tests defined in the class <b>com.getbynder.sdk.BynderServiceIT</b> against the Bynder API, you should execute the following Maven command in the project's root:
```bash
$ mvn verify
```
<b>Note:</b> Before the integration tests are executed, an instance of the <b>BynderService</b> class will be created using the access tokens defined in the "secret.properties" file.

After running this command, if everything is working fine, you should get a similar output as the one shown below, telling you all the tests ran successfully. It can also happen that some tests are skipped and in that case the reason why they were skipped it will be printed in the console.
```bash
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.getbynder.sdk.BynderServiceIT
Tests run: 20, Failures: 0, Errors: 0, Skipped: 0, ... - in com.getbynder.sdk.BynderServiceIT

Results :

Tests run: 20, Failures: 0, Errors: 0, Skipped: 0

```
