package view;

import control.AlunoControl;
import control.ProfessorControl;
import control.RelatorioControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaGeral extends JFrame {
    public VistaGeral(){
        setTitle("PAINEL DE CONTROLE E GESTÃO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel Tela = new JPanel();     // Criacao de uma tela.
        Tela.setBackground(new Color(230, 255, 250));
        Tela.setLayout(new BorderLayout(10, 10));
        Tela.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título da tela
        JLabel titulo = new JLabel("GERENCIAMENTO DE PROFESSORES E ALUNOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Calibri", Font.BOLD, 18));
        titulo.setForeground(new Color(0, 102, 102));
        Tela.add(titulo, BorderLayout.NORTH);

        // Painel dos botões
        JPanel telaBotoes = new JPanel(new GridLayout(4, 1, 10, 10));
        telaBotoes.setBackground(new Color(230, 255, 250));

        JButton btProf = criarBotao("PROFESSORES");
        JButton btAl = criarBotao("ALUNOS");
        JButton btRela = criarBotao("RELATORIOS");

        telaBotoes.add(btProf);      // Anexando os botoes nao tela
        telaBotoes.add(btAl);
        telaBotoes.add(btRela);


        // Anexa a tela dos botoes na tela
        Tela.add(telaBotoes);

        // Anexa Tela principal ao frame
        add(Tela);

        btProf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ProfessorControl();
            }
        });

        btAl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AlunoControl();
            }
        });

        btRela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RelatorioControl();
            }
        });

        setVisible(true);
    }

    // Metodo auxiliar para estilizar botoes
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Calibri", Font.BOLD, 20));
        botao.setBackground(new Color(180, 240, 220));
        botao.setForeground(new Color(0, 51, 102));
        botao.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 102)));
        return botao;
    }



    public static void main(String[] args) {
        new VistaGeral();
    }
}
