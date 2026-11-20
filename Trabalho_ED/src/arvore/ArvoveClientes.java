
package arvore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ArvoreClientes {
    private NoArvore raiz;
    private int tamanho = 0;
    private boolean removeu = false;

    public ArvoreClientes() {
        this.raiz = null;
    }

    public static String normalizarDocumento(String documento) {
        if (documento == null) {
            return null;
        }
        return documento.trim().replace(".", "").replace("-", "").replace("/", "");
    }

    private boolean documentoValido(String documento) {
        if (documento == null) {
            return false;
        }
        if (documento.length() != 11 && documento.length() != 14) {
            return false;
        }
        for (int i = 0; i < documento.length(); i++) {
            char c = documento.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public Cliente cadastrar(String documento, String nome) {
        String doc = normalizarDocumento(documento);
        if (!documentoValido(doc)) {
            System.out.println("Documento invalido. Informe um CPF (11 digitos) ou CNPJ (14 digitos).");
            return null;
        }
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Nome do cliente invalido.");
            return null;
        }
        String dataContrato = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Cliente cliente = new Cliente(doc, nome.trim(), dataContrato);
        if (!inserir(cliente)) {
            return null;
        }
        return cliente;
    }

    public boolean inserir(Cliente cliente) {
        if (cliente == null) {
            System.out.println("Cliente.java invalido.");
            return false;
        }
        NoArvore novo = new NoArvore(cliente);
        if (raiz == null) {
            raiz = novo;
            tamanho++;
            return true;
        }

        NoArvore atual = raiz;
        while (true) {
            int cmp = cliente.getDocumento().compareTo(atual.cliente.getDocumento());
            if (cmp == 0) {
                System.out.println("Ja existe um cliente com o documento " + cliente.getDocumento() + ".");
                return false;
            }
            if (cmp < 0) {
                if (atual.esquerda == null) {
                    atual.esquerda = novo;
                    break;
                }
                atual = atual.esquerda;
            } else {
                if (atual.direita == null) {
                    atual.direita = novo;
                    break;
                }
                atual = atual.direita;
            }
        }
        tamanho++;
        return true;
    }

    public Cliente buscar(String documento) {
        String doc = normalizarDocumento(documento);
        if (doc == null) {
            return null;
        }
        NoArvore atual = raiz;
        while (atual != null) {
            int cmp = doc.compareTo(atual.cliente.getDocumento());
            if (cmp == 0) {
                return atual.cliente;
            }
            if (cmp < 0) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }
        return null;
    }

    public boolean temContrato(String documento) {
        return buscar(documento) != null;
    }

    public boolean remover(String documento) {
        if (raiz == null) {
            System.out.println("A arvore esta vazia!");
            return false;
        }
        String doc = normalizarDocumento(documento);
        if (doc == null || doc.isEmpty()) {
            System.out.println("Documento invalido.");
            return false;
        }
        removeu = false;
        raiz = removerNo(raiz, doc);
        if (!removeu) {
            System.out.println("Cliente.java " + doc + " nao encontrado.");
            return false;
        }
        tamanho--;
        return true;
    }

    private NoArvore removerNo(NoArvore no, String doc) {
        if (no == null) {
            return null;
        }
        int cmp = doc.compareTo(no.cliente.getDocumento());
        if (cmp < 0) {
            no.esquerda = removerNo(no.esquerda, doc);
        } else if (cmp > 0) {
            no.direita = removerNo(no.direita, doc);
        } else {
            removeu = true;
            if (no.esquerda == null) {
                return no.direita;
            }
            if (no.direita == null) {
                return no.esquerda;
            }
            NoArvore sucessor = no.direita;
            while (sucessor.esquerda != null) {
                sucessor = sucessor.esquerda;
            }
            no.cliente = sucessor.cliente;
            no.direita = removerNo(no.direita, sucessor.cliente.getDocumento());
        }
        return no;
    }

    public Cliente menor() {
        if (raiz == null) {
            System.out.println("A arvore esta vazia!");
            return null;
        }
        NoArvore atual = raiz;
        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }
        return atual.cliente;
    }

    public Cliente maior() {
        if (raiz == null) {
            System.out.println("A arvore esta vazia!");
            return null;
        }
        NoArvore atual = raiz;
        while (atual.direita != null) {
            atual = atual.direita;
        }
        return atual.cliente;
    }

    public void listarEmOrdem() {
        if (raiz == null) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        emOrdem(raiz);
    }

    private void emOrdem(NoArvore no) {
        if (no == null) {
            return;
        }
        emOrdem(no.esquerda);
        System.out.println(no.cliente);
        emOrdem(no.direita);
    }

    public void listarPreOrdem() {
        if (raiz == null) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        preOrdem(raiz);
    }

    private void preOrdem(NoArvore no) {
        if (no == null) {
            return;
        }
        System.out.println(no.cliente);
        preOrdem(no.esquerda);
        preOrdem(no.direita);
    }

    public void listarPosOrdem() {
        if (raiz == null) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        posOrdem(raiz);
    }

    private void posOrdem(NoArvore no) {
        if (no == null) {
            return;
        }
        posOrdem(no.esquerda);
        posOrdem(no.direita);
        System.out.println(no.cliente);
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(NoArvore no) {
        if (no == null) {
            return 0;
        }
        int e = altura(no.esquerda);
        int d = altura(no.direita);
        if (e > d) {
            return e + 1;
        }
        return d + 1;
    }

    public boolean estaVazia() {
        return raiz == null;
    }

    public int getTamanho() {
        return tamanho;
    }
}
