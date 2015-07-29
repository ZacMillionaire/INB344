import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Tokenize {
	
	private String _fileDirectory;
	
	// Public because there's no reason to keep them private
	public HashMap<String, Integer> _tokenList = new HashMap<String,Integer>();
	public List<String> _uniqueTokens = new ArrayList<String>();
	public int _totalTokens, _uniqueTokenCount, _fileCount;
	

	public Tokenize(String path) {
		try {
			this._fileDirectory = path;
			this.WalkFiles();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	} // End Constructor
	
	public HashMap<String,Integer> GetTokenStats() {
		
		// because creating a initial set makes too much sense in data, I have
		// to use .put to add each value.
		// I guess return new HashMap<String,Integer>() { {Key:Value},{...}, ...}
		// would make too much sense.
		HashMap<String,Integer> couldJustBeReturnNewHashMap = new HashMap<String,Integer>();
		couldJustBeReturnNewHashMap.put("Total Tokens", _totalTokens);
		couldJustBeReturnNewHashMap.put("Unique Tokens", _uniqueTokenCount);
		couldJustBeReturnNewHashMap.put("Documents Scanned", _fileCount);
		
		// The hell is wrong with Java
		return couldJustBeReturnNewHashMap;
	} // end public HashMap<String,Integer> GetTokenStats()
	
	
	private void WalkFiles() throws IOException {
		File d = new File(_fileDirectory);
		File[] corpus = d.listFiles();
		
		for(File f : corpus){
			ReadLines(f);
			_fileCount++;
		}
	} // End private void WalkFiles()
	
	
	private void ReadLines(File f) throws FileNotFoundException, IOException {
		try(BufferedReader buf = new BufferedReader(new FileReader(f))){
			String l;
			while((l = buf.readLine()) != null){
				ParseLine(l);
			}
		}
	} // End private void ReadLines(File)
	
	
	private void ParseLine(String l){
		// Can't use (.*?)[,\s] because then I don't get the last match
		// (or only match) on a line.
		// Why? Who knows! Above regex would work fine in any other language.
		String p = "([\\w\\.\\-\\<\\>]+){2,}"; // include html tags and hyphenated words
		Pattern regex = Pattern.compile(p);
		Matcher matches = regex.matcher(l);
		
		while(matches.find()){
			
			String m = matches.group().toLowerCase();
			
			if(!_uniqueTokens.contains(m)){
				_uniqueTokens.add(m);
				_uniqueTokenCount++;
			}
			
			if(!_tokenList.containsKey(m)){
				_tokenList.put(m, 1);
			} else if(_tokenList.containsKey(m)){
				_tokenList.put(m, _tokenList.get(m)+1);
			}
			
			_totalTokens++;
		} // End while(matches)
	} // End private void ParseLine(String)
} // End Class Tokenizer
