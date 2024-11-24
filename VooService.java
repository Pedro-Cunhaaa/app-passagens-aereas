package A3;

import java.util.ArrayList;
import java.util.List;

public class VooService {
    private final List<Passagem> passagens;

    public VooService() {
        passagens = new ArrayList<>();
        carregarDados();
    }

    private void carregarDados() {
        passagens.add(new Passagem("PA001", "São Paulo", "Rio de Janeiro", "08:00 01-12-2024", "10:00 01-12-2024", 300.0));
        passagens.add(new Passagem("PA002", "São Paulo", "Belo Horizonte", "09:00 02-12-2024", "11:30 02-12-2024", 350.0));
        passagens.add(new Passagem("PA003", "Rio de Janeiro", "Brasília", "14:00 03-12-2024", "16:00 03-12-2024", 400.0));
        passagens.add(new Passagem("PA001", "Salvador", "Fortaleza", "10:00 05-12-2024", "12:30 05-12-2024", 250.00));
        passagens.add(new Passagem("PA002", "Curitiba", "Porto Alegre", "14:00 06-12-2024", "15:30 06-12-2024", 180.00));
        passagens.add(new Passagem("PA003", "Recife", "Natal", "09:00 07-12-2024", "10:00 07-12-2024", 150.00));
        passagens.add(new Passagem("PA004", "Manaus", "Belém", "13:00 08-12-2024", "15:30 08-12-2024", 320.00));
        passagens.add(new Passagem("PA005", "Brasília", "Goiânia", "07:30 09-12-2024", "08:20 09-12-2024", 120.00));
        passagens.add(new Passagem("PA006", "São Paulo", "Salvador", "16:00 10-12-2024", "18:30 10-12-2024", 400.00));
        passagens.add(new Passagem("PA007", "Rio de Janeiro", "Curitiba", "19:00 11-12-2024", "20:30 11-12-2024", 250.00));
        passagens.add(new Passagem("PA008", "Fortaleza", "Recife", "06:00 12-12-2024", "07:30 12-12-2024", 200.00));
        passagens.add(new Passagem("PA009", "Belo Horizonte", "São Paulo", "12:00 13-12-2024", "13:30 13-12-2024", 180.00));
        passagens.add(new Passagem("PA010", "Porto Alegre", "Florianópolis", "10:00 14-12-2024", "11:20 14-12-2024", 150.00));
    }

    public void mostrarVoosDisponiveis() {
        for (int i = 0; i < passagens.size(); i++) {
            Passagem passagem = passagens.get(i);
            System.out.println((i + 1) + ". " + passagem.getOrigem() + " -> " + passagem.getDestino()
                    + " | Partida: " + passagem.getDataHoraPartida() + " | Chegada: " + passagem.getDataHoraChegada()
                    + " | Preço: R$" + passagem.getPreco());
        }
    }

    public Passagem getPassagemPorIndice(int indice) {
        if (indice >= 0 && indice < passagens.size()) {
            return passagens.get(indice);
        }
        return null;
    }
}
