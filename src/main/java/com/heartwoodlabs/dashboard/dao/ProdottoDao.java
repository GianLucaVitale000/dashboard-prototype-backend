package com.heartwoodlabs.dashboard.dao;

import com.heartwoodlabs.dashboard.dto.ProdottoDto;
import com.heartwoodlabs.dashboard.model.Prodotto;

import java.util.List;

public class ProdottoDao extends BaseDao {

	public List<ProdottoDto> getAll(int limit) {
		//@formatter:off
		String query = "select new com.heartwoodlabs.dashboard.dto.ProdottoDto(p.nome, p.prezzoVendita) " +
				"from " + Prodotto.class.getName() + " p " +
				"order by p.nome desc";
		//@formatter:on
		return entityManager.createQuery(query).setMaxResults(limit).getResultList();
	}

	public Long countByNome(String nome) {
		return entityManager.createNamedQuery("countByNome", Long.class)
				.setParameter("nome", nome)
				.getSingleResult();
	}
}
