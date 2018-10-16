/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.dao;
import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
@SuppressWarnings("unchecked")
public class PaisProdutoDao implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;


	@Transactional
	public Paisproduto salvar(Paisproduto paisProduto) throws SQLException{
        paisProduto = manager.merge(paisProduto);
        return paisProduto;
    }
    
    public List<Pais> listar(int idProduto) throws SQLException{
        Query q = manager.createQuery("select p from Paisproduto p where p.produtos.idprodutos=" + idProduto + " order by p.pais.nome");
        List<Paisproduto> listaPaisProduto = q.getResultList();
        if (listaPaisProduto == null) {
			listaPaisProduto = new ArrayList<>();
		}
        List<Pais> listaPais = new ArrayList<>();
        for (int i = 0; i < listaPaisProduto.size(); i++) {
			listaPais.add(listaPaisProduto.get(i).getPais());
		}
        return listaPais;
    }
    
    
 
    
    public Paisproduto consultar(String sql) throws SQLException{
        Query q = manager.createQuery(sql);
        Paisproduto paisproduto = null;
        if (q.getResultList().size()>0){
        	paisproduto =  (Paisproduto) q.getResultList().get(0);
        }
        return paisproduto;
    }
}
