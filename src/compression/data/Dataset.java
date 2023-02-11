package compression.data;

import compression.LocalConfig;
import compression.grammar.RNAWithStructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class Dataset implements Iterable<RNAWithStructure> {

	public final String name;
	private File RNAFolder;



	public Dataset(final String name) {

		this.name = name;
		this.RNAFolder = new File(LocalConfig.GIT_ROOT + "/datasets/" + name);
		System.out.println("rna folder is: "+ RNAFolder.toString());
		System.out.println(RNAFolder.exists() +" "+ RNAFolder.isDirectory() +" "+ RNAFolder.listFiles()
				+ RNAFolder.listFiles());
		if (!RNAFolder.exists() || !RNAFolder.isDirectory()
				|| RNAFolder.listFiles() == null
				|| RNAFolder.listFiles().length == 0) {
			throw new IllegalArgumentException("Dataset " + name + " is not a valid dataset folder.");
		}
	}

	public static RNAWithStructure readRNA(File file) throws IOException {
		Scanner rnaFile = new Scanner(file);//reads next file containing
		//sample RNA string
		String RNA = rnaFile.nextLine();//stores the primary sequence
		String DB = rnaFile.nextLine();//stores the dot bracket string
		//System.out.println("RNA VALUE IS: " + RNA +" DB Value is :" + DB);
		//RNA = "GCU";
		//DB = "(.)";
		//System.out.println("RNA VALUE IS: " + RNA);
		// split string into list of tokens (terminals)
		return new RNAWithStructure(RNA, DB, file.getName());
	}
	public RNAWithStructure getRNA (String filename) throws IOException{
		BufferedReader rnaFile = new BufferedReader(new FileReader(LocalConfig.GIT_ROOT + "/datasets/" + this.name+"/"+filename));
		String RNA=rnaFile.readLine();
		String DB=rnaFile.readLine();
		return new RNAWithStructure(RNA,DB,filename);
	}

	public int getSize (){
		return RNAFolder.listFiles().length;
	}
	@Override
	public Iterator<RNAWithStructure> iterator() {
		Iterator<File> iterator = Arrays.asList(RNAFolder.listFiles()).iterator();

		return new Iterator<RNAWithStructure>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public RNAWithStructure next() {
				try {
					File file = iterator.next();
					RNAWithStructure RNAWS = readRNA(file);
					return RNAWS;
				} catch (IOException e) {
					throw new DatasetFileIOException(e);
				}
			}
		};

	}

	@Override
	public String toString() {
		return "Dataset(" +
				"name='" + name + '\'' +
				')';
	}
}
