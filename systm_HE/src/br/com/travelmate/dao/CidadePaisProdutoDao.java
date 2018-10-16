package br.com.travelmate.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Cidadepaisproduto;

public class CidadePaisProdutoDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;

	@Transactional
	public Cidadepaisproduto salvar(Cidadepaisproduto cidade) throws SQLException {
		cidade = manager.merge(cidade);
		return cidade;
	}

	@SuppressWarnings("unchecked")
	public List<Cidadepaisproduto> listar(String sql) throws SQLException {
		Query q = manager.createQuery(sql);
		List<Cidadepaisproduto> lista = q.getResultList();
		if (lista != null) {
			if (lista.size() == 0) {
				return null;
			}
		}
		return lista;
	}

	@Transactional
	public void excluir(int idcidade) throws SQLException {
		Query q = manager.createQuery("Select c from Cidadepaisproduto c where c.idcidadepaisproduto=" + idcidade);
		if (q.getResultList().size() > 0) {
			Cidadepaisproduto cidade = (Cidadepaisproduto) q.getResultList().get(0);
			manager.remove(cidade);
		}
	}

	public Cidadepaisproduto consultar(String sql) throws SQLException {
		Query q = manager.createQuery(sql);
		Cidadepaisproduto cidadepaisproduto = null;
		if (q.getResultList().size() > 0) {
			cidadepaisproduto = (Cidadepaisproduto) q.getResultList().get(0);
		}
		return cidadepaisproduto;
	}

}
