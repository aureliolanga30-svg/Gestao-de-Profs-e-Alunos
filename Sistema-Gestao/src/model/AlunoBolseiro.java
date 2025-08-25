package model;

/**
 * Classe que representa um Aluno Bolseiro
 * Herda da classe Aluno e implementa regras específicas para bolseiros
 *
 * Regras:
 * - Dispensa total do pagamento se nota ≥ 16
 * - Caso contrário, paga metade da mensalidade
 * - Dispensa acadêmica se nota ≥ 16
 */
public class AlunoBolseiro extends Aluno {

    private static final double MENSALIDADE_BASE = 5000.0; // Mensalidade base em MT
    private static final float NOTA_DISPENSADO = 16.0f;

    /**
     * Construtor para Aluno Bolseiro
     * @param nome Nome do aluno
     * @param idade Idade do aluno
     * @param matricula Matrícula do aluno
     * @param curso Curso do aluno
     * @param nota Nota do aluno
     */
    public AlunoBolseiro(String nome, byte idade, String matricula, String curso, float nota) {
        super(nome, idade, matricula, curso, "Bolseiro", nota);
    }

    /**
     * Calcula a mensalidade para aluno bolseiro
     * @return valor da mensalidade
     */
    @Override
    public double calcularMensalidade() {
        if (getNota() >= NOTA_DISPENSADO) {
            return 0.0; // Dispensa total do pagamento
        } else {
            return MENSALIDADE_BASE / 2; // Paga metade da mensalidade
        }
    }

    /**
     * Verifica se o aluno está dispensado
     * Para bolseiros: dispensado se nota ≥ 16
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
        return String.format("BOLSEIRO - %s | Matrícula: %s | Curso: %s | Nota: %.1f | Aprovado: %s | Dispensado: %s | Mensalidade: %.2f MT",
                getNome(), getMatricula(), getCurso(), getNota(),
                isAprovado() ? "Sim" : "Não",
                isDispensado() ? "Sim" : "Não",
                calcularMensalidade());
    }
}