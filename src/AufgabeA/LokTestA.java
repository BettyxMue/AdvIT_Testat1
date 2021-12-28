package AufgabeA;

public class LokTestA {

    public static void main(String[] args) {

        // Lok 0 schneller als Lok 1
        LokA lok0 = new LokA(0, 8000);
        LokA lok1 = new LokA(1, 4000);

        lok0.start();
        lok1.start();

        /*// Lok 0 gleich schnell wie Lok 1
        AufgabeA.LokA lok00 = new AufgabeA.LokA(0, 4000);
        AufgabeA.LokA lok01 = new AufgabeA.LokA(1, 4000);

        lok00.start();
        lok01.start();

        // Lok 0 langsamer als Lok 1
        AufgabeA.LokA lok000 = new AufgabeA.LokA(0, 4000);
        AufgabeA.LokA lok001 = new AufgabeA.LokA(1, 8000);

        lok000.start();
        lok001.start();*/
    }
}
