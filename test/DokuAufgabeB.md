# Dokumentation zur Teilaufgabe b)
Implementieren Sie eine Java-Lösung für die `enter`- und `exit`-Methoden mit privaten Semaphoren.

### Zugehörige Dateien
`LokB.java` `LokTestB.java` `LokB.class` `LokTestB.class`

## Umsetzung
Um dieses Problem mit privaten Semaphoren zu lösen, werden für jede Lok ein privates Semaphor erstellt sowie ein 
weiteres Sempahor für die Counter-Varaible. Dessen Einstellungen sehen wie folgt aus:

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

Neben den Semaphoren wird ein Counter benötigt, der die korrekte Reihenfolge der Loks gewährleistet. Da beide Loks bzw. 
Threads auf diesen zugreifen, benötigt man das 3. Semaphor, welches die Bearbeitung dieses Counters als kritischen 
Abschnitt behandelt. So wird ausgeschlossen, dass beide Threads gleichzeitig versuchen diesen Counter zu verändern.

Die Teilaufgabe b) wird mittels Arrays gelöst. Ist jedoch auch auf die Art und Weise wie Teilafgabe b) möglich. Jedoch 
wählte ich diese Variante für b), um beide Wege darzustellen.

Für genauere Details bzgl. der Anpassung der Methoden, o.Ä. findet man genaueres in der direkten Code-Dokumentation.

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
Die Konsolenausgabe sieht für dieses 1. Beispiel wie folgt aus:

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

#### Auswertung
Beispiel 1 erfüllt die vorgegebenen Bedingungen. Obwohl Lok 0 schneller ist als Lok 1, wird die Reihenfolge (Lok 0
--> Lok 1) eingehalten. Dies führt dazu, dass Lok 0 öfter warten muss als Lok 1.

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
Die Konsolenausgabe sieht für dieses 2. Beispiel wie folgt aus:

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

#### Auswertung
Beispiel 2 erfüllt auch die vorgegebenen Bedingungen. Obwohl Lok 0 und Lok 1 gleichzeitig in das gemeinsame
Schienenstück einfahren möchten, so wird durch den Counter die Reihenfolge der Loks (Lok 0 --> Lok 1) gewährleistet.
Des Weiteren wird durch die privaten Semaphoren verhindert, dass beide Loks gleichzeitig in das gemeinsame Teilstück 
einfahren.

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
Die Konsolenausgabe sieht für dieses 3. Beispiel wie folgt aus:

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

#### Auswertung
Auch Beispiel 3 erfüllt die vorgegebenen Bedingungen. Obwohl Lok 1 schneller ist als Lok 0, wird die Reihenfolge (Lok 0
--> Lok 1) eingehalten. Dies führt dazu, dass Lok 1 öfter warten muss als Lok 0.