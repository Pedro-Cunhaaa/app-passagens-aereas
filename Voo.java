package A3;

import java.util.ArrayList;
import java.util.List;

public class Voo {
    private String codigoVoo;
    private String companhiaAerea;
    private String aeronave;
    private int capacidade;
    private List<Passagem> passagensDisponiveis;

    public Voo(String codigoVoo, String companhiaAerea, String aeronave, int capacidade) {
        this.codigoVoo = codigoVoo;
        this.companhiaAerea = companhiaAerea;
        this.aeronave = aeronave;
        this.capacidade = capacidade;
        this.passagensDisponiveis = new ArrayList<>();
    }

    public void adicionarPassagem(Passagem passagem) {
        if (passagensDisponiveis.size() < capacidade) {
            passagensDisponiveis.add(passagem);
        }
    }

    public List<Passagem> getPassagensDisponiveis() {
        return passagensDisponiveis;
    }
}
