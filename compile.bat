@echo off
echo === COMPILING InventoryBarang ===

REM Masuk ke folder project
cd "C:\Users\gabut\OneDrive - Politeknik Negeri Bandung\Semester 3\PBO\TUBES\InventoryBarang"

REM Path tambahan
set JAVAFX_LIB=C:\javafx-sdk-25.0.1\lib
set MYSQL_JAR=lib\mysql-connector-j-9.5.0.jar

javac ^
  --module-path "%JAVAFX_LIB%" ^
  --add-modules javafx.controls,javafx.fxml ^
  -d "target\classes" ^
  -cp "target\classes;src\main\resources;%MYSQL_JAR%" ^
  src\main\java\app\*.java ^
  src\main\java\config\*.java ^
  src\main\java\dao\*.java ^
  src\main\java\dao\barang\*.java ^
  src\main\java\dao\supplier\*.java ^
  src\main\java\dao\transaksi\*.java ^
  src\main\java\exception\*.java ^
  src\main\java\facade\*.java ^
  src\main\java\interfaces\*.java ^
  src\main\java\models\*.java ^
  src\main\java\services\base\*.java ^
  src\main\java\services\barang\*.java ^
  src\main\java\services\supplier\*.java ^
  src\main\java\services\transaksi\*.java ^
  src\main\java\ui\fx\main\*.java ^
  src\main\java\ui\fx\barang\*.java ^
  src\main\java\ui\fx\supplier\*.java ^
  src\main\java\ui\fx\transaksi\*.java ^
  src\main\java\ui\fx\dashboard\*.java ^
  src\main\java\ui\fx\laporan\*.java ^
  src\main\java\utils\*.java

echo === COMPILE SELESAI ===
pause
