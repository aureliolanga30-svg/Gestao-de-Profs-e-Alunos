package model;

/**
 * Classe que representa um Aluno Não-Bolseiro
 * Herda da classe Aluno e implementa regras específicas para não-bolseiros
 *
 * Regras:
 * - Sempre paga a mensalidade completa, mesmo se dispensado
 * - Dispensa acadêmica se nota ≥ 14
 */
public class AlunoNaoBolseiro extends Aluno {

    private static final double MENSALIDADE_BASE = 5000.0; // Mensalidade base em MT
    private static final float NOTA_DISPENSADO = 14.0f;

    /**
     * Construtor para Aluno Não-Bolseiro
     * @param nome Nome do aluno
     * @param idade Idade do aluno
     * @param matricula Matrícula do aluno
     * @param curso Curso do aluno
     * @param nota Nota do aluno
     */
    public AlunoNaoBolseiro(String nome, byte idade, String matricula, String curso, float nota) {
        super(nome, idade, matricula, curso, "Não-Bolseiro", nota);
    }

    /**
     * Calcula a mensalidade para aluno não-bolseiro
     * Sempre paga a mensalidade completa
     * @return valor da mensalidade
     */
    @Override
    public double calcularMensalidade() {
        return MENSALIDADE_BASE; // Sempre paga mensalidade completa
    }

    /**
     * Verifica se o aluno está dispensado academicamente
     * Para não-bolseiros: dispensado se nota ≥ 14
     * @return true se dispensado, false caso contrário
     */
    @Override
    public boolean isDispensado() {
        return getNota() >= NOTA_DISPENSADO;
    }

    /**
     * Verifica se o aluno está aprovado
     * Aprovado se nota ≥ 10
     * @return true se aprovado, false caso contrário
     */
    @Override
    public boolean isAprovado() {
        return getNota() >= 10.0f;
    }

    @Override
    public String toString() {
        return String.format("NÃO-BOLSEIRO - %s | Matrícula: %s | Curso: %s | Nota: %.1f | Aprovado: %s | Dispensado: %s | Mensalidade: %.2f MT",
                getNome(), getMatricula(), getCurso(), getNota(),
                isAprovado() ? "Sim" : "Não",
                isDispensado() ? "Sim" : "Não",
                calcularMensalidade());
    }
}