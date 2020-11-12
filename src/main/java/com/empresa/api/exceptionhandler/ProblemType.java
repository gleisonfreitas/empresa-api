package com.empresa.api.exceptionhandler;

public enum ProblemType {
	
	DADOS_INVALIDOS("/dados-invalidos", "Dados Inválidos"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de Sistema"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	MENSAGEM_INCOMPREENSIVEL("/mesagem-incompreensivel", "Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	NEGOCIO_EXCEPTION("/negocio-exception", "Negócio exception");
	
	private String uri;
	private String title;
	
	private ProblemType(String path, String title) {
		this.uri = "https://empresa.api.com.br" + path;
		this.title = title;
	}
	
	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}



	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}




}
