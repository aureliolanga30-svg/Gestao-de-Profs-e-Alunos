package control;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import model.Professor;
import util.PersistenciaUtil;

/**
 * Classe de controle para gestão de professores
 * Implementa interface gráfica com operações CRUD
 */
public class ProfessorControl extends JFrame {

    private ArrayList<Professor> professores = new ArrayList<>();
    private static final String ARQUIVO_PROFESSORES = "professores.dat";

    public ProfessorControl(){
        // Carregar dados existentes
        carregarDados();

        setTitle("GERENCIAMENTO DE PROFESSORES");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Tela principal
        JPanel tela = new JPanel();
        tela.setBackground(new Color(230, 255, 250));
        tela.setLayout(new BorderLayout(10, 10));
        tela.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("GESTÃO DE PROFESSORES", SwingConstants.CENTER);
        titulo.setFont(new Font("Calibri", Font.BOLD, 18));
        titulo.setForeground(new Color(0, 102, 102));
        tela.add(titulo, BorderLayout.NORTH);

        // Painel dos botões
        JPanel painelBotoes = new JPanel(new GridLayout(4, 1, 10, 10));
        painelBotoes.setBackground(new Color(230, 255, 250));

        // Criação de botões
        JButton btCriar = criarBotao("Criar Professor");
        JButton btVisual = criarBotao("Visualizar Professores");
        JButton btAct = criarBotao("Actualizar Professor");
        JButton btApagar = criarBotao("Eliminar Professor");

        painelBotoes.add(btCriar);
        painelBotoes.add(btVisual);
        painelBotoes.add(btAct);
        painelBotoes.add(btApagar);

        tela.add(painelBotoes);
        add(tela);

        // Event Listeners
        btCriar.addActionListener(e -> criarProfessor());
        btVisual.addActionListener(e -> visualizarProfessores());
        btAct.addActionListener(e -> atualizarProfessor());
        btApagar.addActionListener(e -> apagarProfessor());

        setVisible(true);
    }

    /**
     * Método auxiliar para criar botões estilizados
     */
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Calibri", Font.BOLD, 16));
        botao.setBackground(new Color(180, 240, 220));
        botao.setForeground(new Color(0, 51, 102));
        botao.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102)));
        return botao;
    }

    /**
     * Método para criar novo professor
     */
    private void criarProfessor(){
        try {
            String nome = JOptionPane.showInputDialog("Nome:");
            if (nome == null || nome.trim().isEmpty()) return;

            String idadeStr = JOptionPane.showInputDialog("Idade:");
            if (idadeStr == null) return;
            byte idade = Byte.parseByte(idadeStr);

            String disciplina = JOptionPane.showInputDialog("Disciplina:");
            if (disciplina == null || disciplina.trim().isEmpty()) return;

            String salarioStr = JOptionPane.showInputDialog("Salário:");
            if (salarioStr == null) return;
            double salario = Double.parseDouble(salarioStr);

            if (salario < 0) {
                JOptionPane.showMessageDialog(this, "Salário deve ser positivo!");
                return;
            }

            Professor novoProfessor = new Professor(nome, idade, disciplina, salario);
            professores.add(novoProfessor);
            salvarDados();

            JOptionPane.showMessageDialog(this, "Professor criado com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique os valores numéricos inseridos!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    /**
     * Método para visualizar todos os professores
     */
    private void visualizarProfessores(){
        if (professores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum professor cadastrado!");
            return;
        }

        StringBuilder info = new StringBuilder();
        info.append("=== LISTA DE PROFESSORES ===\n\n");

        for (int i = 0; i < professores.size(); i++) {
            Professor professor = professores.get(i);
            info.append(String.format("%d. %s\n\n", i + 1, professor.toString()));
        }

        JTextArea textArea = new JTextArea(info.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Lista de Professores", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Método para atualizar dados de um professor
     */
    private void atualizarProfessor(){
        if (professores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum professor cadastrado!");
            return;
        }

        // Criar lista para seleção
        String[] opcoes = new String[professores.size()];
        for (int i = 0; i < professores.size(); i++) {
            opcoes[i] = professores.get(i).getNome() + " - " + professores.get(i).getDisciplina();
        }

        String selecao = (String) JOptionPane.showInputDialog(
                this, "Selecione o professor para atualizar:", "Atualizar Professor",
                JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]
        );

        if (selecao != null) {
            int indice = java.util.Arrays.asList(opcoes).indexOf(selecao);
            Professor professor = professores.get(indice);

            try {
                String novoNome = JOptionPane.showInputDialog("Novo nome:", professor.getNome());
                if (novoNome != null && !novoNome.trim().isEmpty()) {
                    professor.setNome(novoNome);
                }

                String novaDisciplina = JOptionPane.showInputDialog("Nova disciplina:", professor.getDisciplina());
                if (novaDisciplina != null && !novaDisciplina.trim().isEmpty()) {
                    professor.setDisciplina(novaDisciplina);
                }

                String novoSalarioStr = JOptionPane.showInputDialog("Novo salário:", String.valueOf(professor.getSalario()));
                if (novoSalarioStr != null) {
                    double novoSalario = Double.parseDouble(novoSalarioStr);
                    if (novoSalario >= 0) {
                        professor.setSalario(novoSalario);
                    }
                }

                salvarDados();
                JOptionPane.showMessageDialog(this, "Professor atualizado com sucesso!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Salário deve ser um número válido!");
            }
        }
    }

    /**
     * Método para apagar um professor
     */
    private void apagarProfessor(){
        if (professores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum professor cadastrado!");
            return;
        }

        String[] opcoes = new String[professores.size()];
        for (int i = 0; i < professores.size(); i++) {
            opcoes[i] = professores.get(i).getNome() + " - " + professores.get(i).getDisciplina();
        }

        String selecao = (String) JOptionPane.showInputDialog(
                this, "Selecione o professor para eliminar:", "Eliminar Professor",
                JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]
        );

        if (selecao != null) {
            int confirmacao = JOptionPane.showConfirmDialog(
                    this, "Tem certeza que deseja eliminar este professor?",
                    "Confirmar Eliminação", JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {
                int indice = java.util.Arrays.asList(opcoes).indexOf(selecao);
                professores.remove(indice);
                salvarDados();
                JOptionPane.showMessageDialog(this, "Professor eliminado com sucesso!");
            }
        }
    }

    /**
     * Método para carregar dados do arquivo
     */
    private void carregarDados() {
        try {
            professores = PersistenciaUtil.carregarProfessores(ARQUIVO_PROFESSORES);
        } catch (Exception e) {
            professores = new ArrayList<>(); // Se houver erro, inicia lista vazia
        }
    }

    /**
     * Método para salvar dados no arquivo
     */
    private void salvarDados() {
        try {
            PersistenciaUtil.salvarProfessores(professores, ARQUIVO_PROFESSORES);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar dados: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ProfessorControl();
    }
}