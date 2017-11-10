@echo off
  REM ***********************************************
  REM * Standard D&D Installation:                  *
  REM *   InitCheck         c:\dnd\initcheck        *
  REM *   Java 1.6.032_b05  c:\dnd\jre6             *
  REM * Created:  Jun 10, 2012                      *
  REM * Updated:  Sep 14, 2013  Corrected jar and paths to initcheck updates
  REM *                         Added default directory to the batch file
  REM * Updated:  Nov 03, 2013  Added Menu if 1st parameter missing.
  REM *                         Added options to run up to 3 instances each with logging
  REM * Updated:  Nov 10, 2013  Added Tee to log and display in DOS window
  REM *                           the results needed to help debug Java exceptions
  REM * Discription:  This file should run an       *
  REM *     instance of the D&D application delete  *
  REM *     log files and handle java version       *
  REM ***********************************************
  REM DUDE Don't you know the Player application?  This is the batch file that will
  REM   start it for you.  %application% takes 2 parameters the first is required!
  REM   Are you a NERD that knows ancient Operating Systems?  If so have fun editing.
  REM .
  REM %application% p1 p2
  REM   p1 = which initcheck sub-application to run
  REM          InitClient = InitClient application to review ORDER and Hit-Sheet
  REM          PlayerManager = PlayerManager Player Character application to edit characters
  REM          MapEditor = MapEditor DM application to edit MAPS
  REM          InitServer = InitServer DM application to serve the campaign
  REM   p2 = (default jre6) (optional parameter)
  REM        DEBUG = run debug version init.DEBUG.jar (avail for live debug)
  REM        jre7 = run the java 7 version
  REM ***********************************************

REM Default Path
  REM  set DnDDir="C:\dnd"
  set DnDDir=%CD%
  cd /D %DnDDir%

REM Parse FIRST Input Parameter (initcheck app to run) set default if missing
  set DnDApp=InitClient
  if "%1" == "/?" goto HELP
    if "%1" == "HELP" goto HELP
    if "%1" == "?" goto HELP
  if "%1" == "InitClient"    set DnDApp=InitClient
  if "%1" == "PlayerManager" set DnDApp=PlayerManager
  if "%1" == "MapEditor"     set DnDApp=MapEditor
  if "%1" == "InitServer"    set DnDApp=InitServer
  REM if "%1" == "StampVersion"   set DnDApp=StampVersion
  REM if "%1" == "ReadSpells2"    set DnDApp=ReadSpells2 %1
  REM if "%1" == "ParsePrereq"    set DnDApp=ParsePrereq
  REM if "%1" == "ParseFeats"     set DnDApp=ParseFeats %1
  REM if "%1" == "RegisterImages" set DnDApp=RegisterImages
  if NOT "%1" == "" goto DoneFirstParm
    CLS
    ECHO.
    ECHO You are starting DnD Initcheck without a parameter
    ECHO   Written by Alan Hunt the Java coding King.
    ECHO.
    ECHO Select the application you wish to start.
    ECHO PRESS...
    ECHO.
    ECHO 1 - CLIENT (%0 InitClient)
    ECHO 2 - PLAYER MANAGER (%0 PlayerManager)
    ECHO 3 - MAP EDITOR (%0 MapEditor)
    ECHO 4 - SERVER (%0 InitServer)
    ECHO.
  SET /P M=Type 1, 2, 3, or 4 then press ENTER:
    IF %M%==1 set DnDApp=InitClient
    IF %M%==2 set DnDApp=PlayerManager
    IF %M%==3 set DnDApp=MapEditor
    IF %M%==4 set DnDApp=InitServer
:DoneFirstParm
  goto SecondParm

:HELP
  ECHO ***********************************************
  ECHO DUDE Don't you know the Player application?  This is the batch file that will
  ECHO   start it for you.  %application% takes 2 parameters the first is required!
  ECHO   Are you a NERD that knows ancient Operating Systems?  If so have fun editing.
  ECHO .
  ECHO %application% p1 p2
  ECHO    p1 = (default InitClient = InitClient)
  ECHO         which initcheck sub-application to run
  ECHO         InitClient = InitClient application to review ORDER and Hit-Sheet
  ECHO         PlayerManager = PlayerManager Player Character application to edit characters
  ECHO         MapEditor = MapEditor DM application to edit MAPS
  ECHO         InitServer = InitServer DM application to serve the campaign
  ECHO    p2 = (optional parameter)
  ECHO         DEBUG = run debug version init.DEBUG.jar (avail for live debug)
  ECHO         JAVA7 = run the java 7 version
  ECHO ***********************************************
  PAUSE
  GOTO DONEDONE

REM Parse SECOND Parameter (java location) set default if missing
:SecondParm
  set DnDDebug=""
    if "%2" == "DEBUG"     set DnDDebug="DEBUG"
  set DnDJava=%DnDDir%\..\jre6
    if NOT "%2" == ""      set DnDJava=%2
  set JAVA_EXE=%DnDJava%\bin

