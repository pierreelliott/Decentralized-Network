package core;

public abstract class Utils extends Comms implements Runnable {

    @Override
    public void run() {
        try {
            init();
            while(true) {
                preprocess();
                process();
                postprocess();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
