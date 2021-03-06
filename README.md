# Tracky-Bug

Tracky-Bug is a ticketing or bug logging application that allows the user to manage and view logs of issues that may occur in their project.


# Progress:

You can view the current progress and tasks of the project on my [Trello Board].

[Trello Board]: https://trello.com/b/XwCLbayH/my-current-projects

___________________________________________________________________________________________________________________________________________________________________________________
Update v0.22: Edit and Delete Bug Fix

- Edit and Delete bug functions are now working.
- Admins are able to edit and delete other user's bugs.
- Changed addbuglog function to accept string for bugid
___________________________________________________________________________________________________________________________________________________________________________________
Update v0.21: Quick Fix

- Changed parameter data type for all methods with that need projectid.
- Edited output string of Changelog.generateLogDescription.
- added ON DELETE CASCADE to necessary tables to fix SQL table FK deletions.

___________________________________________________________________________________________________________________________________________________________________________________
Update v0.2: Major Database Update

Huge restructure of MariaDB using ERD to create better tables
- Recreated data structures and SQL tables.
- Created new tables projectusers & changelog.
- Created and Updated functions InitiateDB file.
- Created new Java Class called ProjectUsers to help with projectusers table.
- Moved CreateUser function from User Helper to ProjectUser Helper.
- Made necessary adjustments to MainWindow.
- Added new ComboBox component to Create User Panel.
- TabViewPanel adjustments and fixes.
- Bug Helper fixes.
- Added new column for table in AdminPanel.


___________________________________________________________________________________________________________________________________________________________________________________
___________________________________________________________________________________________________________________________________________________________________________________
Update v0.1: Major GUI Update

All the major GUI functions are completed, design and layout of GUI has also been changed.

___________________________________________________________________________________________________________________________________________________________________________________

