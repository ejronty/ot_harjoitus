# Työvälinekirjanpito

Sovellus on tarkoitettu avuksi henkilölle, joka työssään matkustaa useiden toimipisteiden välillä. Sovelluksella voi pitää kirjaa eri toimipisteillä sijaitsevista työvälineistä ja niiden määristä.

Sovellus on tarkoitettu käytettäväksi yksityisellä laitteella, eikä se sisällä (vielä) käyttäjänhallintaa.

### Dokumentaatio

[Vaatimusmäärittely](https://github.com/ejronty/ot_harjoitus/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/ejronty/ot_harjoitus/blob/master/dokumentaatio/arkkitehtuuri.md)

[Työaikakirjanpito](https://github.com/ejronty/ot_harjoitus/blob/master/dokumentaatio/tuntikirjanpito.md)

### Releaset

[Viikko 5](https://github.com/ejronty/ot_harjoitus/releases/tag/Viikko5)

### Komentorivikomennot
#### Sovelluksen suorittaminen
Sovelluksen voi suorittaa komentoriviltä komennolla

```
mvn compile exec:java -Dexec.mainClass=tyovalinekirjanpito.ui.Main
```

#### Suoritettavan jarin generointi
Sovelluksesta voi generoida myös suoritettavan jar-tiedoston komennolla:
```
mvn package
```
Komento luo hakemistoon *target* suoritettavan jar-tiedoston nimellä tyovalinekirjanpito-1.0-SNAPSHOT.jar

#### Testaus
Projektin testit suoritaan komennolla

```
mvn test
``` 
Testikattavuusraportin saa luotua komennolla

```
mvn test jacoco:report
```
Raportti löytyy tiedostosta *target/site/jacoco/index.html*.

#### Checkstyle

Tiedoston checkstyle.xml määrittämät koodin laatutarkistukset saa suoritettua komennolla:
```
mvn jxr:jxr checkstyle:checkstyle
```
Tarkistuksen tulos löytyy tiedostosta *target/site/checkstyle.html*.
