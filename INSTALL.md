# Installation Guide
These instructions should include notes on what output to expect, which confirms the code is installed and working.

## Requirements
- Eclipse 2019-09R or newer.
- Xtext v2.19.0 or newer.

## Instructions
- Clone the repository and add it to your eclipse workspace.
- Right-click on `org.xtext.csmLab.symboleo/src/org/xtext/csmLab/symboleo/Symboleo.xtext`> Run As> `Generate Xtext Artifacts`.
- After running the workflow, the final command that illustrates successful build is: `[main] INFO  .emf.mwe2.runtime.workflow.Workflow  - Done.`
- To experience Symboleo IDE in action, right-click on `Symboleo-IDE/org.xtext.csmLab.symboleo` folder> Run As> `Eclipse Application`.
- A runtime eclipse workspace will be opened. Create a New Project.
- create a new file with the `.symboleo` extension.
- System will prompt you to recognize the project as a Xtext project, click on the yes button.
- Now you can specify contracts in Symboleo!
