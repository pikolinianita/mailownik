/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.utils;

import java.io.IOException;
import java.util.logging.Formatter;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

class PikaFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        return record.getLevel().getName() + "\t" + record.getMessage() + "\t" + record.getSourceClassName() + "\t" + record.getSourceMethodName() + "\n";
    }

}
/**
 *
 * @author piko
 */
public class PikaLogger {    
    
    private static  Logger logger;
    /**
     * @param name
     * @param path
     * @throws java.io.IOException
     */
    
    public PikaLogger(String name, String path) throws IOException{
        logger = Logger.getLogger(name);
        Formatter pf = new PikaFormatter();
        FileHandler  fh =  new FileHandler(path);
        fh.setFormatter(pf);
        logger.addHandler(fh);
        logger.setLevel(Level.FINEST);
    }
    
    public Logger getLogger(){
        return logger;
    }
    
}