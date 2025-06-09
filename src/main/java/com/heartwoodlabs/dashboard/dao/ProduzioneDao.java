package com.heartwoodlabs.dashboard.dao;

import com.heartwoodlabs.dashboard.dto.FatturatoDto;
import com.heartwoodlabs.dashboard.dto.ProduzioneDto;
import com.heartwoodlabs.dashboard.model.Produzione;

import java.time.LocalDate;
import java.util.List;

public class ProduzioneDao extends BaseDao {

	public List<ProduzioneDto> produzione(Long lineaId, LocalDate productionLimitDate) {
		//@formatter:off
		String query = "select new com.heartwoodlabs.dashboard.dto.ProduzioneDto(sum(p.pezziProdotti), sum(p.pezziDifettosi), date_format(p.data,'%Y-%m')) " +
				"from " + Produzione.class.getName() + " p " +
				"where p.linea.id = :lineaId " +
				"and p.data >= :productionLimitDate " +
				"group by date_format(p.data,'%Y-%m') " +
				"order by 3";
		//@formatter:on
		return entityManager.createQuery(query)
				.setParameter("lineaId", lineaId)
				.setParameter("productionLimitDate", productionLimitDate)
				.getResultList();
	}

	public List<FatturatoDto> fatturato(LocalDate productionLimitDate) {
		//@formatter:off
		String query = "select new com.heartwoodlabs.dashboard.dto.FatturatoDto(sum(p.pezziProdotti * pr.prezzoVendita), date_format(p.data,'%Y-%m')) " +
				"from " + Produzione.class.getName() + " p " +
				"join p.linea l " +
				"join l.prodotto pr " +
				"where p.data >= :productionLimitDate " +
				"group by date_format(p.data,'%Y-%m') " +
				"order by 2";
		//@formatter:on
		return entityManager.createQuery(query)
				.setParameter("productionLimitDate", productionLimitDate)
				.getResultList();
	}
}
