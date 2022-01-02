package aufgabeB;

import java.util.ArrayList;

public class LokTestB {

    /***
     *
     * Einstiegspunkt des Programms für Übung B
     *
     * Return: void
     * @param args
     *
     */
    public static void main(String[] args) {

        /***
         *
         * Beispiel 1: Lok 0 schneller als Lok 1 (Lok 0 > Lok 1)
         *
         */
        ArrayList<LokB> threads = new ArrayList<>();

        // Initialisierung der standardmäßigen Lokgeschwindigkeit
        final int lokSpeed = 4000;

        // Erzeugung und Start der Lok-Threads
        for(int i = 0; i < 2; i++) {
            LokB lok = new LokB(i, (lokSpeed * i + lokSpeed));
            System.out.println("Die Geschwindigkeit von Lok " + i + " beträgt " + (lokSpeed * i + lokSpeed) + " Einheiten.");
            threads.add(lok);
            threads.get(i).start();
        }

        System.out.println("\nStart der Beispielausgabe: \n" );

        /***
         *
         * Beispiel 2: Lok 0 gleich schnell wie Lok 1 (Lok 0 = Lok 1)
         *
         */
        /*ArrayList<LokB> threads = new ArrayList<>();

        // Initialisierung der standardmäßigen Lokgeschwindigkeit
        final int lokSpeed = 4000;

        // Erzeugung und Start der Lok-Threads
        for(int i = 0; i < 2; i++) {
            LokB lok = new LokB(i, lokSpeed);
            System.out.println("Die Geschwindigkeit von Lok " + i + " beträgt " + lokSpeed + " Einheiten.");
            threads.add(lok);
            threads.get(i).start();
        }

        System.out.println("\nStart der Beispielausgabe: \n" );*/

        /***
         *
         * Beispiel 3: Lok 0 langsamer als Lok 1 (Lok 0 < Lok 1)
         *
         */
        /*ArrayList<LokB> threads = new ArrayList<>();

        // Initialisierung der standardmäßigen Lokgeschwindigkeit
        final int lokSpeed = 4000;

        // Erzeugung und Start der Lok-Threads
        for(int i = 0; i < 2; i++) {
            LokB lok = new LokB(i, ((lokSpeed * 2) - (i * lokSpeed)));
            System.out.println("Die Geschwindigkeit von Lok " + i + " beträgt " + ((lokSpeed * 2) + (i * lokSpeed)) + " Einheiten.");
            threads.add(lok);
            threads.get(i).start();
        }

        System.out.println("\nStart der Beispielausgabe: \n" );*/
    }
}
