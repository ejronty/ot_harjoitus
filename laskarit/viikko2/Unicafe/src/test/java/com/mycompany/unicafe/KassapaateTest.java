
package com.mycompany.unicafe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(500);
    }
    
    @Test
    public void luotuKassaOnOlemassa() {
        assertTrue(kassa!=null);
    }
    
    @Test
    public void luodunKassanRahamaaraOnOikea() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void uusiKassaEiOleMyynytEdullisiaLounaita() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void uusiKassaEiOleMyynytMaukkaitaLounaita() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    
    // Edullinen lounas, käteinen
    
    @Test
    public void syoEdullisestiKateisellaKasvattaaKassanSaldoaOikein() {
        kassa.syoEdullisesti(300);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoEdullisestiKateisellaKasvattaaMyytyjenLounaidenMaaraa() {
        kassa.syoEdullisesti(300);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiKateisellaEiKasvataMaukkaidenLounaidenMyyntia() {
        kassa.syoEdullisesti(300);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiKateisellaPalauttaaOikeanSumman() {
        assertEquals(60, kassa.syoEdullisesti(300));
    }
    
    @Test
    public void kassanSaldoEiMuutuJosRahatEiRiitaEdulliseenLounaaseen() {
        kassa.syoEdullisesti(100);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kaikkiRahatPalautetaanJosMaksuEiRiitaEdulliseenLounaaseen() {
        assertEquals(100, kassa.syoEdullisesti(100));
    }
    
    @Test
    public void myytyjenLounaidenMaaraEiMuutuJosRahatEiRiitaEdulliseenLounaaseen() {
        kassa.syoEdullisesti(100);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    
    // Edullinen lounas, kortti
    
    @Test
    public void syoEdullisestiKortillaVahentaaKortinSaldoaOikein() {
        kassa.syoEdullisesti(kortti);
        assertEquals(260, kortti.saldo());
    }
    
    @Test
    public void syoEdullisestiKortillaEiMuutaKassanSaldoa() {
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoEdullisestiKortillaKasvattaaMyytyjenLounaidenMaaraa() {
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiKortillaEiKasvataMaukkaidenLounaidenMyyntia() {
        kassa.syoEdullisesti(kortti);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiKortillaPalauttaaOikeanArvonKunSaldoaRiittaa() {
        assertTrue(kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void syoEdullisestiKortillaPalauttaaOikeanArvonKunSaldoEiRiita() {
        kassa.syoMaukkaasti(kortti);
        assertFalse(kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void kortinSaldoEiMuutuJosRahatEiRiitaEdulliseenLounaaseen() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(100, kortti.saldo());
    }
 
    @Test
    public void myytyjenLounaidenMaaraEiMuutuJosSaldoEiRiitaEdulliseenLounaaseen() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    
    // Maukas lounas, käteinen
    
    @Test
    public void syoMaukkaastiKateisellaKasvattaaKassanSaldoaOikein() {
        kassa.syoMaukkaasti(500);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiKateisellaKasvattaaMyytyjenLounaidenMaaraa() {
        kassa.syoMaukkaasti(500);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKateisellaEiKasvataEdullistenLounaidenMyyntia() {
        kassa.syoMaukkaasti(500);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKateisellaPalauttaaOikeanSumman() {
        assertEquals(100, kassa.syoMaukkaasti(500));
    }
    
    @Test
    public void kassanSaldoEiMuutuJosRahatEiRiitaMaukkaaseenLounaaseen() {
        kassa.syoMaukkaasti(300);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kaikkiRahatPalautetaanJosMaksuEiRiitaMaukkaaseenLounaaseen() {
        assertEquals(300, kassa.syoMaukkaasti(300));
    }
    
    @Test
    public void myytyjenLounaidenMaaraEiMuutuJosRahatEiRiitaMaukkaaseenLounaaseen() {
        kassa.syoMaukkaasti(300);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    
    // Maukas lounas, kortti
    
    @Test
    public void syoMaukkaastiKortillaVahentaaKortinSaldoaOikein() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void syoMaukkaastiKortillaEiMuutaKassanSaldoa() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiKortillaKasvattaaMyytyjenLounaidenMaaraa() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKortillaEiKasvataEdullistenLounaidenMyyntia() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKortillaPalauttaaOikeanArvonKunSaldoaRiittaa() {
        assertTrue(kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void syoMaukkaastiKortillaPalauttaaOikeanArvonKunSaldoEiRiita() {
        kassa.syoEdullisesti(kortti);
        assertFalse(kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void kortinSaldoEiMuutuJosRahatEiRiitaMaukkaaseenLounaaseen() {
        kassa.syoEdullisesti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(260, kortti.saldo());
    }
 
    @Test
    public void myytyjenLounaidenMaaraEiMuutuJosSaldoEiRiitaMaukkaaseenLounaaseen() {
        kassa.syoEdullisesti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    
    // Rahan lataaminen kortille
    
    @Test
    public void rahanLataaminenKasvattaaKortinSaldoa() {
        kassa.lataaRahaaKortille(kortti, 100);
        assertEquals(600, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenKasvattaaKassanSaldoa() {
        kassa.lataaRahaaKortille(kortti, 100);
        assertEquals(100100, kassa.kassassaRahaa());
    }
    
    @Test
    public void negatiivisenSummanLataaminenEiMuutaKortinSaldoa() {
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(500, kortti.saldo());
    }
    
    @Test
    public void negatiivisenSummanLataaminenEiMuutaKassanSaldoa() {
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    

    
}
