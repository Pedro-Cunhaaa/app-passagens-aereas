package A3;

public class Passagem {
    private String codigo;
    private String origem;
    private String destino;
    private String dataHoraPartida;
    private String dataHoraChegada;
    private double preco;

    public Passagem(String codigo, String origem, String destino, String dataHoraPartida, String dataHoraChegada, double preco) {
        this.codigo = codigo;
        this.origem = origem;
        this.destino = destino;
        this.dataHoraPartida = dataHoraPartida;
        this.dataHoraChegada = dataHoraChegada;
        this.preco = preco;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public String getDataHoraPartida() {
        return dataHoraPartida;
    }

    public String getDataHoraChegada() {
        return dataHoraChegada;
    }

    public double getPreco() {
        return preco;
    }
}
