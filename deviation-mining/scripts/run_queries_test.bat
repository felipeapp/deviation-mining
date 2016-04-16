@echo off

REM This file will run queries in MongoDB and PostgreSQL
REM How to run:
REM		run_queries_test.bat n
REM n: number of executions

IF "%1" == "" GOTO HelpMessage

FOR /L %%I IN (1,1,%1) DO (
	echo "%%I: gradle -q queryPostgreSQL >> postgres.txt"
	cmd /c gradle -q queryPostgreSQL >> postgres.txt
	
	echo "%%I: gradle -q queryMongoDB >> mongodb.txt"
	cmd /c gradle -q queryMongoDB >> mongodb.txt
)

GOTO EndScript

:HelpMessage
echo "Ex 1: run_queries_test.bat 10"

:EndScript
echo "Bye..."
