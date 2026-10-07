@echo off
set "MAVEN_EXE=%LOCALAPPDATA%\Programs\apache-maven-3.9.6\bin\mvn.cmd"
if exist "%MAVEN_EXE%" (
    "%MAVEN_EXE%" %*
) else (
    mvn %*
)
