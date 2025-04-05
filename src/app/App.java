package app;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import data.LexemeData;
import data.GramaticData;
import infra.FileManager;
import others.FilesPath;

public class App {

	public static void main(String[] args) {
		FileManager fileManager = new FileManager();
		
		GramaticData gramaticData = null;
		LexemeData lexemeData = null;
		
		if(fileManager.FileExist(FilesPath.DefaultGramaticFilePath)) {
			try {
				List<String> GramaticFileList = fileManager.ReadFile(FilesPath.DefaultGramaticFilePath);
				gramaticData = new GramaticData(GramaticFileList);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(null, "Erro: Arquivo de gramatica nao existe!");
			System.exit(0);
		}
		
		if(fileManager.FileExist(FilesPath.DefaultExecutionFilePath)) {
			try {
				List<String> ExecutionFileList = fileManager.ReadFile(FilesPath.DefaultExecutionFilePath);
				lexemeData = new LexemeData(ExecutionFileList, gramaticData);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(null, "Erro: Arquivo de compilação nao existe!");
		}
		
		System.out.println(lexemeData.getCods());
		JOptionPane.showMessageDialog(null, "Análise Lexical: \n" + lexemeData.getCods());
	}

}
