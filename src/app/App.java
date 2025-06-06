package app;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import data.LexemeData;
import data.GramaticData;
import data.Token;
import infra.FileManager;
import others.FilesPath;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileManager fileManager = new FileManager();
        GramaticData gramaticData = null;

        if (fileManager.FileExist(FilesPath.DefaultGramaticFilePath)) {
            try {
                List<String> GramaticFileList = fileManager.ReadFile(FilesPath.DefaultGramaticFilePath);
                gramaticData = new GramaticData(GramaticFileList);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.err.println("Erro: Arquivo de gramatica (gramatic.txt) nao existe!");
            scanner.close();
            System.exit(0);
        }

        String caminhoDoArquivo = selecionarArquivo(scanner);

        if (fileManager.FileExist(caminhoDoArquivo)) {
            try {
                List<String> ExecutionFileList = fileManager.ReadFile(caminhoDoArquivo);
                
    
                LexemeData lexemeData = new LexemeData(ExecutionFileList, gramaticData);
                List<Token> tokens = lexemeData.getTokens();
                
                System.out.println("\n--- Lista de Tokens Gerados (Saída do Léxico) ---");
                for (Token token : tokens) {
                    System.out.println(token);
                }
                System.out.println("----------------------------------------------------\n");

                AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico(tokens);
                analisadorSintatico.analisar();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.err.println("Erro: Arquivo de execucao '" + caminhoDoArquivo + "' nao existe ou o caminho está incorreto!");
        }
        
        scanner.close();
    }
    
    private static String selecionarArquivo(Scanner scanner) {
        while (true) {
            System.out.println("\n===== SELECIONE O ARQUIVO PARA ANÁLISE =====");
            System.out.println("1 - execution01.txt");
            System.out.println("2 - execution02.txt");
            System.out.println("3 - execution03.txt");
            System.out.println("4 - Digitar o caminho de outro arquivo");
            System.out.print("Digite sua opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    return FilesPath.DefaultExecutionFile01Path;
                case "2":
                    return FilesPath.DefaultExecutionFile02Path;
                case "3":
                    return FilesPath.DefaultExecutionFile03Path;
                case "4":
                    System.out.print("Digite o caminho completo do arquivo: ");
                    return scanner.nextLine();
                default:
                    System.err.println("Opção inválida! Por favor, tente novamente.");
            }
        }
    }
}
