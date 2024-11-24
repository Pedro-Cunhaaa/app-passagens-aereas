package A3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);

    private VooService vooService = new VooService();
    private PagamentoService pagamentoService = new PagamentoService();
    private double saldoUsuario; // Saldo informado pelo usuário
    private List<Passagem> passagensCompradas = new ArrayList<>(); // Lista de passagens compradas

    public void mostrarMenuPrincipal() {
        System.out.println("=== Bem-vindo ao Sistema de Compra de Passagens ===");
        System.out.println("1. Iniciar Compra");
        System.out.println("2. Sair");

        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer do scanner

        switch (opcao) {
            case 1:
                iniciarCompra();
                mostrarMenuPrincipal(); // Retorna ao menu após a compra
                break;
            case 2:
                exibirResumo();
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
                mostrarMenuPrincipal();
        }
    }

    private void iniciarCompra() {
        System.out.println("Informe seus dados para continuar.");
        System.out.println("Nome:");
        String nome = scanner.nextLine();

        System.out.println("Telefone:");
        String telefone = scanner.nextLine();

        System.out.println("CPF ou CNPJ:");
        String cpfOuCnpj = scanner.nextLine();

        System.out.println("Informe o valor disponível em sua conta (em reais):");
        saldoUsuario = scanner.nextDouble();
        scanner.nextLine(); // Limpa o buffer

        Cliente cliente = new Cliente(nome, telefone, cpfOuCnpj);

        boolean desejaComprarMais = true;

        while (desejaComprarMais) {
            System.out.println("=== Voos Disponíveis ===");
            vooService.mostrarVoosDisponiveis();

            System.out.println("Selecione o número do voo desejado:");
            int escolha = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            Passagem passagemEscolhida = vooService.getPassagemPorIndice(escolha - 1);
            if (passagemEscolhida != null) {
                if (passagemEscolhida.getPreco() > saldoUsuario) {
                    System.out.println("Saldo insuficiente para realizar a compra desta passagem.");
                    System.out.println("1. Escolher outra passagem");
                    System.out.println("2. Sair");
                    int opcao = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer

                    if (opcao == 2) {
                        return; // Sai do método iniciarCompra
                    }
                } else {
                    realizarCompra(cliente, passagemEscolhida);

                    System.out.println("Deseja comprar outra passagem?");
                    System.out.println("1. Sim");
                    System.out.println("2. Não");
                    int opcao = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer

                    if (opcao != 1) {
                        desejaComprarMais = false; // Finaliza o loop
                    }
                }
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void realizarCompra(Cliente cliente, Passagem passagem) {
        System.out.println("=== Detalhes da Compra ===");
        System.out.println("Passagem: " + passagem.getOrigem() + " -> " + passagem.getDestino());
        System.out.println("Preço: R$" + passagem.getPreco());
        System.out.println("Saldo atual: R$" + saldoUsuario);

        System.out.println("Informe o método de pagamento:");
        System.out.println("1. Boleto");
        System.out.println("2. Cartão de Crédito");

        int metodoPagamento = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        Pagamento pagamento = null;

        if (metodoPagamento == 1) {
            pagamento = new PagamentoBoleto(passagem.getPreco());
        } else if (metodoPagamento == 2) {
            System.out.println("Informe os dados do cartão:");
            System.out.println("Número do Cartão:");
            String numeroCartao = scanner.nextLine();

            System.out.println("Nome do Titular:");
            String nomeTitular = scanner.nextLine();

            System.out.println("Validade (MM/AA):");
            String validade = scanner.nextLine();

            System.out.println("Código de Segurança:");
            String codigoSeguranca = scanner.nextLine();

            System.out.println("Escolha o número de parcelas (1 a 8):");
            int parcelas = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            double valorTotal = passagem.getPreco();
            double valorParcela;

            if (parcelas >= 1 && parcelas <= 5) {
                // Sem juros
                valorParcela = valorTotal / parcelas;
                System.out.printf("Compra parcelada em %d vezes de R$%.2f cada (sem juros).%n", parcelas, valorParcela);
            } else if (parcelas >= 6 && parcelas <= 8) {
                // Juros de 12.5% por parcela
                double juros = 0.125; // 12.5%
                valorParcela = (valorTotal * Math.pow(1 + juros, parcelas)) / parcelas; // Fórmula para aplicar juros em cada parcela
                valorTotal = valorParcela * parcelas; // Atualiza o valor total com os juros acumulados
                System.out.printf("Compra parcelada em %d vezes de R$%.2f cada (com juros de 12.5%% por parcela).%n", parcelas, valorParcela);
            } else {
                System.out.println("Número de parcelas inválido.");
                return;
            }

            System.out.println("O valor total da compra será R$" + valorTotal);
            System.out.println("Deseja continuar com o pagamento?");
            System.out.println("1. Sim");
            System.out.println("2. Não");

            int opcaoConfirmacao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            if (opcaoConfirmacao == 1) {
                pagamento = new PagamentoCredito(valorTotal, numeroCartao, nomeTitular, validade, codigoSeguranca);
            } else {
                System.out.println("Pagamento cancelado.");
                return;
            }
        } else {
            System.out.println("Método de pagamento inválido.");
            return;
        }

        if (pagamentoService.processarPagamento(pagamento)) {
            saldoUsuario -= passagem.getPreco(); // Abate o valor da passagem do saldo do usuário
            passagensCompradas.add(passagem); // Armazena a passagem na lista
            Reserva reserva = new Reserva("RES-" + System.currentTimeMillis(), cliente, passagem, pagamento);
            System.out.println("Compra realizada com sucesso!");
            System.out.println("Código da Reserva: " + reserva.getCodigoReserva());
            System.out.println("Seu novo saldo é: R$" + saldoUsuario);

            // Salvar resultado final em um arquivo
            salvarResumoCompra(reserva);
        } else {
            System.out.println("Falha no pagamento. Tente novamente.");
        }
    }

    private void exibirResumo() {
        System.out.println("=== Resumo Final ===");
        System.out.println("Saldo final: R$" + saldoUsuario);
        System.out.println("Passagens compradas:");

        if (passagensCompradas.isEmpty()) {
            System.out.println("Nenhuma passagem foi comprada.");
        } else {
            for (Passagem passagem : passagensCompradas) {
                System.out.println("- " + passagem.getOrigem() + " -> " + passagem.getDestino() +
                        " | Partida: " + passagem.getDataHoraPartida() +
                        " | Chegada: " + passagem.getDataHoraChegada() +
                        " | Preço: R$" + passagem.getPreco());
            }
        }

        System.out.println("Obrigado por usar nosso sistema!");
    }

    private void salvarResumoCompra(Reserva reserva) {
        try (FileWriter writer = new FileWriter("histórico_compra.txt", true)) {
            writer.write("--- Resumo da Compra ---\n");
            writer.write("Código da Reserva: " + reserva.getCodigoReserva() + "\n");
            writer.write("Cliente: " + reserva.getCliente().getNome() + "\n");
            writer.write("Passagem: " + reserva.getPassagem().getOrigem() + " -> " + reserva.getPassagem().getDestino() + "\n");
            writer.write("Preço: R$" + reserva.getPassagem().getPreco() + "\n");
            writer.write("Data de Partida: " + reserva.getPassagem().getDataHoraPartida() + "\n");
            writer.write("Data de Chegada: " + reserva.getPassagem().getDataHoraChegada() + "\n");
            writer.write("Método de Pagamento: " + (reserva.getPagamento() instanceof PagamentoBoleto ? "Boleto" : "Cartão de Crédito") + "\n");
            writer.write("Total Pago: R$" + reserva.getPagamento().getClass() + "\n");
            writer.write("Saldo após compra: R$" + saldoUsuario + "\n");
            writer.write("---------------------------------\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o resumo da compra.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mostrarMenuPrincipal();
    }
}
