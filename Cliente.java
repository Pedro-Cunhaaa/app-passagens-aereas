package A3;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private String telefone;
    private String cpfOuCnpj;
    private List<String> enderecos;

    public Cliente(String nome, String telefone, String cpfOuCnpj) {
        this.nome = nome;
        this.telefone = telefone;
        this.cpfOuCnpj = cpfOuCnpj;
        this.enderecos = new ArrayList<>();
    }

    public void adicionarEndereco(String endereco) {
        enderecos.add(endereco);
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public List<String> getEnderecos() {
        return enderecos;
    }
}
