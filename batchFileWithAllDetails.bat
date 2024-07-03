@echo off

:: chrome, edge, firefox, safari
SET browser=edge

:: tags with 'and, or,not' conditions
SET tag=@checkout and @usa and @gc and not @iou and not @class
SET tcnumber=123456
SET threadcount=2

::dev12
SET env=https://practicetestautomation.com/practice-test-login/

::add multiple emailId with,
SET toEmailId=emailid

::run jar file with tag
java -Dcucumber.filter.tags="%tag" -jar AllProjectJar.jar

::run jar file with TC number
java -Dcucumber.filter.name="%tcNumber" -jar AllProjectJar.jar

SET database=excel
SET database_sql=jdbc:sqlserver://10.99.32.161;databaseName=Automation;user=sa;password=vagaro@09
SET database_mongo=10.99.32.160:27017