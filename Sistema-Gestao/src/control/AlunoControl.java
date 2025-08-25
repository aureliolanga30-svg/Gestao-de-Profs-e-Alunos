package control;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.Aluno;
import model.AlunoBolseiro;
import model.AlunoNaoBolseiro;
import util.PersistenciaUtil;

public class AlunoControl extends JFrame {

    private ArrayList<Aluno> alunos = new ArrayList<>();
    private static final String ARQUIVO_ALUNOS = "alunos.dat";

    public AlunoControl(){
        // Carregar dados existentes
        carregarDados();

        setTitle("GERENCIAMENTO DE ALUNOS");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Tela principal
        JPanel tela = new JPanel();
        tela.setBackground(new Color(230, 255, 250));
        tela.setLayout(new BorderLayout(10, 10));
        tela.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("GESTÃO DE ALUNOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Calibri", Font.BOLD, 18));
        titulo.setForeground(new Color(0, 102, 102));
        tela.add(titulo, BorderLayout.NORTH);

        // Painel dos botões
        JPanel painelBotoes = new JPanel(new GridLayout(4, 1, 10, 10));
        painelBotoes.setBackground(new Color(230, 255, 250));

        // Criação de botões
        JButton btCriar = criarBotao("Criar Aluno");
        JButton btVisual = criarBotao("Visualizar Alunos");
        JButton btAct = criarBotao("Actualizar Aluno");
        JButton btApagar = criarBotao("Eliminar Aluno");

        painelBotoes.add(btCriar);
        painelBotoes.add(btVisual);
        painelBotoes.add(btAct);
        painelBotoes.add(btApagar);

        tela.add(painelBotoes);
        add(tela);

