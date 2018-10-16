package br.com.travelmate.managerBean.higherEducation;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.dao.ClienteDao;
import br.com.travelmate.dao.LeadHistoricoDao;
import br.com.travelmate.dao.PaisDao;
import br.com.travelmate.dao.PaisProdutoDao;
import br.com.travelmate.dao.QuestionarioHeDao;
import br.com.travelmate.dao.TipoContatoDao;
import br.com.travelmate.managedBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadhistorico;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Questionariohe;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadQuestionarioHeMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ClienteDao clienteDao;
	@Inject
	private QuestionarioHeDao questionarioHeDao;
	@Inject
	private PaisDao paisDao;
	@Inject
	private TipoContatoDao tipoContatoDao;
	@Inject
	private LeadHistoricoDao leadHistoricoDao;
	@Inject
	private PaisProdutoDao paisProdutoDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Cliente cliente;
	private Questionariohe questionarioHe;
	private List<Pais> listaPais;
	private String voltarControleVendas = "";
	private boolean habilitarNivel12 = true;
	private boolean habilitarNivel3 = true;
	private boolean habilitarNotas = true;
	private boolean cadastrado = false;
	private Lead lead;
	private boolean habilitarNivel3pais = true;

	@PostConstruct
	public void init() {
		listarPaises();
		if (questionarioHe == null) {
			questionarioHe = new Questionariohe();
			cliente = usuarioLogadoMB.getCliente();
			lead = usuarioLogadoMB.getLead();
		} 
	}


	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Questionariohe getQuestionarioHe() {
		return questionarioHe;
	}

	public void setQuestionarioHe(Questionariohe questionarioHe) {
		this.questionarioHe = questionarioHe;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}


	public boolean isHabilitarNivel12() {
		return habilitarNivel12;
	}

	public void setHabilitarNivel12(boolean habilitarNivel12) {
		this.habilitarNivel12 = habilitarNivel12;
	}

	public boolean isHabilitarNivel3() {
		return habilitarNivel3;
	}

	public void setHabilitarNivel3(boolean habilitarNivel3) {
		this.habilitarNivel3 = habilitarNivel3;
	}

	public boolean isHabilitarNotas() {
		return habilitarNotas;
	}

	public void setHabilitarNotas(boolean habilitarNotas) {
		this.habilitarNotas = habilitarNotas;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}


	public boolean isCadastrado() {
		return cadastrado;
	}


	public void setCadastrado(boolean cadastrado) {
		this.cadastrado = cadastrado;
	}


	public boolean isHabilitarNivel3pais() {
		return habilitarNivel3pais;
	}


	public void setHabilitarNivel3pais(boolean habilitarNivel3pais) {
		this.habilitarNivel3pais = habilitarNivel3pais;
	}


	public String pesquisarCliente() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 650);
		RequestContext.getCurrentInstance().openDialog("consultarCliente", options, null);
		return "";
	}

	public void retornoDialogCliente(SelectEvent event) {
		cliente = (Cliente) event.getObject();
	}

	public String cancelar() {
		if (voltarControleVendas != null) {
			if (voltarControleVendas.length() > 1) {
				return voltarControleVendas;
			}
		}
		return "consquestionarioHe";
	}

	public String salvar() {
		try {
			cliente = clienteDao.salvar(cliente);
			questionarioHe.setCliente(cliente);
			questionarioHe.setUsuario(lead.getUsuario());
			if (questionarioHe.getIdquestionariohe() == null) {
				questionarioHe.setDataenvio(new Date());
				questionarioHe.setSituacao("Processo");
			}
			questionarioHe = questionarioHeDao.salvar(questionarioHe);
			Mensagem.lancarMensagemInfo("Questionario salvo com sucesso!", "");
			salvarHistoricoLead();
			cadastrado = true;
			return "";
		} catch (SQLException e) {
		}
		return "";
	}
	
	
	public void verificarNivel() {
		if (questionarioHe.getPais1().equalsIgnoreCase("Portugal")) {
			habilitarNivel3  = false;
		}else {
			habilitarNivel3 = true;
		}
	}
	
	public void verificarNivel2() {
		if (questionarioHe.getPais2().equalsIgnoreCase("Portugal")) {
			habilitarNivel3pais  = false;
		}else {
			habilitarNivel3pais = true;
		}
	}
	
	
	
	public void verificarEnem() {
		if (questionarioHe.getResultadotesteonline() != null && questionarioHe.getResultadotesteonline().equalsIgnoreCase("Sim")) {
			habilitarNotas = false;
		} else {
			habilitarNotas = true;
		}
	}
	
	public String fechar() {
		usuarioLogadoMB.deslogar();
		cadastrado = false;
		return "index";
	}
	
	
	public void salvarHistoricoLead() {
		Leadhistorico leadhistorico = new Leadhistorico();
		leadhistorico.setCliente(cliente);
		leadhistorico.setDatahistorico(new Date());
		leadhistorico.setDataproximocontato(new Date());
		String sql = "Select t From Tipocontato t where t.tipo='Orçamento'";
		Tipocontato tipocontato;
		try {
			tipocontato = tipoContatoDao.consultar(sql);
			leadhistorico.setTipocontato(tipocontato);
			leadhistorico.setHistorico("Cadastro de um questionário como id: " + questionarioHe.getIdquestionariohe() + ": "
					+ " incluido no dia : " + Formatacao.ConvercaoDataPadrao(new Date()) + ", cliente: " + cliente.getNome() + ". Verifique o questionário e atualize a situaçao para 'Em Análise'");
			leadhistorico.setTipoorcamento("");
			leadhistorico.setIdorcamento(0);
			leadHistoricoDao.salvar(leadhistorico);
		} catch (SQLException e) {
		}
	}
	
	
	public void listarPaises() {
			try {
				listaPais = paisProdutoDao.listar(22);
			} catch (SQLException e) {
			}
    }
	

}
