remset JAVA_EXE=c:\jdk1.4\bin
 set JAVA_EXE=c:\jdk1.4\jdk\bin
set JAVA_LIB=.\lib
set DIST=.\dist

%JAVA_EXE%\java -ms64M -mx128M -classpath %DIST%\INIT.jar;%JAVA_LIB%\util.jar;%JAVA_LIB%\log4j-1.2.8.jar initcheck.ReadSpells2 %1
