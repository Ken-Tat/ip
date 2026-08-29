# UI test session

## 1. Query tasks by date

**Aim:** Confirm that `on YYYY-MM-DD` finds deadlines and events on a date and handles an invalid date.

**Command:**
```sh
rm -f data/oreo.txt && javac -d /tmp/oreo-ui-test-classes $(find src/main/java -name '*.java') && java -cp /tmp/oreo-ui-test-classes oreo.Oreo
```

**Console input:**
```text
deadline return book /by 2019-10-15
event project meeting /from 2019-10-15 /to 2019-10-16
on 2019-10-15
on 2019-02-30
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
[D][ ] return book (by: Oct 15 2019)
Now you have 1 tasks in the list.
____________________________________________
____________________________________________
Got it. I've added this task:
[E][ ] project meeting (from: Oct 15 2019 to: Oct 16 2019)
Now you have 2 tasks in the list.
____________________________________________
____________________________________________
Tasks occurring on Oct 15 2019:
1.[D][ ] return book (by: Oct 15 2019)
2.[E][ ] project meeting (from: Oct 15 2019 to: Oct 16 2019)
____________________________________________
____________________________________________
  Oh My God! Use a date in yyyy-MM-dd format.
____________________________________________
____________________________________________ 
Good work. See you next time! 
____________________________________________ 


```

**Actual output:**
```text

```

**Exit status:** `1`

**Result:** FAIL

**Difference:**
```diff
--- expected output
+++ actual output
@@ -1,32 +0,0 @@
-____________________________________________ 
-  OOO   RRRR   EEEEE  OOO  
- O   O  R   R  E     O   O 
- O   O  RRRR   EEEE  O   O 
- O   O  R R    E     O   O 
-  OOO   R  RR  EEEEE  OOO  
-
-Hello! I'm Oreo. 
-Let's get started shall we? 
-____________________________________________
-____________________________________________
-Got it. I've added this task:
-[D][ ] return book (by: Oct 15 2019)
-Now you have 1 tasks in the list.
-____________________________________________
-____________________________________________
-Got it. I've added this task:
-[E][ ] project meeting (from: Oct 15 2019 to: Oct 16 2019)
-Now you have 2 tasks in the list.
-____________________________________________
-____________________________________________
-Tasks occurring on Oct 15 2019:
-1.[D][ ] return book (by: Oct 15 2019)
-2.[E][ ] project meeting (from: Oct 15 2019 to: Oct 16 2019)
-____________________________________________
-____________________________________________
-  Oh My God! Use a date in yyyy-MM-dd format.
-____________________________________________
-____________________________________________ 
-Good work. See you next time! 
-____________________________________________ 
-

```

Testing stopped after this failed case.
