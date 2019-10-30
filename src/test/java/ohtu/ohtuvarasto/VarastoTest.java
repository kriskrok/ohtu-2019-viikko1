package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    Varasto kuormitettuVarasto;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
        kuormitettuVarasto = new Varasto(50, 10);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void kuormitettuLuoOikeallaTilavuudella() {
        assertEquals(50, kuormitettuVarasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void kuormitettuLuoOikeanAlkusaldon() {
        assertEquals(10, kuormitettuVarasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void kuormitettuNegatiivisellaAlkusaldollaAlustuuTyhjaksi() {
        Varasto negatiivinenAlkuSaldoVarasto = new Varasto(20, -10);
        assertEquals(0, negatiivinenAlkuSaldoVarasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenSaldoVarastollaOikeaTilavuus() {
        Varasto negatiivinenSaldoVarasto = new Varasto(-50);
        assertEquals(0, negatiivinenSaldoVarasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void kuormitettuNegatiivinenSaldoVarastoAlustuuTyhjaksi() {
        Varasto negatiivinenSaldoVarasto = new Varasto(-50, 0);
        assertEquals(0, negatiivinenSaldoVarasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenLisaysEiMuutaSaldoa() {
        varasto.lisaaVarastoon(-8);
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysYliKapasiteetinTayttaaSaldon() {
        varasto.lisaaVarastoon(15);
        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void negatiivisenMaaranOttaminenEiLisaaTilaa() {
        varasto.lisaaVarastoon(5);

        varasto.otaVarastosta(-5);
        assertEquals(5, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenYliKapasiteetinTyhjentaaSaldon() {
        varasto.lisaaVarastoon(6);

        double saatuMaara = varasto.otaVarastosta(8);

        assertEquals(6, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenYliKapasiteetinLisääTilaa() {
        varasto.lisaaVarastoon(3);

        varasto.otaVarastosta(9);

        assertEquals(-10, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void merkkijonoesitysPalauttaaOikeanMerkkijonon() {
        varasto.lisaaVarastoon(4);
        assertEquals("saldo = 4.0, vielä tilaa 6.0", varasto.toString());
    }

}
