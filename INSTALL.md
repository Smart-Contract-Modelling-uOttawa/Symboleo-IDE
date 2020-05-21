# Installation Guide
Please follow the instructions below.

## Requirements
- Eclipse 2019-09R or later.
- Xtext v2.19.0 or later.

## Instructions
- Clone the master branch of the repository and add it to your eclipse workspace.
- Right-click on `org.xtext.csmLab.symboleo/src/org/xtext/csmLab/symboleo/Symboleo.xtext`**| Run As |** `Generate Xtext Artifacts`.
- After running the workflow, the final command that illustrates successful build is: `[main] INFO  .emf.mwe2.runtime.workflow.Workflow  - Done.`
- To experience Symboleo IDE in action, right-click on `Symboleo-IDE/org.xtext.csmLab.symboleo` and navigate to **Run As |** `Eclipse Application`.
- A runtime eclipse workspace will be opened. Create a New **General** Project (**File | New | Project… | General | Project**).
- Inside the project, create a new file with the `.symboleo` extension.
- System will prompt you to convert the project as a Xtext project, you should accept that for the Symboleo editor to work.
- Now you can specify contracts in Symboleo!
