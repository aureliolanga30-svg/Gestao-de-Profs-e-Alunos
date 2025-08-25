package control;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.Aluno;
import model.AlunoBolseiro;
import model.AlunoNaoBolseiro;
import model.Professor;
import util.PersistenciaUtil;

/**
 * Classe de controle para geração de relatórios
 * Utiliza recursividade para percorrer listas e gerar relatórios organizados
 */
public class RelatorioControl extends JFrame {

    private ArrayList<Aluno> alunos = new ArrayList<>();
    private ArrayList<Professor> professores = new ArrayList<>();

    private static final String ARQUIVO_ALUNOS = "alunos.dat";
    private static final String ARQUIVO_PROFESSORES = "professores.dat";

    public RelatorioControl() {
        carregarDados();

        setTitle("SISTEMA DE RELATÓRIOS");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Tela principal
        JPanel tela = new JPanel();
        tela.setBackground(new Color(230, 255, 250));
        tela.setLayout(new BorderLayout(10, 10));
        tela.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("GERAÇÃO DE RELATÓRIOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Calibri", Font.BOLD, 18));
        titulo.setForeground(new Color(0, 102, 102));
        tela.add(titulo, BorderLayout.NORTH);

        // Painel dos botões
        JPanel painelBotoes = new JPanel(new GridLayout(6, 1, 10, 10));
        painelBotoes.setBackground(new Color(230, 255, 250));

        JButton btRelatorioGeral = criarBotao("Relatório Geral");
        JButton btRelatorioAlunos = criarBotao("Relatório de Alunos");
        JButton btRelatorioProfessores = criarBotao("Relatório de Professores");
        JButton btRelatorioFinanceiro = criarBotao("Relatório Financeiro");
        JButton btRelatorioEstatisticas = criarBotao("Estatísticas");
        JButton btExportarTodos = criarBotao("Exportar Todos (.txt)");

        painelBotoes.add(btRelatorioGeral);
        painelBotoes.add(btRelatorioAlunos);
        painelBotoes.add(btRelatorioProfessores);
        painelBotoes.add(btRelatorioFinanceiro);
        painelBotoes.add(btRelatorioEstatisticas);
        painelBotoes.add(btExportarTodos);

        tela.add(painelBotoes);
        add(tela);

        // Event Listeners
        btRelatorioGeral.addActionListener(e -> mostrarRelatorioGeral());
        btRelatorioAlunos.addActionListener(e -> mostrarRelatorioAlunos());
        btRelatorioProfessores.addActionListener(e -> mostrarRelatorioProfessores());
        btRelatorioFinanceiro.addActionListener(e -> mostrarRelatorioFinanceiro());
        btRelatorioEstatisticas.addActionListener(e -> mostrarEstatisticas());
        btExportarTodos.addActionListener(e -> exportarTodosRelatorios());

