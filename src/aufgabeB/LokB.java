package aufgabeB;

import java.util.concurrent.Semaphore;

public class LokB extends Thread {

    private final int anzahl = 2;

    private final int id;
    private final long speed;
    private static int ctr = 0;
    private static boolean[] warten;

    private static Semaphore mutex = new Semaphore(1, true);
    private static Semaphore[] privSem;

    public LokB(int id, long speed) {
        this.id = id;
        this.speed = speed;

        warten = new boolean[anzahl];
        privSem = new Semaphore[anzahl];

        for(int i = 0; i < anzahl; i++) {
            warten[i] = false;
            privSem[i] = new Semaphore(0, true);
        }
    }

    public void enterLok0 () {
        System.out.println("Lok " + id + " möchte die Weiche befahren.");

        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(ctr == 0){
            privSem[0].release();
        } else {
            warten[0] = true;
            System.out.println("Lok " + id + " muss warten.");
        }

        mutex.release();

        try {
            privSem[0].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Lok " + id + " befährt die Weiche.");
    }

    public void exitLok0 () {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ctr = 1;

        System.out.println("Lok " + id + " verlässt die Weiche.");
        if (warten[1]){
            warten[1] = false;
            privSem[1].release();
        }

        mutex.release();
    }

    public void enterLok1 () {
        System.out.println("Lok " + id + " möchte die Weiche befahren.");

        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (ctr == 1){
            privSem[1].release();
        } else {
            warten[1] = true;
            System.out.println("Lok " + id + " muss warten.");
        }

        mutex.release();

        try {
            privSem[1].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Lok " + id + " befährt die Weiche.");
    }

    public void exitLok1 () {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ctr = 0;

        System.out.println("Lok " + id + " verlässt die Weiche.");
        if (warten[0]){
            warten[0] = false;
            privSem[0].release();
        }

        mutex.release();
    }

    public void fahren(long speed)  {
        System.out.println("Lok " + id + " fährt.");
        try {
            sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (id == 0) {
            while (true) {
                try {
                    fahren(speed);
                    enterLok0();
                    sleep(this.speed / 2);    // im k.A.
                    exitLok0();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        if (id == 1) {
            while (true) {
                try {
                    fahren(speed);
                    enterLok1();
                    sleep(this.speed / 2);    // im k.A.
                    exitLok1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}