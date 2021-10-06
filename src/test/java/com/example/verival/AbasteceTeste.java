package com.example.verival;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AbasteceTeste {
    @ParameterizedTest
    @CsvSource({ "0, 0, -1", "200, 0, -1", "500, 0, -1", "0, -10, -1", "200,-10, -1", "500, -10, -1", "0, 350, 350", "200, 350, 300", "500, 350, 0" })
    public void testaRecebeAditivo(int quantidadeNoTanque, int quantidadeAditivo, int resultadoEsperado) {
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(quantidadeNoTanque, 0, 0, 0);
        int resultado = centroDistribuicao.recebeAditivo(quantidadeAditivo);
        Assert.assertEquals(resultadoEsperado, resultado);
    }

    @ParameterizedTest
    @CsvSource({ "0, 0, -1", "5000, 0, -1", "10000, 0, -1", "0, -10, -1", "5000,-10, -1", "10000, -10, -1", "0, 7500, 7500", "5000, 7500, 5000", "10000, 7500, 0" })
    public void testaRecebeGasolina(int quantidadeNoTanque, int quantidadeGasolina, int resultadoEsperado) {
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(0, quantidadeNoTanque, 0, 0);
        int resultado = centroDistribuicao.recebeGasolina(quantidadeGasolina);
        Assert.assertEquals(resultadoEsperado, resultado);
    }

    @ParameterizedTest
    @CsvSource({ "0, 0, 0, -1", "1000, 1000, 0, -1", "1250, 1250, 0, -1", "0, 0, -10, -1", "1000, 1000, -10, -1", "1250, 1250, -10, -1", "0, 0, 1500, 1500", "1000, 1000, 1500, 500", "1250, 1250, 1500, 0" })
    public void testaRecebeAlcool(int quantidadeNoTanque1, int quantidadeNoTanque2, int quantidadeAlcool, int resultadoEsperado) {
        CentroDistribuicao centroDistribuicao = new CentroDistribuicao(0, 0, quantidadeNoTanque1, quantidadeNoTanque2);
        int resultado = centroDistribuicao.recebeAlcool(quantidadeAlcool);
        Assert.assertEquals(resultadoEsperado, resultado);
    }
	
}
