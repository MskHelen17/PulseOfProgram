package PulseOfProgram;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
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
                log.log(Level.SEVERE, "Exception: ", e);
        System.err.println("Конфигурационный файл не найден");
        //log
    } catch (IOException e) {
                log.log(Level.SEVERE, "Exception: ", e);
        System.err.println("Конфигурационный файл не читается");
        //log
    } catch (Exception e) {
                log.log(Level.SEVERE, "Exception: ", e);
        System.err.println("Ошибка загрузки конфигурационного файла");
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

