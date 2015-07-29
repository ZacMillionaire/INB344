import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Tokenize t = new Tokenize("cacm");
		
		// Output data to csv
		try(PrintWriter f = new PrintWriter("data.csv")){
			f.write("Token, Count\n");
			t._tokenList.forEach((k,v) -> {
				f.write(String.format("%s,%d\n",k,v));
			});
			f.write("total,"+t._totalTokens);
		}
		System.out.print("Finished");
	}
}
