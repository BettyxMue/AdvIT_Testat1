package aufgabeB;

import java.util.concurrent.Semaphore;

public class LokB extends Thread {

    // Initialisierung der verwendetet Variablen
    private final int anzahl = 2;

    private final int id;   // Variablenname "Id" statt "Number" hier aufgrund der Initialisierung der Threads durch
                            // Schleife in main-Methode möglich
    private final long geschwindigkeit;
    private static int ctr = 0;
    private static boolean[] warten;

    // Initialisierung der benötigten Semaphoren
    private static Semaphore mutex = new Semaphore(1, true);
    private static Semaphore[] privSem;

    /**
     *
     * Dies ist der Konstruktor der Klasse LokB. Jede Lok stellt dabei einen Thread dar.
     *
     * @return void
     * @param id - Id des Threads --> Verwendung für Bezeichnung der Loks
     * @param geschwindigkeit - Geschwindigkeit jeder Lok wird mittels Zahl (long) als Schlafzeit in Millisekunden
     *                          angegeben und stellt somit deren Geschwindigkeit in undefinierten
     *                          "Geschwindigkeitseinheiten" dar, wobei hier eine niedrigere Geschwindigkeit für mehr
     *                          Schnelligkeit sorgt, da der Thread nicht so lang schlafen muss!!!
     *
     */
    public LokB(int id, long geschwindigkeit) {
        this.id = id;                           // Id des Threads - Verwendung für Bezeichnung der Loks
        this.geschwindigkeit = geschwindigkeit; // Die Geschwindigkeit jeder Lok wird mittels einer Zahl (long) als
                                                // Schlafzeit in Millisekunden angegeben und stellt somit deren
                                                // Geschwindigkeit in undefinierten "Geschwindigkeitseinheiten" dar.

        warten = new boolean[anzahl];
        privSem = new Semaphore[anzahl];

        // for-Schleife zur Initialisierung der privaten Sempahoren, sowie Zuweisung des Status der Loks
        for(int i = 0; i < anzahl; i++) {
            warten[i] = false;
            privSem[i] = new Semaphore(0, true);
        }
    }

