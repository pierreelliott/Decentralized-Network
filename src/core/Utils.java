package core;

public abstract class Utils extends CommInterface implements Runnable {

    public Utils(Object o) throws Exception {
        super(o);
    }

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