        setVisible(true);
    }

    /**
     * Método auxiliar para criar botões estilizados
     */
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Calibri", Font.BOLD, 14));
        botao.setBackground(new Color(180, 240, 220));
        botao.setForeground(new Color(0, 51, 102));
        botao.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102)));
        return botao;
    }

    /**
     * Relatório geral com todos os dados
     */
    private void mostrarRelatorioGeral() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(gerarCabecalho("RELATÓRIO GERAL DO SISTEMA"));

        relatorio.append("\n             === PROFESSORES ===         \n");
        if (professores.isEmpty()) {
            relatorio.append("Nenhum professor cadastrado.\n");
        } else {
            relatorio.append(gerarRelatorioProfessoresRecursivo(professores, 0));
        }

        relatorio.append("\n                === ALUNOS ===          \n");
        if (alunos.isEmpty()) {
            relatorio.append("Nenhum aluno cadastrado.\n");
        } else {
            relatorio.append(gerarRelatorioAlunosRecursivo(alunos, 0));
        }

        mostrarRelatorio(relatorio.toString(), "Relatório Geral");
    }

    /**
     * Relatório específico de alunos
     */
    private void mostrarRelatorioAlunos() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(gerarCabecalho("RELATÓRIO DE ALUNOS"));

        if (alunos.isEmpty()) {
            relatorio.append("Nenhum aluno cadastrado.\n");
        } else {
            relatorio.append(gerarRelatorioAlunosRecursivo(alunos, 0));
        }

        mostrarRelatorio(relatorio.toString(), "Relatório de Alunos");
    }

    /**
     * Relatório específico de professores
     */
    private void mostrarRelatorioProfessores() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(gerarCabecalho("RELATÓRIO DE PROFESSORES"));

        if (professores.isEmpty()) {
            relatorio.append("Nenhum professor cadastrado.\n");
        } else {
            relatorio.append(gerarRelatorioProfessoresRecursivo(professores, 0));
        }

        mostrarRelatorio(relatorio.toString(), "Relatório de Professores");
    }

    /**
     * Relatório financeiro com cálculos recursivos
     */
    private void mostrarRelatorioFinanceiro() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(gerarCabecalho("RELATÓRIO FINANCEIRO"));

        if (alunos.isEmpty()) {
            relatorio.append("Nenhum aluno cadastrado.\n");
        } else {
            double totalMensalidades = calcularTotalMensalidadesRecursivo(alunos, 0);
            double totalBolseiros = calcularMensalidadesPorTipoRecursivo(alunos, 0, "Bolseiro");
            double totalNaoBolseiros = calcularMensalidadesPorTipoRecursivo(alunos, 0, "Não-Bolseiro");

            relatorio.append(String.format("Total de Mensalidades: %.2f MT\n", totalMensalidades));
            relatorio.append(String.format("Total de Bolseiros: %.2f MT\n", totalBolseiros));
            relatorio.append(String.format("Total de Não-Bolseiros: %.2f MT\n", totalNaoBolseiros));
            relatorio.append("\n=== DETALHES POR ALUNO ===\n");
            relatorio.append(gerarRelatorioFinanceiroRecursivo(alunos, 0));
        }

        mostrarRelatorio(relatorio.toString(), "Relatório Financeiro");
    }

    /**
     * Relatório de estatísticas
     */
    private void mostrarEstatisticas() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(gerarCabecalho("ESTATÍSTICAS DO SISTEMA"));

        // Estatísticas gerais
        relatorio.append(String.format("Total de Professores: %d\n", professores.size()));
        relatorio.append(String.format("Total de Alunos: %d\n", alunos.size()));

        if (!alunos.isEmpty()) {
            int bolseiros = contarAlunosPorTipoRecursivo(alunos, 0, "Bolseiro");
            int naoBolseiros = contarAlunosPorTipoRecursivo(alunos, 0, "Não-Bolseiro");
            int aprovados = contarAprovadosRecursivo(alunos, 0);
            int dispensados = contarDispensadosRecursivo(alunos, 0);

            relatorio.append(String.format("Alunos Bolseiros: %d\n", bolseiros));
            relatorio.append(String.format("Alunos Não-Bolseiros: %d\n", naoBolseiros));
            relatorio.append(String.format("Alunos Aprovados: %d\n", aprovados));
            relatorio.append(String.format("Alunos Dispensados: %d\n", dispensados));
            relatorio.append(String.format("Taxa de Aprovação: %.2f%%\n", (double)aprovados/alunos.size()*100));
            relatorio.append(String.format("Taxa de Dispensa: %.2f%%\n", (double)dispensados/alunos.size()*100));
        }

        mostrarRelatorio(relatorio.toString(), "Estatísticas");
    }

    /**
     * Gera relatório de alunos usando recursividade
     */
    private String gerarRelatorioAlunosRecursivo(ArrayList<Aluno> lista, int indice) {
        if (indice >= lista.size()) {
            return "";
        }

        Aluno aluno = lista.get(indice);
        String info = String.format("%d. %s\n", indice + 1, aluno.toString());

        return info + gerarRelatorioAlunosRecursivo(lista, indice + 1);
    }

    /**
     * Gera relatório de professores usando recursividade
     */
    private String gerarRelatorioProfessoresRecursivo(ArrayList<Professor> lista, int indice) {
        if (indice >= lista.size()) {
            return "";
        }

        Professor professor = lista.get(indice);
        String info = String.format("%d. %s\n", indice + 1, professor.toString());

        return info + gerarRelatorioProfessoresRecursivo(lista, indice + 1);
    }

    /**
     * Gera relatório financeiro usando recursividade
     */
    private String gerarRelatorioFinanceiroRecursivo(ArrayList<Aluno> lista, int indice) {
        if (indice >= lista.size()) {
            return "";
        }

        Aluno aluno = lista.get(indice);
        String info = String.format("%s - Mensalidade: %.2f MT\n",
                aluno.getNome(), aluno.calcularMensalidade());

        return info + gerarRelatorioFinanceiroRecursivo(lista, indice + 1);
    }

    /**
     * Calcula total de mensalidades usando recursividade
     */
    private double calcularTotalMensalidadesRecursivo(ArrayList<Aluno> lista, int indice) {
        if (indice >= lista.size()) {
            return 0.0;
        }

        return lista.get(indice).calcularMensalidade() +
                calcularTotalMensalidadesRecursivo(lista, indice + 1);
    }

    /**
     * Calcula mensalidades por tipo usando recursividade
     */
    private double calcularMensalidadesPorTipoRecursivo(ArrayList<Aluno> lista, int indice, String tipo) {
        if (indice >= lista.size()) {
            return 0.0;
        }

        Aluno aluno = lista.get(indice);
        double valor = aluno.getTipo().equals(tipo) ? aluno.calcularMensalidade() : 0.0;

        return valor + calcularMensalidadesPorTipoRecursivo(lista, indice + 1, tipo);
    }

    /**
     * Conta alunos por tipo usando recursividade
     */
    private int contarAlunosPorTipoRecursivo(ArrayList<Aluno> lista, int indice, String tipo) {
        if (indice >= lista.size()) {
            return 0;
        }

        int count = lista.get(indice).getTipo().equals(tipo) ? 1 : 0;
        return count + contarAlunosPorTipoRecursivo(lista, indice + 1, tipo);
    }

    /**
     * Conta alunos aprovados usando recursividade
     */
    private int contarAprovadosRecursivo(ArrayList<Aluno> lista, int indice) {
        if (indice >= lista.size()) {
            return 0;
        }

        int count = lista.get(indice).isAprovado() ? 1 : 0;
        return count + contarAprovadosRecursivo(lista, indice + 1);
    }

    /**
     * Conta alunos dispensados usando recursividade
     */
    private int contarDispensadosRecursivo(ArrayList<Aluno> lista, int indice) {
        if (indice >= lista.size()) {
            return 0;
        }

        int count = lista.get(indice).isDispensado() ? 1 : 0;
        return count + contarDispensadosRecursivo(lista, indice + 1);
    }

    /**
     * Gera cabeçalho para relatórios
     */
    private String gerarCabecalho(String titulo) {
        StringBuilder cabecalho = new StringBuilder();
        cabecalho.append("=".repeat(60)).append("\n");
        cabecalho.append(String.format("               %s\n", titulo));
        cabecalho.append(String.format("        Data: %s\n",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        cabecalho.append("=".repeat(60)).append("\n\n");
        return cabecalho.toString();
    }

    /**
     * Mostra relatório em janela
     */
    private void mostrarRelatorio(String conteudo, String titulo) {
        JTextArea textArea = new JTextArea(conteudo);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 11));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700, 500));

        JOptionPane.showMessageDialog(this, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Exporta todos os relatórios para ficheiros .txt
     */
    private void exportarTodosRelatorios() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            // Relatório geral
            StringBuilder relatorioGeral = new StringBuilder();
            relatorioGeral.append(gerarCabecalho("RELATÓRIO GERAL DO SISTEMA"));
            relatorioGeral.append("\n=== PROFESSORES ===\n");
            if (!professores.isEmpty()) {
                relatorioGeral.append(gerarRelatorioProfessoresRecursivo(professores, 0));
            }
            relatorioGeral.append("\n=== ALUNOS ===\n");
            if (!alunos.isEmpty()) {
                relatorioGeral.append(gerarRelatorioAlunosRecursivo(alunos, 0));
            }

            PersistenciaUtil.exportarParaTxt(relatorioGeral.toString(),
                    "relatorio_geral_" + timestamp + ".txt");

            // Relatório financeiro
            if (!alunos.isEmpty()) {
                StringBuilder relatorioFinanceiro = new StringBuilder();
                relatorioFinanceiro.append(gerarCabecalho("RELATÓRIO FINANCEIRO"));
                double total = calcularTotalMensalidadesRecursivo(alunos, 0);
                relatorioFinanceiro.append(String.format("Total de Mensalidades: %.2f MT\n\n", total));
                relatorioFinanceiro.append(gerarRelatorioFinanceiroRecursivo(alunos, 0));

                PersistenciaUtil.exportarParaTxt(relatorioFinanceiro.toString(),
                        "relatorio_financeiro_" + timestamp + ".txt");
            }

            JOptionPane.showMessageDialog(this,
                    "Relatórios exportados com sucesso!\nFicheiros gerados com timestamp: " + timestamp);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao exportar relatórios: " + e.getMessage());
        }
    }

    /**
     * Carrega dados dos ficheiros
     */
    private void carregarDados() {
        try {
            alunos = PersistenciaUtil.carregarAlunos(ARQUIVO_ALUNOS);
        } catch (Exception e) {
            alunos = new ArrayList<>();
        }

        try {
            professores = PersistenciaUtil.carregarProfessores(ARQUIVO_PROFESSORES);
        } catch (Exception e) {
            professores = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        new RelatorioControl();
    }
}
