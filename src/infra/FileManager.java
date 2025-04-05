package infra;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
	
	private InputStream InputStream;
	private InputStreamReader InputStreamReader;
	private BufferedReader BufferedReader;

	private OutputStream OutputStream;
	private OutputStreamWriter OutputStreamWriter;
	private BufferedWriter BufferedWriter;

	public void BufferFileReader(String FilePath) throws IOException {
		InputStream = new FileInputStream(FilePath);
		InputStreamReader = new InputStreamReader(InputStream);
		BufferedReader = new BufferedReader(InputStreamReader);
	}

	public void BufferFileWriter(String FilePath, boolean append) throws IOException {
		OutputStream = new FileOutputStream(FilePath, append);
		OutputStreamWriter = new OutputStreamWriter(OutputStream);
		BufferedWriter = new BufferedWriter(OutputStreamWriter);
	}
	
	public List<String> ReadFile(String path) throws IOException {
		BufferFileReader(path);
		String ReadLine;
		List<String> DataList = new ArrayList<String>();
		while ((ReadLine = BufferedReader.readLine()) != null) {
			DataList.add(ReadLine);
		}
		closeFile();
		return DataList;
	}

	public boolean FileExist(String path) {
		File file = new File(path);
		return file.exists();
	}
	
	public void closeFile() throws IOException {
		if (BufferedReader != null)
			BufferedReader.close();
		if (BufferedWriter != null)
			BufferedWriter.close();
	}
}
