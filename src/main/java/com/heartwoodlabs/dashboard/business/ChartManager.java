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
        res.setTitle("Produzione e scarti dal " + DateTimeFormatter.ofPattern("dd-MM-yyyy").format(productionLimitDate) + " ad oggi");
        res.setSubtitle("Aggiornato al " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        res.getLabels().addAll(Arrays.asList("Quantità", "Tempo"));

        LineaProduttivaDao lineaProduttivaDao = new LineaProduttivaDao();
        List<LineaProduttiva> linee = lineaProduttivaDao.getAll(LineaProduttiva.class);

        if (linee.isEmpty()) {
            System.out.println("Attenzione: nessuna linea produttiva trovata");
        }

        boolean datiAggiunti = false;

        for (LineaProduttiva linea : linee) {
            ProduzioneDao produzioneDao = new ProduzioneDao();
            List<ProduzioneDto> datiProduzione = produzioneDao.produzione(linea.getId(), productionLimitDate);

            if (datiProduzione.isEmpty()) {
                System.out.println("Attenzione: nessun dato di produzione trovato per la linea " + linea.getNome());
            } else {
                // Serie per la produzione
                ChartSerieDto serieProduzione = new ChartSerieDto();
                serieProduzione.setLabel("Produzione " + linea.getNome());
                serieProduzione.getData().addAll(datiProduzione);
                res.getSeries().add(serieProduzione);

                // Serie per gli scarti
                ChartSerieDto serieScarti = new ChartSerieDto();
                serieScarti.setLabel("Scarti " + linea.getNome());

                // Trasformo i dati per mostrare solo gli scarti
                List<ProduzioneDto> datiScarti = datiProduzione.stream()
                        .map(dto -> new ProduzioneDto(dto.getPezziDifettosi(), 0L, dto.getData()))
                                .toList();

                serieScarti.getData().addAll(datiScarti);
                res.getSeries().add(serieScarti);

                datiAggiunti = true;
            }
        }

        if (!datiAggiunti) {
            System.out.println("Attenzione: nessun dato di produzione disponibile per il periodo selezionato");
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

        // Controllo se la lista è vuota
        if (rifiuti.isEmpty()) {
            System.out.println("Attenzione: nessun dato di rifiuti trovato dal " + limitDate + " ad oggi");
        }

        float totale = 0.0f;
        for (RifiutoDto rifiuto : rifiuti) {
            totale += rifiuto.getQuantita();
        }

        // Protezione dalla divisione per zero
        if (totale > 0) {
            for (RifiutoDto rifiuto : rifiuti) {
                rifiuto.setPercentuale(rifiuto.getQuantita() * 100 / totale);
            }
        } else {
            System.out.println("Attenzione: il totale dei rifiuti è zero");
        }

        ChartSerieDto serie = new ChartSerieDto();
        serie.setLabel("Rifiuti");
        serie.getData().addAll(rifiuti);
        res.getSeries().add(serie);

        return res;
    }
}
