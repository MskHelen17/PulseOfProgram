package pulseOfProgram;

import controller.ErrorMessageLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Preloader {

    //private Properties props;
    //private String fileName;
    private static Logger log = Logger.getLogger(Preloader.class.getName());

    public Preloader() {
        //props = p;
        //fileName = f;
    }


    public void loadConfigFile(String fileName, Properties props) {
        InputStream fs;
        try {
            File file = new File(fileName);
            //if(file.exists()) {
            //fs = new FileInputStream(file);
            //} else {
            //fs = Preloader.class.getResourceAsStream(fileName);
            fs = getClass().getClassLoader().getResourceAsStream(fileName);
            //fs = Preloader.class.getResourceAsStream("/resources/config.properties");
            //}
            props.load(fs);
            fs.close();

        } catch (FileNotFoundException e) {
            ErrorMessageLogger err = ErrorMessageLogger.getInstance();
            err.addErrorToListAndLog(e);
            err.showErrText(e);
            //log
        } catch (IOException e) {
            ErrorMessageLogger err = ErrorMessageLogger.getInstance();
            err.addErrorToListAndLog(e);
            err.showErrText(e);
            //log
        } catch (Exception e) {
            ErrorMessageLogger err = ErrorMessageLogger.getInstance();
            err.addErrorToListAndLog(e);
            err.showErrText(e);
            //log
        }

    }


    public void loadLoggingConfiguration (String fileName) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream fs = classLoader.getResourceAsStream(fileName);
            LogManager.getLogManager().readConfiguration(fs);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла logging.properties: " + e.toString());
        }
    }


}

