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
  REM * Discription:  This file is a wrapper file   *
  REM *     to DnD_Work.bat to allow a happy DOS    *
  REM *     debug window.                           *
  REM ***********************************************
  REM DUDE Don't you know the Player application?  This is the batch file that will
  REM   start it for you.  %application% takes 2 parameters the first is required!
  REM   Are you a NERD that knows ancient Operating Systems?  If so have fun editing.
  REM .
  REM %application% p1 p2
  REM   p1 = (default InitClient)
  REM        which initcheck sub-application to run
  REM        InitClient = InitClient application to review ORDER and Hit-Sheet
  REM        PlayerManager = PlayerManager Player Character application to edit characters
  REM        MapEditor = MapEditor DM application to edit MAPS
  REM        InitServer = InitServer DM application to serve the campaign
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

REM START IT with a separate window with a title and
  start "D&D %DnDApp% ...%DnDUser%..." /MIN /HIGH DnD_Work.bat %DnDApp% %2

REM Were Done Close It Down
  GOTO DONEDONE

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

:DONEDONE
  ECHO "GoodBye"