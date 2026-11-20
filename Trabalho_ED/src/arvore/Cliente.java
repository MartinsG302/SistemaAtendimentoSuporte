
package arvore;

public class Cliente {
    private String documento;
    private String nome;
    private String dataContrato;

    public Cliente(String documento, String nome, String dataContrato) {
        this.documento = documento;
        this.nome = nome;
        this.dataContrato = dataContrato;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getDataContrato() {
        return dataContrato;
    }

    public String getTipoDocumento() {
        if (documento.length() == 11) {
            return "CPF";
        }
        return "CNPJ";
    }

    public String toString() {
        return "Cliente " + nome + " | " + getTipoDocumento() + " " + documento + " | contrato desde " + dataContrato;
    }
}
