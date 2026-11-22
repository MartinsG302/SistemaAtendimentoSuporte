package sistema;

import arvore.ArvoreClientes;
import arvore.Cliente;
import fila.Fila;
import fila.Ticket;
import lista.ListaMensagens;
import java.util.Scanner;

public class Main {
	static ArvoreClientes clientes = new ArvoreClientes();
	static Fila fila = new Fila();
	static ListaMensagens mensagens = new ListaMensagens();
	static Scanner entrada = new Scanner(System.in);

	public static void main(String[] args) {
		int opcao = -1;
		while (opcao != 0) {
			mostrarOpcoes();
			opcao = lerInt("Escolha: ");
			System.out.println();
			switch (opcao) {
				case 1:
					cadastrarCliente();
					break;
				case 2:
					buscarCliente();
					break;
				case 3:
					removerCliente();
					break;
				case 4:
					listarClientes();
					break;
				case 5:
					abrirTicket();
					break;
				case 6:
					Ticket proximo = fila.consultarProximo();
					if (proximo != null) {
						System.out.println("Proximo da fila: " + proximo);
					}
					break;
				case 7:
					fila.listar();
					break;
				case 8:
					fila.listarPorCliente(lerDocumento());
					break;
				case 9:
					atenderProximo();
					break;
				case 10:
					adicionarMensagem();
					break;
				case 11:
					mensagens.listarPorTicket(lerInt("Id do ticket: "));
					break;
				case 12:
					if (fila.remover(lerInt("Id do ticket: "))) {
						System.out.println("Ticket cancelado.");
					}
					break;
				case 13:
					carregarExemplo();
					break;
				case 0:
					System.out.println("Encerrando o sistema.");
					break;
				default:
					System.out.println("Opcao invalida.");
			}
		}
	}

	static void mostrarOpcoes() {
		System.out.println();
		System.out.println("===== SISTEMA DE ATENDIMENTO E SUPORTE =====");
		System.out.println("1  - Cadastrar cliente");
		System.out.println("2  - Buscar cliente / consultar contrato");
		System.out.println("3  - Remover cliente");
		System.out.println("4  - Listar clientes");
		System.out.println("5  - Abrir ticket");
		System.out.println("6  - Ver proximo ticket da fila");
		System.out.println("7  - Listar tickets pendentes");
		System.out.println("8  - Listar tickets pendentes de um cliente");
		System.out.println("9  - Atender proximo ticket");
		System.out.println("10 - Adicionar mensagem a um ticket pendente");
		System.out.println("11 - Ver historico de mensagens de um ticket");
		System.out.println("12 - Cancelar ticket");
		System.out.println("13 - Carregar clientes de exemplo");
		System.out.println("0  - Sair");
	}

	static void cadastrarCliente() {
		String documento = lerTexto("CPF ou CNPJ: ");
		String nome = lerTexto("Nome: ");
		Cliente cliente = clientes.cadastrar(documento, nome);
		if (cliente != null) {
			System.out.println("Cadastrado: " + cliente);
		}
	}

	static void buscarCliente() {
		Cliente cliente = clientes.buscar(lerDocumento());
		if (cliente == null) {
			System.out.println("Cliente não encontrado.");
		} else {
			System.out.println(cliente);
		}
	}

	static void removerCliente() {
		String documento = lerDocumento();
		if (clientes.buscar(documento) == null) {
			System.out.println("Cliente não encontrado.");
		} else if (fila.contarPorCliente(documento) > 0) {
			System.out.println("Cliente tem tickets pendentes. Atenda ou cancele antes de remover.");
		} else if (clientes.remover(documento)) {
			System.out.println("Cliente removido.");
		}
	}

	static void listarClientes() {
		System.out.println("1 - Em ordem | 2 - Pre-ordem | 3 - Pos-ordem");
		int ordem = lerInt("Ordem: ");
		if (ordem == 1) {
			clientes.listarEmOrdem();
		} else if (ordem == 2) {
			clientes.listarPreOrdem();
		} else if (ordem == 3) {
			clientes.listarPosOrdem();
		} else {
			System.out.println("Ordem invalida.");
		}
	}

	static void abrirTicket() {
		String documento = lerDocumento();
		Cliente cliente = clientes.buscar(documento);
		if (cliente == null) {
			System.out.println("Cliente nao cadastrado. Cadastre antes de abrir o ticket.");
			return;
		}
		String descricao = lerTexto("Descreva o problema: ");
		Ticket ticket = fila.adicionarTicket(documento, descricao);
		if (ticket != null) {
			mensagens.adicionarMensagem(ticket.getId(), cliente.getNome(), ticket.getDescricao());
			System.out.println("Ticket aberto: " + ticket);
		}
	}

	static void atenderProximo() {
		Ticket ticket = fila.desenfileirar();
		if (ticket == null) {
			return;
		}
		System.out.println("Atendendo: " + ticket);
		System.out.println("Historico ate agora:");
		mensagens.listarPorTicket(ticket.getId());
		System.out.println("Digite as mensagens do tecnico. Enter vazio encerra o atendimento.");
		String texto = lerTexto("Tecnico: ");
		while (!texto.isEmpty()) {
			mensagens.adicionarMensagem(ticket.getId(), "Tecnico", texto);
			texto = lerTexto("Tecnico: ");
		}
		System.out.println("Atendimento do ticket " + ticket.getId() + " encerrado.");
	}

	static void adicionarMensagem() {
		int id = lerInt("Id do ticket: ");
		String autor = lerTexto("Autor: ");
		String texto = lerTexto("Mensagem: ");
		if (fila.buscar(id) == null) {
			System.out.println("Ticket nao esta pendente na fila.");
		} else if (autor.isEmpty() || texto.isEmpty()) {
			System.out.println("Autor e mensagem sao obrigatorios.");
		} else {
			mensagens.adicionarMensagem(id, autor, texto);
			System.out.println("Mensagem adicionada.");
		}
	}

	static void carregarExemplo() {
		clientes.cadastrar("02395418245", "Mercado central");
		clientes.cadastrar("34856719282", "Padaria sol");
		clientes.cadastrar("96840201285", "Oficina silva");
		clientes.cadastrar("12345678000190", "Escola Aurora");
		clientes.cadastrar("63070652412", "Clinica Vida");
		System.out.println("Clientes de exemplo cadastrados.");
	}

	static String lerDocumento() {
		return ArvoreClientes.normalizarDocumento(lerTexto("CPF ou CNPJ: "));
	}

	static String lerTexto(String pergunta) {
		System.out.print(pergunta);
		return entrada.nextLine().trim();
	}

	static int lerInt(String pergunta) {
		while (true) {
			try {
				return Integer.parseInt(lerTexto(pergunta));
			} catch (NumberFormatException e) {
				System.out.println("Digite um numero inteiro.");
			}
		}
	}
}