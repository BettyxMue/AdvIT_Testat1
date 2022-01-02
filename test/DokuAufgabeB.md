# Dokumentation zur Teilaufgabe b)
Implementieren Sie eine Java-Lösung für die `enter`- und `exit`-Methoden mit privaten Semaphoren.

## Umsetzung


## Beispiele


### Beispiel 1: Lok 0 > Lok 1



```java
ArrayList<LokB> threads = new ArrayList<>();

final int lokSpeed = 4000;

for(int i = 0; i < 2; i++) {
    LokB lok = new LokB(i, (lokSpeed * i + lokSpeed));
    System.out.println("Die Geschwindigkeit von Lok " + i + " beträgt " + (lokSpeed * i + lokSpeed) + " Einheiten.");
    threads.add(lok);
    threads.get(i).start();
}

System.out.println("\nStart der Beispielausgabe: \n" );
```

### Beispiel 2: Lok 0 = Lok 1



```java
ArrayList<LokB> threads = new ArrayList<>();

final int lokSpeed = 4000;

for(int i = 0; i < 2; i++) {
    LokB lok = new LokB(i, lokSpeed);
    System.out.println("Die Geschwindigkeit von Lok " + i + " beträgt " + lokSpeed + " Einheiten.");
    threads.add(lok);
    threads.get(i).start();
}

System.out.println("\nStart der Beispielausgabe: \n" );
```

### Beispiel 3: Lok 0 < Lok 1



```java
ArrayList<LokB> threads = new ArrayList<>();

final int lokSpeed = 4000;

for(int i = 0; i < 2; i++) {
    LokB lok = new LokB(i, ((lokSpeed * 2) - (i * lokSpeed)));
    System.out.println("Die Geschwindigkeit von Lok " + i + " beträgt " + ((lokSpeed * 2) + (i * lokSpeed)) + " Einheiten.");
    threads.add(lok);
    threads.get(i).start();
}

System.out.println("\nStart der Beispielausgabe: \n" );
```

## Auswertung