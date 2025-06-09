package com.heartwoodlabs.dashboard.dao;

import com.heartwoodlabs.dashboard.model.Dispositivo;

import java.util.List;

public class DispositivoDao extends BaseDao {
	public List<Dispositivo> findByTipo(Dispositivo.Tipo tipo) {
		//@formatter:off
		String query = "select d " +
				"from " + Dispositivo.class.getName() + " d " +
				"where d.tipo = :tipo ";
		//@formatter:on
		return entityManager.createQuery(query)
				.setParameter("tipo", tipo)
				.getResultList();
	}
}
