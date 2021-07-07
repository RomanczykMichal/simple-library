# Simple Library school project
Java Servlets, Tomcat and Apache Derby DB combined to create very simple search app.

# How to run
1. You need to dowloand Apache DerbyDB and latest version of TomCat. You can do it from there:
- [Apache Derby](https://db.apache.org/derby/releases/release-10_15_2_0.cgi)
- [TomCat](https://tomcat.apache.org/download-10.cgi)
2. Edit file `derby.bat` in `DerbyDB` folder on:
```
set JAVA_HOME= YOUR JDK LOCATION
set DERBY_HOME= DERBY LIB FOLDER
set DERBY_SYSTEM_HOME= LOCATION WHERE YOU WANT TO CREATE YOUR NEW DB
```
Then save file.
  
3. Start PowerShell in `DerbyDB` folder.
4. If you haven't created DB yet, do it using command `./derby.bat ij createdb.sql`.
5. Run DB with command `start derby start`.
6. Open project.
7. Configure Application Server (Intellij Tutorial):

![image](https://user-images.githubusercontent.com/80456075/124751749-489e8900-df27-11eb-9588-6f21ed5f073b.png)
![image](https://user-images.githubusercontent.com/80456075/124751794-55bb7800-df27-11eb-948a-63b7a5ef4aa8.png)
![image](https://user-images.githubusercontent.com/80456075/124751841-6370fd80-df27-11eb-8987-a3708aee3d9e.png)
![image](https://user-images.githubusercontent.com/80456075/124751927-7aafeb00-df27-11eb-9060-8ba6ea736149.png)

And comfirm.

8. Run project and browse through list of books and have fun :)


# Solutions
Project contains basic usage of Java Servlets, TomCat Server, Apache DerbyDB, HTML, CSS and JS library for sorting.

# Screenshots
![image](https://user-images.githubusercontent.com/80456075/124752168-cd89a280-df27-11eb-80d1-1474eff32b4a.png)
![image](https://user-images.githubusercontent.com/80456075/124752211-dc705500-df27-11eb-983a-928aef028736.png)

