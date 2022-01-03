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

Um die Reihenfolge zu gewährleisten wird der Counter zum Anfang auf 0 gesetzt. Eine Überprüfung dessen auf 0, befähigt
Lok 0 den kritischen Abschnitt betreten zu können durch die Freigabe ihres privaten Semaphors, welches den Startwert 0 
besitzt. Die Überprüfung des Counters erfolgt in einem `static Semaphor mutex` mit Startwert 1. Nur so kann Lok 0 dieses 
für die Einfahrt in den kritischen Bereich erhalten. Sollte der Counter 1 betragen, so bedeutete das, dass Lok 1 den 
kritischen Bereich noch nicht verlassen hat und Lok 0 somit warten müsste. Dies wird dadurch realisiert, dass das 
private Semaphor von Lok 0 nicht freigegeben wird und sie somit beim `privSem[0].acquire()` warten muss. 

Bei der Ausfahrt aus dem kritischen Bereich wird wie anfangs im `mutex` der Counter auf 1 gesetzt, sodass Lok 1 bei 
ihrer Überprüfung des Counters nun in den Bereich einfahren könnte. Des Weiteren findet ein erneuter Anstoß von Lok 1 
statt, sollte diese aus einer möglichen vorherigen Runde bereits auf das Einfahren in den kritischen Bereich warten. Ist
dies der Fall, so wird das private Semaphore `privSem[1]` von Lok 1 freigegeben. Auch dieses besitzt den Startwert 0.

Dieses oben beschrieben Prinzip wird auch bei Lok 1 so übernommen nur mit der Veränderung des Counters und den 
privaten Semaphoren von Lok 1 statt Lok 0. So wird z.B., sollte Lok 0 auf Eintritt in den kritischen Abschnitt warten, auch 
diese beim Verlassen von Lok 1 erneut angestoßen.

Die Teilaufgabe b) wird mittels Arrays gelöst. Ist jedoch auch auf die Art und Weise wie Teilafgabe b) möglich. Jedoch 
wählte ich diese Variante für b), um beide Wege darzustellen.

Für genauere Details bzgl. der Anpassung der Methoden, o.Ä. findet man genaueres in der direkten Code-Dokumentation.

## Beispiele
Zur Darstellung der Anforderungen an den Code werden drei Beispielfälle mit jeweils unterschiedlichen
Geschwindigkeitsverteilungen durchgeführt. Hierbei ist es wichtig die Reihenfolge der beiden Loks (zuerst Lok 0, danach
Lok 1) beizubehalten. Die Beispiele sollen verdeutlichen, dass der Programmcode funktioniert und die gestellten
Anforderungen der Aufgabe (Lösung mit privaten Sempahoren) erfüllt.

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