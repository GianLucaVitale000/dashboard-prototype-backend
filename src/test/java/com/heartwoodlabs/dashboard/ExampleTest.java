package com.heartwoodlabs.dashboard;

import com.heartwoodlabs.dashboard.business.InvalidDataException;
import com.heartwoodlabs.dashboard.business.ProdottoManager;
import com.heartwoodlabs.dashboard.dto.ChartDto;
import com.heartwoodlabs.dashboard.dto.ProdottoDto;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class ExampleTest {

    private List<Integer> getLista() {
        return new ArrayList<>();
    }

    private void popolaLista(List<Integer> l) {
        l.add(10);
    }

    private Integer somma(List<Integer> l) {
        int i = 0;
        for (Integer li : l) {
            i += li;
        }
        return i;
    }


    @Test
    @Order(10)
    public void run() {
        List<Integer> lista = getLista();
        Assertions.assertTrue(lista.isEmpty());

        popolaLista(lista);
        Assertions.assertTrue(lista.get(0) > 0);
        Assertions.assertEquals(10, lista.get(0));
    }

    @Test
    @Order(20)
    public void run2() {

        List<Integer> lista = getLista();
        Assertions.assertNotNull(lista);

        Integer somma = somma(lista);
        Assertions.assertTrue(somma == 0);
        Assertions.assertEquals(0, somma);

        popolaLista(lista);
        somma = somma(lista);
        Assertions.assertTrue(somma > 0);
    }

    @Test
    @Order(30)
    public void chart() {
        ChartDto chart = new ChartDto(ChartDto.ChartType.BASIC_LINE);
        chart.setTitle("Esempio");

        Assertions.assertNotNull(chart);
        Assertions.assertEquals(ChartDto.ChartType.BASIC_LINE, chart.getType());
        Assertions.assertEquals("Esempio", chart.getTitle());
        Assertions.assertNull(chart.getSubtitle());
        Assertions.assertNotNull(chart.getSeries());
        Assertions.assertSame(0, chart.getSeries().size());
    }

    @Test
    @Order(40)
    public void chart2() {
        ChartDto chart = new ChartDto(ChartDto.ChartType.BASIC_LINE);
        chart.setTitle("Esempio");

        Assertions.assertAll("chart-test", () -> {
            Assertions.assertNotNull(chart);
            Assertions.assertEquals(ChartDto.ChartType.BASIC_LINE, chart.getType());

        }, () -> {
            Assertions.assertEquals("Esempio", chart.getTitle());
            Assertions.assertNull(chart.getSubtitle());
        }, () -> {
			Assertions.assertNotNull(chart.getSeries());
			Assertions.assertSame(0, chart.getSeries().size());

        });

    }

	@Test
    @Order(50)
	public void exception(){
		Assertions.assertThrows(InvalidDataException.class, () -> {
			ProdottoDto dto = new ProdottoDto("", BigDecimal.ZERO);
			ProdottoManager.verify(dto);
		});
	}

   /* @Test
    public void failure() {
        ProdottoDto dto = new ProdottoDto("", BigDecimal.ZERO);
        if (dto.getNome().isBlank()) {
            Assertions.fail();
        }
        Assertions.assertTrue(dto.getNome().isBlank());

    }*/

}