        // Event Listeners
        btCriar.addActionListener(e -> criarAluno());
        btVisual.addActionListener(e -> visualizarAlunos());
        btAct.addActionListener(e -> atualizarAluno());
        btApagar.addActionListener(e -> apagarAluno());

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
     * Método para criar novo aluno
     */
    private void criarAluno(){
        try {
            String nome = JOptionPane.showInputDialog("Nome:");
            if (nome == null || nome.trim().isEmpty()) return;

            String idadeStr = JOptionPane.showInputDialog("Idade:");
            if (idadeStr == null) return;
            byte idade = Byte.parseByte(idadeStr);

            String matricula = JOptionPane.showInputDialog("Matrícula:");
            if (matricula == null || matricula.trim().isEmpty()) return;

            String curso = JOptionPane.showInputDialog("Curso:");
            if (curso == null || curso.trim().isEmpty()) return;

            // Escolher tipo de aluno
            String[] tipos = {"Bolseiro", "Não-Bolseiro"};
            String tipo = (String) JOptionPane.showInputDialog(
                    null, "Selecione o tipo de aluno:", "Tipo de Aluno",
                    JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]
            );
            if (tipo == null) return;

            String notaStr = JOptionPane.showInputDialog("Nota (0-20):");
            if (notaStr == null) return;
            float nota = Float.parseFloat(notaStr);

            if (nota < 0 || nota > 20) {
                JOptionPane.showMessageDialog(this, "Nota deve estar entre 0 e 20!");
                return;
            }

            // Criar aluno baseado no tipo
            Aluno novoAluno;
            if (tipo.equals("Bolseiro")) {
                novoAluno = new AlunoBolseiro(nome, idade, matricula, curso, nota);
            } else {
                novoAluno = new AlunoNaoBolseiro(nome, idade, matricula, curso, nota);
            }

            alunos.add(novoAluno);
            salvarDados();

            JOptionPane.showMessageDialog(this,
                    "Aluno criado com sucesso!\n" +
                            "Status: " + (novoAluno.isAprovado() ? "Aprovado" : "Reprovado") + "\n" +
                            "Dispensado: " + (novoAluno.isDispensado() ? "Sim" : "Não") + "\n" +
                            "Mensalidade: " + String.format("%.2f MT", novoAluno.calcularMensalidade())
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique os valores numéricos inseridos!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    /**
     * Método para visualizar todos os alunos
     */
    private void visualizarAlunos(){
        if (alunos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum aluno cadastrado!");
            return;
        }

        StringBuilder info = new StringBuilder();
        info.append("=== LISTA DE ALUNOS ===\n\n");

        for (int i = 0; i < alunos.size(); i++) {
            Aluno aluno = alunos.get(i);
            info.append(String.format("%d. %s\n", i + 1, aluno.toString()));
            info.append(String.format("   Status: %s | Dispensado: %s\n",
                    aluno.isAprovado() ? "Aprovado" : "Reprovado",
                    aluno.isDispensado() ? "Sim" : "Não"
            ));
            info.append(String.format("   Mensalidade: %.2f MT\n\n", aluno.calcularMensalidade()));
        }

        JTextArea textArea = new JTextArea(info.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Lista de Alunos", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Método para atualizar dados de um aluno
     */
    private void atualizarAluno(){
        if (alunos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum aluno cadastrado!");
            return;
        }

        // Criar lista para seleção
        String[] opcoes = new String[alunos.size()];
        for (int i = 0; i < alunos.size(); i++) {
            opcoes[i] = alunos.get(i).getMatricula() + " - " + alunos.get(i).getNome();
        }

        String selecao = (String) JOptionPane.showInputDialog(
                this, "Selecione o aluno para atualizar:", "Atualizar Aluno",
                JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]
        );

        if (selecao != null) {
            int indice = java.util.Arrays.asList(opcoes).indexOf(selecao);
            Aluno aluno = alunos.get(indice);

            try {
                String novoNome = JOptionPane.showInputDialog("Novo nome:", aluno.getNome());
                if (novoNome != null && !novoNome.trim().isEmpty()) {
                    aluno.setNome(novoNome);
                }

                String novoCurso = JOptionPane.showInputDialog("Novo curso:", aluno.getCurso());
                if (novoCurso != null && !novoCurso.trim().isEmpty()) {
                    aluno.setCurso(novoCurso);
                }

                String novaNotaStr = JOptionPane.showInputDialog("Nova nota:", String.valueOf(aluno.getNota()));
                if (novaNotaStr != null) {
                    float novaNota = Float.parseFloat(novaNotaStr);
                    if (novaNota >= 0 && novaNota <= 20) {
                        aluno.setNota(novaNota);
                    }
                }

                salvarDados();
                JOptionPane.showMessageDialog(this, "Aluno atualizado com sucesso!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Nota deve ser um número válido!");
            }
        }
    }

    /**
     * Método para apagar um aluno
     */
    private void apagarAluno(){
        if (alunos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum aluno cadastrado!");
            return;
        }

        String[] opcoes = new String[alunos.size()];
        for (int i = 0; i < alunos.size(); i++) {
            opcoes[i] = alunos.get(i).getMatricula() + " - " + alunos.get(i).getNome();
        }

        String selecao = (String) JOptionPane.showInputDialog(
                this, "Selecione o aluno para eliminar:", "Eliminar Aluno",
                JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]
        );

        if (selecao != null) {
            int confirmacao = JOptionPane.showConfirmDialog(
                    this, "Tem certeza que deseja eliminar este aluno?",
                    "Confirmar Eliminação", JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {
                int indice = java.util.Arrays.asList(opcoes).indexOf(selecao);
                alunos.remove(indice);
                salvarDados();
                JOptionPane.showMessageDialog(this, "Aluno eliminado com sucesso!");
            }
        }
    }

    /**
     * Método para carregar dados do arquivo
     */
    private void carregarDados() {
        try {
            alunos = PersistenciaUtil.carregarAlunos(ARQUIVO_ALUNOS);
        } catch (Exception e) {
            alunos = new ArrayList<>(); // Se houver erro, inicia lista vazia
        }
    }

    /**
     * Método para salvar dados no arquivo
     */
    private void salvarDados() {
        try {
            PersistenciaUtil.salvarAlunos(alunos, ARQUIVO_ALUNOS);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar dados: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AlunoControl();
    }
}