# Dokumentation zur Teilaufgabe a)
Implementieren Sie eine Java-Lösung für die `enter`- und `exit`-Methoden als Erzeuger-/Verbraucher-Problem.

## Umsetzung
Um die Problemstellung auf Grundlage der Erzeuger-/Verbraucher-Problematik zu beheben, werden zwei Sempahoren verwendet 
mit den folgenden Eigenschaften:
```java
    private static Semaphore besetzt = new Semaphore(0, true);
    private static Semaphore frei = new Semaphore(1, true);
```

## Beispiele
Zur Darstellung der Anforderungen an den Code werden drei Beispielfälle mit jeweils unterschiedlichen 
Geschwindigkeitsverteilungen durchgeführt. Hierbei ist es wichtig die Reihenfolge der beiden Loks (zuerst Lok 0, danach 
Lok 1) beizubehalten.

### Beispiel 1: Lok 0 > Lok 1
Im ersten Beispiel ist Lok 0 schneller als Lok 1. Dies wird über die Dauer der Schlafenszeit der beiden Threads
realisiert. Diese werden am Anfang bei Initialisierung an den Konstruktor in Form einer undefinierten und daher eher 
veranschaulichenden Geschwindigkeitseinheit mitgegeben.

```java
    LokA lok0 = new LokA(0, 8000);
    LokA lok1 = new LokA(1, 4000);

    lok0.start();
    lok1.start();
```

#### Ausgabe


### Beispiel 2: Lok 0 = Lok 1
Im zweiten Beispiel sind beide Loks gleich schnell, jedoch muss trotzdem die vorgegebene Reihenfolge beibehalten werden.

```java
    LokA lok00 = new LokA(0, 4000);
    LokA lok01 = new LokA(1, 4000);
    
    lok00.start();
    lok01.start();
```

#### Ausgabe


### Beispiel 3: Lok 0 < Lok 1
Im dritten Beispiel ist die Geschwindigkeit von Lok 1 höher als die Geschwindigkeit von Lok 0. Daher wird Lok 1 öfters 
warten müssen, um die abwechselnde Reihenfolge von Lok 0 und Lok 1 zu gewährleisten.

```java
    LokA lok000 = new LokA(0, 4000);
    LokA lok001 = new LokA(1, 8000);

    lok000.start();
    lok001.start();
```

#### Ausgabe


## Auswertung