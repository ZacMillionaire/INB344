import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Tokenize t = new Tokenize("cacm");
		
		// Output data to csv
		try(PrintWriter f = new PrintWriter("data.csv")){
			f.write("token, occurance, frequency\n");
			t._tokenList.forEach((k,v) -> {
				f.write(String.format("%s,%d,%f\n",k,v,(float)v/t._uniqueTokenCount));
			});
			f.write("total,"+t._uniqueTokenCount);
		}
		System.out.print("Finished");
	}
}
