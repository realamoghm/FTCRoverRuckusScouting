## Welcome!
This GitHub repository contains the source code that is used to build an Android app to electronically keep track of team records and scores in a FIRST FTC competition.

If you are new to FIRST, then you should use this link https://en.wikipedia.org/wiki/FIRST_Tech_Challenge to read up on FIRST and what we do.

## Downloading the Project
It is important to note that this repository is large and can take a long time and use a lot of space to download. If you would like to save time and space, there are some options that you can choose to download only the most current version of the Android project folder:

* You can use the "Download Zip" button available through the main repository page.  Downloading the project as a .ZIP file will keep the size of the download manageable.

## Version/Release Information
(Version 3.2, Built on 1/17/2019)
Changes Include:

* Bug has been fixed where on smaller mobile devices, the Live Score wouldn't show on the top bar due to overcrowding
(Version 3.1, Built on 1/7/2019)
Changes Include:

* Bug has been fixed where on newer phones above API 22, the storage prompt for external directory storage wouldn't show up, this led to the CSV file not being stored
* Live Score has been added, where score is displayed as you enter it
* Help guide has been added to help user navigate app
* Color of navigation bar header has been changed to better match app aesthetic 
* App has been resubmitted to the Google Play Store

(Version 3.0, Built on 1/1/2019)
Changes Include:

* App icons and pictures in navigation bar have now been finalized
* App is now ready to be submitted to the Google Play Store

(Version 2.9, Built on 12/29/2018)
Changes Include: 

* App has now added fully functional Export functionality to an CSV File on the local Device DCIM storage. 

(Version 2.5, Built on 12/26/2018)
Changes Include: 

* App storage engine for records changed from JSON to SQLite in most places
* App now has Edit function for old records
* App now has Delete function for old records
* Color selector has been added for which alliance and team in that alliance you are judging
* Create button has been changed from AppBar to FloatingActionButton
* Search function has been added, based on team number, team name, game number, game date, game score
    
(Version 2.0, Built on 12/14/2018)
Changes Include:

* App has navigation connected to all screens besides Export. This makes it possible for the app to make and store records of the game scores for other teams
* App record viewer has been enabled and is now able to fully function. This allows for viewing of old records
* Create record has been enabled and allows you to make new records of teams at FTC competitions
* View record function has been enabled and allows you to view old records in a recognizable format

(Version 1.2, Built on 12/09/2018)
Changes Include: 

* Major changes to app UI
* Added framework to connect View buttons to actual code, this made it possible to view added records and store records in local device storage
* Added framework to connect Create button to actual code, while the Create button doesn't have an action tied to it yet, it is able to click
* Added framework to make and operate a sideBar menu

(Version 1.1, Built on 12/08/18)
Changes Include:

* Added framework to make buttons on initial homescreen work, such as Create, View, and Export

(Version 1.0, Built on 12/03/18)
Changes Include: 

* Made a preliminary UI
* Planned out functions of app, including Create new records, View old records, and Export records
