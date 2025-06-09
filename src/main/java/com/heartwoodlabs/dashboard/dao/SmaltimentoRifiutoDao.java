package com.heartwoodlabs.dashboard.dao;

import com.heartwoodlabs.dashboard.dto.FatturatoDto;
import com.heartwoodlabs.dashboard.dto.RifiutoDto;
import com.heartwoodlabs.dashboard.model.SmaltimentoRifiuto;

import java.time.LocalDate;
import java.util.List;

public class SmaltimentoRifiutoDao extends BaseDao {

	public List<FatturatoDto> costo(LocalDate limitDate) {
		//@formatter:off
		String query = "select new com.heartwoodlabs.dashboard.dto.CostoDto(sum(s.quantita * r.costoSmaltimento), date_format(s.data,'%Y-%m')) " +
				"from " + SmaltimentoRifiuto.class.getName() + " s " +
				"join s.rifiuto r " +
				"where s.data >= :limitDate " +
				"group by date_format(s.data,'%Y-%m') " +
				"order by 2";
		//@formatter:on
		return entityManager.createQuery(query)
				.setParameter("limitDate", limitDate)
				.getResultList();
	}

	public List<RifiutoDto> rifiuti(LocalDate limitDate) {
		//@formatter:off
		String query = "select new com.heartwoodlabs.dashboard.dto.RifiutoDto(sum(s.quantita), r.tipo) " +
				"from " + SmaltimentoRifiuto.class.getName() + " s " +
				"join s.rifiuto r " +
				"where s.data >= :limitDate " +
				"group by r.tipo ";
		//@formatter:on
		return entityManager.createQuery(query)
				.setParameter("limitDate", limitDate)
				.getResultList();
	}
}
