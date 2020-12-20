# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on tarkoitettu avuksi henkilölle, joka työssään matkustaa useiden toimipisteiden välillä.
Sovelluksella voi pitää kirjaa eri toimipisteillä sijaitsevista työvälineistä ja niiden määristä.

## Tominnallisuudet

* Koska kyseessä on työpöytäsovellus, ei tässä vaiheessa toteuteta käyttäjänhallintaa. Sovellusta oletetaan käytettävän yksityisellä laitteella.

#### Aloitusnäkymä
* Valikko
    * Toimipisteet/Työvälineet
    * Toimintopainikkeet
        * näytä
        * luo uusi
        * muokkaa
        * poista
        * liitä
    * Lopetus
* Työvälineiden listaus

#### Muita näkymiä / toimintoja
* Työväline
    * uuden luominen
    * muokkaaminen
    * poistaminen
    * toimipisteeseen yhdistäminen
    * yhdistettyjen toimipisteiden listaus
        * toimipisteestä poistaminen

* Toimipiste
    * uuden luominen
    * uudelleennimeäminen
    * poistaminen
    * työvälineen lisääminen
    * yhdistettyjen työvälineiden listaus
        * Yhdistettyjen työvälineiden määrän muutokset
        * Yhdistetyn työvälineen siirto toiseen toimipisteeseen
        * Työvälineen poisto toimipisteestä
    
#### Muita vaatimuksia
* Graafinen käyttöliittymä
* Tiedon tallentaminen (tietokanta)
* Tieto työvälineiden määrästä eri toimipisteillä.


#### Jatkokehitysideoita
* Hakukenttä (esim GUI:n ylälaitaan)
* Työvälineille voi tallentaa luokkia/kategorioita/avainsanoja, joita voi hyödyntää haussa.
* Käyttäjänhallinta, jolloin sovellusta voisi käyttää myös jaetulla laitteella.
    * Mahdollisesti myös eri käyttäjärooleja (ylläpitäjä ja normaali käyttäjä).
* Kulumattoman työvälineen voi varata, jolloin se ei ole muiden käyttäjien saatavilla.
* Toimipisteille varastotilavuus, johon perustuen työvälineiden määrää rajoitetaan.
