package com.example.verival;


import java.security.InvalidParameterException;
import java.util.Random;

import com.example.verival.CentroDistribuicao.SITUACAO;
import com.example.verival.CentroDistribuicao.TIPOPOSTO;

import org.junit.Assert;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CentroDistribuicaoDriver {
    CentroDistribuicao centro;
    //#region Teste de Valor Limite
    //#region Situação Normal
    @Test
    void defineSituacaoOnPointNormal() {
        int tAlcoolMetade = (int) (CentroDistribuicao.MAX_ALCOOL * 0.25);
        int tAlcoolCheio = CentroDistribuicao.MAX_ALCOOL / 2;

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2, CentroDistribuicao.MAX_GASOLINA / 2, tAlcoolMetade, tAlcoolMetade);
        Assert.assertEquals(SITUACAO.NORMAL, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, CentroDistribuicao.MAX_GASOLINA, tAlcoolCheio, tAlcoolCheio);
        Assert.assertEquals(SITUACAO.NORMAL, centro.getSituacao());
    }

    @RepeatedTest(10)
    @Test
    void defineSituacaoInPointNormal() {
        int[] valoresTeste =  GetValoresTesteLimite(SITUACAO.NORMAL);
        System.out.printf("defineSituacaoInPointNormal()\n\taditivo: "+valoresTeste[0]+"\n\tgasolina: "+valoresTeste[1]+"\n\talcool1: "+valoresTeste[2]+"\n\talcool2: "+valoresTeste[3]+"\n\n");
        centro = new CentroDistribuicao(valoresTeste[0], valoresTeste[1], valoresTeste[2], valoresTeste[3]);
        Assert.assertEquals(SITUACAO.NORMAL, centro.getSituacao());
    }

    @Test
    void defineSituacaoOffPointNormal() {
	int tAlcoolMetade = CentroDistribuicao.MAX_ALCOOL / 2;
    int[] valoresTeste =  new int[]{CentroDistribuicao.MAX_ADITIVO / 2  -1,
        CentroDistribuicao.MAX_GASOLINA / 2  -1, tAlcoolMetade / 2 - 1, tAlcoolMetade / 2 - 1};
				
        centro = new CentroDistribuicao(valoresTeste[0], CentroDistribuicao.MAX_GASOLINA, tAlcoolMetade, tAlcoolMetade);
        Assert.assertNotEquals(CentroDistribuicao.SITUACAO.NORMAL, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, valoresTeste[1], tAlcoolMetade, tAlcoolMetade);
        Assert.assertNotEquals(SITUACAO.NORMAL, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, CentroDistribuicao.MAX_GASOLINA, valoresTeste[2], valoresTeste[3]);
        Assert.assertNotEquals(SITUACAO.NORMAL, centro.getSituacao());
    }

    @RepeatedTest(10)
    @Test
    void defineSituacaoOutPointNormal() {
        int[] valoresTeste =  GetValoresTesteLimite(SITUACAO.SOBRAVISO);
        System.out.printf("defineSituacaoOutPointNormal()\n\taditivo: "+valoresTeste[0]+"\n\tgasolina: "+valoresTeste[1]+"\n\talcool1: "
                +valoresTeste[2]+"\n\talcool2: "+valoresTeste[3]+"\n\n");

        centro = new CentroDistribuicao(valoresTeste[0], valoresTeste[1], valoresTeste[2], valoresTeste[3]);
        Assert.assertNotEquals(SITUACAO.NORMAL, centro.getSituacao());
    }
    //#endregion
    //#region Situação Sobreaviso
    @Test
    void defineSituacaoOnPointSobreaviso() {
        final int tAlcoolCheio = CentroDistribuicao.MAX_ALCOOL / 2;
        final int tAlcoolSobreaviso = tAlcoolCheio / 2 - 1;
        int[] tanquesAlcool = valoresQueResultamOEsperado(new Random(), tAlcoolSobreaviso / 2 + 1, tAlcoolSobreaviso);

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2 - 1, CentroDistribuicao.MAX_GASOLINA, tAlcoolCheio, tAlcoolCheio);
        Assert.assertEquals(SITUACAO.SOBRAVISO, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, CentroDistribuicao.MAX_GASOLINA / 2 - 1, tAlcoolCheio, tAlcoolCheio);
        Assert.assertEquals(SITUACAO.SOBRAVISO, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, CentroDistribuicao.MAX_GASOLINA, tanquesAlcool[0], tanquesAlcool[1]);
        Assert.assertEquals(SITUACAO.SOBRAVISO, centro.getSituacao());
    }

    @RepeatedTest(10)
    @Test
    void defineSituacaoInPointSobreaviso() {
        int[] valoresTeste =  GetValoresTesteLimite(SITUACAO.SOBRAVISO);
        int tAlcoolCheio = CentroDistribuicao.MAX_ALCOOL / 2;
        System.out.printf("defineSituacaoInPointSobreaviso():\n\taditivo: "+valoresTeste[0]+"\n\tgasolina: "+valoresTeste[1]+"\n\talcool1: "
                +valoresTeste[2]+"\n\talcool2: "+valoresTeste[3]+"\n\n");

        centro = new CentroDistribuicao(valoresTeste[0], CentroDistribuicao.MAX_GASOLINA / 2, tAlcoolCheio, tAlcoolCheio);
        Assert.assertEquals(SITUACAO.SOBRAVISO, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2 - 1, valoresTeste[1], tAlcoolCheio, tAlcoolCheio);
        Assert.assertEquals(SITUACAO.SOBRAVISO, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2 - 1, CentroDistribuicao.MAX_GASOLINA / 2, valoresTeste[2], tAlcoolCheio);
        Assert.assertEquals(SITUACAO.SOBRAVISO, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2 - 1, CentroDistribuicao.MAX_GASOLINA / 2, tAlcoolCheio, valoresTeste[3]);
        Assert.assertEquals(SITUACAO.SOBRAVISO, centro.getSituacao());
    }

    @Test
    void defineSituacaoOffPointSobreaviso() {
        int tAlcoolCheio = CentroDistribuicao.MAX_ALCOOL / 2;
        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2, CentroDistribuicao.MAX_GASOLINA / 2, tAlcoolCheio / 2, tAlcoolCheio / 2);
        Assert.assertNotEquals(SITUACAO.SOBRAVISO, centro.getSituacao());

        centro = new CentroDistribuicao((int)(CentroDistribuicao.MAX_ADITIVO * 0.25), (int)(CentroDistribuicao.MAX_GASOLINA * 0.25), (int)(tAlcoolCheio * 0.25), (int)(tAlcoolCheio * 0.25));
        Assert.assertNotEquals(SITUACAO.SOBRAVISO, centro.getSituacao());
    }

    @RepeatedTest(10)
    @Test
    void defineSituacaoOutPointSobreaviso() {
        int[] valoresTeste =  GetValoresTesteLimite(SITUACAO.EMERGENCIA);
        int tAlcoolCheio = CentroDistribuicao.MAX_ALCOOL / 2;
        System.out.printf("defineSituacaoOutPointSobreaviso()\n\tSITUACAO.EMERGENCIA:\n\taditivo: "+valoresTeste[0]+"\n\tgasolina: "+valoresTeste[1]+"\n\talcool1: "
                +valoresTeste[2]+"\n\talcool2: "+valoresTeste[3]+"\n\n");

        centro = new CentroDistribuicao(valoresTeste[0], CentroDistribuicao.MAX_GASOLINA, tAlcoolCheio, tAlcoolCheio);
        Assert.assertNotEquals(SITUACAO.SOBRAVISO, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, valoresTeste[1], tAlcoolCheio, tAlcoolCheio);
        Assert.assertNotEquals(SITUACAO.SOBRAVISO, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, CentroDistribuicao.MAX_GASOLINA, valoresTeste[2], tAlcoolCheio);
        Assert.assertNotEquals(SITUACAO.SOBRAVISO, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, CentroDistribuicao.MAX_GASOLINA, tAlcoolCheio, valoresTeste[3]);
        Assert.assertNotEquals(SITUACAO.SOBRAVISO, centro.getSituacao());

        valoresTeste =  GetValoresTesteLimite(SITUACAO.NORMAL);
        System.out.printf("defineSituacaoOutPointSobreaviso()\n\tSITUACAO.NORMAL:\n\taditivo: "+valoresTeste[0]+"\n\tgasolina: "+valoresTeste[1]+"\n\talcool1: "
                +valoresTeste[2]+"\n\talcool2: "+valoresTeste[3]+"\n\n");

        centro = new CentroDistribuicao(valoresTeste[0], CentroDistribuicao.MAX_GASOLINA / 2, tAlcoolCheio / 2, tAlcoolCheio / 2);
        Assert.assertNotEquals(SITUACAO.SOBRAVISO, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2, valoresTeste[1], tAlcoolCheio / 2, tAlcoolCheio / 2);
        Assert.assertNotEquals(SITUACAO.SOBRAVISO, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2, CentroDistribuicao.MAX_GASOLINA / 2, valoresTeste[2], tAlcoolCheio / 2);
        Assert.assertNotEquals(SITUACAO.SOBRAVISO, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2, CentroDistribuicao.MAX_GASOLINA / 2, tAlcoolCheio / 2, valoresTeste[3]);
        Assert.assertNotEquals(SITUACAO.SOBRAVISO, centro.getSituacao());
    }
    //#endregion
    //#region Situação Emergencia
    @Test
    void defineSituacaoOnPointEmergencia() {
        final int MAX_ALCOOL_TANQUE = CentroDistribuicao.MAX_ALCOOL / 2;
        final int tAlcoolSobreaviso = (int)(MAX_ALCOOL_TANQUE * 0.25) - 1;
        int[] tanquesAlcool = valoresQueResultamOEsperado(new Random(), 0, tAlcoolSobreaviso);

        centro = new CentroDistribuicao((int)((CentroDistribuicao.MAX_ADITIVO * 0.25)) - 1, CentroDistribuicao.MAX_GASOLINA, MAX_ALCOOL_TANQUE, MAX_ALCOOL_TANQUE);
        Assert.assertEquals(SITUACAO.EMERGENCIA, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO,(int)((CentroDistribuicao.MAX_GASOLINA * 0.25)) - 1, MAX_ALCOOL_TANQUE, MAX_ALCOOL_TANQUE);
        Assert.assertEquals(SITUACAO.EMERGENCIA, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, CentroDistribuicao.MAX_GASOLINA, tanquesAlcool[0], tanquesAlcool[1]);
        Assert.assertEquals(SITUACAO.EMERGENCIA, centro.getSituacao());
    }

    @Test
    @RepeatedTest(10)
    void defineSituacaoOutPointEmergencia() {
        int[] valoresTeste =  GetValoresTesteLimite(SITUACAO.SOBRAVISO);
        int tAlcoolCheio = CentroDistribuicao.MAX_ALCOOL / 2;
        System.out.printf("defineSituacaoOutPointEmergencia()\n\tSITUACAO.EMERGENCIA:\n\taditivo: "+valoresTeste[0]+"\n\tgasolina: "+valoresTeste[1]+"\n\talcool1: "
                +valoresTeste[2]+"\n\talcool2: "+valoresTeste[3]+"\n\n");

        centro = new CentroDistribuicao(valoresTeste[0], CentroDistribuicao.MAX_GASOLINA, tAlcoolCheio, tAlcoolCheio);
        Assert.assertNotEquals(SITUACAO.EMERGENCIA, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, valoresTeste[1], tAlcoolCheio, tAlcoolCheio);
        Assert.assertNotEquals(SITUACAO.EMERGENCIA, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, CentroDistribuicao.MAX_GASOLINA, valoresTeste[2], tAlcoolCheio);
        Assert.assertNotEquals(SITUACAO.EMERGENCIA, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, CentroDistribuicao.MAX_GASOLINA, tAlcoolCheio, valoresTeste[3]);
        Assert.assertNotEquals(SITUACAO.EMERGENCIA, centro.getSituacao());

        valoresTeste =  GetValoresTesteLimite(SITUACAO.NORMAL);
        System.out.printf("defineSituacaoOutPointEmergencia()\n\tSITUACAO.NORMAL:\n\taditivo: "+valoresTeste[0]+"\n\tgasolina: "+valoresTeste[1]+"\n\talcool1: "
                +valoresTeste[2]+"\n\talcool2: "+valoresTeste[3]+"\n\n");

        centro = new CentroDistribuicao(valoresTeste[0], CentroDistribuicao.MAX_GASOLINA / 2, tAlcoolCheio / 2, tAlcoolCheio / 2);
        Assert.assertNotEquals(SITUACAO.EMERGENCIA, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2, valoresTeste[1], tAlcoolCheio / 2, tAlcoolCheio / 2);
        Assert.assertNotEquals(SITUACAO.EMERGENCIA, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2, CentroDistribuicao.MAX_GASOLINA / 2, valoresTeste[2], tAlcoolCheio / 2);
        Assert.assertNotEquals(SITUACAO.EMERGENCIA, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2, CentroDistribuicao.MAX_GASOLINA / 2, tAlcoolCheio / 2, valoresTeste[3]);
        Assert.assertNotEquals(SITUACAO.EMERGENCIA, centro.getSituacao());
    }

    @Test
    void defineSituacaoOffPointEmergencia() {
        int tAlcoolCheio = CentroDistribuicao.MAX_ALCOOL / 2;

        centro = new CentroDistribuicao((int)(CentroDistribuicao.MAX_ADITIVO * 0.25) + 1, (int)(CentroDistribuicao.MAX_GASOLINA * 0.25) + 1, (int)(tAlcoolCheio * 0.25) + 1, (int)(tAlcoolCheio * 0.25) + 1);
        Assert.assertNotEquals(SITUACAO.EMERGENCIA, centro.getSituacao());
    }

    @RepeatedTest(10)
    @Test
    void defineSituacaoInPointEmergencia() {
        int[] valoresTeste =  GetValoresTesteLimite(SITUACAO.EMERGENCIA);
        int tAlcoolCheio = CentroDistribuicao.MAX_ALCOOL / 2;
        System.out.printf("defineSituacaoInPointEmergencia():\n\taditivo: "+valoresTeste[0]+"\n\tgasolina: "+valoresTeste[1]+"\n\talcool1: "
                +valoresTeste[2]+"\n\talcool2: "+valoresTeste[3]+"\n\n");

        centro = new CentroDistribuicao(valoresTeste[0], CentroDistribuicao.MAX_GASOLINA / 2, tAlcoolCheio, tAlcoolCheio);
        Assert.assertEquals(SITUACAO.EMERGENCIA, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2 - 1, valoresTeste[1], tAlcoolCheio, tAlcoolCheio);
        Assert.assertEquals(SITUACAO.EMERGENCIA, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2 - 1, CentroDistribuicao.MAX_GASOLINA / 2, valoresTeste[2], (int)(tAlcoolCheio * 0.25));
        Assert.assertEquals(SITUACAO.EMERGENCIA, centro.getSituacao());

        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2 - 1, CentroDistribuicao.MAX_GASOLINA / 2, (int)(tAlcoolCheio * 0.25), valoresTeste[3]);
        Assert.assertEquals(SITUACAO.EMERGENCIA, centro.getSituacao());
    }
    //#endregion

    //#endregion
    //#endregion
    //#region Teste baseado em Modelos e Teste de partição por categoria
    @Test
    void centroDistribuicaoJogaExcecaoBaseadoNoLimiteDosTanques(){
        Assert.assertThrows(InvalidParameterException.class, () -> new CentroDistribuicao(-1, 0, 0, 0));
        Assert.assertThrows(InvalidParameterException.class, () -> new CentroDistribuicao(0, -1, 0, 0));
        Assert.assertThrows(InvalidParameterException.class, () -> new CentroDistribuicao(0, 0, -1, 0));
        Assert.assertThrows(InvalidParameterException.class, () -> new CentroDistribuicao(0, 0, 0, -1));
        Assert.assertThrows(InvalidParameterException.class, () -> new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO + 1, 0, 0, 0));
        Assert.assertThrows(InvalidParameterException.class, () -> new CentroDistribuicao(0, CentroDistribuicao.MAX_GASOLINA + 1, 0, 0));
        Assert.assertThrows(InvalidParameterException.class, () -> new CentroDistribuicao(0, 0, CentroDistribuicao.MAX_ALCOOL + 1, 0));
        Assert.assertThrows(InvalidParameterException.class, () -> new CentroDistribuicao(0, 0, 0, CentroDistribuicao.MAX_ALCOOL + 1));
    }
    @Test
    void recebeGasolinaQtdadeValidaSituacaoNormal() {
        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2, CentroDistribuicao.MAX_GASOLINA / 2, CentroDistribuicao.MAX_ALCOOL / 2, CentroDistribuicao.MAX_ALCOOL / 2);
        SITUACAO situacaoInicial = centro.getSituacao();
        final int gasolinaInicial = centro.getTanGasolina();
        final int qtdadeValida = 100;
        int qtdadeAbastecida = centro.recebeGasolina(qtdadeValida);

        Assert.assertEquals(SITUACAO.NORMAL,situacaoInicial);
        Assert.assertEquals(centro.getTanGasolina(), gasolinaInicial + qtdadeValida);
        Assert.assertEquals(qtdadeValida, qtdadeAbastecida);
    }

    @Test
    void recebeGasolinaQtdadeValidaTanqueQuaseCheio() {
        centro = new CentroDistribuicao(0, (int)(CentroDistribuicao.MAX_GASOLINA * 0.75), 0, 0);
        final int gasolinaInicial = centro.getTanGasolina();
        final int qtdadeACompletar = (int)(CentroDistribuicao.MAX_GASOLINA  * 0.25);
        final int qtRetorno = centro.recebeGasolina(qtdadeACompletar + 100);

        Assert.assertEquals(qtdadeACompletar, qtRetorno);
        Assert.assertEquals(gasolinaInicial + qtdadeACompletar, centro.getTanGasolina());
    }

    @Test
    void recebeGasolinaQtdadeValidaMudaSituacao() {
        final int gasolina = CentroDistribuicao.MAX_GASOLINA / 2 - 1;
        final int alcool = CentroDistribuicao.MAX_ALCOOL / 2;
        final int aditivo = CentroDistribuicao.MAX_ADITIVO / 2;
        final int qtdadeACompletar = 1;
        centro = new CentroDistribuicao(aditivo, gasolina, alcool, alcool);
        SITUACAO situacaoIncial = centro.getSituacao();
        int qtdadeAbastecida = centro.recebeGasolina(qtdadeACompletar);

        Assert.assertNotEquals(situacaoIncial, centro.getSituacao());
        Assert.assertEquals(gasolina + qtdadeACompletar, centro.getTanGasolina());
        Assert.assertEquals(qtdadeACompletar, qtdadeAbastecida);
    }

    @Test
    void recebeGasolinaQtdadeInvalida() {
        centro = new CentroDistribuicao(0, 0, 0, 0);
        final int qtRetorno = centro.recebeGasolina(-100);
        Assert.assertEquals(-1, qtRetorno);
    }

    @Test
    void recebeAditivoQtdadeValidaSituacaoNormal() {
        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2, CentroDistribuicao.MAX_GASOLINA / 2, CentroDistribuicao.MAX_ALCOOL / 2, CentroDistribuicao.MAX_ALCOOL / 2);
        SITUACAO situacaoInicial = centro.getSituacao();
        final int aditivoInicial = centro.getTanAditivo();
        final int qtdadeValida = 100;
        final int qtdadeAbastecida = centro.recebeAditivo(qtdadeValida);

        Assert.assertEquals(SITUACAO.NORMAL,situacaoInicial);
        Assert.assertEquals(centro.getTanAditivo(), aditivoInicial + qtdadeValida);
        Assert.assertEquals(qtdadeValida, qtdadeAbastecida);
    }

    @Test
    void recebeAditivoQtdadeValidaTanqueQuaseCheio() {
        centro = new CentroDistribuicao((int)(CentroDistribuicao.MAX_ADITIVO * 0.75), 0, 0, 0);
        final int aditivoInicial = centro.getTanAditivo();
        final int qtdadeACompletar = (int)(CentroDistribuicao.MAX_ADITIVO  * 0.25);
        final int qtRetorno = centro.recebeAditivo(qtdadeACompletar +  100);

        Assert.assertEquals(qtdadeACompletar, qtRetorno);
        Assert.assertEquals(aditivoInicial + qtdadeACompletar, centro.getTanAditivo());
    }

    @Test
    void recebeAditivoQtdadeValidaMudaSituacao() {
        final int gasolina = CentroDistribuicao.MAX_GASOLINA / 2;
        final int alcool = CentroDistribuicao.MAX_ALCOOL / 2;
        final int aditivo = CentroDistribuicao.MAX_ADITIVO / 2 - 1;
        final int qtdadeACompletar = 1;
        centro = new CentroDistribuicao(aditivo, gasolina, alcool, alcool);
        SITUACAO situacaoIncial = centro.getSituacao();
        final int qtRetorno = centro.recebeAditivo(qtdadeACompletar);

        Assert.assertNotEquals(situacaoIncial, centro.getSituacao());
        Assert.assertEquals(SITUACAO.NORMAL, centro.getSituacao());
        Assert.assertEquals(aditivo + qtdadeACompletar, centro.getTanAditivo());
        Assert.assertEquals(qtdadeACompletar, qtRetorno);
    }

    @Test
    void recebeAditivoQtdadeInvalida() {
        centro = new CentroDistribuicao(0, 0, 0, 0);
        final int qtRetorno = centro.recebeAditivo(-100);
        Assert.assertEquals(-1, qtRetorno);
    }
    @Test
    void recebeAlcoolQtdadeValidaSituacaoNormal() {
        final int METADE_TANQUE_ALCOOL = (int)(CentroDistribuicao.MAX_ALCOOL * 0.25);
        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2, CentroDistribuicao.MAX_GASOLINA / 2, METADE_TANQUE_ALCOOL, METADE_TANQUE_ALCOOL);
        SITUACAO situacaoInicial = centro.getSituacao();
        final int tAlcool1Inicial = centro.getTanAlcool1();
        final int tAlcool2Inicial = centro.getTanAlcool2();
        final int qtdadeValida = 100;
        final int qtdadeAbastecida = centro.recebeAlcool(qtdadeValida);

        Assert.assertEquals(SITUACAO.NORMAL,situacaoInicial);
        Assert.assertEquals(centro.getTanAlcool1(), tAlcool1Inicial + qtdadeValida / 2);
        Assert.assertEquals(centro.getTanAlcool2(), tAlcool2Inicial + qtdadeValida / 2);
        Assert.assertEquals(qtdadeValida, qtdadeAbastecida);
    }

    @Test
    void recebeAlcoolQtdadeValidaTanqueQuaseCheio() {
        int metade_tanque_alcool = CentroDistribuicao.MAX_ALCOOL / 2;
        int alcoolIncial = (int)(metade_tanque_alcool * 0.75);
        centro = new CentroDistribuicao(0, 0, alcoolIncial, alcoolIncial);
        int tAlcool1Inicial = centro.getTanAlcool1();
        int tAlcool2Inicial = centro.getTanAlcool2();
        int qtdadeACompletar = metade_tanque_alcool / 2 + 1;
        int qtdadeValida = 100;
        int qtRetorno = centro.recebeAlcool(qtdadeACompletar + qtdadeValida);

        Assert.assertEquals(qtdadeACompletar, qtRetorno);
        Assert.assertEquals(tAlcool1Inicial, tAlcool2Inicial);
        Assert.assertEquals(tAlcool1Inicial + (qtdadeACompletar / 2), centro.getTanAlcool1());
        Assert.assertEquals(tAlcool2Inicial + (qtdadeACompletar / 2), centro.getTanAlcool2());
    }

    @Test
    void recebeAlcoolQtdadeValidaMudaSituacao() {
        int gasolina = CentroDistribuicao.MAX_GASOLINA / 2;
        int alcool = CentroDistribuicao.MAX_ALCOOL / 2;
        int aditivo = CentroDistribuicao.MAX_ADITIVO / 2 - 1;
        int qtdadeACompletar = 1;
        centro = new CentroDistribuicao(aditivo, gasolina, alcool, alcool);
        SITUACAO situacaoIncial = centro.getSituacao();
        int qtRetorno = centro.recebeAditivo(qtdadeACompletar);

        Assert.assertNotEquals(situacaoIncial, centro.getSituacao());
        Assert.assertEquals(SITUACAO.NORMAL, centro.getSituacao());
        Assert.assertEquals(aditivo + qtdadeACompletar, centro.getTanAditivo());
        Assert.assertEquals(qtdadeACompletar, qtRetorno);
    }

    @Test
    void recebeAlcoolQtdadeInvalida() {
        centro = new CentroDistribuicao(0, 0, 0, 0);
        final int qtRetorno = centro.recebeAditivo(-100);
        Assert.assertEquals(-1, qtRetorno);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8 , 9, 10, 25, 50, 75, 100, 1000})
    void encomendaCombustivelQtValidaSNormalAdquireMistura(int litrosCombustivel){
        final int MAX_ALCOOL_TANQUE = CentroDistribuicao.MAX_ALCOOL / 2;
        int[][] misturaRecebida = new int[2][4];
        int[] misturaEsperada   = new int[3];
        SITUACAO[] situacoes =  new SITUACAO[2];
        CentroDistribuicao centroPComum = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, CentroDistribuicao.MAX_GASOLINA, MAX_ALCOOL_TANQUE, MAX_ALCOOL_TANQUE);
        CentroDistribuicao centroPEstrategico = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, CentroDistribuicao.MAX_GASOLINA, MAX_ALCOOL_TANQUE, MAX_ALCOOL_TANQUE);
        misturaEsperada[0] = (int)(CentroDistribuicao.MAX_ADITIVO - litrosCombustivel * 0.05);
        misturaEsperada[1] = (int)(CentroDistribuicao.MAX_GASOLINA - litrosCombustivel * 0.7);
        misturaEsperada[2] = (int)(MAX_ALCOOL_TANQUE - litrosCombustivel * 0.125);
        situacoes[0] = centroPComum.getSituacao();
        situacoes[1] = centroPEstrategico.getSituacao();
        misturaRecebida[0] = centroPComum.encomendaCombustivel(litrosCombustivel, TIPOPOSTO.COMUM);
        misturaRecebida[1] = centroPEstrategico.encomendaCombustivel(litrosCombustivel, TIPOPOSTO.ESTRATEGICO);

        //TIPOPOSTO.COMMM
        Assert.assertEquals(SITUACAO.NORMAL, situacoes[0]);
        Assert.assertEquals(4, misturaRecebida[0].length);
        Assert.assertEquals(misturaEsperada[0], misturaRecebida[0][0]);
        Assert.assertEquals(misturaEsperada[1], misturaRecebida[0][1]);
        Assert.assertEquals(misturaEsperada[2], misturaRecebida[0][2]);
        Assert.assertEquals(misturaEsperada[2], misturaRecebida[0][3]);
        //TIPOPOSTO.ESTRATEGICO
        Assert.assertEquals(SITUACAO.NORMAL, situacoes[1]);
        Assert.assertEquals(4, misturaRecebida[1].length);
        Assert.assertEquals(misturaEsperada[0], misturaRecebida[1][0]);
        Assert.assertEquals(misturaEsperada[1], misturaRecebida[1][1]);
        Assert.assertEquals(misturaEsperada[2], misturaRecebida[1][2]);
        Assert.assertEquals(misturaEsperada[2], misturaRecebida[1][3]);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 7, 8 , 9, 10, 25, 50, 75, 100, 1000})
    void encomendaCombustivelQtValidaSSobreavisoPComumAdquireMetadeDaMistura(int litrosCombustivel){
        final int ADITIVO_TANQUE = CentroDistribuicao.MAX_ADITIVO / 2 - 1;
        final int GASOLINA_TANQUE = CentroDistribuicao.MAX_GASOLINA / 2 - 1;
        final int ALCOOL_TANQUE = CentroDistribuicao.MAX_ALCOOL / 2 - 1;
        int[] misturaRecebida;
        int[] misturaEsperada = new int[3];
        centro = new CentroDistribuicao(ADITIVO_TANQUE, GASOLINA_TANQUE, ALCOOL_TANQUE , ALCOOL_TANQUE);
        SITUACAO situacao =  centro.getSituacao();
        misturaEsperada[0] = (int)(ADITIVO_TANQUE  - (litrosCombustivel / 2) * 0.05);
        misturaEsperada[1] = (int)(GASOLINA_TANQUE - (litrosCombustivel / 2) * 0.7);
        misturaEsperada[2] = (int)(ALCOOL_TANQUE   - (litrosCombustivel / 2) * 0.125);

        misturaRecebida = centro.encomendaCombustivel(litrosCombustivel, TIPOPOSTO.COMUM);

        Assert.assertEquals(SITUACAO.SOBRAVISO, situacao);
        Assert.assertEquals(4, misturaRecebida.length);
        Assert.assertEquals(misturaEsperada[0], misturaRecebida[0]);
        Assert.assertEquals(misturaEsperada[1], misturaRecebida[1]);
        Assert.assertEquals(misturaEsperada[2], misturaRecebida[2]);
        Assert.assertEquals(misturaEsperada[2], misturaRecebida[3]);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 7, 8 , 9, 10, 25, 50, 75, 100, 1000})
    void encomendaCombustivelQtValidaSSobreavisoPEstrategicoAdquireMistura(int litrosCombustivel){
        final int ADITIVO_TANQUE  = CentroDistribuicao.MAX_ADITIVO / 2;
        final int GASOLINA_TANQUE = CentroDistribuicao.MAX_GASOLINA / 2 - 1;
        final int ALCOOL_TANQUE   = CentroDistribuicao.MAX_ALCOOL / 2 - 1;
        int[] misturaRecebida;
        int[] misturaEsperada = new int[3];
        centro = new CentroDistribuicao(ADITIVO_TANQUE, GASOLINA_TANQUE, ALCOOL_TANQUE, ALCOOL_TANQUE);
        SITUACAO situacao =  centro.getSituacao();

        misturaEsperada[0] = (int)(ADITIVO_TANQUE  - litrosCombustivel * 0.05);
        misturaEsperada[1] = (int)(GASOLINA_TANQUE - litrosCombustivel * 0.7);
        misturaEsperada[2] = (int)(ALCOOL_TANQUE   - litrosCombustivel * 0.125);

        misturaRecebida = centro.encomendaCombustivel(litrosCombustivel, TIPOPOSTO.ESTRATEGICO);

        Assert.assertEquals(SITUACAO.SOBRAVISO, situacao);
        Assert.assertEquals(4, misturaRecebida.length);
        Assert.assertEquals(misturaEsperada[0], misturaRecebida[0]);
        Assert.assertEquals(misturaEsperada[1], misturaRecebida[1]);
        Assert.assertEquals(misturaEsperada[2], misturaRecebida[2]);
        Assert.assertEquals(misturaEsperada[2], misturaRecebida[3]);
    }

    @Test
    void encomendaCombustivelQtValidaSSobreavisoPComumNaoAdquireMisturaCodigoPraSituaco(){
        final int ALCOOL_TANQUE = CentroDistribuicao.MAX_ALCOOL / 2;
        int[] misturaRecebida;
        int[] misturaEsperada = new int[] {-14};
        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2 - 1, CentroDistribuicao.MAX_GASOLINA, ALCOOL_TANQUE, ALCOOL_TANQUE);
        misturaRecebida = centro.encomendaCombustivel(1, TIPOPOSTO.COMUM);
        SITUACAO situacao =  centro.getSituacao();

        Assert.assertEquals(SITUACAO.SOBRAVISO, situacao);
        Assert.assertEquals(1, misturaRecebida.length);
        Assert.assertEquals(misturaEsperada[0], misturaRecebida[0]);
    }

    @Test
    void encomendaCombustivelQtValidaSSobreavisoPComumNaoAdquireMistura(){
        final int ADITIVO_TANQUE = CentroDistribuicao.MAX_ADITIVO / 2 - 1;
        final int ALCOOL_TANQUE  = (int)CentroDistribuicao.MAX_ALCOOL / 2;
        int[] misturaRecebida;
        int[] misturaEsperada = new int[] {-14};
        centro = new CentroDistribuicao(ADITIVO_TANQUE, CentroDistribuicao.MAX_GASOLINA, ALCOOL_TANQUE, ALCOOL_TANQUE);
        SITUACAO situacao =  centro.getSituacao();
        misturaRecebida = centro.encomendaCombustivel(1, TIPOPOSTO.COMUM);

        Assert.assertEquals(SITUACAO.SOBRAVISO, situacao);
        Assert.assertEquals(1, misturaRecebida.length);
        Assert.assertEquals(misturaEsperada[0], misturaRecebida[0]);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 7, 8 , 9, 10, 25, 50, 75, 100, 1000})
    void encomendaCombustivelQtValidaSEmergenciaPEstrategicoAdquireMistura(int litrosCombustivel){
        final int ADITIVO_TANQUE  = (int)(CentroDistribuicao.MAX_ADITIVO  * 0.25)  - 1;
        final int ALCOOL_TANQUE   = (int)(CentroDistribuicao.MAX_ALCOOL   * 0.125) - 1;
        final int GASOLINA_TANQUE = (int)(CentroDistribuicao.MAX_GASOLINA * 0.25)  - 1;
        int[] misturaRecebida;
        int[] misturaEsperada = new int[3];
        misturaEsperada[0] = (int)(ADITIVO_TANQUE  - litrosCombustivel * 0.05);
        misturaEsperada[1] = (int)(GASOLINA_TANQUE - litrosCombustivel * 0.7);
        misturaEsperada[2] = (int)(ALCOOL_TANQUE   - litrosCombustivel * 0.125);
        centro = new CentroDistribuicao(ADITIVO_TANQUE, GASOLINA_TANQUE, ALCOOL_TANQUE, ALCOOL_TANQUE);
        SITUACAO situacao =  centro.getSituacao();
        misturaRecebida = centro.encomendaCombustivel(litrosCombustivel, TIPOPOSTO.ESTRATEGICO);

        Assert.assertEquals(SITUACAO.EMERGENCIA, situacao);
        Assert.assertEquals(4, misturaRecebida.length);
        Assert.assertEquals(misturaEsperada[0], misturaRecebida[0]);
        Assert.assertEquals(misturaEsperada[1], misturaRecebida[1]);
        Assert.assertEquals(misturaEsperada[2], misturaRecebida[2]);
        Assert.assertEquals(misturaEsperada[2], misturaRecebida[3]);
    }

    @Test
    void encomendaCombustivelQtValidaAdquireMisturaMudaSituacao(){
        final int ALCOOL_TANQUE = (int)(CentroDistribuicao.MAX_ALCOOL * 0.25);
        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO / 2, CentroDistribuicao.MAX_GASOLINA / 2, ALCOOL_TANQUE, ALCOOL_TANQUE);
        SITUACAO situacaoAntes  = centro.getSituacao();

        centro.encomendaCombustivel(1000, TIPOPOSTO.COMUM);

        Assert.assertNotEquals(situacaoAntes, centro.getSituacao());
    }

    @Test
    void encomendaCombustivelQtValidaPoucaMisturaNaoSEmergenciaPEstrategicoNaoAdquireMistura(){
        final int ADITIVO_TANQUE  = (int)(CentroDistribuicao.MAX_ADITIVO * 0.25) + 1;
        final int ALCOOL_TANQUE   = (int)CentroDistribuicao.MAX_ALCOOL / 2;
        final int GASOLINA_TANQUE = CentroDistribuicao.MAX_GASOLINA;
        int[] misturaRecebida;
        int[] misturaEsperada = new int[] {-21};
        centro = new CentroDistribuicao(ADITIVO_TANQUE, GASOLINA_TANQUE, ALCOOL_TANQUE, ALCOOL_TANQUE);
        misturaRecebida = centro.encomendaCombustivel(5000, TIPOPOSTO.ESTRATEGICO);

        Assert.assertNotEquals(SITUACAO.EMERGENCIA, centro.getSituacao());
        Assert.assertEquals(1, misturaRecebida.length);
        Assert.assertEquals(misturaEsperada[0], misturaRecebida[0]);
        Assert.assertEquals(ADITIVO_TANQUE, centro.getTanAditivo());
        Assert.assertEquals(GASOLINA_TANQUE, centro.getTanGasolina());
        Assert.assertEquals(ALCOOL_TANQUE, centro.getTanAlcool1());
        Assert.assertEquals(ALCOOL_TANQUE, centro.getTanAlcool2());
    }

    @Test
    void encomendaCombustivelQtInvalidaCodigoPraQt(){
        final int ALCOOL_TANQUE  = CentroDistribuicao.MAX_ALCOOL / 2 - 1;
        int[] misturaRecebida;
        int[] misturaEsperada = new int[] {-7};
        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, CentroDistribuicao.MAX_GASOLINA, ALCOOL_TANQUE, ALCOOL_TANQUE);
        misturaRecebida = centro.encomendaCombustivel(-1, TIPOPOSTO.COMUM);

        Assert.assertEquals(1, misturaRecebida.length);
        Assert.assertEquals(misturaEsperada[0], misturaRecebida[0]);
    }

    @Test
    void encomendaCombustivelQtValidaNaoAdquireMisturaCodigoPraFaltaDeMistura(){
        final int ALCOOL_TANQUE = CentroDistribuicao.MAX_ALCOOL / 2;
        int[] misturaRecebida;
        int litrosCombustivel = Integer.MAX_VALUE;
        int[] misturaEsperada = new int[] {-21};
        centro = new CentroDistribuicao(CentroDistribuicao.MAX_ADITIVO, CentroDistribuicao.MAX_GASOLINA, ALCOOL_TANQUE, ALCOOL_TANQUE);
        misturaRecebida = centro.encomendaCombustivel(litrosCombustivel, TIPOPOSTO.COMUM);

        Assert.assertEquals(1, misturaRecebida.length);
        Assert.assertEquals(misturaEsperada[0], misturaRecebida[0]);
    }
    //#endregion

    //#region métodos úteis
    private int[] GetValoresTesteLimite(SITUACAO situacao){
        int[] retorno = new int[4];
        Random genRandom = new Random();
        int minAditivo, maxAditivo, minGasolina, maxGasolina, minAlcool, maxAlcool;
        final int MAX_ALCOOL_TANQUE = CentroDistribuicao.MAX_ALCOOL / 2;
        if(situacao == SITUACAO.NORMAL){
            minAditivo = CentroDistribuicao.MAX_ADITIVO / 2;
            maxAditivo = CentroDistribuicao.MAX_ADITIVO;
            minGasolina = CentroDistribuicao.MAX_GASOLINA / 2;
            maxGasolina = CentroDistribuicao.MAX_GASOLINA;
            minAlcool = MAX_ALCOOL_TANQUE / 2;
            maxAlcool = MAX_ALCOOL_TANQUE;
        }
        else if(situacao == SITUACAO.SOBRAVISO){
            minAditivo = (int) (CentroDistribuicao.MAX_ADITIVO * 0.25) + 1;
            maxAditivo = CentroDistribuicao.MAX_ADITIVO / 2 - 1;
            minGasolina = (int) (CentroDistribuicao.MAX_GASOLINA * 0.25) + 1;
            maxGasolina = CentroDistribuicao.MAX_GASOLINA / 2 - 1;
            minAlcool = (int) (MAX_ALCOOL_TANQUE * 0.25) + 1;
            maxAlcool = MAX_ALCOOL_TANQUE / 2 - 1;
        }
        else{
            minGasolina = minAlcool = minAditivo = 0;
            maxAditivo = (int) (CentroDistribuicao.MAX_ADITIVO * 0.25);
            maxGasolina = (int) (CentroDistribuicao.MAX_GASOLINA * 0.25);
            maxAlcool = (int) (MAX_ALCOOL_TANQUE * 0.25);
        }
        retorno[0] = genRandom.ints(minAditivo, maxAditivo).findAny().getAsInt();
        retorno[1] = genRandom.ints(minGasolina, maxGasolina).findAny().getAsInt();
        int[] valoresAlcool = valoresQueResultamOEsperado(genRandom, minAlcool, maxAlcool);
        retorno[2] = valoresAlcool[0];
        retorno[3] = valoresAlcool[1];
        return retorno;
    }
    private int[] valoresQueResultamOEsperado(Random gerador, int minimo, int maximo){
        int[] valores = {gerador.ints(minimo, maximo).findAny().getAsInt(), gerador.ints(minimo, maximo).findAny().getAsInt()};
        while((valores[0] + valores[1]) / 2 > maximo){
            valores[0] = gerador.ints(minimo, maximo).findAny().getAsInt();
            valores[1] = gerador.ints(minimo, maximo).findAny().getAsInt();
        }
        return valores;
    }
    // //#endregion
}
