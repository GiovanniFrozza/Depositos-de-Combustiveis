package com.example.verival;

public class CentroDistribuicao {
    public enum SITUACAO { NORMAL, SOBRAVISO, EMERGENCIA }
    public enum TIPOPOSTO { COMUM, ESTRATEGICO }

    public static final int MAX_ADITIVO = 500;
    public static final int MAX_ALCOOL = 2500;
    public static final int MAX_GASOLINA = 10000;

    private int tAditivo, tGasolina, tAlcool1, tAlcool2;
    private SITUACAO situacao;

    public CentroDistribuicao(int tAditivo, int tGasolina, int tAlcool1, int tAlcool2) {
        this.tAditivo = tAditivo;
        this.tGasolina = tGasolina;
        this.tAlcool1 = tAlcool1;
        this.tAlcool2 = tAlcool2;
        defineSituacao();
    }

    public void defineSituacao() {
        if (tAditivo >= 250 && tGasolina >= 5000 && tAlcool1 >= 625 && tAlcool2 >= 625) {
            situacao = SITUACAO.NORMAL;
        } else if (tAditivo < 125 || tGasolina < 2500 || tAlcool1 < 313 || tAlcool2 < 313) {
            situacao = SITUACAO.EMERGENCIA;
        } else {
            situacao = SITUACAO.SOBRAVISO;
        }
    }

    public int recebeAditivo(int qtdade) {
        if (qtdade <= 0) {
            return -1;
        }

        if (tAditivo + qtdade <= MAX_ADITIVO) {
            tAditivo = (tAditivo + qtdade);
            defineSituacao();
            return qtdade;
        }

        int retorno = MAX_ADITIVO - tAditivo;
        tAditivo = MAX_ADITIVO;
        defineSituacao();
        return retorno;
    }

    public int recebeGasolina(int qtdade) {
        if (qtdade <= 0) {
            return -1;
        }

        if (tGasolina + qtdade < MAX_GASOLINA) {
            tGasolina = (tGasolina + qtdade);
            defineSituacao();
            return qtdade;
        }

        int retorno = MAX_GASOLINA - tGasolina;
        tGasolina = MAX_GASOLINA;
        defineSituacao();
        return retorno;
    }

    public int recebeAlcool(int qtdade) {
        if (qtdade <= 0) {
            return -1;
        }

        int montanteAlcool = qtdade + tAlcool1 + tAlcool2;
        if (montanteAlcool < MAX_ALCOOL) {
            tAlcool1 = tAlcool2 = montanteAlcool / 2;
            defineSituacao();
            return qtdade;
        }

        int retorno = MAX_ALCOOL - tAlcool1 - tAlcool2;
        tAlcool1 = tAlcool2 = MAX_ALCOOL / 2;
        defineSituacao();
        return retorno;
    }

}
