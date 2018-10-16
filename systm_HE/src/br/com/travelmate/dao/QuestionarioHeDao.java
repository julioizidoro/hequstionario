package br.com.travelmate.dao;

import br.com.travelmate.connection.Transactional;
import br.com.travelmate.model.Questionariohe;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class QuestionarioHeDao implements Serializable{
	
	
     
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager manager;

	
	@Transactional
	public Questionariohe salvar(Questionariohe questionariohe) throws SQLException{
		questionariohe = manager.merge(questionariohe);
        return questionariohe;
    }

    @SuppressWarnings("unchecked")
    public List<Questionariohe> listar(String sql) throws SQLException{
        Query q = manager.createQuery(sql);
        List<Questionariohe> lista = q.getResultList();
        return lista;
    }
    
    public Questionariohe consultar(int idhe) throws SQLException {
        Query q = manager.createQuery("select q from Questionariohe q where q.idquestionariohe=" + idhe);
        Questionariohe questionariohe = null;
        if (q.getResultList().size()>0){
        	questionariohe = (Questionariohe) q.getResultList().get(0);
        }
        return questionariohe;
    } 
    

    
    public Questionariohe consultarQuestionario(int idcliente, String tipoquestionario) throws SQLException {
        Query q = manager.createQuery("select q from Questionariohe q where q.tipoquestionario='" + tipoquestionario + "' and q.cliente.idcliente=" + idcliente);
        Questionariohe questionariohe = null;
        if (q.getResultList().size()>0){
        	questionariohe = (Questionariohe) q.getResultList().get(0);
        }
        return questionariohe;
    } 
    
    
    public Questionariohe consultarVenda(int idvenda) throws SQLException {
        Query q = manager.createQuery("select q from Questionariohe q where q.vendas_idvendas=" + idvenda);
        Questionariohe questionariohe = null;
        if (q.getResultList().size()>0){
        	questionariohe = (Questionariohe) q.getResultList().get(0);
        }
        return questionariohe;
    } 
}
