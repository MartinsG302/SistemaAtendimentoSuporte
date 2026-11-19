package sistema;

import lista.ListaMensagens;
import lista.Mensagem;

public class Main {
	public static void main(String[] args) {
		ListaMensagens lista = new ListaMensagens();
		
		System.out.println("Lista vazia: " + lista.estaVazia());
		lista.listarTodas();

		lista.adicionarMensagem(1, "Cliente", "O sistema esta fora do ar");
		lista.adicionarMensagem(2, "Cliente", "Duvida sobre fatura");
		lista.adicionarMensagem(1, "Tecnico", "Estou verificando o problema");
		lista.adicionarMensagem(1, "Cliente", "Obrigado");

		System.out.println("\nTodas as mensagens:");
		lista.listarTodas();

		System.out.println("\nMensagens do ticket 1:");
		lista.listarPorTicket(1);

		System.out.println("\nMensagens do ticket 9:");
		lista.listarPorTicket(9);

		System.out.println("\nTotal ticket 1: " + lista.contarPorTicket(1));

		Mensagem m = lista.buscarMensagem(3);
		System.out.println("\nBusca id 3: " + m);
		System.out.println("Busca id 99: " + lista.buscarMensagem(99));

		System.out.println("\nExcluir primeiro (id 1): " + lista.excluirMensagem(1));
		System.out.println("Excluir meio (id 3): " + lista.excluirMensagem(3));
		System.out.println("Excluir ultimo (id 4): " + lista.excluirMensagem(4));
		System.out.println("Excluir inexistente (id 99): " + lista.excluirMensagem(99));

		System.out.println("\nApos exclusoes:");
		lista.listarTodas();
		System.out.println("Tamanho: " + lista.getTamanho());

		lista.excluirMensagem(2);
		System.out.println("\nLista vazia: " + lista.estaVazia());
		lista.adicionarMensagem(5, "Tecnico", "Nova mensagem apos esvaziar");
		lista.listarTodas();
		
	}
}