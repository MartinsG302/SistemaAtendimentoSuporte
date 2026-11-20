package fila;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Fila {
	private NoFila<Ticket> inicio;
	private NoFila<Ticket> fim;
	private int proximoId = 1;
	private int tamanho = 0;

	public Fila() {
		this.inicio = null;
		this.fim = null;
	}

	public Ticket adicionarTicket(String documentoCliente, String descricao) {
		if (documentoCliente == null || documentoCliente.trim().isEmpty()) {
			System.out.println("Documento do cliente invalido.");
			return null;
		}
		if (descricao == null || descricao.trim().isEmpty()) {
			System.out.println("Descricao do ticket invalida.");
			return null;
		}
		String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		Ticket ticket = new Ticket(proximoId, documentoCliente.trim(), descricao.trim(), dataHora);
		proximoId++;
		
		if (!enfileirar(ticket)) {
			return null;
		}
		return ticket;
	}

	public boolean enfileirar(Ticket ticket) {
		if (ticket == null) {
			System.out.println("Ticket invalido.");
			return false;
		}
		if (buscar(ticket.getId()) != null) {
			System.out.println("Ja existe um ticket com o id " + ticket.getId() + " na fila.");
			return false;
		}
		NoFila<Ticket> novo = new NoFila<>(ticket);
		if (fim == null) {
			inicio = novo;
			fim = novo;
		} else {
			fim.proximo = novo;
			fim = novo;
		}
		tamanho++;
		return true;
	}

	public Ticket desenfileirar() {
		if (inicio == null) {
			System.out.println("A fila esta vazia!");
			return null;
		}
		
		Ticket removido = inicio.dado;
		inicio = inicio.proximo;
		if (inicio == null) {
			fim = null;
		}
		tamanho--;
		return removido;
	}

	public Ticket consultarProximo() {
		if (inicio == null) {
			System.out.println("A fila esta vazia!");
			return null;
		}
		return inicio.dado;
	}

	public Ticket buscar(int id) {
		NoFila<Ticket> atual = inicio;
		while (atual != null) {
			if (atual.dado.getId() == id) {
				return atual.dado;
			}
			atual = atual.proximo;
		}
		return null;
	}

	public boolean remover(int id) {
		if (inicio == null) {
			System.out.println("A fila esta vazia!");
			return false;
		}
		
		NoFila<Ticket> atual = inicio;
		NoFila<Ticket> anterior = null;
		
		while (atual != null) {
			if (atual.dado.getId() == id) {
				if (atual == inicio) {
					inicio = atual.proximo;
					if (inicio == null) {
						fim = null;
					}
				} else {
					anterior.proximo = atual.proximo;
					if (atual == fim) {
						fim = anterior;
					}
				}
				
				tamanho--;
				return true;
				
			}
			anterior = atual;
			atual = atual.proximo;
		}
		System.out.println("Ticket " + id + " nao encontrado na fila.");
		return false;
	}

	public void listar() {
		if (inicio == null) {
			System.out.println("Nenhum ticket pendente.");
			return;
		}
		int posicao = 1;
		NoFila<Ticket> atual = inicio;
		while (atual != null) {
			System.out.println(posicao + " - " + atual.dado);
			posicao++;
			atual = atual.proximo;
		}
	}

	public void listarPorCliente(String documentoCliente) {
		if (documentoCliente == null || documentoCliente.trim().isEmpty()) {
			System.out.println("Documento do cliente invalido.");
			return;
		}
		boolean achou = false;
		NoFila<Ticket> atual = inicio;
		while (atual != null) {
			if (atual.dado.getDocumentoCliente().equals(documentoCliente.trim())) {
				System.out.println(atual.dado);
				achou = true;
			}
			atual = atual.proximo;
		}
		if (!achou) {
			System.out.println("Nenhum ticket pendente para o cliente " + documentoCliente + ".");
		}
	}

	public int contarPorCliente(String documentoCliente) {
		if (documentoCliente == null) {
			return 0;
		}
		int total = 0;
		NoFila<Ticket> atual = inicio;
		while (atual != null) {
			if (atual.dado.getDocumentoCliente().equals(documentoCliente.trim())) {
				total++;
			}
			atual = atual.proximo;
		}
		return total;
	}

	public boolean temPendentes() {
		return inicio != null;
	}

	public boolean estaVazia() {
		return inicio == null;
	}

	public int getTamanho() {
		return tamanho;
	}
}