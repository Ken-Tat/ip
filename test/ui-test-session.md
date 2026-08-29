# UI test session

## 1. Greeting and graceful exit

**Aim:** Confirm that Oreo displays its greeting and exits with its goodbye message when the user enters `bye`.

**Command:**
```sh
rm -f data/oreo.txt && javac -d /tmp/oreo-ui-test-classes src/main/java/*.java && java -cp /tmp/oreo-ui-test-classes Oreo
```

**Console input:**
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

**Actual output:**
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

**Exit status:** `0`

**Result:** PASS

## 2. Recover from invalid commands without changing state

**Aim:** Confirm that an empty to-do description and an unknown command produce exception-based error messages, while valid tasks remain intact.

**Command:**
```sh
rm -f data/oreo.txt && javac -d /tmp/oreo-ui-test-classes src/main/java/*.java && java -cp /tmp/oreo-ui-test-classes Oreo
```

**Console input:**
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
  Oh My God! To do what task exactly?.
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

**Actual output:**
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
  Oh My God! To do what task exactly?.
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

**Exit status:** `0`

**Result:** PASS

## 3. Reject invalid task numbers without changing task state

**Aim:** Confirm that invalid task numbers, including an out-of-range delete command, are handled safely and leave the task list unchanged.

**Command:**
```sh
rm -f data/oreo.txt && javac -d /tmp/oreo-ui-test-classes src/main/java/*.java && java -cp /tmp/oreo-ui-test-classes Oreo
```

**Console input:**
```text
todo buy milk
mark abc
delete 2
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
  Oh My God! I can't find that task number.
____________________________________________
____________________________________________
Here are the tasks in your list:
1.[T][ ] buy milk
____________________________________________
____________________________________________ 
Good work. See you next time! 
____________________________________________ 


```

**Actual output:**
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
  Oh My God! I can't find that task number.
____________________________________________
____________________________________________
Here are the tasks in your list:
1.[T][ ] buy milk
____________________________________________
____________________________________________ 
Good work. See you next time! 
____________________________________________ 


```

**Exit status:** `0`

**Result:** PASS

## 4. Add and list every task subtype

**Aim:** Confirm that the `Todo`, `Deadline`, and `Event` subclasses retain their type-specific details and that the `TaskType` enum preserves their existing display markers through the shared `Task` list.

**Command:**
```sh
rm -f data/oreo.txt && javac -d /tmp/oreo-ui-test-classes src/main/java/*.java && java -cp /tmp/oreo-ui-test-classes Oreo
```

**Console input:**
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

**Actual output:**
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

**Exit status:** `0`

**Result:** PASS

## 5. Mark and unmark a task

**Aim:** Confirm that marking a task as done and then unmarking it updates its status without changing the task description or list position.

**Command:**
```sh
rm -f data/oreo.txt && javac -d /tmp/oreo-ui-test-classes src/main/java/*.java && java -cp /tmp/oreo-ui-test-classes Oreo
```

**Console input:**
```text
todo buy milk
mark 1
unmark 1
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
Nice! I've marked this task as done:
  [T][X] buy milk
____________________________________________
____________________________________________
OK, I've marked this task as not done yet:
  [T][ ] buy milk
____________________________________________
____________________________________________
Here are the tasks in your list:
1.[T][ ] buy milk
____________________________________________
____________________________________________ 
Good work. See you next time! 
____________________________________________ 


```

**Actual output:**
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
Nice! I've marked this task as done:
  [T][X] buy milk
____________________________________________
____________________________________________
OK, I've marked this task as not done yet:
  [T][ ] buy milk
____________________________________________
____________________________________________
Here are the tasks in your list:
1.[T][ ] buy milk
____________________________________________
____________________________________________ 
Good work. See you next time! 
____________________________________________ 


```

**Exit status:** `0`

**Result:** PASS

## 6. Load tasks saved by a previous run

**Aim:** Confirm that a task saved in one run is loaded and listed when Oreo starts again.

**Command:**
```sh
rm -f data/oreo.txt && javac -d /tmp/oreo-ui-test-classes src/main/java/*.java && printf 'todo buy milk\nbye\n' | java -cp /tmp/oreo-ui-test-classes Oreo >/tmp/oreo-first-run.txt && printf 'list\nbye\n' | java -cp /tmp/oreo-ui-test-classes Oreo
```

**Console input:**
```text
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
Here are the tasks in your list:
1.[T][ ] buy milk
____________________________________________
____________________________________________ 
Good work. See you next time! 
____________________________________________ 


```

**Actual output:**
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
Here are the tasks in your list:
1.[T][ ] buy milk
____________________________________________
____________________________________________ 
Good work. See you next time! 
____________________________________________ 


```

**Exit status:** `0`

**Result:** PASS

## 7. Delete tasks and reject invalid delete numbers

**Aim:** Confirm that deletion removes the selected task and re-numbers the list, while missing, zero, out-of-range, and empty-list delete commands leave the list unchanged.

**Command:**
```sh
rm -f data/oreo.txt && javac -d /tmp/oreo-ui-test-classes src/main/java/*.java && java -cp /tmp/oreo-ui-test-classes Oreo
```

**Console input:**
```text
todo read book
todo return book
delete 1
list
delete
delete 0
delete 2
delete 1
list
delete 1
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
[T][ ] return book
Now you have 2 tasks in the list.
____________________________________________
____________________________________________
Noted. I've removed this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________
____________________________________________
Here are the tasks in your list:
1.[T][ ] return book
____________________________________________
____________________________________________
  Oh My God! Sooo which task is it?
____________________________________________
____________________________________________
  Oh My God! I can't find that task number.
____________________________________________
____________________________________________
  Oh My God! I can't find that task number.
____________________________________________
____________________________________________
Noted. I've removed this task:
  [T][ ] return book
Now you have 0 tasks in the list.
____________________________________________
____________________________________________
No tasks in the list.
____________________________________________
____________________________________________
  Oh My God! I can't find that task number.
____________________________________________
____________________________________________ 
Good work. See you next time! 
____________________________________________ 


```

**Actual output:**
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
[T][ ] return book
Now you have 2 tasks in the list.
____________________________________________
____________________________________________
Noted. I've removed this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________
____________________________________________
Here are the tasks in your list:
1.[T][ ] return book
____________________________________________
____________________________________________
  Oh My God! Sooo which task is it?
____________________________________________
____________________________________________
  Oh My God! I can't find that task number.
____________________________________________
____________________________________________
  Oh My God! I can't find that task number.
____________________________________________
____________________________________________
Noted. I've removed this task:
  [T][ ] return book
Now you have 0 tasks in the list.
____________________________________________
____________________________________________
No tasks in the list.
____________________________________________
____________________________________________
  Oh My God! I can't find that task number.
____________________________________________
____________________________________________ 
Good work. See you next time! 
____________________________________________ 


```

**Exit status:** `0`

**Result:** PASS

All 7 test case(s) passed.
