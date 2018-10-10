package br.com.travelmate.dao;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Pais;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;


@SuppressWarnings("unchecked")
public class PaisDao implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;

	
	@Transactional
	public Pais salvar(Pais pais) throws SQLException{
        pais = manager.merge(pais);
        return pais;
    }
    
    public List<Pais> listar(String nome) throws SQLException{
        Query q = manager.createQuery("select p from Pais p where p.nome like '%" + nome + "%' order by p.nome");
        List<Pais> listaPais = q.getResultList();
        return listaPais;
    }
    
    public List<Pais> listar() throws SQLException{
        Query q = manager.createQuery("select p from Pais p order by p.nome");
        List<Pais> listaPais = q.getResultList();
        return listaPais;
    }
    
    public Pais consultarNome(String nome) throws SQLException{
        Query q = manager.createQuery("select p from Pais p where p.nome like '%" + nome + "%'" );
        Pais pais = null;
        if (q.getResultList().size() > 0) {
        	pais = (Pais) q.getResultList().get(0);
        } 
        return pais;
     }
    
    
    public Pais consultar(int id) throws SQLException{
        Query q = manager.createQuery("select p from Pais p where p.idpais="+id );
        Pais pais = null;
        if (q.getResultList().size() > 0) {
        	pais = (Pais) q.getResultList().get(0);
        } 
        return pais;
     }
    
    
    public List<Pais> listarModelo(String sql) throws SQLException{
        Query q = manager.createQuery(sql);
        List<Pais> listaPais = q.getResultList();
        return listaPais;
    }
    
    
    
}
