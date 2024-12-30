@echo off

set jar_path="C:\Users\yumpt\.jdks\corretto-23.0.1\bin\java.exe"
set server_name=server.jar

set server_path="C:\Users\yumpt\Eris\ErisWebsite\build\libs\ErisWebsite*.jar"
set server_argument="--help --test --WebServerPort 12345"

:Start

echo BASH - Copying the server jar
xcopy /s %server_path% "./%server_name%" /Y
echo BASH - Done copying the server jar

echo BASH - Starting erisweb server.

echo.
echo. 

%jar_path% -jar -Xms4G -Xmx8G "%server_name%" "%server_argument%"

echo.
echo.

echo BASH - Erisweb server stopped.
timeout 5

goto Start