SAP BusinessObjects Universe Metadata Extractor
==========

This Java application can extract the universe metadata into a json file from SAP BusinessObjects.

### System Requirements ###
  	To run the application your system should satisfy the following requirements:
  		* Install Java Development Kit 32-bit version.
  		* Download and install SAP BO Client tools 4.1 from SAP Marketplace (Make sure to select SemanticLayer SDK from the Developer components during the installation process. This is not selected by default). An installation guide is available [here](http://scn.sap.com/community/businessobjects-web-intelligence/blog/2013/11/15/bo-client-installation-and-configuration).
  		* Download the [Log4j2](http://logging.apache.org/log4j/2.x/) library and put the jar file in the `lib/` folder.
  		* Download the [Gson](http://search.maven.org/#artifactdetails%7Ccom.google.code.gson%7Cgson%7C2.5%7Cjar) library and put the jar file in the `lib/` folder.

### Running the application ###
	Starting the application is very easy using the `BOMetadataExtraction.bat` file. You only need the set two environmental variables:
		* `JAVA_HOME` Set the path of your installed JDK for example: `SET JAVA_HOME=C:\Program Files (x86)\Java\jdk1.8.0_65`
		* `BO_HOME` Set the path of your installed BO Client library for example: `SET BO_HOME=C:\Program Files (x86)\SAP BusinessObjects\SAP BusinessObjects Enterprise XI 4.0`
	After setting the variables you only start the `BOMetadataExtraction.bat` file. The result will be available in the same folder where the script was running from.


