package compression.util;


import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author Sebastian Wild (wild@liverpool.ac.uk)
 */
public class CSVFile implements Closeable {

	private final File file;
	private final BufferedWriter out;
	private final int nCols;

	public CSVFile(final File file, String... headers) throws IOException {
		this.file = file;
		out = new BufferedWriter(new FileWriter(file));
		nCols = headers.length;
		for (int i = 0; i < headers.length; i++) {
			if (i > 0) out.append(",");
			out.append(csvify(headers[i]));
		}
		out.newLine();
		out.flush();
	}

	public CSVFile(final File file, List<String> headers) throws IOException {
		this(file, headers.toArray(new String[0]));
	}

	public static String csvify(String cell) {
		boolean hasComma = cell.indexOf(',') != -1;
		boolean hasNewline = cell.indexOf('\n') != -1;
		boolean hasQuot = cell.indexOf('"') != -1;
		if (!hasComma && !hasQuot && !hasNewline) return cell;
		if (cell.matches("\"[^\"]*\"")) return cell;
		return '"' + cell.replaceAll("\"", "\"\"") + '"';
	}

	public CSVFile appendRow(String... cells) throws IOException {
		if (cells.length != nCols) throw new IllegalArgumentException();
		for (int i = 0; i < cells.length; i++) {
			if (i > 0) out.append(",");
			out.append(csvify(cells[i]));
		}
		out.newLine();
		out.flush();
		return this;
	}

	public CSVFile appendRow(List<String> cells) throws IOException {
		return appendRow(cells.toArray(new String[0]));
	}

	@Override
	public void close() throws IOException {
		out.close();
	}

	public static void main(String[] args) throws IOException {
		CSVFile csv = new CSVFile(new File("/tmp/test.csv"),"name","age");
		csv.appendRow("Karl","17");
		csv.appendRow("\"Trude\"","14");
	}
}
