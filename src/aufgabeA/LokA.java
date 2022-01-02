package aufgabeA;

import java.util.concurrent.Semaphore;

public class LokA extends Thread {

    // Initialisierung der verwendeten Variablen
    private final int number;
    private final long geschwindigkeit;

    // Initialisierung der benötigten Semaphoren
    private static Semaphore besetzt = new Semaphore(0, true);
    private static Semaphore frei = new Semaphore(1, true);

    /***
     *
     * Dies ist der Konstruktor der Klasse LokA. Jede Lok stellt dabei einen Thread dar.
     *
     * Return: void
     * @param: number, geschwindigkeit
     *
     */
    public LokA(int number, long geschwindigkeit) {
        this.number = number;                           // Id des Threads - Verwendung für Bezeichnung der Loks
        this.geschwindigkeit = geschwindigkeit; // Die Geschwindigkeit jeder Lok wird mittels einer Zahl (long) als
                                                // Schlafzeit in Millisekunden angegeben und stellt somit deren
                                                // Geschwindigkeit in undefinierten "Geschwindigkeitseinheiten" dar.
    }

    /***
     *
     * Diese Funktion lässt Lok 0 den kritischen Abschnitt betreten. Ggf. muss diese warten, wenn der Abschnitt
     * noch nicht frei ist.
     *
     * Return: void
     *
     */
    public void enterLok0() {
        System.out.println("Lok " + number + " möchte das gemeinsame Schienenstück befahren.");

        try {
            // Lok 0 erwirbt Semaphor "frei" (Setzen des Semaphorwerts auf 0), welches Startwert 1 hat und durch
            // exit-Methode von Lok 1 freigegeben werden kann
            frei.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        System.out.println("Lok " + number + " befährt das gemeinsame Schienenstück.");
    }

    /***
     *
     * Diese Funktion lässt Lok 0 den kritischen Abschnitt verlassen. Dies passiert nach einer bestimmten Zeit,
     * abhängig von der Geschwindigkeit der Lok (siehe dafür: run-Methode).
     *
     * Return: void
     *
     */
    public void exitLok0() {
        // Lok 0 gibt Semaphor "besetzt" frei (Setzen des Semaphorwerts auf 1), welches Startwert 0 hat, und gibt somit
        // Semaphor für Lok 1 frei, sodass diese kritischen Abschnitt befahren kann
        besetzt.release();
        System.out.println("Lok " + number + " verlässt das gemeinsame Schienenstück.");
    }

    /***
     *
     * Diese Funktion lässt Lok 1 den kritischen Abschnitt betreten. Ggf. muss diese warten, wenn der Abschnitt
     * noch nicht frei ist.
     *
     * Return: void
     *
     */
    public void enterLok1() {
        System.out.println("Lok " + number + " möchte das gemeinsame Schienenstück befahren.");

        try {
            // Lok 1 erwirbt Semaphor "besetzt" (Setzen des Semaphorwerts auf 0), welches vorher durch das Verlassen von
            // Lok 0 aus kritischen Bereich auf 1 gesetzt wurde
            besetzt.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die acquire-Funktion eines Sempahores
        }

        System.out.println("Lok " + number + " befährt das gemeinsame Schienenstück.");
    }

    /***
     *
     * Diese Funktion lässt Lok 0 den kritischen Abschnitt verlassen. Dies passiert nach einer bestimmten Zeit,
     * abhängig von der Geschwindigkeit der Lok (siehe dafür: run-Methode).
     *
     * Return: void
     *
     */
    public void exitLok1() {
        // Lok 1 gibt Semaphor "frei" frei (Setzen des Semaphorwerts auf 1), sodass Lok 0 den kritischen Abschnitt
        // wieder betreten bzw. Semaphor "frei" für Eintritt in dieses erhalten kann
        frei.release();
        System.out.println("Lok " + number + " verlässt das gemeinsame Schienenstück.");
    }

    /***
     *
     * Diese Funktion lässt die Loks (im Sinne der Threads) schlafen, um das Fahren von Loks zu simulieren.
     * Je kürzer eine Lok bzw. der durch die Lok dargestellten Thread schläft, desto "schneller" fährt sie.
     *
     * Return: void
     * @param: geschwindigkeit
     *
     */
    public void fahren(long geschwindigkeit) {
        System.out.println("Lok " + number + " fährt.");

        try {
            sleep(geschwindigkeit);
        } catch (InterruptedException e) {
            e.printStackTrace(); // automatisch generierter catch-Block durch die sleep-Funktion eines Threads
        }
    }

    /***
     *
     * Diese Funktion legt den Ablauf und Funktionsweise eines Threads (hier einer Lok) fest, sobald dieser in der
     * main-Methode gestartet wird. Beide Loks können dauerhaft laufen auf Grund einer Endlosschleife.
     *
     * Return: void
     *
     */
    @Override
    public void run() {
        if (number == 0) {  // Durch eine if-Abfrage, wird sichergestellt, dass auch nur Lok0-Funktionen für Lok 0
                        // ausgeführt werden.
            while (true) {
                try {
                    // Simulation des Fahrens auf der eigenen Strecke von Lok 0
                    fahren(geschwindigkeit);
                    // Start des Einfahrens der Lok 0 in den kritischen Bereich (bzw. Versuch, wenn dieser bereits
                    // von Lok 1 befahren wird)
                    enterLok0();
                    sleep(this.geschwindigkeit / 2);   // Um den Zeitraum innerhalb des kritischen Abschnitts zu
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

        if (number == 1) {  // Durch eine if-Abfrage, wird sichergestellt, dass auch nur Lok1-Funktionen für Lok 1
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

    /***
     *
     * Diese Funktion gibt die Geschwindigkeit einer Lok zurück in einer fiktiven Geschwindigkeitseinheit.
     *
     * Return: long
     *
     */
    public long getGeschwindigkeit() {
        return geschwindigkeit;
    }

    /***
     *
     * Diese Funktion gibt die "Id" einer Lok zurück. Der Name "number" wurde hier gewählt, um nicht mit der
     * standardmäßig gesetzten Id eines Threads verwechselt zu werden.
     *
     * Return: int
     *
     */
    public int getNumber(){
        return number;
    }
}