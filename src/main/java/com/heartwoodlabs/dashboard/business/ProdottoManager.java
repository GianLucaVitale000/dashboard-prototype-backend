package com.heartwoodlabs.dashboard.business;

import com.heartwoodlabs.dashboard.dao.ProdottoDao;
import com.heartwoodlabs.dashboard.dto.ProdottoDto;
import com.heartwoodlabs.dashboard.model.Prodotto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProdottoManager {

    private static final Logger log = LogManager.getLogger(ProdottoManager.class);

    public static void verify(ProdottoDto dto) {
        if (dto == null) {
            log.warn("Prodotto non valido");
            throw new InvalidDataException("Prodotto non valido");
        }

        if (dto.getNome() == null || dto.getNome().isBlank()) {
            log.warn("Nome non valido");
            throw new InvalidDataException("Nome non valido");
        }

        if (dto.getPrezzo() == null || dto.getPrezzo().doubleValue() <= 0) {
            log.warn("Prezzo non valido");
            throw new InvalidDataException("Prezzo non valido");
        }

        ProdottoDao dao = new ProdottoDao();
        Long count = dao.countByNome(dto.getNome());
        dao.close();

        if (count > 0) {
            throw new InvalidDataException("Nome duplicato");
        }


    }

    public static Long insert(ProdottoDto dto) {
        verify(dto);

        Prodotto prodotto = new Prodotto();
        prodotto.setNome(dto.getNome());
        prodotto.setPrezzoVendita(dto.getPrezzo());

        ProdottoDao dao = new ProdottoDao();
        dao.save(prodotto);
        dao.close();

        log.debug("Inserimento prodotto {}", prodotto.getId());

        return prodotto.getId();
    }
}
