package util;

import model.Aluno;
import model.Professor;
import java.io.*;
import java.util.ArrayList;

/**
 * Classe utilitária para persistência de dados em ficheiros binários
 * Utiliza ObjectOutputStream e ObjectInputStream para serialização
 */
public class PersistenciaUtil {

    /**
     * Salva lista de alunos em ficheiro binário
     * @param alunos Lista de alunos a serem salvos
     * @param nomeArquivo Nome do ficheiro
     * @throws IOException Se houver erro na escrita
     */
    public static void salvarAlunos(ArrayList<Aluno> alunos, String nomeArquivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(alunos);
        }
    }

    /**
     * Carrega lista de alunos do ficheiro binário
     * @param nomeArquivo Nome do ficheiro
     * @return Lista de alunos carregada
     * @throws IOException Se houver erro na leitura
     * @throws ClassNotFoundException Se a classe não for encontrada
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Aluno> carregarAlunos(String nomeArquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            return (ArrayList<Aluno>) ois.readObject();
        }
    }

    /**
     * Salva lista de professores em ficheiro binário
     * @param professores Lista de professores a serem salvos
     * @param nomeArquivo Nome do ficheiro
     * @throws IOException Se houver erro na escrita
     */
    public static void salvarProfessores(ArrayList<Professor> professores, String nomeArquivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(professores);
        }
    }

    /**
     * Carrega lista de professores do ficheiro binário
     * @param nomeArquivo Nome do ficheiro
     * @return Lista de professores carregada
     * @throws IOException Se houver erro na leitura
     * @throws ClassNotFoundException Se a classe não for encontrada
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Professor> carregarProfessores(String nomeArquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            return (ArrayList<Professor>) ois.readObject();
        }
    }

    /**
     * Verifica se o ficheiro existe
     * @param nomeArquivo Nome do ficheiro
     * @return true se o ficheiro existe, false caso contrário
     */
    public static boolean arquivoExiste(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        return arquivo.exists() && arquivo.isFile();
    }

    /**
     * Exporta dados para ficheiro de texto
     * @param dados Dados a serem exportados
     * @param nomeArquivo Nome do ficheiro .txt
     * @throws IOException Se houver erro na escrita
     */
    public static void exportarParaTxt(String dados, String nomeArquivo) throws IOException {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write(dados);
        }
    }
}