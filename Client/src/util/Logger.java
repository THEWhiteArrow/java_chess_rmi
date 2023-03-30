package util;

public class Logger {
    private static Logger instance;
    private static Object lock = new Object();
    private Logger(){  }

    public static Logger getInstance(){
        if( instance == null){
            synchronized (lock){
                if( instance == null)
                    instance = new Logger();
            }
        }
        return instance;
    }

    private void print(String s){
        System.out.println("LOGGER: "+ s);
    }

    public static void log(String s){
        getInstance().print(s);
    }
}
