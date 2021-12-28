# Advanced IT - Testat 1

Dies ist das Repository von _Babett Müller_ (Matrikelnummer: _2696346_) des Studiengangs "WWI 2020 SE B" für die Bearbeitung der 
ersten Testataufgabe des Moduls "Advanced IT" der DHWB Mannheim unter Leitung von Herr Prof. Pagnia.

## Aufgabenstellung

Beim Spielen mit ihrer neuen Holzeisenbahn legen Wladimir und Donald eine Strecke aus zwei Schienenkreisen,
die in der Mitte ein gemeinsames Teilstück haben. Damit es keinen Streit gibt, beschließen die beiden,
dass ihre Loks immer nur _abwechselnd_ dieses gemeinsame Schienenstück befahren dürfen.

Entwerfen Sie unter Verwendung von Semaphoren eine Steuerung für die beiden Loks. Beachten Sie dabei,
dass die Loks unterschiedlich schnell sind. Eine Lok, welche die Weiche erreicht, jedoch noch nicht an der
Reihe ist, muss warten, bis die andere Lok das Mittelstück verlässt. Andererseits soll die Lok ohne zu warten
weiterfahren, wenn sie die Weiche erreicht und klar ist, dass das Schienenstück für sie frei ist. Lok 0 soll zu
Beginn das Mittelstück zuerst befahren.

### Teilaufgabe a)

Implementieren Sie eine Java-Lösung für die `enter`- und `exit`-Methoden als Erzeuger/Verbraucher-
Problem.

#### Zugehörige Dateien

`aufgabeA.LokA.java`
`LokATest.java`
`aufgabeA.LokA.class`
`LokATest.class`

### Teilaufgabe b)

Implementieren Sie eine Java-Lösung für die `enter`- und `exit`-Methoden mit privaten Semaphoren.

#### Zugehörige Dateien

`aufgabeB.LokB.java`
`LokBTest.java`
`aufgabeB.LokB.class`
`LokBTest.class`

