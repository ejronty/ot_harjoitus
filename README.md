# Työvälinekirjanpito

Sovellus on tarkoitettu avuksi henkilölle, joka työssään matkustaa useiden toimipisteiden välillä. Sovelluksella voi pitää kirjaa eri toimipisteillä sijaitsevista työvälineistä ja niiden määristä.

Sovellus on tarkoitettu käytettäväksi yksityisellä laitteella, eikä se sisällä (vielä) käyttäjänhallintaa.

### Dokumentaatio

[Vaatimusmäärittely](https://github.com/ejronty/ot_harjoitus/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/ejronty/ot_harjoitus/blob/master/dokumentaatio/arkkitehtuuri.md)

[Työaikakirjanpito](https://github.com/ejronty/ot_harjoitus/blob/master/dokumentaatio/tuntikirjanpito.md)

### Komentorivikomennot
#### Sovelluksen suorittaminen
Sovelluksen voi suorittaa komentoriviltä komennolla

```
mvn compile exec:java -Dexec.mainClass=tyovalinekirjanpito.ui.TextUI
```

Sovelluksen voi suorittaa myös Netbeansissa. Tällöin on täytyy main-luokaksi valita TextUI. Muut vaihtoehdot liittyvät graafiseen käyttöliittymään, joka ei vielä toimi.
#### Testaus
Projektin testit suoritaan komennolla

```
mvn test
``` 
Testikattavuusraportin saa luotua komennolla

```
mvn test jacoco:report
```
