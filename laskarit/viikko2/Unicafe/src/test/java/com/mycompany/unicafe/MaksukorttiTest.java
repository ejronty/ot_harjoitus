package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(15);
        assertEquals(25, kortti.saldo());
    }
    
    @Test
    public void rahanOttaminenVahentaaSaldoaOikein() {
        kortti.otaRahaa(5);
        assertEquals(5, kortti.saldo());
    }
    
    @Test
    public void rahanOttamisestaOikeaPaluuArvoJosRahaaRiitti() {
        assertTrue(kortti.otaRahaa(5));
    }
    
    @Test
    public void rahanOttamisestaOikeaPaluuArvoJosRahaaEiRiittanyt() {
        assertFalse(kortti.otaRahaa(15));
    }
    
    @Test
    public void saldoEiMuutuJosRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(15);
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void toStringPalauttaaOikeanTekstin() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
}
