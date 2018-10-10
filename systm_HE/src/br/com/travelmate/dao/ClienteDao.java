/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;


import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Cliente;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
@SuppressWarnings("unchecked")
public class ClienteDao implements Serializable{
	
	
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;

	@Transactional
	public Cliente salvar(Cliente cliente) throws SQLException{
        cliente = manager.merge(cliente);
        return cliente;
    }
    
    public Cliente consultar(int idCliente) throws SQLException{
        Cliente cliente = manager.find(Cliente.class, idCliente);
        return cliente;
    }
    
    public Cliente consultarEmail(String email) throws SQLException{
	    Query q = manager.createQuery("select c from Cliente c where c.email='" + email + "'" );
        Cliente cliente = null;
        if (q.getResultList().size()>0){
            cliente = (Cliente) q.getResultList().get(0);
        } 
        return cliente;
    }
    
    
    public Cliente consultarEmailSql(String email) throws SQLException{
	    Query q = manager.createQuery(email);
        Cliente cliente = null;
        if (q.getResultList().size()>0){
            cliente = (Cliente) q.getResultList().get(0);
        } 
        return cliente;
    }
    
    public Cliente consultarCpf(String cpf) throws SQLException{
	    Query q = manager.createQuery("select c from Cliente c where c.cpf='" + cpf + "'" );
        Cliente cliente = null;
        if (q.getResultList().size()>0){
            cliente =  (Cliente) q.getSingleResult();
        } 
        return cliente;
    }
       
    
    public Cliente consultarCpfLista(String cpf) throws SQLException{
	    Query q = manager.createQuery("select c from Cliente c where c.cpf='" + cpf + "'" );
        Cliente cliente = null;
        if (q.getResultList().size()>0){
            cliente =  (Cliente) q.getResultList().get(0);
        } 
        return cliente;
    }
    
    public List<Cliente> consultarNome(String nome) throws SQLException{
        Query q = manager.createQuery("select c from Cliente c where c.nome like '%" + nome + "%'" );
        List<Cliente> lista = q.getResultList();
        return lista;
    }
    
    public List<Cliente> listar(String sql) throws SQLException{
        Query q = manager.createQuery(sql);
        List<Cliente> lista = q.getResultList();
        return lista;
    }
    
}
