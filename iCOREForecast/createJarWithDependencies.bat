call mvn clean install
call mvn assembly:assembly -DdescriptorId=jar-with-dependencies
IF NOT "%1" == "" (
   copy serialized\icoreForecaster.ser d:\proiecte\iCORE\svn\Developement\transportationcvomng\src\main\webapp\serialized\icoreForecaster.ser 
)