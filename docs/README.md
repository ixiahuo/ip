# Frogge User Guide

Introducing Frogge, a helpful frog buddy for you to keep track of your tasks!
Frogge is a task management app for desktops with a GUI and stores information locally.

(./Ui.png)

## Adding todos
Add a simple task to the list. By default, this task is not done yet.

Format: `todo NAME`

Example:
```
todo buy a gift for friend
```
Outcome: Adds a task to the list.

## Adding deadlines
Add a task with a deadline to the list. By default, this task is not done yet.

Format: `deadline NAME /by yyyy-mm-dd`

Example:
```
deadline finish homework /by 2026-02-20
```
Outcome: Adds a task with a deadline to the list.

## Adding events
Add an event with a start and end date to the list. By default, this event is not done yet.

Format: `event NAME /from yyyy-mm-dd /to yyyy-mm-dd`

Example:
```
event tea with sally /from 2026-02-21 /to 2026-02-21
```
Outcome: Adds an event with a start and end date to the list.

## Listing tasks
Display all tasks in the list.

Format: `list`

## Marking tasks as done
Mark a task as done, using its number in the list.

Format: `mark NUMBER`

Example:
```
mark 1
```
Outcome: The task with that number in the list is marked as done.

## Unmarking tasks
Mark a task as not yet done, using its number in the list.

Format: `umark NUMBER`

Example:
```
umark 1
```
Outcome: The task with that number in the list is marked as not done.

## Finding tasks
Find all tasks containing a keyword and displays them.

Format: `find KEYWORD`

Example:
```
find eat
```
Outcome: All tasks containing that keyword will be displayed in a list.

## Deleting tasks
Delete the specified task, using its number in the list.

Format: `delete NUMBER`

Example:
```
delete 3
```
Outcome: The task with that number in the list will be deleted.

## Sorting tasks
Sort tasks in the list by the following priority order:
1. Unmarked > marked
2. Task with deadline > events > task
3. Date of deadline or start date

Format: `sort`

Outcome: The list will be sorted and the save file will be updated accordingly.

## Exiting the app
Close the app using the following command: `bye`
