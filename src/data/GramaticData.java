package data;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class GramaticData {
	List<String> GramaticData;
	Dictionary<String, Integer> GramaticTokens;
	
	public GramaticData(List<String> pGramaticData) {
		GramaticData = pGramaticData;
		CreateDictionary();
	}
	
	public void CreateDictionary() {
		GramaticTokens = new Hashtable();
		for (int i = 0; i < GramaticData.size(); i++) {
			String[] CodeAndToken = GramaticData.get(i).split(" ");
			GramaticTokens.put(CodeAndToken[1], Integer.parseInt(CodeAndToken[0]));
		}
		System.out.println(GramaticTokens.toString());
	}
	
	public int SearchDictionary(String lexema) {
		if(GramaticTokens.get(lexema) != null) {
			return GramaticTokens.get(lexema);
		}
		return 0;
	}
	
	
}
