# Työvälinekirjanpito

Sovellus on tarkoitettu avuksi henkilölle, joka työssään matkustaa useiden toimipisteiden välillä. Sovelluksella voi pitää kirjaa eri toimipisteillä sijaitsevista työvälineistä ja niiden määristä.

Sovellus on tarkoitettu käytettäväksi yksityisellä laitteella, eikä se sisällä (vielä) käyttäjänhallintaa.

Sovellus hakee vielä muotoaan, eikä se tällä hetkellä toteuta kerrosarkkitehtuuria kovinkaan hyvin. Arkkitehtuuriin, sovelluslogiikkaan ja käyttöliittymään on odotettavissa parannuksia.

### Dokumentaatio

[Vaatimusmäärittely](https://github.com/ejronty/ot_harjoitus/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/ejronty/ot_harjoitus/blob/master/dokumentaatio/tuntikirjanpito.md)

### Komentorivikomennot
#### Sovelluksen suorittaminen
Sovelluksen voi suorittaa komentoriviltä komennolla

```
mvn compile exec:java -Dexec.mainClass=tyovalinekirjanpito.ui.TextUI
```
#### Testaus
Projektin testit suoritaan komennolla

```
mvn test
``` 
Testikattavuusraportin saa luotua komennolla

```
mvn test jacoco:report
```
