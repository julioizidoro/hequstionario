/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managedBean;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Lead;
import br.com.travelmate.util.Mensagem;


/**
 *
 * @author Wolverine
 */
@Named
@SessionScoped
public class UsuarioLogadoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private LeadDao leadDao;
	private Cliente cliente;
	private String mensagemOla;
	private boolean controle;
	private boolean logar;
	private String login;
	private Lead lead;

	@PostConstruct
	public void init() {
		removerAtributosSessao();
		cliente = new Cliente();
	}

	public boolean isControle() {
		return controle;
	}

	public void setControle(boolean controle) {
		this.controle = controle;
	}

	public String getMensagemOla() {
		return mensagemOla;
	}

	public void setMensagemOla(String mensagemOla) {
		this.mensagemOla = mensagemOla;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isLogar() {
		return logar;
	}

	public void setLogar(boolean logar) {
		this.logar = logar;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public String logar() {
		if (logar) {
			return "paginainicial";
		} else
			return "";
	}

	public boolean validarUsuario(){
		cliente = new Cliente();
		if ((login == null)) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro", "Acesso Negado."));
		} else {
			lead = leadDao.consultar("SELECT l FROM Lead l WHERE l.cliente.email like '%"+ login +"%'");
			if (lead == null) {
				Mensagem.lancarMensagemInfo("Atenção", "Seu questionário ainda não está liberado; Consulte seu consultor;");
			} else {
				if (lead.getCliente().isOnline()) {
					mensagemOla();
					cliente = lead.getCliente();
					return logar = true;
				}else {
					Mensagem.lancarMensagemInfo("Atenção", "Seu questionário ainda não está liberado; Consulte seu consultor;");
				}
			}
		}
		cliente = new Cliente();
		return logar = false;
	}

	public void erroLogin(String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(mensagem, ""));
	}
	

	public String deslogar() {
		Map<?, ?> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.clear();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		return "index";
	}

	public void mensagemOla(){
		mensagemOla = "Olá " + cliente.getNome();
	}

	public void removerAtributosSessao() {
		try {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);
			session.removeAttribute("acessounidade");
			session.removeAttribute("valoresacomodacao");
			session.removeAttribute("vendas");
			session.removeAttribute("listaArquivos");
			session.removeAttribute("adicionar");
			session.removeAttribute("alteracao");
			session.removeAttribute("alteracaofinanceiro");
			session.removeAttribute("ano");
			session.removeAttribute("arquivo1");
			session.removeAttribute("arquivo2");
			session.removeAttribute("arquivo3");
			session.removeAttribute("arquivo4");
			session.removeAttribute("arquivo5");
			session.removeAttribute("arquivoBean");
			session.removeAttribute("arquivosNovos");
			session.removeAttribute("aupair");
			session.removeAttribute("aviso");
			session.removeAttribute("avisousuario");
			session.removeAttribute("banco");
			session.removeAttribute("campo");
			session.removeAttribute("campoAlteracao");
			session.removeAttribute("cancelamento");
			session.removeAttribute("cartaocredito");
			session.removeAttribute("chamadaTela");
			session.removeAttribute("cliente");
			session.removeAttribute("cobranca");
			session.removeAttribute("coeficientejuros");
			session.removeAttribute("comissaocontrole");
			session.removeAttribute("competencia");
			session.removeAttribute("confirmar");
			session.removeAttribute("contapagar");
			session.removeAttribute("contareceber");
			session.removeAttribute("controle");
			session.removeAttribute("controlecurso");
			session.removeAttribute("controleseguro");
			session.removeAttribute("controlework");
			session.removeAttribute("controleworkentrevista");
			session.removeAttribute("controleworlembarque");
			session.removeAttribute("coprodutos");
			session.removeAttribute("credito");
			session.removeAttribute("crmcobranca");
			session.removeAttribute("crmcobrancahistorico");
			session.removeAttribute("curso");
			session.removeAttribute("cursospacote");
			session.removeAttribute("data");
			session.removeAttribute("dataFinal");
			session.removeAttribute("dataIni");
			session.removeAttribute("demipair");
			session.removeAttribute("departamento");
			session.removeAttribute("editar");
			session.removeAttribute("editarUsuario");
			session.removeAttribute("faseHe");
			session.removeAttribute("fatura");
			session.removeAttribute("feed");
			session.removeAttribute("filtrarEscolaBean");
			session.removeAttribute("filtroorcamentoproduto");
			session.removeAttribute("fornecedor");
			session.removeAttribute("fornecedorcidade");
			session.removeAttribute("fornecedorCidade");
			session.removeAttribute("fornecedorcidadeidioma");
			session.removeAttribute("fornecedorCidadeIdiomaProduto");
			session.removeAttribute("fornecedorcidadeidiomaproduto");
			session.removeAttribute("fornecedordocs");
			session.removeAttribute("fornecedorfinanceiro");
			session.removeAttribute("funcao");
			session.removeAttribute("grupoacesso");
			session.removeAttribute("guiaescola");
			session.removeAttribute("he");
			session.removeAttribute("highschool");
			session.removeAttribute("historico");
			session.removeAttribute("idcliente");
			session.removeAttribute("idioma");
			session.removeAttribute("idlead");
			session.removeAttribute("idunidade");
			session.removeAttribute("indiceLsita");
			session.removeAttribute("invoice");
			session.removeAttribute("lancamento");
			session.removeAttribute("lead");
			session.removeAttribute("linhacontrole");
			session.removeAttribute("listaAndamento");
			session.removeAttribute("listaArquivo");
			session.removeAttribute("listaArquivoVencidoBean");
			session.removeAttribute("listaAvisosArquivosNovos");
			session.removeAttribute("listaCancelada");
			session.removeAttribute("listaCancelamento");
			session.removeAttribute("listaCategoriaBean");
			session.removeAttribute("listaCidade");
			session.removeAttribute("listaCliente");
			session.removeAttribute("listaContas");
			session.removeAttribute("listaContasReceber");
			session.removeAttribute("listaControle");
			session.removeAttribute("listaCrmAtrasado");
			session.removeAttribute("listaCrmCobranca");
			session.removeAttribute("listaCrmCobrancaHoje");
			session.removeAttribute("listaCrmCobrancaNovos");
			session.removeAttribute("listaCrmCobrancaProx7");
			session.removeAttribute("listaCrmCobrancaTodos");
			session.removeAttribute("listaDadosEscolas");
			session.removeAttribute("listaDepartamentos");
			session.removeAttribute("listaDocs");
			session.removeAttribute("listaFinalizar");
			session.removeAttribute("listaFinanceiro");
			session.removeAttribute("listaFornecedorCidade");
			session.removeAttribute("listaHe");
			session.removeAttribute("listaInvoice");
			session.removeAttribute("listaLead");
			session.removeAttribute("listaleadtotal");
			session.removeAttribute("listaOcurso");
			session.removeAttribute("listaOpcionais");
			session.removeAttribute("listaOrcamento");
			session.removeAttribute("listaOrcamentoCurso");
			session.removeAttribute("listaOrcamentoVoluntariado");
			session.removeAttribute("listaPacotes");
			session.removeAttribute("listapais");
			session.removeAttribute("listaposvenda");
			session.removeAttribute("listaProcesso");
			session.removeAttribute("listaProductRunnersBean");
			session.removeAttribute("listaProdutos");
			session.removeAttribute("listarArquivos");
			session.removeAttribute("listaRelatorio");
			session.removeAttribute("listaSelecao");
			session.removeAttribute("listaSolicitacoes");
			session.removeAttribute("listaTabelaCidade");
			session.removeAttribute("listaTabelaPais");
			session.removeAttribute("listatipocontato");
			session.removeAttribute("listausuario");
			session.removeAttribute("listaVendaNova");
			session.removeAttribute("listaVendaPendente");
			session.removeAttribute("listaVendas");
			session.removeAttribute("listaVendasAndamento");
			session.removeAttribute("listaVendasCancelada");
			session.removeAttribute("listaVendasCursoAndamento");
			session.removeAttribute("listaVendasCursoCancelada");
			session.removeAttribute("listaVendasCursoFinalizada");
			session.removeAttribute("listaVendasCursoFinanceiro");
			session.removeAttribute("listaVendasCursoProcesso");
			session.removeAttribute("listaVendasFinalizada");
			session.removeAttribute("listaVendasFinanceiro");
			session.removeAttribute("listaVendasProcesso");
			session.removeAttribute("listaVersoes");
			session.removeAttribute("listaVoluntariadoProjeto");
			session.removeAttribute("mes");
			session.removeAttribute("metasfaturamentomensal");
			session.removeAttribute("modeloOrcamentoCurso");
			session.removeAttribute("motivocancelamento");
			session.removeAttribute("msgBilhete");
			session.removeAttribute("mtp");
			session.removeAttribute("nome");
			session.removeAttribute("nomeClientePacote");
			session.removeAttribute("nomePrograma");
			session.removeAttribute("novoCartao");
			session.removeAttribute("novoValor");
			session.removeAttribute("numero");
			session.removeAttribute("ocCliente");
			session.removeAttribute("ocurso");
			session.removeAttribute("ocursoferiado");
			session.removeAttribute("operacao");
			session.removeAttribute("orcamento");
			session.removeAttribute("orcamentoManual");
			session.removeAttribute("outroslancamentos");
			session.removeAttribute("pacote");
			session.removeAttribute("pacotesfornecedor");
			session.removeAttribute("pacoteTrecho");
			session.removeAttribute("pais");
			session.removeAttribute("palavrachave");
			session.removeAttribute("passagemaerea");
			session.removeAttribute("pasta");
			session.removeAttribute("pasta1");
			session.removeAttribute("pasta2");
			session.removeAttribute("pasta3");
			session.removeAttribute("pasta4");
			session.removeAttribute("pasta5");
			session.removeAttribute("pastavideo");
			session.removeAttribute("percentualcomissao");
			session.removeAttribute("pesquisar");
			session.removeAttribute("planoconta");
			session.removeAttribute("posicao");
			session.removeAttribute("produtoorcamentoindice");
			session.removeAttribute("produtos");
			session.removeAttribute("produtosTrainee");
			session.removeAttribute("programasTeens");
			session.removeAttribute("promocaoacomodacao");
			session.removeAttribute("promocaoAcomodacao");
			session.removeAttribute("promocaobrindecurso");
			session.removeAttribute("promocaocurso");
			session.removeAttribute("promocaotaxacurso");
			session.removeAttribute("questionariohe");
			session.removeAttribute("recinternacional");
			session.removeAttribute("redirecionar");
			session.removeAttribute("regraVenda");
			session.removeAttribute("resultadoOracmentoBean");
			session.removeAttribute("seguro");
			session.removeAttribute("seguroViagem");
			session.removeAttribute("sql");
			session.removeAttribute("sqlContasReceber");
			session.removeAttribute("tarifa");
			session.removeAttribute("telaRetorno");
			session.removeAttribute("terceiros");
			session.removeAttribute("texto");
			session.removeAttribute("tipo");
			session.removeAttribute("tipo2");
			session.removeAttribute("tipomidias");
			session.removeAttribute("tipoorcamento");
			session.removeAttribute("tipoproduto");
			session.removeAttribute("tipoVenda");
			session.removeAttribute("tisolicitacoeshistorico");
			session.removeAttribute("tisolicitaoes");
			session.removeAttribute("tituloPagina");
			session.removeAttribute("tmstar");
			session.removeAttribute("total");
			session.removeAttribute("traducaojuramentada");
			session.removeAttribute("trainee");
			session.removeAttribute("transacaoBean");
			session.removeAttribute("treinamentorespostas");
			session.removeAttribute("turismo");
			session.removeAttribute("unidade");
			session.removeAttribute("urlDocs");
			session.removeAttribute("valor");
			session.removeAttribute("valorCambio");
			session.removeAttribute("valorcampo");
			session.removeAttribute("valorcoprodutos");
			session.removeAttribute("valorEntrada");
			session.removeAttribute("valorestrainee");
			session.removeAttribute("valorJuros");
			session.removeAttribute("valorOriginal");
			session.removeAttribute("venda");
			session.removeAttribute("venda1");
			session.removeAttribute("vendacomissao");
			session.removeAttribute("vendaMatriz");
			session.removeAttribute("vendamotivopendencia");
			session.removeAttribute("vendascomissao");
			session.removeAttribute("video");
			session.removeAttribute("video1");
			session.removeAttribute("videopasta1");
			session.removeAttribute("videopasta2");
			session.removeAttribute("videopasta3");
			session.removeAttribute("videopasta4");
			session.removeAttribute("videopasta5");
			session.removeAttribute("vistos");
			session.removeAttribute("voltar");
			session.removeAttribute("voltarControleVendas");
			session.removeAttribute("voltarPagina");
			session.removeAttribute("voluntariado");
			session.removeAttribute("voluntariadopacote");
			session.removeAttribute("voluntariadoprojeto");
			session.removeAttribute("voluntariadoprojetovalor");
			session.removeAttribute("work");
			session.removeAttribute("workempregador");
			session.removeAttribute("worksponsor");
			session.removeAttribute("worktravel");
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
