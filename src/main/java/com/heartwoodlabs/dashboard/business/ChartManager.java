package com.heartwoodlabs.dashboard.business;

import com.heartwoodlabs.dashboard.dao.*;
import com.heartwoodlabs.dashboard.dto.*;
import com.heartwoodlabs.dashboard.model.Dispositivo;
import com.heartwoodlabs.dashboard.model.LineaProduttiva;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


/**
 * La classe di occupa di eseguire gli algoritmi di aggregazione dei dati per popolare dei grafici.
 *
 * @author mpirruccio
 */
public class ChartManager {

    /**
     * Carica l'elenco dei prodotti dal database usando {@link ProdottoDao#getAll(int)}.
     *
     * @param limit il numero massimo di elementi restituiti
     * @return l'elenco dei prodotti
     */
    public static ChartDto elencoProdotti(int limit) {
        ChartDto res = new ChartDto(ChartDto.ChartType.TABLE);
        res.setTitle("Elenco prodotti");
        res.setSubtitle("Aggiornato al " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        res.getLabels().addAll(Arrays.asList("Nome", "Prezzo di vendita"));

        ChartSerieDto serie = new ChartSerieDto();
        serie.setLabel("Anagrafica Prodotti");
        serie.getData().addAll(new ProdottoDao().getAll(limit));

        res.getSeries().add(serie);

        return res;
    }

    public static ChartDto produzione(int numMonths) {
        ChartDto res = new ChartDto(ChartDto.ChartType.BASIC_LINE);

        LocalDate productionLimitDate = LocalDate.now().minusMonths(numMonths);
        res.setTitle("Produzione dal " + DateTimeFormatter.ofPattern("dd-MM-yyyy").format(productionLimitDate) + " ad oggi");
        res.setSubtitle("Aggiornato al " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        res.getLabels().addAll(Arrays.asList("Prodotti", "Tempo"));

        LineaProduttivaDao lineaProduttivaDao = new LineaProduttivaDao();
        for (LineaProduttiva linea : lineaProduttivaDao.getAll(LineaProduttiva.class)) {
            ChartSerieDto serie = new ChartSerieDto();
            serie.setLabel(linea.getNome());
            serie.getData().addAll(new ProduzioneDao().produzione(linea.getId(), productionLimitDate));

            res.getSeries().add(serie);
        }

        return res;
    }

    public static ChartDto energiaElettrica(int numMonths) {
        ChartDto res = new ChartDto(ChartDto.ChartType.DUAL_AXIS);

        LocalDate limitDate = LocalDate.now().minusMonths(numMonths);
        res.setTitle("Consumo e produzione energia elettrica dal " + DateTimeFormatter.ofPattern("dd-MM-yyyy").format(limitDate) + " ad oggi");
        res.setSubtitle("Aggiornato al " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        res.getLabels().addAll(Arrays.asList("KilowattHour", "Tempo"));

        ConsumoDao consumoDao = new ConsumoDao();

        ChartSerieDto serie = new ChartSerieDto();
        serie.setLabel("Consumo");
        serie.getData().addAll(consumoDao.consumi(List.of(Dispositivo.Tipo.CONSUMO_ELETTRICO_MONOFASE, Dispositivo.Tipo.CONSUMO_ELETTRICO_TRIFASE), limitDate));
        res.getSeries().add(serie);

        serie = new ChartSerieDto();
        serie.setLabel("Produzione");
        serie.getData().addAll(consumoDao.consumi(List.of(Dispositivo.Tipo.PRODUZIONE_ELETTRICA_MONOFASE, Dispositivo.Tipo.PRODUZIONE_ELETTRICA_TRIFASE), limitDate));
        res.getSeries().add(serie);

        return res;
    }

    public static ChartDto fatturato(int numMonths) {
        ChartDto res = new ChartDto(ChartDto.ChartType.DUAL_AXIS);

        LocalDate limitDate = LocalDate.now().minusMonths(numMonths);
        res.setTitle("Fatturato e costi di smaltimento rifiuti dal " + DateTimeFormatter.ofPattern("dd-MM-yyyy").format(limitDate) + " ad oggi");
        res.setSubtitle("Aggiornato al " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        res.getLabels().addAll(Arrays.asList("Euro", "Tempo"));

        ChartSerieDto serie = new ChartSerieDto();
        serie.setLabel("Fatturato");
        serie.getData().addAll(new ProduzioneDao().fatturato(limitDate));
        res.getSeries().add(serie);

        serie = new ChartSerieDto();
        serie.setLabel("Costo smaltimento");
        serie.getData().addAll(new SmaltimentoRifiutoDao().costo(limitDate));
        res.getSeries().add(serie);

        return res;
    }

    public static ChartDto consumoIdrico(int numMonths) {
        ChartDto res = new ChartDto(ChartDto.ChartType.STACKED_COLUMNS);

        LocalDate limitDate = LocalDate.now().minusMonths(numMonths);
        res.setTitle("Consumo idrico dal " + DateTimeFormatter.ofPattern("dd-MM-yyyy").format(limitDate) + " ad oggi");
        res.setSubtitle("Aggiornato al " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        res.getLabels().addAll(Arrays.asList("Metri cubi", "Tempo"));

        ConsumoDao consumoDao = new ConsumoDao();

        ChartSerieDto serie = new ChartSerieDto();
        serie.setLabel("Consumo bagni");
        serie.getData().addAll(consumoDao.consumi(List.of(Dispositivo.Tipo.CONSUMO_ACQUA_BAGNO), limitDate));
        res.getSeries().add(serie);

        serie = new ChartSerieDto();
        serie.setLabel("Consumo produzione");
        serie.getData().addAll(consumoDao.consumi(List.of(Dispositivo.Tipo.CONSUMO_ACQUA_PRODUZIONE), limitDate));
        res.getSeries().add(serie);

        return res;
    }

    public static ChartDto rifiuti(int numMonths) {
        ChartDto res = new ChartDto(ChartDto.ChartType.PIE);

        LocalDate limitDate = LocalDate.now().minusMonths(numMonths);
        res.setTitle("Rifiuti prodotti dal " + DateTimeFormatter.ofPattern("dd-MM-yyyy").format(limitDate) + " ad oggi");
        res.setSubtitle("Aggiornato al " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        res.getLabels().addAll(Arrays.asList("Quintali", "Tempo"));

        List<RifiutoDto> rifiuti = new SmaltimentoRifiutoDao().rifiuti(limitDate);

        float totale = 0.0f;
        for (RifiutoDto rifiuto : rifiuti) {
            totale += rifiuto.getQuantita();
        }

        for (RifiutoDto rifiuto : rifiuti) {
            rifiuto.setPercentuale(rifiuto.getQuantita() * 100 / totale);
        }

        ChartSerieDto serie = new ChartSerieDto();
        serie.setLabel("Rifiuti");
        serie.getData().addAll(rifiuti);
        res.getSeries().add(serie);

        return res;
    }
}
