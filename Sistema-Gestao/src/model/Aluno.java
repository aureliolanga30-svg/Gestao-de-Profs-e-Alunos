package model;

import java.io.Serializable;

/**
 * Classe abstrata que representa um Aluno
 * Herda os atributos da classe Pessoa e adiciona atributos específicos de aluno
 * Implementa Serializable para persistência em ficheiros
 */
public abstract class Aluno extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    private String matricula;
    private String curso;
    private String tipo;
    private float nota;

    /**
     * Construtor da classe Aluno
     * @param nome Nome do aluno
     * @param idade Idade do aluno
     * @param matricula Matrícula do aluno
     * @param curso Curso do aluno
     * @param tipo Tipo do aluno (Bolseiro ou Não-Bolseiro)
     * @param nota Nota do aluno
     */
    public Aluno(String nome, byte idade, String matricula, String curso, String tipo, float nota) {
        super(nome, idade);
        this.matricula = matricula;
        this.curso = curso;
        this.tipo = tipo;
        this.nota = nota;
    }

    // Getters e Setters
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    /**
     * Método abstrato para calcular mensalidade
     * Deve ser implementado pelas classes filhas
     * @return valor da mensalidade
     */
    public abstract double calcularMensalidade();

    /**
     * Método abstrato para verificar se está dispensado
     * Deve ser implementado pelas classes filhas
     * @return true se dispensado, false caso contrário
     */
    public abstract boolean isDispensado();

    /**
     * Método abstrato para verificar se está aprovado
     * Deve ser implementado pelas classes filhas
     * @return true se aprovado, false caso contrário
     */
    public abstract boolean isAprovado();

    @Override
    public abstract String toString();
}