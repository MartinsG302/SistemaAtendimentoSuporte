package lista;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ListaMensagens {
	private NoMensagem primeiro;
	private NoMensagem ultimo;
	private int proximoId = 1;
	private int tamanho = 0;

	public Mensagem adicionarMensagem(int idTicket, String autor, String texto) {
		String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		Mensagem m = new Mensagem(proximoId, idTicket, autor, texto, dataHora);
		proximoId++;
		NoMensagem n = new NoMensagem(m);
		if (primeiro == null) {
			primeiro = n;
			ultimo = n;
		} else {
			ultimo.proximo = n;
			ultimo = n;
		}
		tamanho++;
		return m;
	}

	public void listarTodas() {
		if (primeiro == null) {
			System.out.println("Nenhuma mensagem registrada.");
			return;
		}
		NoMensagem item = primeiro;
		while (item != null) {
			System.out.println(item.mensagem);
			item = item.proximo;
		}
	}

	public void listarPorTicket(int idTicket) {
		boolean achou = false;
		NoMensagem item = primeiro;
		while (item != null) {
			if (item.mensagem.getIdTicket() == idTicket) {
				System.out.println(item.mensagem);
				achou = true;
			}
			item = item.proximo;
		}
		if (!achou) {
			System.out.println("Nenhuma mensagem para o ticket " + idTicket + ".");
		}
	}

	public Mensagem buscarMensagem(int id) {
		NoMensagem item = primeiro;
		while (item != null) {
			if (item.mensagem.getId() == id) {
				return item.mensagem;
			}
			item = item.proximo;
		}
		return null;
	}

	public boolean excluirMensagem(int id) {
		NoMensagem item = primeiro;
		NoMensagem anterior = null;
		while (item != null) {
			if (item.mensagem.getId() == id) {
				if (item == primeiro) {
					primeiro = item.proximo;
					if (primeiro == null) {
						ultimo = null;
					}
				} else {
					anterior.proximo = item.proximo;
					if (item == ultimo) {
						ultimo = anterior;
					}
				}
				tamanho--;
				return true;
			}
			anterior = item;
			item = item.proximo;
		}
		return false;
	}

	public int contarPorTicket(int idTicket) {
		int total = 0;
		NoMensagem item = primeiro;
		while (item != null) {
			if (item.mensagem.getIdTicket() == idTicket) {
				total++;
			}
			item = item.proximo;
		}
		return total;
	}

	public boolean estaVazia() {
		return primeiro == null;
	}

	public int getTamanho() {
		return tamanho;
	}
}