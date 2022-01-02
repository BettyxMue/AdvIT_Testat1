# Dokumentation zur Teilaufgabe b)
Implementieren Sie eine Java-Lösung für die `enter`- und `exit`-Methoden mit privaten Semaphoren.

## Umsetzung


## Beispiele


### Beispiel 1: Lok 0 < Lok 1
Im ersten Beispiel ist Lok 0 langsamer als Lok 1. Dies wird über die Dauer der Schlafenszeit der beiden Threads
realisiert. Diese werden am Anfang bei Initialisierung an den Konstruktor in Form einer undefinierten und daher eher
veranschaulichenden Geschwindigkeitseinheit mitgegeben. Hierbei ist wichtig zu verstehen, dass die Schlafenszeit länger
ist je größer die "Geschwindigkeit" ist.

```java
ArrayList<LokB> threads = new ArrayList<>();

final int geschwindigkeit = 8000;

for(int i = 0; i < 2; i++) {
    LokB lok = new LokB(i, (geschwindigkeit - ((geschwindigkeit / 2) * i)));
    System.out.println("Die Geschwindigkeit von Lok " + i + " beträgt " + (geschwindigkeit - ((geschwindigkeit / 2) * i)) + " Einheiten.");
    threads.add(lok);
    threads.get(i).start();
}

System.out.println("\nStart der Beispielausgabe: \n" );
```

### Beispiel 2: Lok 0 = Lok 1
Im zweiten Beispiel sind beide Loks gleich schnell, jedoch muss trotzdem die vorgegebene Reihenfolge beibehalten werden.

```java
ArrayList<LokB> threads = new ArrayList<>();

final int geschwindigkeit = 4000;

for(int i = 0; i < 2; i++) {
    LokB lok = new LokB(i, geschwindigkeit);
    System.out.println("Die Geschwindigkeit von Lok " + i + " beträgt " + geschwindigkeit + " Einheiten.");
    threads.add(lok);
    threads.get(i).start();
}

System.out.println("\nStart der Beispielausgabe: \n" );
```

### Beispiel 3: Lok 0 > Lok 1
Im dritten Beispiel ist die Geschwindigkeit von Lok 1 geringer als die Geschwindigkeit von Lok 0. Daher wird Lok 0
öfters warten müssen, um die abwechselnde Reihenfolge von Lok 0 und Lok 1 zu gewährleisten.

```java
ArrayList<LokB> threads = new ArrayList<>();

final int geschwindigkeit = 4000;

for(int i = 0; i < 2; i++) {
    LokB lok = new LokB(i, ((geschwindigkeit) + (i * geschwindigkeit)));
    System.out.println("Die Geschwindigkeit von Lok " + i + " beträgt " + ((geschwindigkeit) + (i * geschwindigkeit)) + " Einheiten.");
    threads.add(lok);
    threads.get(i).start();
}

System.out.println("\nStart der Beispielausgabe: \n" );
```

## Auswertung