# Installation Guide
Please follow the instructions below.

## Requirements
- Eclipse 2019-09R or later.
- Download Xtext v2.19.0 or later.

## Instructions
- Clone or download the master branch of the repository
- Open Eclipse
- Use `File` > `Open Projets from File System`
- Import each one of these five cloned directories into your Eclipse workspace (`ca.uottawa.csmlab.symboleo`, `ca.uottawa.csmlab.symboleo.ide`, `ca.uottawa.csmlab.symboleo.tests`, `ca.uottawa.csmlab.symboleo.ui`, `ca.uottawa.csmlab.symboleo.ui.tests`)
- In the `Package Explorer` panel, right click on the `Symboleo.xtext` file under `ca.uottawa.csmlab.symboleo\src\ca\uottawa\csmlab\symboleo` directory, then click on `Run as` > `Generate Xtext Artifacts`
![alt text](https://github.com/Smart-Contract-Modelling-uOttawa/Symboleo-IDE/blob/master/images/p1.png "Generate Xtext Artifacts")
- Wait for the process to finish, you should see the `Done` message in the Eclipse console
- Now click on the `Symboleo.xtext` file. Select `Yes` to convert the project to an Xtext project
- For each of the five projects (`ca.uottawa.csmlab.symboleo.*`), find the `src-gen` directory in the `Package Explorer`
![alt text](https://github.com/Smart-Contract-Modelling-uOttawa/Symboleo-IDE/blob/master/images/p2.png "Use as source folder")
- Right click on the `src-gen` folder, select `Build path` > `Use as source folder`
- To start the Symboloe IDE right click on the `ca.uottawa.csmlab.symboleo` project in the `Package Explorer`, then select `Run as` > `Eclipse aplication`
![alt text](https://github.com/Smart-Contract-Modelling-uOttawa/Symboleo-IDE/blob/master/images/p3.png "Run as Eclipse aplication")
- In the new opened Eclipse window create a new project then create a new file with `.symboleo` extension. The generated Hyperledger Fabric code is inside the `src-gen` folder.
