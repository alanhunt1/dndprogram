set JAVA_EXE=%JAVA_HOME%\bin
set JAVA_LIB=.\lib
set DIST=.\dist

rem set file=homeftp.txt
set file=workftp.txt
del player.zip
"%JAVA_EXE%\java" -ms64M -mx128M -classpath %DIST%\INIT.jar;%JAVA_LIB%\util.jar;%JAVA_LIB%\log4j-1.2.8.jar initcheck.StampVersion
"%JAVA_EXE%\java" -ms64M -mx128M -classpath %DIST%\INIT.jar;%JAVA_LIB%\util.jar;%JAVA_LIB%\log4j-1.2.8.jar initcheck.RegisterImages
"c:\pacl\pacomp" player.zip dist\INIT.jar player.mdb
ftp -n -v -s:%file% hadar.cse.buffalo.edu
