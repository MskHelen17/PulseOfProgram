
package controller;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrorMessageLogger {

    private static ArrayList<Exception> errorList;
    private static Logger log = Logger.getLogger(ErrorMessageLogger.class.getName());

    private ErrorMessageLogger(){
        errorList = new ArrayList<>();
    }

    private static class ErrorMessageLoggerHolder {
        private final static ErrorMessageLogger instance = new ErrorMessageLogger();
    }

    public static ErrorMessageLogger getInstance() {
        return ErrorMessageLoggerHolder.instance;
    }

    public int addErrorToListAndLog(Exception e) {
        errorList.add(e);
        if (Controller.isLoggingOn()) {
            log.log(Level.SEVERE, "Exception: ", e);
        }
        return errorList.size();
    }

    public int getErrorCount () {
        return errorList.size();
    }

    public void showErrText(Exception e) {

        System.err.println(e.getMessage());
    }

}