REM Setup Log File Remove old and START
  set DnDUser=%USERNAME%
  set DnDLOG="%DnDDir%\D&D_Start.%DnDApp%.log"

  del %DnDLOG%
  if exist %DnDLOG% goto DnD1LOGFILEFAIL
    goto DnDLOGFILEPASS
  :DnD1LOGFILEFAIL
    set DnDUser=%USERNAME%2
    set DnDLOG="%DnDDir%\D&D_Start.%DnDApp%.instance2.log"
    del %DnDLOG%
    if exist %DnDLOG% goto DnD2LOGFILEFAIL
      goto DnDLOGFILEPASS
  :DnD2LOGFILEFAIL
    set DnDUser=%USERNAME%3
    set DnDLOG="%DnDDir%\D&D_Start.%DnDApp%.instance3.log"
    del %DnDLOG%
    if exist %DnDLOG% goto DnD3LOGFILEFAIL
      goto DnDLOGFILEPASS
  :DnD3LOGFILEFAIL
    set DnDUser=%USERNAME%4
    set DnDLOG="%DnDDir%\D&D_Start.%DnDApp%.instance4.log"
    del %DnDLOG%
    if exist %DnDLOG% goto DnD4LOGFILEFAIL
      goto DnDLOGFILEPASS
  :DnD4LOGFILEFAIL
    echo "FAILED -> TO MANY INSTANCES RUNNING...please close and restart"
    pause
    goto DONEDONE
  :DnDLOGFILEPASS
    echo Removed log file %DnDLOG%

REM Delete the Temporary Files
  if "%DnDApp%" == "InitClient"    set INITLogFile=ClientLog.txt
  if "%DnDApp%" == "PlayerManager" set INITLogFile=ManagerLog.txt
  if "%DnDApp%" == "InitServer"    set INITLogFile=ServerLog.txt

  del %INITLogFile%
  if errorlevel 1 goto INITLOGFILEFAIL
    echo "Removed Temporary log file %INITLogFIle%" 1>> %DnDLOG% 2>>&1
    goto INITLOGFILEPASS
  :INITLOGFILEFAIL
    echo "CAN NOT remove Temporary log file %INITLogFIle%" 1>> %DnDLOG% 2>>&1
  :INITLOGFILEPASS

REM Default Environment Variables
  set JAVA_LIB=.\lib
  set DIST=.\dist
  if %DnDDebug% == "" set CLASSPATH=%DIST%\INIT.jar
    if %DnDDebug% == "DEBUG" set CLASSPATH=%DIST%\INIT.DEBUG.jar
  set CLASSPATH=%CLASSPATH%;%JAVA_LIB%\util.jar
  set CLASSPATH=%CLASSPATH%;%JAVA_LIB%\looks-2.3.1.jar
  set CLASSPATH=%CLASSPATH%;%JAVA_LIB%\util.jar
  set CLASSPATH=%CLASSPATH%;%JAVA_LIB%\looks-2.3.1.jar
  set CLASSPATH=%CLASSPATH%;%JAVA_LIB%\jgoodies-common-1.0.0.jar
  set CLASSPATH=%CLASSPATH%;%JAVA_LIB%\log4j-1.2.16.jar
  set CLASSPATH=%CLASSPATH%;%JAVA_LIB%\jeks.jar
  set CLASSPATH=%CLASSPATH%;%JAVA_LIB%\jeksparser.jar
  set CLASSPATH=%CLASSPATH%;%JAVA_LIB%\jekstools.jar
  set CLASSPATH=%CLASSPATH%;%JAVA_LIB%\slf4j-api-1.6.1.jar
  set CLASSPATH=%CLASSPATH%;%JAVA_LIB%\jgroups-3.2.11.Final.jar
  set CLASSPATH=%CLASSPATH%;%JAVA_LIB%\rt.jar
  set J_Parm=-ms64M -mx128M -classpath %CLASSPATH% -Djava.net.preferIPv4Stack=true

REM WHICH ARE YOU RUNNING
  if "%DnDApp%" == "InitServer"    SET RUN_ME="%JAVA_EXE%\java" %J_Parm% initcheck.%DnDApp% %DnDUser%
  if "%DnDApp%" == "InitClient"    SET RUN_ME="%JAVA_EXE%\java" %J_Parm% initcheck.%DnDApp% %DnDUser%
  if "%DnDApp%" == "MapEditor"     SET RUN_ME="%JAVA_EXE%\java" %J_Parm% initcheck.%DnDApp%
  if "%DnDApp%" == "PlayerManager" SET RUN_ME="%JAVA_EXE%\java" %J_Parm% initcheck.%DnDApp%

REM START IT
  echo *********************************************** | tee -a %DnDLOG%
  ECHO * D and D RUNNING   %DnDDebug%    %DnDApp% | tee -a %DnDLOG%
    "%JAVA_EXE%\java" -version
    "%JAVA_EXE%\java" -version 1>> %DnDLOG% 2>>&1
    if     "%DnDApp%" == "InitServer" ECHO ... | tee -a %DnDLOG%
    if     "%DnDApp%" == "InitServer" ECHO Creating Server ... %DnDUser% ... | tee -a %DnDLOG%
    if     "%DnDApp%" == "InitServer" ECHO ... | tee -a %DnDLOG%
  ECHO *********************************************** | tee -a %DnDLOG%
  ECHO %RUN_ME% | tee -a %DnDLOG%
  ECHO *********************************************** | tee -a %DnDLOG%
  %RUN_ME% | tee -a %DnDLOG%

:DONEDONE
  echo *********************************************** | tee -a %DnDLOG%
  ECHO "GoodBye" | tee -a %DnDLOG%
EXIT