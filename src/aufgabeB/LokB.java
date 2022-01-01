package aufgabeB;

import java.util.concurrent.Semaphore;

public class LokB extends Thread {

    // Initialisierung der verwendetet Variablen
    private final int anzahl = 2;

    private final int id;
    private final long geschwindigkeit;
    private static int ctr = 0;
    private static boolean[] warten;

    private static Semaphore mutex = new Semaphore(1, true);
    private static Semaphore[] privSem;

    // Konstruktor eines Lok-Threads
    public LokB(int id, long geschwindigkeit) {
        this.id = id;
        this.geschwindigkeit = geschwindigkeit; // Die Geschwindigkeit jeder Lok wird mittels einer Zahl (long) als
                                                // Schlafzeit in Millisekunden angegeben und stellt somit deren
                                                // Geschwindigkeit in undefinierten "Geschwindigkeitseinheiten" dar.

        warten = new boolean[anzahl];
        privSem = new Semaphore[anzahl];

        // for-Schleife zur Initialisierung der privaten Sempahoren, sowie Zuweisung des Statuses der Loks
        for(int i = 0; i < anzahl; i++) {
            warten[i] = false;
            privSem[i] = new Semaphore(0, true);
        }
    }

    /*
     *
     * Diese Funktion lässt Lok 0 den kritischen Abschnitt betreten. Ggf. muss diese warten, wenn der Abschnitt
     * noch nicht frei ist. Durch die Variable ctr wird die Reihenfolge der Loks realisiert.
     *
     */
    public void enterLok0 () {
        System.out.println("Lok " + id + " möchte die Weiche befahren.");

        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
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
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        System.out.println("Lok " + id + " befährt die Weiche.");
    }

    /*
     *
     * Diese Funktion lässt Lok 0 den kritischen Abschnitt verlassen. Gleichzeitig wird hier der Thread von Lok 1
     * erneut angestoßen, sollte Lok 1 auf das Verlassen des kritischen Abschnitts von Lok 0 warten.
     *
     */
    public void exitLok0 () {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        ctr = 1;

        System.out.println("Lok " + id + " verlässt die Weiche.");
        if (warten[1]){
            warten[1] = false;
            privSem[1].release();
        }

        mutex.release();
    }

    /*
     *
     * Diese Funktion lässt Lok 1 den kritischen Abschnitt betreten. Ggf. muss diese warten, wenn der Abschnitt
     * noch nicht frei ist. Durch die Variable ctr wird die Reihenfolge der Loks realisiert.
     *
     */
    public void enterLok1 () {
        System.out.println("Lok " + id + " möchte die Weiche befahren.");

        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
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
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        System.out.println("Lok " + id + " befährt die Weiche.");
    }

    /*
     *
     * Diese Funktion lässt Lok 1 den kritischen Abschnitt verlassen. Gleichzeitig wird hier der Thread von Lok 0
     * erneut angestoßen, sollte Lok 0 auf das Verlassen des kritischen Abschnitts von Lok 1 warten.
     *
     */
    public void exitLok1 () {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        ctr = 0;

        System.out.println("Lok " + id + " verlässt die Weiche.");
        if (warten[0]){
            warten[0] = false;
            privSem[0].release();
        }

        mutex.release();
    }

    /*
     *
     * Diese Funktion lässt die Loks (im Sinne der Threads) schlafen, um das Fahren von Loks zu simulieren.
     * Je kürzer eine Lok bzw. der durch de Lok dargestellten Thread schläft, desto "schneller" fährt sie.
     * Übergabeparameter: @geschwindigkeit
     *
     */
    public void fahren(long geschwindigkeit)  {
        System.out.println("Lok " + id + " fährt.");
        try {
            sleep(geschwindigkeit);
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die sleep-Funktion eines Threads
        }
    }

    /*
     *
     * Diese Funktion legt den Ablauf und Funktionsweise eines Threads (hier einer Lok) fest, sobald dieser in der
     * main-Methode gestartet wird. Beide Loks können dauerhaft laufen auf Grund einer Endlosschleife.
     *
     */
    @Override
    public void run() {
        if (id == 0) {  // Durch eine if-Abfrage, wird sichergestellt, dass auch nur Lok0-Funktionen für Lok 0
                        // ausgeführt werden.
            while (true) {
                try {
                    fahren(geschwindigkeit);
                    enterLok0();
                    sleep(this.geschwindigkeit / 2); // Um den Zeitraum innerhalb des kritischen Abschnitts zu
                                                          // simulieren, schläft die Lok bzw. der Thread hier für die
                                                          // Hälfte der Zeit, die als ihre Geschwindigkeit angegeben
                                                          // ist.
                    exitLok0();
                } catch (InterruptedException e) {
                    e.printStackTrace(); // automatisch generierter catch-Block durch die sleep-Funktion eines Threads
                }

            }
        }

        if (id == 1) {  // Durch eine if-Abfrage, wird sichergestellt, dass auch nur Lok1-Funktionen für Lok 1
                        // ausgeführt werden.
            while (true) {
                try {
                    fahren(geschwindigkeit);
                    enterLok1();
                    sleep(this.geschwindigkeit / 2); // Um den Zeitraum innerhalb des kritischen Abschnitts zu
                                                          // simulieren, schläft die Lok bzw. der Thread hier für die
                                                          // Hälfte der Zeit, die als ihre Geschwindigkeit angegeben
                                                          // ist.
                    exitLok1();
                } catch (InterruptedException e) {
                    e.printStackTrace(); // automatisch generierter catch-Block durch die sleep-Funktion eines Threads
                }
            }
        }
    }
}