# Oreo project template

This is a project template for a greenfield Java project. The chatbot is named _Oreo_. Given below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 25, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 25** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/oreo/Oreo.java` file, right-click it, and choose `Run 'Oreo.main()'` (if the code editor is showing compile errors, try restarting the IDE). IntelliJ should recognise `src/main/java` as the source root and use the `oreo` package automatically. If the setup is correct, you should see something like the below as the output:
   ```
     OOO   RRRR   EEEEE  OOO  
    O   O  R   R  E     O   O 
    O   O  RRRR   EEEE  O   O 
    O   O  R R    E     O   O 
     OOO   R  RR  EEEEE  OOO  
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

The Java classes are organised into packages under the source root:

```text
src/main/java/oreo/
├── Oreo.java       # Application entry point
├── command/        # User commands
├── core/           # Parsing and shared application logic
├── model/          # Task-related classes
├── storage/        # File persistence
└── ui/             # Console input and output
```

To run the application from a terminal using JDK 25:

```sh
javac -d /tmp/oreo-classes $(find src/main/java -name '*.java')
java -cp /tmp/oreo-classes oreo.Oreo
```

## Acknowledgement of AI use 

### Week 2 iP
- ChatGPT/Codex was used mostly for the code in the submission following the wk 2 iP Increments as followed
- Review was done and tested personally myself for the example test cases as well.

### Week 3 iP
- ChatGPT/Codex was used mostly for the code in the submission following the wk 3 iP Increments as instructed
- Review was done making use of the test scripts and reviewed manually by myself
- made use of google AI to help understand some code I was unfamiliar with as well.
