# Installation Guide
Please follow the instructions below.

## Requirements
- Eclipse 2019-09R or later.
- Xtext v2.19.0 or later.

## Instructions
- Clone the master branch of the repository and add it to your eclipse workspace (make sure that you open `org.xtext.csmLab.symboleo` as the project directory).
- Right-click on `org.xtext.csmLab.symboleo/src/org/xtext/csmLab/symboleo/Symboleo.xtext`**| Run As |** `Generate Xtext Artifacts`.
- While running the workflow, you will see some warnings (in red), please ignore them. The final command that illustrates successful build is: `[main] INFO  .emf.mwe2.runtime.workflow.Workflow  - Done.`
- To experience Symboleo IDE in action, right-click on `Symboleo-IDE/org.xtext.csmLab.symboleo` and navigate to **Run As |** `Eclipse Application`.
- A runtime eclipse workspace will be opened. Create a New **General** Project (**File | New | Project… | General | Project**).
- Inside the project, create a new file with the `.symboleo` extension.
- System will prompt you to convert the project as a Xtext project, you should accept that for the Symboleo editor to work.
- Paste the text of the sample Sales-of-Goods contract (`samples/sogContract.txt`) in your Symboleo file. You should see keywords bolded.
![alt text](https://github.com/Smart-Contract-Modelling-uOttawa/Symboleo-IDE/blob/master/samples/sogOutput.png "Sales-of-Goods contract specified in Symboleo text editor")
- Now you can specify contracts in Symboleo and enjoy its syntax highlighting and autofill capabilities!
