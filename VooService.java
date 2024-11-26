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
        passagens.add(new Passagem("PA001", "São Paulo", "Rio de Janeiro", "08:00 11-12-2024", "10:00 11-12-2024", 300.0));
        passagens.add(new Passagem("PA002", "São Paulo", "Belo Horizonte", "09:00 12-12-2024", "11:30 12-12-2024", 350.0));
        passagens.add(new Passagem("PA003", "Rio de Janeiro", "Brasília", "14:00 13-12-2024", "16:00 13-12-2024", 400.0));
        passagens.add(new Passagem("PA004", "Salvador", "Fortaleza", "10:00 15-12-2024", "12:30 15-12-2024", 250.00));
        passagens.add(new Passagem("PA005", "Curitiba", "Porto Alegre", "14:00 16-12-2024", "15:30 16-12-2024", 180.00));
        passagens.add(new Passagem("PA006", "Recife", "Natal", "09:00 17-12-2024", "10:00 17-12-2024", 150.00));
        passagens.add(new Passagem("PA007", "Manaus", "Belém", "13:00 18-12-2024", "15:30 18-12-2024", 320.00));
        passagens.add(new Passagem("PA008", "Brasília", "Goiânia", "07:30 19-12-2024", "08:20 19-12-2024", 120.00));
        passagens.add(new Passagem("PA009", "São Paulo", "Salvador", "16:00 20-12-2024", "18:30 20-12-2024", 400.00));
        passagens.add(new Passagem("PA010", "Rio de Janeiro", "Curitiba", "19:00 21-12-2024", "20:30 21-12-2024", 250.00));
        passagens.add(new Passagem("PA011", "Fortaleza", "Recife", "06:00 22-12-2024", "07:30 22-12-2024", 200.00));
        passagens.add(new Passagem("PA012", "Belo Horizonte", "São Paulo", "12:00 23-12-2024", "13:30 23-12-2024", 180.00));
        passagens.add(new Passagem("PA013", "Porto Alegre", "Florianópolis", "10:00 24-12-2024", "11:20 24-12-2024", 150.00));
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
