SAP BusinessObjects Universe Metadata Extractor
==========

This Java application can extract the universe metadata into a json file from SAP BusinessObjects.

### System Requirements ###

To run the application your system should satisfy the following requirements:

* Install Java Development Kit 32-bit version. (The connector has been succesfully tested using Java 1.7)

* Download and install SAP BO Client tools 4.1 from SAP Marketplace (Make sure
  to select SemanticLayer SDK from the Developer components during the
  installation process. This is not selected by default). An installation guide
  is available [here](http://scn.sap.com/community/businessobjects-web-intelligence/blog/2013/11/15/bo-client-installation-and-configuration).

* Install [Apache Maven binary](https://maven.apache.org/download.cgi). More info about how to install Maven can be found [here](https://maven.apache.org/install.html).
  
  
You can test the installations by running *mvn -version* from the command line. 
It should list the Maven version and the Java version too.

### Configuring the application ###

The application is configurable through a XML file: [src/main/resources/BOTConfig.xml](src/main/resources/BOTConfig.xml). 
For the detailed explanation of the structure of the config see the `BOT_Configuration.docx` document.

### Running the application ###

Starting the application is very easy using the `BOMetadataExtraction.bat` file. 
You only need the set one environmental variable:

* `BO_HOME` Set the path of your installed BO Client library for example: `SET BO_HOME=C:\Program Files (x86)\SAP BusinessObjects\SAP BusinessObjects Enterprise XI 4.0`

After setting the variable you only start the `BOMetadataExtraction.bat` file.
The result will be available in the same folder where the script was running
from.


