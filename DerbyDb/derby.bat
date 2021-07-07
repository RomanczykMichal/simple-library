@echo off
set JAVA_HOME=D:\Programowanie\jdk 12.0.2
set DERBY_HOME=D:\Uczelnia\4 sem\TPO\Cw\7\Dzialaj\db-derby-10.15.2.0-lib
set DERBY_SYSTEM_HOME=D:\Programowanie\Derby

@if "%DERBY_SYSTEM_HOME%" == "" goto noSystemHome
set LOCALCLASSPATH=%DERBY_HOME%/lib/derby.jar;%DERBY_HOME%/lib/derbynet.jar;%DERBY_HOME%/lib/derbyclient.jar;%DERBY_HOME%/lib/derbytools.jar
@if "%1" == "start" goto startServer
@if "%1" == "stop" goto stopServer
@if "%1" == "ij" goto ij
:ij
"%JAVA_HOME%\bin\java" -Dderby.system.home=%DERBY_SYSTEM_HOME% -cp "%LOCALCLASSPATH%;%CLASSPATH%" -Dij.protocol=jdbc:derby: org.apache.derby.tools.ij %2
goto end
:startServer
"%JAVA_HOME%\bin\java" -Dderby.system.home=%DERBY_SYSTEM_HOME% -cp "%LOCALCLASSPATH%;%CLASSPATH%" org.apache.derby.drda.NetworkServerControl start
goto end
:stopServer
"%JAVA_HOME%\bin\java" -Dderby.system.home=%DERBY_SYSTEM_HOME% -cp "%LOCALCLASSPATH%;%CLASSPATH%" org.apache.derby.drda.NetworkServerControl shutdown
goto end
:noSystemHome
echo DERBY_SYSTEM_HOME not set
:end
