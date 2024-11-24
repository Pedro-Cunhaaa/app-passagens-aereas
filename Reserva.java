package A3;

public class Reserva {
    private String codigoReserva;
    private Cliente cliente;
    private Passagem passagem;
    private Pagamento pagamento;

    public Reserva(String codigoReserva, Cliente cliente, Passagem passagem, Pagamento pagamento) {
        this.codigoReserva = codigoReserva;
        this.cliente = cliente;
        this.passagem = passagem;
        this.pagamento = pagamento;
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Passagem getPassagem() {
        return passagem;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }
}
