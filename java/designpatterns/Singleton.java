package designpatterns;

public class Singleton {

    // Step 1: volatile is mandatory
    private static volatile Singleton instance;

    // Step 2: private constructor
    private Singleton() {
    }

    public static Singleton getInstance() {
        // First check (no locking)
        if (instance == null) {
            synchronized (Singleton.class) {
                // Second check (with locking)
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
