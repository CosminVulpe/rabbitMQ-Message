package parser;

import java.util.logging.Logger;

public abstract class FileParser {

    protected Logger LOG;

    public FileParser(Logger LOG) {
        this.LOG = LOG;
    }

    public abstract void parseFile(String fileName);

    protected abstract String getFilePath(String typeOfFile);

    protected void printLogInfo(String message){
        LOG.info(message);
    }


    protected void printLogWarn(String message){
        LOG.warning(message);
    }
}
