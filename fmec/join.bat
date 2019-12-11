@echo off
cd d:\apps\fmec
d:
echo join %* >> D:\apps\fmec\fmec.log
"c:\Program Files (x86)\Java\jre7\bin\java.exe" -classpath fmec.jar;d:\apps\FME\fmeobjects\java\fmeobjects.jar nl.jess.servlet.geography.fme.Join %* >> D:\apps\fmec\fmec.log  2>&1 
echo. >> D:\apps\fmec\fmec.log