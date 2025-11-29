@echo off
echo === Menjalankan InventoryBarang ===

set JAVAFX_LIB=C:\javafx-sdk-25.0.1\lib
set MYSQL_JAR=lib\mysql-connector-j-9.5.0.jar
set OUT_DIR=target\classes

java ^
 --enable-native-access=javafx.graphics ^
 --module-path "%JAVAFX_LIB%" ^
 --add-modules javafx.controls,javafx.fxml ^
 -cp "%OUT_DIR%;%MYSQL_JAR%" ^
 ui.fx.main.MainApp

pause
