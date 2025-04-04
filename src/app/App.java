package app;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import infra.FileManager;

public class App {

	public static void main(String[] args) {
		FileManager fileManager = new FileManager();
		
		if(fileManager.ExecutionFileExist()) {
			try {
				List<String> DataFile = fileManager.ReadExecutionFile();
				JOptionPane.showMessageDialog(null, DataFile.toString());
			} catch (IOException e) {
				
			}
		} else {
			JOptionPane.showMessageDialog(null, "Erro: Arquivo de compilação nao existe!");
		}
	}

}
