package data;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class GramaticData {
	List<String> GramaticData;
	Dictionary<String, Integer> GramaticTokens;
	
	public GramaticData(List<String> pGramaticData) {
		GramaticData = pGramaticData;
		CreateDictionary();
		InformaGramatica();
	}
	
	private void CreateDictionary() {
		GramaticTokens = new Hashtable<String, Integer>();
		for (int i = 0; i < GramaticData.size(); i++) {
			String[] CodeAndToken = GramaticData.get(i).split(" ");
			GramaticTokens.put(CodeAndToken[1], Integer.parseInt(CodeAndToken[0]));
		}		
	}
	public int SearchToken(String lexema) {
		if(GramaticTokens.get(lexema) != null) {
			return GramaticTokens.get(lexema);
		}
		return 0;
	}
	public void InformaGramatica() {
		System.out.println("Seguinte relaçao de tokens e codigos foi utilizada: \n");
		for (String string : GramaticData) {
			System.out.println(string);
		}
		System.out.println();
	}
}