# Dokumentation zur Teilaufgabe a)
Implementieren Sie eine Java-Lösung für die `enter`- und `exit`-Methoden als Erzeuger-/Verbraucher-Problem.

### Zugehörige Dateien
`LokA.java` `LokTestA.java` `LokA.class` `LokTestA.class`

## Umsetzung
Um die Problemstellung auf Grundlage der Erzeuger-/Verbraucher-Problematik zu beheben, werden zwei Sempahoren verwendet 
mit den folgenden Eigenschaften:
```java
private static Semaphore besetzt = new Semaphore(0, true);
private static Semaphore frei = new Semaphore(1, true);
```

Durch die unterschiedliche Initialisierung kann sichergestellt werden, dass zuerst Lok 0 in den gemeinsamen Bereich 
einfährt. Diese Einstellung sorgt auch dafür, dass die Loks alternierend das gemeinsame Schienenstück befahren. Das 
liegt daran, dass das Einfahren von Lok 1 das Verlassen von Lok 0 erfordert und umgekehrt. Nebenbei sorgt dies auch 
dafür, dass niemals beide Loks gleichzeitig sich innerhalb des gemeinsamen Bereichs befinden können.

Für genauere Details bzgl. der Anpassung der Methoden, o.Ä. findet man genaueres in der direkten Code-Dokumentation.

## Beispiele
Zur Darstellung der Anforderungen an den Code werden drei Beispielfälle mit jeweils unterschiedlichen 
Geschwindigkeitsverteilungen durchgeführt. Hierbei ist es wichtig die Reihenfolge der beiden Loks (zuerst Lok 0, danach 
Lok 1) beizubehalten.

### Beispiel 1: Lok 0 > Lok 1
Im ersten Beispiel ist Lok 0 schneller als Lok 1. Dies wird über die Dauer der Schlafenszeit der beiden Threads
realisiert. Diese werden am Anfang bei Initialisierung an den Konstruktor in Form einer undefinierten und daher eher
veranschaulichenden Geschwindigkeitseinheit mitgegeben. Lok 0 wird öfters warten müssen, um die Reihenfolge zu
gewährleisten.

```java
LokA lok0 = new LokA(0, 8000);
LokA lok1 = new LokA(1, 4000);

System.out.println("Die Geschwindigkeit von Lok " + lok0.getNummer() + " beträgt " + lok0.getGeschwindigkeit() + " Einheiten.");
System.out.println("Die Geschwindigkeit von Lok " + lok1.getNummer() + " beträgt " + lok1.getGeschwindigkeit() + " Einheiten.");
System.out.println("\nStart der Beispielausgabe: \n" );

lok0.start();
lok1.start();
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
Lok 0 möchte das gemeinsame Schienenstück befahren.
Lok 1 möchte das gemeinsame Schienenstück befahren.
Lok 1 befährt das gemeinsame Schienenstück.
Lok 1 verlässt das gemeinsame Schienenstück.
Lok 1 fährt.
Lok 0 befährt das gemeinsame Schienenstück.
Lok 0 verlässt das gemeinsame Schienenstück.
Lok 0 fährt.
Lok 0 möchte das gemeinsame Schienenstück befahren.
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
LokA lok0 = new LokA(0, 4000);
LokA lok1 = new LokA(1, 4000);

System.out.println("Die Geschwindigkeit von Lok " + lok0.getNummer() + " beträgt " + lok0.getGeschwindigkeit() + " Einheiten.");
System.out.println("Die Geschwindigkeit von Lok " + lok1.getNummer() + " beträgt " + lok1.getGeschwindigkeit() + " Einheiten.");
System.out.println("\nStart der Beispielausgabe: \n" );

lok0.start();
lok1.start();
```

#### Ausgabe
Die Konsolenausgabe sieht für dieses 2. Beispiel wie folgt aus:

