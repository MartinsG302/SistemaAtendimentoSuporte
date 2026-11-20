package fila;

public class NoFila<T> {
	T dado;
	NoFila<T> proximo;

	public NoFila(T dado) {
		this.dado = dado;
		this.proximo = null;
	}
}