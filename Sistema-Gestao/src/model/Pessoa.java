package model;

import java.io.Serializable;

/**
 * Classe base que representa uma Pessoa
 * Implementa Serializable para persistência em ficheiros
 */
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private byte idade;

    /**
     * Construtor para Pessoa
     * @param nome Nome da pessoa
     * @param idade Idade da pessoa
     */
    public Pessoa(String nome, byte idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte getIdade() {
        return idade;
    }

    public void setIdade(byte idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return String.format("Nome: %s | Idade: %d", nome, idade);
    }
}
