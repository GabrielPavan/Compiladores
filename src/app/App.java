package app;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import data.GramaticData;
import infra.FileManager;
import others.FilesPath;

public class App {

	public static void main(String[] args) {
		FileManager fileManager = new FileManager();
		
		if(fileManager.FileExist(FilesPath.DefaultGramaticFilePath)) {
			try {
				List<String> GramaticFileList = fileManager.ReadFile(FilesPath.DefaultGramaticFilePath);
				GramaticData gramaticData = new GramaticData(GramaticFileList);
				
			} catch (IOException e) {
				
			}
		} else {
			JOptionPane.showMessageDialog(null, "Erro: Arquivo de gramatica nao existe!");
			System.exit(0);
		}
		
		
		if(fileManager.FileExist(FilesPath.DefaultExecutionFilePath)) {
			try {
				List<String> ExecutionFile = fileManager.ReadFile(FilesPath.DefaultExecutionFilePath);
				
			} catch (IOException e) {
				
			}
		} else {
			JOptionPane.showMessageDialog(null, "Erro: Arquivo de compilação nao existe!");
		}
	}

}
