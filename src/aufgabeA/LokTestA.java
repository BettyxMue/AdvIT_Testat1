package aufgabeA;

public class LokTestA {

    public static void main(String[] args) {

        // Beispiel 1: Lok 0 schneller als Lok 1 (Lok 0 > Lok 1)
        LokA lok0 = new LokA(0, 8000);
        LokA lok1 = new LokA(1, 4000);

        lok0.start();
        lok1.start();

        /*// Beispiel 2: Lok 0 gleich schnell wie Lok 1 (Lok 0 = Lok 1)
        LokA lok00 = new LokA(0, 4000);
        LokA lok01 = new LokA(1, 4000);

        lok00.start();
        lok01.start();

        // Beispiel 3: Lok 0 langsamer als Lok 1 (Lok 0 < Lok 1)
        LokA lok000 = new AufgabeA.LokA(0, 4000);
        LokA lok001 = new AufgabeA.LokA(1, 8000);

        lok000.start();
        lok001.start();*/
    }
}
