rem set file=homeftp.txt
set file=homeftpfull.txt
del playerfull.zip
"c:\pacl\pacomp" -r -p playerfull.zip c:\dev\initcheck\dist\*.* c:\dev\initcheck\player.mdb c:\dev\initcheck\images\*.* c:\dev\inticheck\sounds\*.* c:\dev\initcheck\lib\*.*  
ftp -n -v -s:%file% hadar.cse.buffalo.edu
