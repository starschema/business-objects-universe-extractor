@echo off
setlocal

set PATH=%BO_HOME%\win32_x86;%PATH%;

mvn clean package exec:exec
