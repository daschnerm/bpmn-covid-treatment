### Installation Guide
1. Download and install JDK8  for your OS=> https://www.adoptopenjdk.net/installation.html
2. Download and install NetBeans IDE "Java SE"-Version to an appropriate folder => https://netbeans.apache.org/download/nb113/nb113.html
3. Download and install Camunda Modeler in a different folder => https://camunda.com/download/modeler/
5. Open and build this project in netbeans by selecting:
    File->Open Project
    Open ApplicationApprivalProcess
    Right Click on Project -> Clean and Build
6. Install node.js
   run in command line, while replacing "netbeans_path" with your NetBeans Install path your where this README.md lies
  "netbeanspath"/java/maven/bin/mvn com.github.eirslett:frontend-maven-plugin:1.7.6:install-node-and-npm -DnodeVersion="v12.14.0"

### Start your process application
1. Right click on your project "ApplicationApprovalProcess"
2. Click Run As->Spring Boot App.e
3. If there comes an error, check your .bpmn file for missing configurations.

## Environment Restrictions
Built and tested against Camunda BPM version 7.12.0.

## Known Limitations

## License
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
