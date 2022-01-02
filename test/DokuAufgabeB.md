# Dokumentation zur Teilaufgabe b)
Implementieren Sie eine Java-Lösung für die `enter`- und `exit`-Methoden mit privaten Semaphoren.

## Umsetzung


```java
private static boolean[] warten;

private static Semaphore mutex = new Semaphore(1, true);
private static Semaphore[] privSem;

public LokB(int id, long geschwindigkeit) {
    this.id = id;                          
    this.geschwindigkeit = geschwindigkeit; 
       
    warten = new boolean[2];
    privSem = new Semaphore[2];
    
    for(int i = 0; i < 2; i++) {
        warten[i] = false;
        privSem[i] = new Semaphore(0, true);
    }
}
```        
    
## Beispiele


### Beispiel 1: Lok 0 > Lok 1
Im ersten Beispiel ist Lok 0 schneller als Lok 1. Dies wird über die Dauer der Schlafenszeit der beiden Threads
realisiert. Diese werden am Anfang bei Initialisierung an den Konstruktor in Form einer undefinierten und daher eher
veranschaulichenden Geschwindigkeitseinheit mitgegeben. Lok 0 wird öfters warten müssen, um die Reihenfolge zu 
gewährleisten.

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

#### Ausgabe


```java
Die Geschwindigkeit von Lok 0 beträgt 8000 Einheiten.
Die Geschwindigkeit von Lok 1 beträgt 4000 Einheiten.

Start der Beispielausgabe: 

Lok 0 fährt.
Lok 1 fährt.
Lok 0 möchte das gemeinsame Schienenstück befahren.
Lok 0 befährt das gemeinsame Schienenstück.
Lok 0 verlässt das gemeinsame Schienenstück.
Lok 0 fährt.
Lok 1 möchte das gemeinsame Schienenstück befahren.
Lok 1 befährt das gemeinsame Schienenstück.
Lok 0 möchte das gemeinsame Schienenstück befahren.
Lok 0 muss warten.
Lok 1 verlässt das gemeinsame Schienenstück.
Lok 1 fährt.
Lok 0 befährt das gemeinsame Schienenstück.
Lok 0 verlässt das gemeinsame Schienenstück.
Lok 0 fährt.
Lok 0 möchte das gemeinsame Schienenstück befahren.
Lok 0 muss warten.
Lok 1 möchte das gemeinsame Schienenstück befahren.
Lok 1 befährt das gemeinsame Schienenstück.
Lok 1 verlässt das gemeinsame Schienenstück.
Lok 1 fährt.
Lok 0 befährt das gemeinsame Schienenstück.
Lok 0 verlässt das gemeinsame Schienenstück.
Lok 0 fährt.
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

#### Ausgabe


```java
Die Geschwindigkeit von Lok 0 beträgt 4000 Einheiten.
Die Geschwindigkeit von Lok 1 beträgt 4000 Einheiten.

Start der Beispielausgabe: 

Lok 0 fährt.
Lok 1 fährt.
Lok 1 möchte das gemeinsame Schienenstück befahren.
Lok 0 möchte das gemeinsame Schienenstück befahren.
Lok 1 muss warten.
Lok 0 befährt das gemeinsame Schienenstück.
Lok 0 verlässt das gemeinsame Schienenstück.
Lok 0 fährt.
Lok 1 befährt das gemeinsame Schienenstück.
Lok 1 verlässt das gemeinsame Schienenstück.
Lok 1 fährt.
Lok 0 möchte das gemeinsame Schienenstück befahren.
Lok 0 befährt das gemeinsame Schienenstück.
Lok 1 möchte das gemeinsame Schienenstück befahren.
Lok 1 muss warten.
Lok 0 verlässt das gemeinsame Schienenstück.
Lok 0 fährt.
Lok 1 befährt das gemeinsame Schienenstück.
Lok 1 verlässt das gemeinsame Schienenstück.
Lok 1 fährt.
```

### Beispiel 3: Lok 0 < Lok 1
Im dritten Beispiel ist die Geschwindigkeit von Lok 1 höher als die Geschwindigkeit von Lok 0. Daher wird Lok 1
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

#### Ausgabe


```java
Die Geschwindigkeit von Lok 0 beträgt 4000 Einheiten.
Die Geschwindigkeit von Lok 1 beträgt 8000 Einheiten.

Start der Beispielausgabe: 

Lok 0 fährt.
Lok 1 fährt.
Lok 1 möchte das gemeinsame Schienenstück befahren.
Lok 1 muss warten.
Lok 0 möchte das gemeinsame Schienenstück befahren.
Lok 0 befährt das gemeinsame Schienenstück.
Lok 0 verlässt das gemeinsame Schienenstück.
Lok 0 fährt.
Lok 1 befährt das gemeinsame Schienenstück.
Lok 1 verlässt das gemeinsame Schienenstück.
Lok 1 fährt.
Lok 1 möchte das gemeinsame Schienenstück befahren.
Lok 1 muss warten.
Lok 0 möchte das gemeinsame Schienenstück befahren.
Lok 0 befährt das gemeinsame Schienenstück.
Lok 0 verlässt das gemeinsame Schienenstück.
Lok 0 fährt.
Lok 1 befährt das gemeinsame Schienenstück.
Lok 1 verlässt das gemeinsame Schienenstück.
Lok 1 fährt.
Lok 1 möchte das gemeinsame Schienenstück befahren.
Lok 1 muss warten.
```

## Auswertung