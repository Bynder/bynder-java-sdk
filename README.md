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

public int getImageAssetsTotalByMetapropertyIds(final List<String> propertyOptionIds);

public int getImageAssetsTotal();

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

Create a new properties file called "secret.properties" like the one shown below. Where your bynder api base url shall have this structure: <i>https://&#91;accountdomain&#93;/api/</i>
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
<b>Important:</b> You just need to fill the <b>USERNAME</b>, <b>PASSWORD</b>, <b>REQUEST_TOKEN_KEY</b> and <b>REQUEST_TOKEN_SECRET</b> properties if you need to login to the Bynder API in order get the access tokens. If you already possess the access tokens, just insert them in the <b>ACCESS_TOKEN_KEY</b> and <b>ACCESS_TOKEN_SECRET</b> properties. Don't forget to add this file to your <b>.gitignore</b>.

Build the project from its root with the following Maven command:
```bash
$ mvn clean install -DskipTests
```
This command tells Maven to build all the modules and to install it in the local repository, skipping the tests.

## How does it work
Before executing any request to the Bynder API, it is necessary to instantiate the class <b>BynderApiService</b>.

For this purpose there are three different constructors you can use:
```java
public BynderApiService();

public BynderApiService(final String username, final String password);

public BynderApiService(final String baseUrl, final String username, final String password);
```
The first constructor, the one without parameters, it is supposed to be used when you don't need to login to the Bynder API because you already have the access tokens (explained previously). Meaning that in this case the properties <b>ACCESS_TOKEN_KEY</b> and <b>ACCESS_TOKEN_SECRET</b> of your "secret.properties" file were already filled in with those same access tokens.

The second constructor takes two String parameters: <b>username</b> and <b>password</b>. Gets the <b>BASE_URL</b> property from the "secret.properties" file, calls the <b>login</b> method that receives the <b>username</b> and <b>password</b> as arguments and logins to the Bynder API to get the access tokens that are necessary to make the requests.

The third and last constructor takes three String parameters: <b>baseUrl</b>, <b>username</b> and <b>password</b>. Uses the <b>baseUrl</b> received as parameter instead of getting it from the "secret.properties" file and calls the <b>login</b> method that receives the <b>username</b> and <b>password</b> as arguments and logins to the Bynder API to get the access tokens that are necessary to make the requests.

As shown above the access tokens that are retrieved by the Bynder API after a successful login are stored in the instance variable <b>userAccessData</b>.

Code example to instantiate the <b>BynderApiService</b> class using this constructor:
```java
BynderApiService bynderApiService = new BynderApiService("https://example.getbynder.com/api/", "test", "12345");
```
In the example above the <b>BynderApiService</b> class is instantiated with the <b>baseUrl</b> "ht&#8203;tp://example.getbynder.com/api/", <b>username</b> "test" and <b>password</b> "12345".

After instantiating the <b>BynderApiService</b> class successfully it is possible to call any of the methods listed in the section <b>Current Status</b>. Example:

```java
BynderApiService bynderApiService = new BynderApiService("https://example.getbynder.com/api/", "test", "12345");

Map<String, Metaproperty> metaproperties = bynderApiService.getMetaproperties();
```

## Running the tests
To run the integration tests defined in the class <b>com.getbynder.sdk.BynderApiServiceIT</b> against the Bynder API, you should execute the following Maven command in the project's root:
```bash
$ mvn verify
```
<b>Note:</b> Before the integration tests are executed, an instance of the <b>BynderApiService</b> class will be created using the access tokens defined in the "secret.properties" file.

After running this command, if everything is working fine, you should get a similar output as the one shown below, telling you all the tests ran successfully.

<b>Important:</b> It can also happen that some tests are skipped and in that case the reason why they were skipped it will be printed in the console.
```bash
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.getbynder.sdk.BynderServiceIT
Tests run: 20, Failures: 0, Errors: 0, Skipped: 0, ... - in com.getbynder.sdk.BynderServiceIT

Results :

Tests run: 20, Failures: 0, Errors: 0, Skipped: 0

```
