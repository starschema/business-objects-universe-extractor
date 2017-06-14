@echo off
setlocal

set CURRENT_DIR=%cd%
set "_RUNJAVA=%JAVA_HOME%/bin/java"
set "_RUNJAVAC=%JAVA_HOME%/bin/javac"
set "_RUNJAR=%JAVA_HOME%/bin/jar"

set CLASSES=%CURRENT_DIR%\lib\gson-2.5.jar;%CURRENT_DIR%\lib\log4j-api-2.8.2.jar;%CURRENT_DIR%\lib\log4j-core-2.8.2.jar;%CURRENT_DIR%\resources\*;%BO_HOME%\dataAccess\connectionServer\java\*;%BO_HOME%\SL SDK\java\sl_sdk.jar

set PATH=C:\Program Files (x86)\SAP BusinessObjects\SAP BusinessObjects Enterprise XI 4.0\win32_x86;%PATH%;

rd /S /Q "build"
md "build\classes"

"%_RUNJAVAC%" -cp "%CLASSES%" -sourcepath src -d build\classes src\com\bot\botmeta\BOMetaDataExtraction.java

"%_RUNJAVA%" -Dbusinessobjects.connectivity.directory="%BO_HOME%\dataAccess\connectionServer"  -Dlog4j.configurationFile="%CURRENT_DIR%\resources\log4j2.xml" -cp "%CLASSES%;build\classes" com.bot.botmeta.BOMetaDataExtraction %CONFIG_FILE%
