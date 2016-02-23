# bynder-java-sdk

The main goal of this SDK is to speed up the integration of Bynder customers who use JAVA. Making it easier to connect to the Bynder API v4 (http://docs.bynder.apiary.io) and executing Requests on it.

## Current status

At the moment this JAVA SDK provides a default library with the following methods:

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

Change the BASE_URL property to your Bynder API v4 base url (hostname).
Example: &lt;hostname&gt;/api/v4/

Create a new properties file called "secret.properties" with the following content:
```bash
# dev env login credentials
USERNAME=<your username>
PASSWORD=<your password>

# dev env oauth header parameters
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

## Running the tests
To run the integration tests defined in the class <b>com.getbynder.sdk.BynderServiceIT</b> against the Bynder API, you should execute the following maven command in the project's root:
```bash
$ mvn verify
```
After running this command, if everything is working fine, you should get the output shown below, telling you all the tests run successfuly:
```bash
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.getbynder.sdk.BynderServiceIT
Tests run: 20, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 18.311 sec - in com.getbynder.sdk.BynderServiceIT

Results :

Tests run: 20, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------
```