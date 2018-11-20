::-----------------------------------------------------------------------------::
:: Name .........: make.cmd
:: Project ......: Part of the JTSDK Version 3.0.0 Project
:: Description ..: JTEnvJV Build Script
:: Project URL ..: https://github.com/KI7MT
::
:: Author .......: Greg, Beam, KI7MT, <ki7mt@yahoo.com>
:: Copyright ....: Copyright (C) 2018 Greg Beam, KI7MT
:: License ......: GPL-3
::
:: make.cmd is free software: you can redistribute it and/or modify it
:: under the terms of the GNU General Public License as published by the Free
:: Software Foundation either version 3 of the License, or (at your option) any
:: later version. 
::
:: make.cmd is distributed in the hope that it will be useful, but WITHOUT
:: ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
:: FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
:: details.
::
:: You should have received a copy of the GNU General Public License
:: along with this program.  If not, see <http://www.gnu.org/licenses/>.
::-----------------------------------------------------------------------------::
@ECHO OFF

SET appname=JTEnvJV

:: Set the Git tag into a file
>%JTSDK_HOME%\ver.git (
git rev-parse --short HEAD
)

:: Get Command line Options %1
IF /I [%1]==[clean] ( GOTO _CLEAN )
IF /I [%1]==[build] ( GOTO _BUILD )
IF /I [%1]==[install] ( GOTO _INSTALL )
IF /I [%1]==[help] ( GOTO _HELP )
GOTO HELP

:: Note: The requires that MSYS2 be installed first as it uses the (rm) package
:_CLEAN
CLS
ECHO ------------------------------
ECHO  Clean %appname%
ECHO ------------------------------
ECHO.
PUSHD %CD%
gradle clean && ECHO.
POPD
ECHO.
GOTO EOF

:_BUILD
CLS
ECHO ------------------------------
ECHO  Building %appname%
ECHO ------------------------------
ECHO.
PUSHD %CD%
gradle installDist && ECHO.
POPD
GOTO EOF

:_INSTALL
CLS
ECHO -------------------------------------------
ECHO  Installing %appname% Application
ECHO -------------------------------------------
ECHO.

:: Change Directories to : jtenv\build\install and copy files
PUSHD %CD%\build\install
ECHO   Installing JTEnvJV Application
robocopy %CD%\ %JTSDK_HOME%\tools\scripts\java /E /NFL /NDL /NJH /NJS /nc /ns /np
ECHO   Destination..: %JTSDK_HOME%\tools\scripts\java
ECHO   Usage........: Type, jtenv^-jv ^-h
ECHO.
ECHO   Finished
POPD
GOTO EOF

:: ----------------------------------------------------------------------------
::  HELP MESSAGE
:: ----------------------------------------------------------------------------
:_HELP
CLS
ECHO ------------------------------
ECHO  %appname% Make Help
ECHO ------------------------------
ECHO.
ECHO  The build script takes one option^:
ECHO.
ECHO    clean       :  clean the build tree
ECHO    build       :  build the application
ECHO    install     :  install the application
ECHO.
ECHO    Example:
ECHO    make build
ECHO    make install
ECHO.
GOTO EOF

:EOF
EXIT /b 0

:: ----------------------------------------------------------------------------
::  ERROR MESSAGES
:: ----------------------------------------------------------------------------
:_NOT_DEFINED
CLS
ECHO ------------------------------
ECHO  Environment Error
ECHO ------------------------------
ECHO.
ECHO   JTSDK_HOME ^= NOT SET
ECHO. 
ECHO   This script must be run from within
ECHO   the JTSDK Environment.
ECHO.
ECHO   Alternatively, you can manyally set the
ECHO   JTSDK_HOME variable with the following:
ECHO.
ECHO   C-Drive Location
ECHO   set JTSDK_HOME=C:\JTSDK-Tools
ECHO.
ECHO   D-Drive Location
ECHO   set JTSDK_HOME=D:\JTSDK-Tools
ECHO.
ECHO   Then re-run your commands.
ECHO.
exit /b 1