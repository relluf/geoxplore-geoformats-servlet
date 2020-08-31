@echo off
echo ------ >> D:\apps\GeoFormats\fmw\gx-klicvectorizer.log
date /t >> D:\apps\GeoFormats\fmw\gx-klicvectorizer.log
time /t >> D:\apps\GeoFormats\fmw\gx-klicvectorizer.log
echo IN: %1 >> D:\apps\GeoFormats\fmw\gx-klicvectorizer.log
echo OUT: %2 >> D:\apps\GeoFormats\fmw\gx-klicvectorizer.log
REM TODO I think the -DXF_DEST_FILE is ignored and hard-coded to layers.dxf 
"C:\Program Files\FME\fme.exe" D:\apps\GeoFormats\KV.fmw --GML_INPUT_FILE %1  --GML_INPUT_FILE_3 %1 --DGN_DEST_DIR %2 --DXF_DEST_DIR %2 --DXF_DEST_FILE %2\LG_nl-imkl.dxf >> D:\apps\GeoFormats\fmw\gx-klicvectorizer.log 2>&1