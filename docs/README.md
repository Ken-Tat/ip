# Oreo User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Merchandise details

Each task can have one optional free-form merchandise detail. Merchandise is
managed independently from the normal task commands.

```text
merchandise TASK_NUMBER DETAILS
edit-merchandise TASK_NUMBER DETAILS
delete-merchandise TASK_NUMBER
find-merchandise KEYWORD
listall
```

For example:

```text
todo Sell house
merchandise 1 4-room flat at Bishan, 2 bathrooms
listall
```

The normal `list` and `find` commands continue to show and search task
descriptions only. Merchandise is stored together with its parent task and is
removed when that task is deleted.

