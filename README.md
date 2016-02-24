# Bynder Java SDK

The main goal of this SDK is to speed up the integration of Bynder customers who use JAVA. Making it easier to connect to the Bynder API v4 (http://docs.bynder.apiary.io) and executing Requests on it.

## Current status

At the moment this JAVA SDK provides a default library with the following methods defined in the class <b>com.getbynder.sdk.BynderService</b>:

```java
public UserAccessData getUserAccessData(final String username, final String password);

public List<Category> getCategories();

public List<MediaAsset> getAllImageAssets();

public List<MediaAsset> getImageAssets(final int limit, final int offset);

public List<MediaAsset> getImageAssetsByKeyword(final String keyword);

public List<MediaAsset> getImageAssetsByMetapropertyId(final String metapropertyId);

public int getImageAssetsTotal();

public List<MediaAsset> getAllMediaAssets();

public MediaAsset getMediaAssetById(final String id, final Boolean includeVersions);

public int setMediaAssetProperties(final MediaAsset mediaAsset);

public List<Metaproperty> getAllMetaproperties();

public int addMetapropertyToAsset(final String assetId, final String metapropertyId, final String... optionsIds);
```

## Installation

Components necessary to install and run the project:
* Java JDK (version 1.7.0_80)
* Apache Maven 3.3.3

<b>Important:</b> Don't forget to define the environmental variables for Java and Maven

### Installation steps
Clone the repository
```bash
$ git clone git@github.com:Bynder/bynder-java-sdk.git
```
Access the "config.properties" file
```bash
$ vi bynder-java-sdk/src/main/resources/config.properties
```

Change the <b>BASE_URL</b> property to your Bynder API v4 base url (hostname).
Example: <i>https://&lt;hostname&gt;/api/v4/</i>

Create a new properties file called "secret.properties" with the following content:
```bash
# bynder login credentials
USERNAME=<your username>
PASSWORD=<your password>

# bynder access tokens
CONSUMER_KEY=<your consumer key>
CONSUMER_SECRET=<your consumer secret>
ACCESS_TOKEN=<your access token>
ACCESS_TOKEN_SECRET=<your access token secret>
```

Build the project from its root with the following maven command:
```bash
$ mvn clean install -DskipTests
```
This command tells Maven to build all the modules and to install it in the local repository. Skipping the tests.

## How does it work
Before executing any Request to the Bynder API, it is necessary to instantiate the class <b>BynderService</b>.

The constructor in the <b>BynderService</b> class takes three String arguments: <b>baseUrl</b>, <b>username</b> and <b>password</b>. Stores the <b>baseUrl</b> in an instance variable and calls the <b>getUserAccessData</b> method that receives the <b>username</b> and <b>password</b> as arguments and logins to the Bynder API to get the tokens that are necessary to make the Requests. Constructor code:

```java
public BynderService(final String baseUrl, final String username, final String password) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, ClientProtocolException, IOException, URISyntaxException {
    this.baseUrl = baseUrl;
    this.userAccessData = getUserAccessData(username, password);
}
```
As shown above the tokens that are retrieved by the Bynder API after a successful login are stored in the instance variable <b>userAccessData</b>.

Code example to instantiate the <b>BynderService</b> class:
```java
BynderService bynderService = new BynderService("https://example.getbynder.com/api/v4/", "test", "12345");
```
In the example above the BynderService class is instantiated with the baseUrl "ht&#8203;tp://example.getbynder.com/api/v4/", username "test" and password "12345".

After instantiating the <b>BynderService</b> class successfully it is possible to call any of the methods listed in the above section <b>Current Status</b>. Example:

```java
BynderService bynderService = new BynderService("https://example.getbynder.com/api/v4/", "test", "12345");

List<MediaAsset> allImageAssets = bynderService.getAllImageAssets();
```

## Running the tests
To run the integration tests defined in the class <b>com.getbynder.sdk.BynderServiceIT</b> against the Bynder API, you should execute the following maven command in the project's root:
```bash
$ mvn verify
```
<b>Note:</b> Before the integration tests are executed, an instance of the <b>BynderService</b> class will be created with the login credentials and oauth header parameters defined in the "secret.properties" file.

After running this command, if everything is working fine, you should get the output shown below, telling you all the tests ran successfully:
```bash
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.getbynder.sdk.BynderServiceIT
Tests run: 20, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 18.311 sec - in com.getbynder.sdk.BynderServiceIT

Results :

Tests run: 20, Failures: 0, Errors: 0, Skipped: 0

```
