package A3;

public class PagamentoCredito implements Pagamento {
    private double valor;
    private String numeroCartao;
    private String nomeTitular;
    private String validade;
    private String codigoSeguranca;

    public PagamentoCredito(double valor, String numeroCartao, String nomeTitular, String validade, String codigoSeguranca) {
        this.valor = valor;
        this.numeroCartao = numeroCartao;
        this.nomeTitular = nomeTitular;
        this.validade = validade;
        this.codigoSeguranca = codigoSeguranca;
    }

    @Override
    public boolean processarPagamento() {
        System.out.println("Pagamento via cartão de crédito no valor de R$" + valor + " processado com sucesso.");
        return true;
    }
}
