rem Find the data file on disk and build the registry file
java RegisterDB

rem Add a registry file to registry
regedit /s database.reg

