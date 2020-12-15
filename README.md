TT File sorter

This is a simple solution to sort files and directories for coping and coding.

To start the application you need to download it from Github, add a project to your IDE for example at Intellij the best way would be to add a project from version control.

Before starting make sure that you have installed the Lombok plugin and annotation processor are enabled.

You have to have installed MySql DB on your pc as well. At project util package -> class ConnectionUtil change the properties of user and password (at the moment they are user: admin, password: 12345) as they are at your DB.

Use a script from init_db.sql at resource package to create the schema. The simplest way to do it is: open MySql WorkBench, login by your user account copy to the new SQL tab all data from init_db.sql, and execute all rows.

If IDE will ask you to do any updates or install plugins, please do it :)

Hopefully, all is done in the correct way so now you can start the application. The result you will see at the terminal of your IDE.
