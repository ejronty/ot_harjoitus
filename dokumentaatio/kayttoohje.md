# Käyttöohje

Lataa tiedosto [tyovalinekirjanpito.jar](https://github.com/ejronty/ot_harjoitus/releases/tag/viikko6)

## Konfigurointi

Ohjelma olettaa, että suoritushakemistosta löytyy tiedosto config.properties, jossa määritetään tietokannan sijainti. Esimerkiksi seuraavasti:
```
dbPath=inventory.db
```

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla
```
java -jar tyovalinekirjanpito.jar
```

## Alkunäkymä

Ohjelman aloitusnäkymä on lista työvälineistä, tai tyhjä lista, jollei työvälineitä ole vielä lisätty.
Vasemman yläreunan pudotusvalikosta voi valita, käsitelläänkö työvälineitä vai toimipisteitä.

![alku](kuvat/aloitus.png)

## Uuden työvälineen / toimipisteen luominen

Ohjelmaan saa luotua uuden kohteen painamalla vasemman reunan päävalikosta nappia 'Luo uusi'.
Avautuu näkymä, johon voi antaa uuden kohteen nimen. Vasemman reunan pudotusvalikko määrää, kumman tyyppinen kohde luodaan.

![luo_uusi](kuvat/luo_uusi.png)

Jos välineen luominen onnistui, päivittyy ikkunan keskellä oleva lista.

![luotu](kuvat/luotu.png)

## Kohteen nimen muuttaminen

Jos kohdetta luodessa sattui virhe, voi kohteen nimeä muuttaa valitsemalla kohteen listasta ja painamalla nappia 'Muokkaa'. Avautuu näkymä, johon voi syöttää kohteelle uuden nimen.

![muokkaa](kuvat/muokkaa.png)

Jos muutos onnistui, katoaa muokkauslomake, ja lista päivittyy.

## Kohteen poistaminen

Kohteen voi poistaa ohjelmasta valitsemalla sen listasta ja painamalla päävalikon nappia 'Poista'.
Avautuu näkymä, jossa käyttäjä voi varmistaa poiston.

![poisto](kuvat/poistetaanko.png)

## Työvälineen liittäminen toimipisteeseen

Liittääkseen työvälineen toimipisteeseen käyttäjän on valittava listasta työväline, ja painettava päävalikon nappia 'Liitä'.
Avautuu näkymä, jossa on lista toimipisteistä, jotka eivät vielä sisällä kyseistä työvälinettä.

![mihin_liitetaan](kuvat/mihin_liitetaan.png)

Tästä uudesta listasta valitaan haluttu toimipiste, ja painetaan 'Valitse'. Jos liitos onnistui, aukeaa näkymä, jossa on lista kaikista toimipisteistä, joihin työväline on liitetty.
Prosessi toimii hyvin samankaltaisesti myös toimipisteiden näkymästä.

## Kohteen tietojen näyttäminen

Kohteen tiedot (liitetyt työvälineet tai toimipisteet) saa näkyviin valitsemalla kohteen listasta ja painamalla päävalikon nappia 'Näytä'.

![nayta](kuvat/nayta.png)

## Työvälineen poistaminen toimipisteestä

Työvälineen saa poistettua toimipisteestä näyttämällä kohteen tiedot, valitsemalla näkymästä riippuen poistettavan työvälineen, tai toimipisteen, josta työväline poistetaan, ja painamalla 'Poista valittu'. Kohteen tiedot näyttävä lista päivittyy merkiksi, että poisto on onnistunut.
