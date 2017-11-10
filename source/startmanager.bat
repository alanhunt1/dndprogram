rem set JAVA_EXE=.\
set JAVA_EXE=%JAVA_HOME%\bin
rem set JAVA_EXE=C:\Program Files (x86)\Java\jre6\bin
rem set java_exe=c:\glassfish3\jdk\bin
rem set JAVA_EXE=c:\jdk1.4\jdk\bin
rem set JAVA_EXE=c:\jdk1.4\bin
set JAVA_LIB=.\lib
set DIST=.\dist
del ServerLog.txt

set classpath=%DIST%\INIT.jar;%JAVA_LIB%\util.jar;%JAVA_LIB%\looks-2.3.1.jar
set classpath=%CLASSPATH%;%JAVA_LIB%\jgoodies-common-1.0.0.jar;%JAVA_LIB%\log4j-1.2.16.jar;%JAVA_LIB%\jeks.jar
set classpath=%CLASSPATH%;%JAVA_LIB%\jeksparser.jar;%JAVA_LIB%\jekstools.jar;%JAVA_LIB%\slf4j-api-1.6.1.jar
set classpath=%CLASSPATH%;%JAVA_LIB%\jgroups-3.2.11.Final.jar
set classpath=%CLASSPATH%;%JAVA_LIB%\jackcess-2.1.3.jar
set classpath=%CLASSPATH%;%JAVA_LIB%\hsqldb.jar
set classpath=%CLASSPATH%;%JAVA_LIB%\rt.jar

"%JAVA_EXE%\java" -ms64M -mx128M -classpath %CLASSPATH%  -Djava.net.preferIPv4Stack=true initcheck.PlayerManager
