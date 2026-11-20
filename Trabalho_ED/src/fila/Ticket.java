package fila;

public class Ticket {
	private int id;
	private String documentoCliente;
	private String descricao;
	private String dataHora;

	public Ticket(int id, String documentoCliente, String descricao, String dataHora) {
		this.id = id;
		this.documentoCliente = documentoCliente;
		this.descricao = descricao;
		this.dataHora = dataHora;
	}

	public int getId() {
		return id;
	}

	public String getDocumentoCliente() {
		return documentoCliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getDataHora() {
		return dataHora;
	}

	public String toString() {
		return "Ticket " + id + " | Cliente " + documentoCliente + " | " + descricao + " | aberto em " + dataHora;
	}
}