```java
Die Geschwindigkeit von Lok 0 beträgt 4000 Einheiten.
Die Geschwindigkeit von Lok 1 beträgt 4000 Einheiten.

Start der Beispielausgabe: 

Lok 1 fährt.
Lok 0 fährt.
Lok 0 möchte das gemeinsame Schienenstück befahren.
Lok 1 möchte das gemeinsame Schienenstück befahren.
Lok 0 befährt das gemeinsame Schienenstück.
Lok 0 verlässt das gemeinsame Schienenstück.
Lok 0 fährt.
Lok 1 befährt das gemeinsame Schienenstück.
Lok 1 verlässt das gemeinsame Schienenstück.
Lok 1 fährt.
Lok 0 möchte das gemeinsame Schienenstück befahren.
Lok 0 befährt das gemeinsame Schienenstück.
Lok 0 verlässt das gemeinsame Schienenstück.
Lok 1 möchte das gemeinsame Schienenstück befahren.
Lok 0 fährt.
Lok 1 befährt das gemeinsame Schienenstück.
Lok 1 verlässt das gemeinsame Schienenstück.
Lok 1 fährt.
```

#### Auswertung
Beispiel 2 erfüllt auch die vorgegebenen Bedingungen. Obwohl Lok 0 und Lok 1 gleichzeitig in das gemeinsame 
Schienenstück einfahren möchten, so wird durch die unterschiedlichen Einstellungen der beiden Semaphoren die Reihenfolge 
der Loks (Lok 0 --> Lok 1) gewährleistet. Des Weiteren wird durch die Semaphoren verhindert, dass beide Loks 
gleichzeitig in das gemeinsame Teilstück einfahren.

### Beispiel 3: Lok 0 < Lok 1
Im dritten Beispiel ist die Geschwindigkeit von Lok 1 höher als die Geschwindigkeit von Lok 0. Daher wird Lok 1 
öfters warten müssen, um die abwechselnde Reihenfolge von Lok 0 und Lok 1 zu gewährleisten.

```java
LokA lok0 = new LokA(0, 4000);
LokA lok1 = new LokA(1, 8000);

System.out.println("Die Geschwindigkeit von Lok " + lok0.getNummer() + " beträgt " + lok0.getGeschwindigkeit() + " Einheiten.");
System.out.println("Die Geschwindigkeit von Lok " + lok1.getNummer() + " beträgt " + lok1.getGeschwindigkeit() + " Einheiten.");
System.out.println("\nStart der Beispielausgabe: \n" );

lok0.start();
lok1.start();
```

#### Ausgabe
Die Konsolenausgabe sieht für dieses 3. Beispiel wie folgt aus:

```java
Die Geschwindigkeit von Lok 0 beträgt 4000 Einheiten.
Die Geschwindigkeit von Lok 1 beträgt 8000 Einheiten.

Start der Beispielausgabe: 

Lok 1 fährt.
Lok 0 fährt.
Lok 1 möchte das gemeinsame Schienenstück befahren.
Lok 0 möchte das gemeinsame Schienenstück befahren.
Lok 0 befährt das gemeinsame Schienenstück.
Lok 0 verlässt das gemeinsame Schienenstück.
Lok 0 fährt.
Lok 1 befährt das gemeinsame Schienenstück.
Lok 1 verlässt das gemeinsame Schienenstück.
Lok 1 fährt.
Lok 1 möchte das gemeinsame Schienenstück befahren.
Lok 0 möchte das gemeinsame Schienenstück befahren.
Lok 0 befährt das gemeinsame Schienenstück.
Lok 0 verlässt das gemeinsame Schienenstück.
Lok 0 fährt.
Lok 1 befährt das gemeinsame Schienenstück.
Lok 1 verlässt das gemeinsame Schienenstück.
Lok 1 fährt.
```

#### Auswertung
Auch Beispiel 3 erfüllt die vorgegebenen Bedingungen. Obwohl Lok 1 schneller ist als Lok 0, wird die Reihenfolge (Lok 0 
--> Lok 1) eingehalten. Dies führt dazu, dass Lok 1 öfter warten muss als Lok 0.