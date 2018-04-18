java -cp "%USERPROFILE%/.m2/repository/org/hsqldb/hsqldb/2.4.0/hsqldb-2.4.0.jar" org.hsqldb.Server -port 9003 -database.0 file:"%~dp0database\hsqldb\timer" -dbname.0 timer
pause