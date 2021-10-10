package com.example.verival;

import java.security.InvalidParameterException;

import lombok.Getter;
import lombok.ToString;

 @ToString @Getter
public class CentroDistribuicao {
    public enum SITUACAO { NORMAL, SOBRAVISO, EMERGENCIA }
    public enum TIPOPOSTO { COMUM, ESTRATEGICO }

    public static final int MAX_ADITIVO = 500;
    public static final int MAX_ALCOOL = 2500;
    public static final int MAX_GASOLINA = 10000;

    
    private int tanAditivo, tanGasolina, tanAlcool1, tanAlcool2;
    private SITUACAO situacao;

    public CentroDistribuicao(int tAditivo, int tGasolina, int tAlcool1, int tAlcool2) {
        int totalAlcool = tAlcool2 + tAlcool1;

        if(tAditivo > MAX_ADITIVO || tGasolina > MAX_GASOLINA || totalAlcool > MAX_ALCOOL || tAditivo < 0 || tGasolina < 0 || totalAlcool < 0)
            throw new InvalidParameterException("Infração no limite de capacidade de um ou mais tanques do Centro de Distribuição");

        this.tanAditivo = tAditivo;   
        this.tanGasolina = tGasolina;
        this.tanAlcool2 = this.tanAlcool1 = totalAlcool / 2;
        defineSituacao();
    }

    public void defineSituacao() {
        if (tanAditivo >= MAX_ADITIVO * 0.5 && tanGasolina >= MAX_GASOLINA * 0.5 && (tanAlcool1 + tanAlcool2) >= MAX_ALCOOL * 0.5 ) {
            situacao = SITUACAO.NORMAL;
        } else if (tanAditivo < MAX_ADITIVO * 0.25 || tanGasolina < MAX_GASOLINA * 0.25  || (tanAlcool1 + tanAlcool2) < MAX_ALCOOL * 0.25) {
            situacao = SITUACAO.EMERGENCIA;
        } else {
            situacao = SITUACAO.SOBRAVISO;
        }
    }

    public int recebeAditivo(int qtdade) {
        if (qtdade <= 0) {
            return -1;
        }

        if (tanAditivo + qtdade <= MAX_ADITIVO) {
            tanAditivo = (tanAditivo + qtdade);
            defineSituacao();
            return qtdade;
        }

        int retorno = MAX_ADITIVO - tanAditivo;
        tanAditivo = MAX_ADITIVO;
        defineSituacao();
        return retorno;
    }

    public int recebeGasolina(int qtdade) {
        if (qtdade <= 0) {
            return -1;
        }

        if (tanGasolina + qtdade <= MAX_GASOLINA) {
            tanGasolina = (tanGasolina + qtdade);
            defineSituacao();
            return qtdade;
        }

        int retorno = MAX_GASOLINA - tanGasolina;
        tanGasolina = MAX_GASOLINA;
        defineSituacao();
        return retorno;
    }

    public int recebeAlcool(int qtdade) {
        if (qtdade <= 0) {
            return -1;
        }

        int montanteAlcool = qtdade + tanAlcool1 + tanAlcool2;
        if (montanteAlcool <= MAX_ALCOOL) {
            tanAlcool1 = tanAlcool2 = montanteAlcool / 2;
            defineSituacao();
            return qtdade;
        }

        int retorno = MAX_ALCOOL - tanAlcool1 - tanAlcool2;
        tanAlcool1 = tanAlcool2 = MAX_ALCOOL / 2;
        defineSituacao();
        return retorno;
    }
    public int[] encomendaCombustivel(int qtdade, TIPOPOSTO tipoPosto){
        if(qtdade <= 0 ){
            return new int[] {-7};
        }
        if(tipoPosto == TIPOPOSTO.COMUM){
            if(situacao == SITUACAO.EMERGENCIA){
                return new int[] {-14};
            }
            if(situacao == SITUACAO.SOBRAVISO){
                qtdade /= 2;
                if(qtdade == 0)
                    return new int[] {-14};
            }
        }
        boolean conseguiuCombustivel = consegueCombustivel(qtdade, tipoPosto);
        int[] combustivelTanques = conseguiuCombustivel ? new int[] {tanAditivo, tanGasolina, tanAlcool1, tanAlcool2} : new int[]{-21};
        
        return combustivelTanques;
    }
    private boolean consegueCombustivel(int qtdade, TIPOPOSTO tipoPosto){
        int aditivoMistura  = (int)(tanAditivo - qtdade * 0.05); // tudo divido por 100
        int gasolinaMistura = (int)(tanGasolina - qtdade * 0.7); // tudo divido por 100
        int alcoolMistura   = (int)((tanAlcool2 + tanAlcool1) - qtdade * 0.25); // tudo divido por 100
        boolean consegueMistura = false;

        if(gasolinaMistura >= 0 && alcoolMistura >= 0){
            if(aditivoMistura >= 0){
                retiraCombustivel(aditivoMistura, gasolinaMistura, alcoolMistura);
                consegueMistura = true;
            }
            else if(tipoPosto == TIPOPOSTO.ESTRATEGICO && situacao == SITUACAO.EMERGENCIA){
                retiraCombustivel(tanAditivo, gasolinaMistura, alcoolMistura);
                consegueMistura = true;
            }
        }
        return consegueMistura;
    }

    private void retiraCombustivel(int aditivo, int gasolina, int alcool) {
        tanAditivo = aditivo;
        tanGasolina = gasolina;
        tanAlcool2 = tanAlcool1 = alcool / 2;
        defineSituacao();
    }
        
}
