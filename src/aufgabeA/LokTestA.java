package aufgabeA;

public class LokTestA {

    /**
     *
     * Einstiegspunkt des Programms für Übung A
     *
     * @return void
     * @param args
     */
    public static void main(String[] args) {

        /**
         *
         * Beispiel 1: Lok 0 schneller als Lok 1 (Lok 0 > Lok 1)
         *
         */
        LokA lok0 = new LokA(0, 8000);
        LokA lok1 = new LokA(1, 4000);

        System.out.println("Die Geschwindigkeit von Lok " + lok0.getNummer() + " beträgt " + lok0.getGeschwindigkeit() + " Einheiten.");
        System.out.println("Die Geschwindigkeit von Lok " + lok1.getNummer() + " beträgt " + lok1.getGeschwindigkeit() + " Einheiten.");
        System.out.println("\nStart der Beispielausgabe: \n" );

        lok0.start();
        lok1.start();

        /**
         *
         * Beispiel 2: Lok 0 gleich schnell wie Lok 1 (Lok 0 = Lok 1)
         *
         */
        /*LokA lok00 = new LokA(0, 4000);
        LokA lok01 = new LokA(1, 4000);

        System.out.println("Die Geschwindigkeit von Lok " + lok0.getNumber() + " beträgt " + lok0.getGeschwindigkeit() + " Einheiten.");
        System.out.println("Die Geschwindigkeit von Lok " + lok1.getNumber() + " beträgt " + lok1.getGeschwindigkeit() + " Einheiten.");
        System.out.println("\nStart der Beispielausgabe: \n" );

        lok0.start();
        lok1.start();*/

        /**
         *
         * Beispiel 3: Lok 0 langsamer als Lok 1 (Lok 0 < Lok 1)
         *
         */
        /*LokA lok000 = new LokA(0, 4000);
        LokA lok001 = new LokA(1, 8000);

        System.out.println("Die Geschwindigkeit von Lok " + lok0.getNumber() + " beträgt " + lok0.getGeschwindigkeit() + " Einheiten.");
        System.out.println("Die Geschwindigkeit von Lok " + lok1.getNumber() + " beträgt " + lok1.getGeschwindigkeit() + " Einheiten.");
        System.out.println("\nStart der Beispielausgabe: \n" );

        lok0.start();
        lok1.start();*/
    }
}
