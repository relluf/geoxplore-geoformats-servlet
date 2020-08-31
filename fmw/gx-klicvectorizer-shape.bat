@echo off
echo ------ >> D:\apps\GeoFormats\fmw\gx-klicvectorizer.log
date /t >> D:\apps\GeoFormats\fmw\gx-klicvectorizer.log
time /t >> D:\apps\GeoFormats\fmw\gx-klicvectorizer.log
echo IN: %1 >> D:\apps\GeoFormats\fmw\gx-klicvectorizer.log
echo OUT: %2 >> D:\apps\GeoFormats\fmw\gx-klicvectorizer.log
echo FORMAT: SHAPE
"C:\Program Files\FME\fme.exe" D:\apps\GeoFormats\KV-shape.fmw --GML_INPUT_FILE %1  --GML_INPUT_FILE_3 %1 --DestDataset_SHAPEFILE %2 >> D:\apps\GeoFormats\fmw\gx-klicvectorizer.log 2>&1