import java.util.ArrayList;

public class LokTestB {

    public static void main(String[] args) {

        // Lok 0 langsamer als Lok 1
        ArrayList<LokB> threads = new ArrayList<>();

        final int lokSpeed = 4000;

        for(int i = 0; i < 2; i++) {
            LokB lok = new LokB(i, (lokSpeed * i + lokSpeed));
            System.out.println("Die Geschwindigkeit von Lok " + i + " beträgt " + (lokSpeed * i + lokSpeed) + " Einheiten.");
            threads.add(lok);
            threads.get(i).start();
        }

        /*// Lok 0 gleich schnell wie Lok 1
        ArrayList<LokB> threads = new ArrayList<>();

        final int lokSpeed = 4000;

        for(int i = 0; i < 2; i++) {
            LokB lok = new LokB(i, lokSpeed);
            System.out.println("Die Geschwindigkeit von Lok " + i + " beträgt " + lokSpeed + " Einheiten.");
            threads.add(lok);
            threads.get(i).start();
        }

        // Lok 0 schneller als Lok 1
        ArrayList<LokB> threads = new ArrayList<>();

        final int lokSpeed = 4000;

        for(int i = 0; i < 2; i++) {
            LokB lok = new LokB(i, (lokSpeed * 2) - (i * lokSpeed));
            System.out.println("Die Geschwindigkeit von Lok " + i + " beträgt " + (lokSpeed * 2) - (i * lokSpeed) + " Einheiten.");
            threads.add(lok);
            threads.get(i).start();
        }*/
    }
}
