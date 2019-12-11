package nl.geoxplore.geoformats.fme;

import java.io.File;

public class Configuration
{
    private String sourceFileName;
    private String destFileName;
    private String sourceType;
    private String destType;

    public Configuration(File source, File dest, String sourceType, String destType)
    {
        this(source.getAbsolutePath(), dest.getAbsolutePath(), sourceType, destType);
    }

    public Configuration(String sourceFileName, String destFileName, String sourceType, String destType)
    {
        this.sourceFileName = sourceFileName;
        this.destFileName = destFileName;
        this.sourceType = sourceType;
        this.destType = destType;
    }

    public String getDestFileName()
    {
        return destFileName;
    }

    public void setDestFileName(String destFile)
    {
        destFileName = destFile;
    }

    public String getDestType()
    {
        return destType;
    }

    public void setDestType(String destType)
    {
        this.destType = destType;
    }

    public String getSourceFileName()
    {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFile)
    {
        sourceFileName = sourceFile;
    }

    public String getSourceType()
    {
        return sourceType;
    }

    public void setSourceType(String sourceType)
    {
        this.sourceType = sourceType;
    }
}
