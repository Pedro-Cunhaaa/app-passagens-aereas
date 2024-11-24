package A3;

public class PagamentoService {
    public boolean processarPagamento(Pagamento pagamento) {
        if (pagamento == null) {
            System.out.println("Erro no processamento do pagamento.");
            return false;
        }
        return pagamento.processarPagamento();
    }
}
