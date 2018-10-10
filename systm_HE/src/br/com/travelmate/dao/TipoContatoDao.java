package br.com.travelmate.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.travelmate.connection.ConectionFactory;
import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Tipocontato; 

public class TipoContatoDao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<Tipocontato> lista(String sql) throws SQLException {
		Query q = manager.createQuery(sql);
		List<Tipocontato> lista = q.getResultList();
		
		return lista;
	}

	public Tipocontato consultar(int idTipocontato) throws SQLException {
		Tipocontato tipocontato = manager.find(Tipocontato.class, idTipocontato);
		return tipocontato;
	}
	
	public Tipocontato consultar(String sql)throws SQLException{
        Query q = manager.createQuery(sql);
        Tipocontato tipocontato= null;
        if (q.getResultList().size()>0){
        	tipocontato =  (Tipocontato) q.getSingleResult();
        } 
        return tipocontato;
    }

	@Transactional
	public Tipocontato salvar(Tipocontato tipocontato) throws SQLException {
		tipocontato = manager.merge(tipocontato);
		return tipocontato;
	}
 
}
