package model;

import java.io.Serializable;

/**
 * Classe que representa um Professor
 * Herda da classe Pessoa e implementa Serializable para persistência
 */
public class Professor extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    private String disciplina;
    private double salario;

    /**
     * Construtor para Professor
     * @param nome Nome do professor
     * @param idade Idade do professor
     * @param disciplina Disciplina que leciona
     * @param salario Salário do professor
     */
    public Professor(String nome, byte idade, String disciplina, double salario) {
        super(nome, idade);
        this.disciplina = disciplina;
        this.salario = salario;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return String.format("PROFESSOR - %s | Idade: %d | Disciplina: %s | Salário: %.2f MT",
                getNome(), getIdade(), disciplina, salario);
    }
}
