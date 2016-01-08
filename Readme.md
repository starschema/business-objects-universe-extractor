# BOT connector has two major parts.
  
  1. BOT Extractor
  2. BOT Connector

## BOT Extractor:

Below are the files and steps to configure BOT Extractor. 

1. BOTConfig.xml : This XML have following nodes which need to configure. 

    ```XML
    <?xml version="1.0" encoding="utf-8" ?>
    <bot-config>
     <universe-name> {{ Universe name }} </universe-name>
      <universe-parent-folder>{{ Universe parent folder name }} </universe-parent-folder>
      <local-universe-path>{{Local universe path}}</local-universe-path>
      <output-path>{{Output path where user wants to store extractor json output}}</output-path>
      <bo-server-login-info>
        <server-ip>{{Server IP}}</server-ip>
        <server-port>{{Port}}</server-port>
        <user-name>{{User Name}}</user-name>
        <password>{{Password}}</password>
        <user-type>1</user-type>
        <authentication>secEnterprise</authentication>
      </bo-server-login-info>
    </bot-config>
    ```

    As shown in above cell, all node values (which are marked in yellow) need to be set as per user BO environment. 

    Values of following nodes are required.
    `<universe-name>`: Name of universe.

    `<universe-parent-folder>`:  Folder name in which universe resides.

    `<local-universe-path>`: Folder name where universe is locally extracted. We need to import the universe locally in IDT. Now the path of that locally imported universe need to pass here.

    `<output-path>`: Provide the folder name where user wants to save the output of this application. This application gives output in JSON format. 

    `<server-ip>`: BO server IP

    `<server-port>`:BO server port

    `<user-name>`:BO login User Name

    `<password>`:BO login password

2. BOMetadataExtraction.bat: This Bat file will work as trigger to start metadata extraction. This BAT file required few configurations. Please modify the configuration as per user system. 
Second last parameter of this BAT file is BOTExtraction.jar file path and last parameter of this BAT file is BOTConfig.xml file. (Marked red in below screenshot)
![botconfig](/botconfig-xml.png)

## BOT Connector:

This connector is a node base application. It gives the flexibility to use BO semantic layer and create reports in tableau desktop. By using Tableau 9.1 Web Data Connector approach this connector bridge two different BI world. To use this connector please install Node.js setup. Node.js is prerequisite to use this connector.

Below are the steps to configure BOT connector.

1.  Please install Node.js setup
2.  Copy the extracted BO semantic layer JSON output (file extracted by BOT Extractor)
3.  Start the CMD window.
4.  Navigate to BOT connector folder
5.  Run the command: Node server.js
6.  Step no. 4 will host the BOT connector. 
7.  Try URL http://localhost:1337 in browser. This will open the BOT connector screen.
8. Open the tableau desktop 9.1 and select WDC as source and use BOT connector.

To use BOT connector with tableau desktop 9.1 please refer the below gif file.
![tableau](/tableau-screen.png)


