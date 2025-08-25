package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    public Login(){
        getContentPane().setBackground(new Color(180, 240, 220));
        setSize(500,250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(true);
        setTitle("JANELA DE ABERTURA");


        JLabel lbUser = new JLabel("USER");
        JLabel lbSenha =  new JLabel("SENHA");

        lbUser.setBounds(50, 40, 80, 40);
        lbUser.setForeground(new Color(0, 102, 102));
        lbUser.setFont(new Font("Calibri", Font.BOLD, 20));

        lbSenha.setBounds(50, 80, 80, 40);
        lbSenha.setForeground(new Color(0, 102, 102));
        lbSenha.setFont(new Font("Calibiri", Font.BOLD, 18));

        JTextField tfUser = new JTextField(60);
        tfUser.setBounds(140, 40, 200, 30);
        tfUser.setFont(new Font("Time New Roman", Font.ITALIC, 20));

        JPasswordField pwSenha = new JPasswordField(60);
        pwSenha.setBounds(140, 80, 200, 30);
        pwSenha.setFont(new Font("Time New Roman", Font.ITALIC, 20));

        JButton btAv = new JButton("AVANCAR");
        btAv.setBounds(50, 170, 150, 35);
        btAv.setForeground(Color.WHITE);
        btAv.setBackground(new Color(0, 102, 102));
        btAv.setFont(new Font("algerbrian", Font.BOLD, 20));

        JButton btCanc =  new JButton("CANCELAR");
        btCanc.setBounds(280, 170, 150, 35);
        btCanc.setForeground(Color.WHITE);
        btCanc.setBackground(new Color(0, 102, 102));
        btCanc.setFont(new Font("algerbrian", Font.BOLD, 20));


        add(lbUser);
        add(lbSenha);
        add(tfUser);
        add(pwSenha);
        add(btAv);
        add(btCanc);

        setVisible(true);

        btAv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Nome = tfUser.getText();
                String Acess  = new String(pwSenha.getPassword());

                try{
                    if(Nome.equals("0000")&&(Acess.equals("0000"))){
                        JOptionPane.showMessageDialog(null, "ACESSO PERMITIDO", "CONFIRMACAOO", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(null, "SISTEMA DE GERENCIAMENTO DE PROFESSORES E ALUNOS", "BOAS VINDAS", JOptionPane.INFORMATION_MESSAGE);

                         dispose();
                         new VistaGeral();
                        } else{
                        JOptionPane.showMessageDialog(null, "Digitou credênciais erradas!", "ERRO AO TENTAR ACESSAR AO SISTEMA", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex){
                    throw new RuntimeException(ex);
                }

            }
        });

        btCanc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfUser.setText("");
                pwSenha.setText("");
            }
        });

    }
    public static void main(String[] args) {new Login();
    }

}


