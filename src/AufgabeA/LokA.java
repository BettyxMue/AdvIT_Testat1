package AufgabeA;

import java.util.concurrent.Semaphore;

public class LokA extends Thread {

    private final int id;
    private final long speed;
    private static Semaphore besetzt = new Semaphore(0, true);
    private static Semaphore frei = new Semaphore(1, true);

    public LokA(int id, long speed) {
        this.id = id;
        this.speed = speed;
    }

    public void enterLok0() {
        System.out.println("Lok " + id + " möchte die Weiche befahren.");

        try {
            frei.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Lok " + id + " befährt die Weiche.");
    }

    public void exitLok0() {
        besetzt.release();
        System.out.println("Lok " + id + " verlässt die Weiche.");
    }

    public void enterLok1() {
        System.out.println("Lok " + id + " möchte die Weiche befahren.");

        try {
            besetzt.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Lok " + id + " befährt die Weiche.");
    }

    public void exitLok1() {
        frei.release();
        System.out.println("Lok " + id + " verlässt die Weiche.");
    }

    public void fahren(long speed) {
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