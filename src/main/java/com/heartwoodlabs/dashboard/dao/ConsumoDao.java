package com.heartwoodlabs.dashboard.dao;

import com.heartwoodlabs.dashboard.dto.ConsumoDto;
import com.heartwoodlabs.dashboard.model.Consumo;
import com.heartwoodlabs.dashboard.model.Dispositivo;

import java.time.LocalDate;
import java.util.List;

public class ConsumoDao extends BaseDao {

	public List<ConsumoDto> consumi(List<Dispositivo.Tipo> tipiDispositivo, LocalDate limitDate) {
		//@formatter:off
		String query = "select new com.heartwoodlabs.dashboard.dto.ConsumoDto(sum(c.quantita), date_format(c.data,'%Y-%m')) " +
				"from " + Consumo.class.getName() + " c " +
				"join c.dispositivo d " +
				"where d.tipo in (:tipi) " +
				"and c.data >= :limitDate " +
				"group by date_format(c.data,'%Y-%m') " +
				"order by 2";
		//@formatter:on
		return entityManager.createQuery(query)
				.setParameter("tipi", tipiDispositivo)
				.setParameter("limitDate", limitDate)
				.getResultList();
	}
}
