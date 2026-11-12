package lista;

public class Mensagem {
	private int id;
	private int idTicket;
	private String autor;
	private String texto;
	private String dataHora;

	public Mensagem(int id, int idTicket, String autor, String texto, String dataHora) {
		this.id = id;
		this.idTicket = idTicket;
		this.autor = autor;
		this.texto = texto;
		this.dataHora = dataHora;
	}

	public int getId() {
		return id;
	}

	public int getIdTicket() {
		return idTicket;
	}

	public String getAutor() {
		return autor;
	}

	public String getTexto() {
		return texto;
	}

	public String getDataHora() {
		return dataHora;
	}

	public String toString() {
		return "[" + dataHora + "] Ticket " + idTicket + " | Msg " + id + " | " + autor + ": " + texto;
	}
}