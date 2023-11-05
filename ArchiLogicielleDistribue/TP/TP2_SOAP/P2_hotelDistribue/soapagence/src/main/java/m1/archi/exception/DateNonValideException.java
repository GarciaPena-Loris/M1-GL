package m1.archi.exception;

public class DateNonValideException extends Exception {
    
        public DateNonValideException() {
            super();
        }
    
        public DateNonValideException(String message) {
            super(message);
        }
    
        public DateNonValideException(String message, Throwable cause) {
            super(message, cause);
        }
    
        public DateNonValideException(Throwable cause) {
            super(cause);
        }
    
        protected DateNonValideException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
}
