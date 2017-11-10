set JAVA_EXE=c:\jdk1.4\bin
rem set JAVA_EXE=c:\jdk1.4\jdk\bin
set JAVA_LIB=.\lib
set DIST=.\dist

set file=homeftp.txt
rem set file=workftp.txt
%JAVA_EXE%\java -ms64M -mx128M -classpath %DIST%\INIT.jar;%JAVA_LIB%\util.jar;%JAVA_LIB%\log4j-1.2.8.jar initcheck.StampVersion
del player.zip
"c:\pacl\pacomp" player.zip dist\INIT.jar player.mdb
ftp -n -v -s:%file% alanhunt.zxq.net
