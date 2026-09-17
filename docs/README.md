# Oreo User Guide

Oreo is a friendly task manager for to-do items, deadlines, events, and
optional merchandise details. It provides helpful feedback for invalid
commands and continues safely when its data file is missing or contains
malformed records.

// Product screenshot goes here

The command-line and JavaFX interfaces use the same task-management logic.
Oreo stores data in `data/oreo.txt` and creates the directory and file when
tasks are first saved.

## Credits

This project began from the NUS CS2103 Duke starter template. The starter
structure and instructional material are retained with attribution in the
repository history. JavaFX is used under its OpenJFX distribution licence.

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
