# Fitbit-Cracker
Test if your fitbit account is working or not.

# How to install
Mac-user install :

Download MAMP (free version), install it. (To have a local webserver)
Make sure cURL is enabled
Go in "Preferences" and check the port for Apache (Mine is 8888)
Put the servers of MAMP on.
Now download this project and save all the files in your "Applications/MAMP/htdocs" folder, so that you can access index.php
with "Applications/MAMP/htdocs/index.php"
Once done, open the "Applications/MAMP/htdocs/accounts" file and put all the accounts you want to crack in it with this shape:
email:pass
email2:pass2
etc:etc

Save, and then go to the web URL : http://localhost:YOURAPACHEPORTHERE/

For my case for exemple it's http://localhost:8888/

And the script will be running you just need to wait, I advice you to not put too much accounts in a row as it will take time
to finish them all.


Windows install is exactly the same but for windows there is WAMP or EasyPHP as you want.


Feel free to contact me for ideas, donations or whatever you want : aurelien.schiltz@epitech.eu