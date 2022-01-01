package aufgabeA;

import java.util.concurrent.Semaphore;

public class LokA extends Thread {

    // Initialisierung der verwendeten Variablen
    private final int id;
    private final long geschwindigkeit;
    private static Semaphore besetzt = new Semaphore(0, true);
    private static Semaphore frei = new Semaphore(1, true);

    // Konstruktor eines Lok-Threads
    public LokA(int id, long geschwindigkeit) {
        this.id = id;
        this.geschwindigkeit = geschwindigkeit; // Die Geschwindigkeit jeder Lok wird mittels einer Zahl (long) als
                                                // Schlafzeit in Millisekunden angegeben und stellt somit deren
                                                // Geschwindigkeit in undefinierten "Geschwindigkeitseinheiten" dar.
    }

    /*
     *
     * Diese Funktion lässt Lok 0 den kritischen Abschnitt betreten. Ggf. muss diese warten, wenn der Abschnitt
     * noch nicht frei ist.
     *
     */
    public void enterLok0() {
        System.out.println("Lok " + id + " möchte die Weiche befahren.");

        try {
            frei.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        System.out.println("Lok " + id + " befährt die Weiche.");
    }

    /*
     *
     * Diese Funktion lässt Lok 0 den kritischen Abschnitt verlassen. Dies passiert nach einer bestimmten Zeit,
     * abhängig von der Geschwindigkeit der Lok (siehe dafür: run-Methode).
     *
     */
    public void exitLok0() {
        besetzt.release();
        System.out.println("Lok " + id + " verlässt die Weiche.");
    }

    /*
     *
     * Diese Funktion lässt Lok 1 den kritischen Abschnitt betreten. Ggf. muss diese warten, wenn der Abschnitt
     * noch nicht frei ist.
     *
     */
    public void enterLok1() {
        System.out.println("Lok " + id + " möchte die Weiche befahren.");

        try {
            besetzt.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        System.out.println("Lok " + id + " befährt die Weiche.");
    }

    /*
     *
     * Diese Funktion lässt Lok 0 den kritischen Abschnitt verlassen. Dies passiert nach einer bestimmten Zeit,
     * abhängig von der Geschwindigkeit der Lok (siehe dafür: run-Methode).
     *
     */
    public void exitLok1() {
        frei.release();
        System.out.println("Lok " + id + " verlässt die Weiche.");
    }

    /*
     *
     * Diese Funktion lässt die Loks (im Sinne der Threads) schlafen, um das Fahren von Loks zu simulieren.
     * Je kürzer eine Lok bzw. der durch de Lok dargestellten Thread schläft, desto "schneller" fährt sie.
     * Übergabeparameter: @speed
     *
     */
    public void fahren(long geschwindigkeit) {
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
                    sleep(this.geschwindigkeit / 2);   // Um den Zeitraum innerhalb des kritischen Abschnitts zu
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
                    sleep(this.geschwindigkeit / 2);    // Um den Zeitraum innerhalb des kritischen Abschnitts zu
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