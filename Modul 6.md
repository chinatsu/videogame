# Modul 6, notat

Jeg har laget et enkelt labyrint-lignende spill med JavaFX. En blå spiller prøver å nå et gult mål, og har noen evner for å kunne nå sitt mål.
Banene er generert av en [OpenSimplex noise-funksjon](https://gist.github.com/KdotJPG/b1270127455a94ac5d19) og kan ofte resultere
i at målet er umulig (eller bare veldig, veldig vanskelig) å nå.

Jeg har prøvd mitt beste å dokumentere koden, med både kommentarer som brukes til JavaDoc-generering, 
og noen inline kommentarer for å gjøre visse ting mer klare. Jeg vil påstå at velstrukturert kode vil ofte tale for seg selv,
og trengs sjelden å kommenteres utover det. Følger man den tanken, bør man strekke seg mot å skrive mest mulig lesbar kode, 
men man kan likevel ikke alltid gjøre valg som gir mening uten kommentar.

## Spillekontroller og konsepter

Spilleren bruker W, A, S, D eller piltaster for å flytte, samt Q for å teleportere 90 grader mot klokken, rundt matrisens senter.
I følgende eksempel er spilleren tallet 1, og roterer to ganger.
```
[0,1] -> [1,0] -> [0,0]
[0,0]  Q [0,0]  Q [1,0]
```
E brukes også til å teleportere 90 grader *med* klokken. R skifter farge på bakgrunnen, men har ingen funksjon utover det,
og Escape lukker spillet.

Man kan ikke bevege seg inn i vegger, men ved rotering kan man ende opp i en vegg. Om dette skjer, vil man få game over.
Det fins også "grå" vegger, som man kan bevege seg inn i. Når man går ut av cellen, vil veggen bli gjort om til svart og man 
vil ikke kunne flytte seg tilbake.

## Eksempler av bruk av klasser fra standardbiblioteket

I spillet har jeg ikke brukt mange klasser fra standardbiblioteket, hovedsaklig fordi det ikke har vært nødvendig enda.
Jeg vil uansett påstå at jeg har grep på hva enn jeg måtte ha bruk for, gjennom dokumentasjonen er det enkelt å vite
hvordan man skal bruke noe.

Her er uansett noen spesifikke eksempler.
```java
String string = "Hello!";
if (string.contains("Hello") {
    System.out.println(string + " Strings are cool");
}
// Hello! Strings are cool
```

```java
import java.util.ArrayList;
import java.util.Iterator;

ArrayList<int> values = new ArrayList<int>();
for (int i = 0; i < 10; i++) {
    values.add(i);
}
// {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
Iterator<int> iter_values = values.iterator();
while (iter.hasNext()) {
    value = iter.next();
    if (value % 3 == 0) {
        value.remove();
    }
}
// {1, 2, 4, 5, 7, 8}
```

```java
import java.util.ArrayList;
import java.util.HashMap;
/* Pseudokode som etterligner BeautifulSoup HTML parsing...
 * La oss late som om en side har følgende type table:
 * [ Rank       | 10  ]
 * [ Max Combo  | 12  ]
 * [ Max Speed  | 230 ]
 * [ Avg. Speed | 139 ]
 */
Html website = parse('http://somewebsite.com/tableoverview.html')
ArrayList<Tag> table = website.find('table').getChildren();
HashMap<String, Int> entries = new HashMap<String, String>();
for (child : children) {
    ArrayList<Tag> tableRows = child.find_all('tr');
    String key = tableRows[0].getText();
    String value = Integer.parseInt(tableRows[1].getText());
    entries.put(key, value);
}
// {"Rank": 10, "Max Combo": 12, "Max Speed": 230, "Avg. Speed": 139}
```

## Videre ting

* Generert JavaDoc skal finnes i `doc/`
* `publiC` metoder i en klasse kan brukes av andre klasser, `public` variabler (for eksempel `public int value`) kan leses direkte utenfra,
men kan også endres fritt av andre klasser.
* `private` variabler og metoder kan kun brukes innad i egen klasse
* Konstanter i en klasse kan defineres med `final` for å brukes innad i klassen, 
men kan også skrives med `static final` for å kunne bli direkte lest fra andre klasser.
 * I mitt tilfelle brukte jeg `static final int SCALE = 16;` i Main, som jeg bruker i andre klasser. For å få tak i verdien leser jeg `Main.SCALE` 
