del /s d:\Programs\jboss\jboss-as-7.1.1.Final-icore\standalone\deployments\logisticclient.war 
call mvn clean install
copy target\logisticclient.war d:\Programs\jboss\jboss-as-7.1.1.Final-icore\standalone\deployments\