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

    /*
        Todo scanner.nextLine() vai limpar o buffer
     */

    private void iniciarCompra() {
        System.out.println("Informe seus dados para continuar.");

        String nome = obterNomeValido();

        System.out.println("Telefone:");
        String telefone = scanner.nextLine();

        String cpfOuCnpj = obterCpfOuCnpjValido();

        System.out.println("Informe o valor disponível em sua conta (em reais):");
        saldoUsuario = scanner.nextDouble();
        scanner.nextLine();

        Cliente cliente = new Cliente(nome, telefone, cpfOuCnpj);

        if (cpfOuCnpj.length() == 11) {
            System.out.println("CPF: " + cpfOuCnpj);
        } else if (cpfOuCnpj.length() == 14) {
            System.out.println("CNPJ: " + cpfOuCnpj);
        }

        boolean desejaComprarMais = true;

        while (desejaComprarMais) {
            System.out.println("=== Voos Disponíveis ===");
            vooService.mostrarVoosDisponiveis();

            System.out.println("Selecione o número do voo desejado:");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            Passagem passagemEscolhida = vooService.getPassagemPorIndice(escolha - 1);
            if (passagemEscolhida != null) {
                if (passagemEscolhida.getPreco() > saldoUsuario) {
                    System.out.println("Saldo insuficiente para realizar a compra desta passagem.");
                    System.out.println("1. Escolher outra passagem");
                    System.out.println("2. Sair");
                    int opcao = scanner.nextInt();
                    scanner.nextLine();

                    if (opcao == 2) {
                        return;
                    }
                } else {
                    realizarCompra(cliente, passagemEscolhida);

                    System.out.println("Deseja comprar outra passagem?");
                    System.out.println("1. Sim");
                    System.out.println("2. Não");
                    int opcao = scanner.nextInt();
                    scanner.nextLine();

                    if (opcao != 1) {
                        desejaComprarMais = false;
                    }
                }
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private String obterNomeValido() {
        while (true) {
            System.out.println("Nome (somente letras):");
            String nome = scanner.nextLine();

            if (nome.matches("^[a-zA-ZáéíóúÁÉÍÓÚãõçÇâêîôûÂÊÎÔÛ ]+$")) {
                return nome;
            } else {
                System.out.println("Nome inválido. Por favor, insira apenas letras.");
            }
        }
    }


    private String obterCpfOuCnpjValido() {
        while (true) {
            System.out.println("CPF ou CNPJ:");
            String cpfOuCnpj = scanner.nextLine();

            if (cpfOuCnpj.length() == 11 || cpfOuCnpj.length() == 14) {
                return cpfOuCnpj;
            } else {
                System.out.println("Digite um CPF ou um CNPJ válido (11 ou 14 dígitos).");
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
                // sem juros
                valorParcela = valorTotal / parcelas;
                System.out.printf("Compra parcelada em %d vezes de R$%.2f cada (sem juros).%n", parcelas, valorParcela);
            } else if (parcelas >= 6 && parcelas <= 8) {
                // juros de 12.5% por parcela
                double juros = 0.125; // 12.5%
                valorParcela = (valorTotal * Math.pow(1 + juros, parcelas)) / parcelas;
                valorTotal = valorParcela * parcelas;
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
            saldoUsuario -= passagem.getPreco();
            passagensCompradas.add(passagem);
            Reserva reserva = new Reserva("RES-" + System.currentTimeMillis(), cliente, passagem, pagamento);
            System.out.println("Compra realizada com sucesso!");
            System.out.println("Código da Reserva: " + reserva.getCodigoReserva());
            System.out.println("Seu novo saldo é: R$" + saldoUsuario);

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
}
