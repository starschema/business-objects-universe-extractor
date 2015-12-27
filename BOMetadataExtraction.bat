@echo off

rem :: BO Home variable. Installed location of BO
set BO_HOME=C:\Program Files (x86)\SAP BusinessObjects\SAP BusinessObjects Enterprise XI 4.0

rem :: JAVA Home
set JAVA_HOME=C:\Program Files (x86)\Java\jdk1.7.0_55
set CURRENT_DIR=%cd%

echo Using JAVA_HOME: %JAVA_HOME%
echo Using CURRENT_DIR: %CURRENT_DIR%

set _RUNJAVA="%JAVA_HOME%/bin/java"
set _RUNJAVAC="%JAVA_HOME%/bin/javac"
set _RUNJAR="%JAVA_HOME%/bin/jar"

set CLASSPATH=%BO_HOME%\dataAccess\connectionServer\java\cs_celogon.jar;%BO_HOME%\dataAccess\connectionServer\java\cs_corba.jar;%BO_HOME%\dataAccess\connectionServer\java\cs_core.jar;%BO_HOME%\dataAccess\connectionServer\java\cs_ex.jar;%BO_HOME%\dataAccess\connectionServer\java\cs_java.jar;%BO_HOME%\dataAccess\connectionServer\java\cs_lib.jar;%BO_HOME%\dataAccess\connectionServer\java\cs_logging.jar;%BO_HOME%\dataAccess\connectionServer\java\cs_seccred.jar;%BO_HOME%\dataAccess\connectionServer\java\cs_sso.jar;%BO_HOME%\dataAccess\connectionServer\java\cs_tools.jar;%BO_HOME%\SL SDK\java\sl_sdk.jar;%CLASSPATH%;

set PATH=C:\Program Files (x86)\SAP BusinessObjects\SAP BusinessObjects Enterprise XI 4.0\win32_x86;%PATH%;




%_RUNJAVA%  -Dbusinessobjects.connectivity.directory="C:\Program Files (x86)\SAP BusinessObjects\SAP BusinessObjects Enterprise XI 4.0\dataAccess\connectionServer" -classpath "%CLASSPATH%" -jar "D:\Workarea\Work\BOToTableauConnector\Release\20Dec15\BOT_Extraction\BOTExtraction.jar" "D:\Workarea\Work\BOToTableauConnector\Release\20Dec15\BOT_Extraction\BOTConfig.xml"

pause
