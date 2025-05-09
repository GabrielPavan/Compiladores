package app;

import java.io.IOException;
import java.util.List;
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
			System.err.println("Erro: Arquivo de gramatica nao existe!");
			System.exit(0);
		}
		
		if(fileManager.FileExist(FilesPath.DefaultExecutionFile01Path)) {
			try {
				//Altere qual arquivo quer executar
				List<String> ExecutionFileList = fileManager.ReadFile(FilesPath.DefaultExecutionFile01Path);
				//List<String> ExecutionFileList = fileManager.ReadFile(FilesPath.DefaultExecutionFile02Path);
				//List<String> ExecutionFileList = fileManager.ReadFile(FilesPath.DefaultExecutionFile03Path);
				lexemeData = new LexemeData(ExecutionFileList, gramaticData);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.err.println("Erro: Arquivo de compilação nao existe!");
			System.exit(0);
		}
		
		System.out.println(lexemeData.getCods());
	}

}
