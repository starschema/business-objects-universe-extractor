@echo off

setlocal
rem :: BO Home variable. Installed location of BO
set BO_HOME=C:\Program Files (x86)\SAP BusinessObjects\SAP BusinessObjects Enterprise XI 4.0

rem :: JAVA Home
set JAVA_HOME=C:\Program Files (x86)\Java\jdk1.8.0_65
set CURRENT_DIR=%cd%

set _RUNJAVA="%JAVA_HOME%/bin/java"
set _RUNJAVAC="%JAVA_HOME%/bin/javac"
set _RUNJAR="%JAVA_HOME%/bin/jar"

set CLASSES=%CLASSPATH%;%CURRENT_DIR%\lib\*;%CURRENT_DIR%\resources\*;%BO_HOME%\dataAccess\connectionServer\java\*;%BO_HOME%\SL SDK\java\sl_sdk.jar;

set PATH=C:\Program Files (x86)\SAP BusinessObjects\SAP BusinessObjects Enterprise XI 4.0\win32_x86;%PATH%;
rem::echo %PATH%

rd /S /Q "build"
md "build\classes"

%_RUNJAVAC% -cp "%CLASSES%" -sourcepath src -d build\classes src\com\bot\botmeta\BOMetaDataExtraction.java

%_RUNJAVA% -Dbusinessobjects.connectivity.directory="%BO_HOME%\dataAccess\connectionServer"  -Dlog4j.configurationFile="%CURRENT_DIR%\resources\log4j2.xml" -cp "%CLASSES%;build\classes" com.bot.botmeta.BOMetaDataExtraction
