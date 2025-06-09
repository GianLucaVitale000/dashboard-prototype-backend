package com.heartwoodlabs.dashboard.business;

import com.heartwoodlabs.dashboard.dao.*;
import com.heartwoodlabs.dashboard.model.*;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class PersistData {

	private static final boolean insertProdotto = true;
	private static final boolean insertLinea = true;
	private static final boolean insertProduzione = true;
	private static final boolean insertRifiuto = true;
	private static final boolean insertSmaltimento = true;
	private static final boolean insertDispositivo = true;
	private static final boolean insertConsumoAcqua = true;
	private static final boolean insertEnergiaElettrica = true;

	public static void main(String[] args) throws Exception {
		BaseDao.initFactory("DefaultPersistenceUnit");
		Faker faker = new Faker(Locale.ITALIAN);

		if (insertProdotto) {
			ProdottoDao prodottoDao = new ProdottoDao();

			for (int i = 0; i < 10; i++) {
				Prodotto prodotto = new Prodotto();
				prodotto.setNome(faker.commerce().productName());

				NumberFormat format = NumberFormat.getInstance(Locale.ITALIAN);
				prodotto.setPrezzoVendita(BigDecimal.valueOf(format.parse(faker.commerce().price()).doubleValue()));

				prodottoDao.save(prodotto);
			}
		}

		if (insertLinea) {
			LineaProduttivaDao lineaProduttivaDao = new LineaProduttivaDao();
			List<Prodotto> prodotti = new ProdottoDao().getAll(Prodotto.class);
			for (Prodotto prodotto : prodotti) {
				LineaProduttiva lp = new LineaProduttiva();
				lp.setNome(String.format("Linea produttiva di '%s'", prodotto.getNome()));
				lp.setProdotto(prodotto);

				lineaProduttivaDao.save(lp);
			}
		}

		if (insertProduzione) {
			LocalDate currentDate = LocalDate.now();
			ProduzioneDao produzioneDao = new ProduzioneDao();
			List<LineaProduttiva> linee = new LineaProduttivaDao().getAll(LineaProduttiva.class);
			for (LineaProduttiva linea : linee) {
				LocalDate startDate = currentDate.minusYears(1);
				while (startDate.isBefore(currentDate)) {
					if (startDate.getDayOfWeek() != DayOfWeek.SATURDAY && startDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
						Produzione produzione = new Produzione();
						produzione.setData(startDate);
						produzione.setLinea(linea);
						produzione.setPezziProdotti(faker.number().numberBetween(10, 99));
						produzione.setPezziDifettosi(faker.number().randomDigit());

						produzioneDao.save(produzione);
					}

					startDate = startDate.plusDays(1);
				}
			}
		}

		if (insertRifiuto) {
			RifiutoDao rifiutoDao = new RifiutoDao();
			for (Rifiuto.Tipo tipo : Rifiuto.Tipo.values()) {
				Rifiuto rifiuto = new Rifiuto();
				rifiuto.setTipo(tipo);
				rifiuto.setCostoSmaltimento(BigDecimal.valueOf(faker.number().randomDouble(2, 17, 250)));

				rifiutoDao.save(rifiuto);
			}
		}

		if (insertSmaltimento) {
			SmaltimentoRifiutoDao smaltimentoRifiutoDao = new SmaltimentoRifiutoDao();
			LocalDate currentDate = LocalDate.now();
			List<Rifiuto> rifiuti = new RifiutoDao().getAll(Rifiuto.class);
			for (Rifiuto rifiuto : rifiuti) {
				LocalDate startDate = currentDate.minusYears(1);
				while (startDate.isBefore(currentDate)) {
					if (startDate.getDayOfWeek() != DayOfWeek.MONDAY) {
						SmaltimentoRifiuto sr = new SmaltimentoRifiuto();
						sr.setData(startDate);
						sr.setRifiuto(rifiuto);
						sr.setQuantita(faker.number().randomDouble(2, 1, 5));

						smaltimentoRifiutoDao.save(sr);
					}

					startDate = startDate.plusDays(1);
				}
			}
		}

		if (insertDispositivo) {
			DispositivoDao dispositivoDao = new DispositivoDao();
			for (Dispositivo.Tipo tipo : Dispositivo.Tipo.values()) {
				for (int i = 0; i < 3; i++) {
					Dispositivo dispositivo = new Dispositivo();
					dispositivo.setTipo(tipo);
					dispositivo.setNome(getNomeDispositivo(tipo, i));

					dispositivoDao.save(dispositivo);
				}
			}
		}

		if (insertConsumoAcqua) {
			consumi(List.of(Dispositivo.Tipo.CONSUMO_ACQUA_BAGNO, Dispositivo.Tipo.CONSUMO_ACQUA_PRODUZIONE), faker);
		}

		if (insertEnergiaElettrica) {
			consumi(List.of(Dispositivo.Tipo.CONSUMO_ELETTRICO_MONOFASE, Dispositivo.Tipo.CONSUMO_ELETTRICO_TRIFASE,
					Dispositivo.Tipo.PRODUZIONE_ELETTRICA_MONOFASE, Dispositivo.Tipo.PRODUZIONE_ELETTRICA_TRIFASE), faker);
		}

		BaseDao.shutdownFactory();
	}

	private static String getNomeDispositivo(Dispositivo.Tipo tipo, int i) {
		switch (tipo) {
			case PRODUZIONE_ELETTRICA_MONOFASE -> {
				return "Pannello fotovoltaico monofase n. " + (i + 1);
			}
			case PRODUZIONE_ELETTRICA_TRIFASE -> {
				return "Pannello fotovoltaico trifase n. " + (i + 1);
			}
			case CONSUMO_ELETTRICO_MONOFASE -> {
				return "Macchinario monofase " + (i + 1);
			}
			case CONSUMO_ELETTRICO_TRIFASE -> {
				return "Macchinario trifase " + (i + 1);
			}
			case CONSUMO_ACQUA_BAGNO -> {
				return "Bagno al piano " + (i + 1);
			}
			case CONSUMO_ACQUA_PRODUZIONE -> {
				return "Macchinario " + (i + 1);
			}
		}

		return "";
	}

	private static void consumi(List<Dispositivo.Tipo> tipi, Faker faker) {
		ConsumoDao consumoDao = new ConsumoDao();
		LocalDate currentDate = LocalDate.now();
		for (Dispositivo.Tipo tipo : tipi) {
			List<Dispositivo> dispositivi = new DispositivoDao().findByTipo(tipo);
			for (Dispositivo dispositivo : dispositivi) {
				LocalDate startDate = currentDate.minusYears(1);
				while (startDate.isBefore(currentDate)) {
					if (startDate.getDayOfWeek() != DayOfWeek.SATURDAY && startDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
						Consumo consumo = new Consumo();
						consumo.setData(startDate);
						consumo.setDispositivo(dispositivo);
						consumo.setQuantita(faker.number().randomDouble(2, 1, 100));

						consumoDao.save(consumo);
					}

					startDate = startDate.plusDays(1);
				}
			}
		}
	}
}
