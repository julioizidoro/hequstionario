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
import br.com.travelmate.dao.QuestionarioHeDao;
import br.com.travelmate.dao.TipoContatoDao;
import br.com.travelmate.managedBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cliente;
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
	private UsuarioLogadoMB usuarioLogadoMB;
	private Cliente cliente;
	private Questionariohe questionarioHe;
	private List<Pais> listaPais;
	private String voltarControleVendas = "";
	private boolean habilitarNivel12 = true;
	private boolean habilitarNivel3 = false;
	private boolean habilitarNotas = true;
	private boolean cadastrado = false;

	@PostConstruct
	public void init() {
		try {
			listaPais = paisDao.listar();
		} catch (SQLException e) {
		}
		if (questionarioHe == null) {
			questionarioHe = new Questionariohe();
			cliente = usuarioLogadoMB.getCliente();
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
		String msg = validarDados();
		if (msg != null && msg.length() == 0) {
			try {
				cliente = clienteDao.salvar(cliente);
				questionarioHe.setCliente(cliente);
				questionarioHe.setUsuario(usuarioLogadoMB.getLead().getUsuario());
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
		}else {
			Mensagem.lancarMensagemInfo(msg, "");
		}
		return "";
	}
	
	
	public void verificarNivel() {
		if (questionarioHe.getPais1().equalsIgnoreCase("Portugal")) {
			habilitarNivel3  = true;
			habilitarNivel12 = false;
		}else {
			habilitarNivel12 = true;
			habilitarNivel3 = false;
		}
	}
	
	
	public String  validarDados() {
		String msg = "";
		if (cliente == null || cliente.getIdcliente() == null) {
			 msg = msg + "Cliente não informado";
		}
		
		if (questionarioHe.getDiplomas() == null || questionarioHe.getDiplomas().length() <= 0) {
			msg = msg + "Informe o nome do curso; \n \n";
		}
		
		if (questionarioHe.getNivelcetificado() == null || questionarioHe.getNivelcetificado().length() <= 0) {
			msg = msg + "Informe o Nivel mais alto de escolaridade no Brasil; \n \n";
		}
		
		if (questionarioHe.getOntuacaotoefl() == null || questionarioHe.getOntuacaotoefl().length() <= 0) {
			msg = msg + "Informe a Pontuação no teste de proficiência ou teste online; \n \n";
		}
		
		if (questionarioHe.getOcupacao1() == null || questionarioHe.getOcupacao1().length() <= 0) {
			msg = msg + "Informe Descreva suas duas última principais ocupações profissionais; \n \n";
		}
		
		
		if (questionarioHe.getPrograma() == null || questionarioHe.getPrograma().length() <= 0) {
			msg = msg + "Informe Programa / Área de interesse; \n \n";
		}
		
		if (questionarioHe.getNivelcertificadointeresse() == null || questionarioHe.getNivelcertificadointeresse().length() <= 0) {
			msg = msg + "Informe Nível de Certificação de interesse; \n \n";
		}
		
		
		if (questionarioHe.getPais1() == null || questionarioHe.getPais1().length() <=0) {
			msg = msg + "Informe o Destino em que prefere estudar; \n \n";
		}
		
		
		if (questionarioHe.getDataprograma() == null) {
			msg = msg + "Informe Data aproximada do Programa; \n \n";
		}
		
		if (questionarioHe.getPrecisatrabalahar() == null || questionarioHe.getPrecisatrabalahar().length() <= 0) {
			msg = msg + "Informe Preciso trabalhar durante meu curso; \n \n";
		}
		
		
		if (questionarioHe.getObservacao() == null || questionarioHe.getObservacao().length() <= 0) {
			msg = msg + "Informe a Observações e parecer do consultor; \n \n";
		}
		
		if (questionarioHe.getPrecisatrabalahar() == null || questionarioHe.getPrecisatrabalahar().length() <= 0) {
			msg = msg + "Informe 'Preciso trabalhar durante meu curso?'; \n \n";
		}
		
		if (questionarioHe.getInteresseemimigrar() == null || questionarioHe.getInteresseemimigrar().length() <= 0) {
			msg = msg + "Informe 'Tenho interesse em imigrar'; \n \n";
		}
		
		if (questionarioHe.getVistotrabalho() == null || questionarioHe.getVistotrabalho().length() <= 0) {
			msg = msg + "Informe 'Tenho interesse em visto de trabalho após o curso'; \n \n";
		}
		return msg;
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
			leadhistorico.setHistorico("Cadastor de um questionário como id: " + questionarioHe.getIdquestionariohe() + ": "
					+ " incluido no dia : " + Formatacao.ConvercaoDataPadrao(new Date()) + ", cliente: " + cliente.getNome());
			leadhistorico.setTipoorcamento("");
			leadhistorico.setIdorcamento(0);
			leadHistoricoDao.salvar(leadhistorico);
		} catch (SQLException e) {
		}
	}
	
	

}
