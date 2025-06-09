package com.heartwoodlabs.dashboard.dao;

import com.heartwoodlabs.dashboard.dto.ProdottoDto;
import com.heartwoodlabs.dashboard.model.Prodotto;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdottoDaoTest {

	@BeforeAll
	public static void init() {
		BaseDao.initFactory("TestPersistenceUnit");
	}

	@AfterAll
	public static void shutdown() {
		BaseDao.shutdownFactory();
	}

	@Test
	@Order(0)
	public void save() throws ParseException {
		Faker faker = new Faker(Locale.ITALIAN);
		ProdottoDao prodottoDao = new ProdottoDao();

		for (int i = 0; i < 10; i++) {
			Prodotto prodotto = new Prodotto();
			prodotto.setNome(faker.commerce().productName());

			NumberFormat format = NumberFormat.getInstance(Locale.ITALIAN);
			prodotto.setPrezzoVendita(BigDecimal.valueOf(format.parse(faker.commerce().price()).doubleValue()));

			prodottoDao.save(prodotto);

			Assertions.assertNotNull(prodotto.getId());
		}
	}

	@Test
	@Order(1)
	public void getAll() {
		List<ProdottoDto> products = new ProdottoDao().getAll(10);
		Assertions.assertFalse(products.isEmpty());
		Assertions.assertEquals(10, products.size());
	}
}