    /**
     *
     * Diese Funktion lässt Lok 0 den kritischen Abschnitt betreten. Ggf. muss diese warten, wenn der Abschnitt
     * noch nicht frei ist. Durch die Variable ctr wird die Reihenfolge der Loks realisiert.
     *
     * @return void
     *
     */
    public void enterLok0 () {
        System.out.println("Lok " + id + " möchte das gemeinsame Schienenstück befahren.");

        try {
            // Start des kritischen Abschnitts in Bezug auf Counter, sodass dieser immer nur von einer Lok gleichzeitig
            // verändert werden kann
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        // Überprüfung durch Counter, ob Lok 1 bereits den kritischen Abschnitt verlassen hat, um die Reihenfolge
        // 0 --> 1 zu gewährleisten
        if(ctr == 0){
            // wenn ja, könnte Lok 0 den kritischen Abschnitt nun betreten
            privSem[0].release();
        } else {
            // wenn nein, muss Lok 0 warten bis Lok 1 kritischen Abschnitt verlassen hat
            warten[0] = true;
            System.out.println("Lok " + id + " muss warten.");
        }

        // Ende des kritischen Abschnitts für Counter
        mutex.release();

        try {
            // Lok 0 erhält ihr privates Semaphor und betritt damit den kritischen Bereich
            privSem[0].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        System.out.println("Lok " + id + " befährt das gemeinsame Schienenstück.");
    }

    /**
     *
     * Diese Funktion lässt Lok 0 den kritischen Abschnitt verlassen. Gleichzeitig wird hier der Thread von Lok 1
     * erneut angestoßen, sollte Lok 1 auf das Verlassen des kritischen Abschnitts von Lok 0 warten.
     *
     * @return void
     *
     */
    public void exitLok0 () {
        try {
            // Start des kritischen Abschnitts in Bezug auf Counter, sodass dieser immer nur von einer Lok gleichzeitig
            // verändert werden kann
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        ctr = 1; // Setzen des Counters auf 1, um Reihenfolge von 0 --> 1 zu gewährleisten

        System.out.println("Lok " + id + " verlässt das gemeinsame Schienenstück.");
        if (warten[1]){                 // Wenn Lok 1 bereits auf das Betreten des kritischen Abschnitts wartet, wird
            warten[1] = false;          // ihr Thread erneut angestoßen, sodass sie diesen Bereich nun befahren kann
            privSem[1].release();
        }

        // Ende des kritischen Abschnitts für Counter
        mutex.release();
    }

    /**
     *
     * Diese Funktion lässt Lok 1 den kritischen Abschnitt betreten. Ggf. muss diese warten, wenn der Abschnitt
     * noch nicht frei ist. Durch die Variable ctr wird die Reihenfolge der Loks realisiert.
     *
     * @return void
     *
     */
    public void enterLok1 () {
        System.out.println("Lok " + id + " möchte das gemeinsame Schienenstück befahren.");

        try {
            // Start des kritischen Abschnitts in Bezug auf Counter, sodass dieser immer nur von einer Lok gleichzeitig
            // verändert werden kann
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        // Überprüfung durch Counter, ob Lok 0 bereits den kritischen Abschnitt verlassen hat, um die Reihenfolge
        // 0 --> 1 zu gewährleisten
        if (ctr == 1){
            // wenn ja, könnte Lok 1 den kritischen Abschnitt nun betreten
            privSem[1].release();
        } else {
            // wenn nein, muss Lok 1 warten bis Lok 0 kritischen Abschnitt verlassen hat
            warten[1] = true;
            System.out.println("Lok " + id + " muss warten.");
        }

        // Ende des kritischen Abschnitts für Counter
        mutex.release();

        try {
            // Lok 1 erhält ihr privates Semaphor und betritt damit den kritischen Bereich
            privSem[1].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        System.out.println("Lok " + id + " befährt das gemeinsame Schienenstück.");
    }

    /**
     *
     * Diese Funktion lässt Lok 1 den kritischen Abschnitt verlassen. Gleichzeitig wird hier der Thread von Lok 0
     * erneut angestoßen, sollte Lok 0 auf das Verlassen des kritischen Abschnitts von Lok 1 warten.
     *
     * @return void
     *
     */
    public void exitLok1 () {
        try {
            // Start des kritischen Abschnitts in Bezug auf Counter, sodass dieser immer nur von einer Lok gleichzeitig
            // verändert werden kann
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        ctr = 0; // Setzen des Counters auf 0, um Reihenfolge von 0 --> 1 zu gewährleisten

        System.out.println("Lok " + id + " verlässt das gemeinsame Schienenstück.");
        if (warten[0]){                 // Wenn Lok 1 bereits auf das Betreten des kritischen Abschnitts wartet, wird
            warten[0] = false;          // ihr Thread erneut angestoßen, sodass sie diesen Bereich nun befahren kann
            privSem[0].release();
        }

        // Ende des kritischen Abschnitts für Counter
        mutex.release();
    }

    /**
     *
     * Diese Funktion lässt die Loks (im Sinne der Threads) schlafen, um das Fahren von Loks zu simulieren.
     * Je kürzer eine Lok bzw. der durch die Lok dargestellten Thread schläft, desto "schneller" fährt sie.
     *
     * @return void
     * @param geschwindigkeit
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

    /**
     *
     * Diese Funktion legt den Ablauf und Funktionsweise eines Threads (hier einer Lok) fest, sobald dieser in der
     * main-Methode gestartet wird. Beide Loks können dauerhaft laufen auf Grund einer Endlosschleife.
     *
     * @return void
     *
     */
    @Override
    public void run() {
        if (id == 0) {  // Durch eine if-Abfrage, wird sichergestellt, dass auch nur Lok0-Funktionen für Lok 0
                        // ausgeführt werden.
            while (true) {
                try {
                    // Simulation des Fahrens auf der eigenen Strecke von Lok 0
                    fahren(geschwindigkeit);
                    // Start des Einfahrens der Lok 0 in den kritischen Bereich (bzw. Versuch, wenn dieser bereits
                    // von Lok 1 befahren wird)
                    enterLok0();
                    sleep(this.geschwindigkeit / 2); // Um den Zeitraum innerhalb des kritischen Abschnitts zu
                                                          // simulieren, schläft die Lok bzw. der Thread hier für die
                                                          // Hälfte der Zeit, die als ihre Geschwindigkeit angegeben
                                                          // ist.
                    // Start des Verlassens des kritischen Bereichs von Lok 0
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
                    // Simulation des Fahrens auf der eigenen Strecke von Lok 1
                    fahren(geschwindigkeit);
                    // Start des Einfahrens der Lok 1 in den kritischen Bereich (bzw. Versuch, wenn dieser bereits
                    // von Lok 0 befahren wird)
                    enterLok1();
                    sleep(this.geschwindigkeit / 2);    // Um den Zeitraum innerhalb des kritischen Abschnitts zu
                                                             // simulieren, schläft die Lok bzw. der Thread hier für die
                                                             // Hälfte der Zeit, die als ihre Geschwindigkeit angegeben
                                                             // ist.
                    // Start des Verlassens des kritischen Bereichs von Lok 1
                    exitLok1();
                } catch (InterruptedException e) {
                    e.printStackTrace(); // automatisch generierter catch-Block durch die sleep-Funktion eines Threads
                }
            }
        }
    }
}