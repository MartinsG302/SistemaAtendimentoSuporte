
package arvore;

public class NoArvore {
    public Cliente cliente;
    public NoArvore esquerda;
    public NoArvore direita;

    public NoArvore(Cliente cliente) {
        this.cliente = cliente;
        this.esquerda = null;
        this.direita = null;
    }
}
