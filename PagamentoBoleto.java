package A3;

public class PagamentoBoleto implements Pagamento {
    private double valor;
    private String codigoBarras;

    public PagamentoBoleto(double valor) {
        this.valor = valor;
        this.codigoBarras = gerarCodigoBarras();
    }

    private String gerarCodigoBarras() {
        return "12345678900000000000" + System.currentTimeMillis();
    }

    @Override
    public boolean processarPagamento() {
        System.out.println("Pagamento via boleto no valor de R$" + valor + " processado com sucesso.");
        return true;
    }
}
