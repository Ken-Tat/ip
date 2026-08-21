# UI test plan

## Notes

- Run this plan from the repository root with Java 25 selected: `sdk use java 25.0.3.fx-zulu`, then `python3 .codex/skills/test-ui/scripts/run_ui_tests.py`.
- Each test command compiles the current Java source before running it, so the test checks the version currently in the working tree.
- Output comparisons are exact, including spaces and blank lines.

## Test cases

### Test: Greeting and graceful exit

**Aim:** Confirm that Oreo displays its greeting and exits with its goodbye message when the user enters `bye`.

**Command:**
```sh
javac -d /tmp/oreo-ui-test-classes src/main/java/*.java && java -cp /tmp/oreo-ui-test-classes Oreo
```

**Input:**
```text
bye
```

**Expected output:**
```text
____________________________________________ 
  OOO   RRRR   EEEEE  OOO  
 O   O  R   R  E     O   O 
 O   O  RRRR   EEEE  O   O 
 O   O  R R    E     O   O 
  OOO   R  RR  EEEEE  OOO  

Hello! I'm Oreo. 
Let's get started shall we? 
____________________________________________
____________________________________________ 
Good work. See you next time! 
____________________________________________ 

```

### Test: Recover from invalid commands without changing state

**Aim:** Confirm that an empty to-do description and an unknown command produce exception-based error messages, while valid tasks remain intact.

**Command:**
```sh
javac -d /tmp/oreo-ui-test-classes src/main/java/*.java && java -cp /tmp/oreo-ui-test-classes Oreo
```

**Input:**
```text
todo
todo buy milk
blah
list
bye
```

**Expected output:**
```text
____________________________________________ 
  OOO   RRRR   EEEEE  OOO  
 O   O  R   R  E     O   O 
 O   O  RRRR   EEEE  O   O 
 O   O  R R    E     O   O 
  OOO   R  RR  EEEEE  OOO  

Hello! I'm Oreo. 
Let's get started shall we? 
____________________________________________
____________________________________________
  Oh My God! to do what task exactly?.
____________________________________________
____________________________________________
Got it. I've added this task:
[T][ ] buy milk
Now you have 1 tasks in the list.
____________________________________________
____________________________________________
  Oh My God! I cannot comprehend your English.
____________________________________________
____________________________________________
Here are the tasks in your list:
1.[T][ ] buy milk
____________________________________________
____________________________________________ 
Good work. See you next time! 
____________________________________________ 

```

### Test: Reject invalid task numbers without changing task state

**Aim:** Confirm that a non-numeric mark command is handled safely and that the existing task remains incomplete.

**Command:**
```sh
javac -d /tmp/oreo-ui-test-classes src/main/java/*.java && java -cp /tmp/oreo-ui-test-classes Oreo
```

**Input:**
```text
todo buy milk
mark abc
list
bye
```

**Expected output:**
```text
____________________________________________ 
  OOO   RRRR   EEEEE  OOO  
 O   O  R   R  E     O   O 
 O   O  RRRR   EEEE  O   O 
 O   O  R R    E     O   O 
  OOO   R  RR  EEEEE  OOO  

Hello! I'm Oreo. 
Let's get started shall we? 
____________________________________________
____________________________________________
Got it. I've added this task:
[T][ ] buy milk
Now you have 1 tasks in the list.
____________________________________________
____________________________________________
  Oh My God! That is not a valid task number.
____________________________________________
____________________________________________
Here are the tasks in your list:
1.[T][ ] buy milk
____________________________________________
____________________________________________ 
Good work. See you next time! 
____________________________________________ 

```

### Test: Add and list every task subtype

**Aim:** Confirm that the `Todo`, `Deadline`, and `Event` subclasses retain their type-specific details and are displayed correctly through the shared `Task` list.

**Command:**
```sh
javac -d /tmp/oreo-ui-test-classes src/main/java/*.java && java -cp /tmp/oreo-ui-test-classes Oreo
```

**Input:**
```text
todo read book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
bye
```

**Expected output:**
```text
____________________________________________ 
  OOO   RRRR   EEEEE  OOO  
 O   O  R   R  E     O   O 
 O   O  RRRR   EEEE  O   O 
 O   O  R R    E     O   O 
  OOO   R  RR  EEEEE  OOO  

Hello! I'm Oreo. 
Let's get started shall we? 
____________________________________________
____________________________________________
Got it. I've added this task:
[T][ ] read book
Now you have 1 tasks in the list.
____________________________________________
____________________________________________
Got it. I've added this task:
[D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
____________________________________________
____________________________________________
Got it. I've added this task:
[E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________
____________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________
____________________________________________ 
Good work. See you next time! 
____________________________________________ 

```
