rem set file=homeftp.txt
set file=workftpfull.txt
del playerfull.zip
"c:\pacl\pacomp" -r -p playerfull.zip c:\oldbin\initcheck\dist\*.* c:\oldbin\initcheck\player.mdb c:\oldbin\initcheck\start*.bat c:\oldbin\initcheck\install.bat c:\oldbin\initcheck\images\*.* c:\oldbin\inticheck\sounds\*.* c:\oldbin\initcheck\saves\*.* c:\oldbin\initcheck\lib\*.* c:\oldbin\initcheck\database.reg c:\oldbin\initcheck\*.class
ftp -n -v -s:%file% hadar.cse.buffalo.edu
