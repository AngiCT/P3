import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class BookInfoReader {

	public static BookInfo readBookFile (String fileName) throws IOException {
		
		BufferedReader in = new BufferedReader(new FileReader(fileName));

		String title = in.readLine();
		String autor = in.readLine();
		String series = in.readLine();
		int pages = Integer.parseInt(in.readLine());
		String startDate = in.readLine();
		String endDate = in.readLine();
		Boolean finished = endDate != null;
		
		in.close();
		
		return new BookInfo(
			(title == null) ? "" : title,
			(autor == null) ? "" : autor,
			(series == null) ? "" : series,
			pages,
			finished,
			(startDate == null) ? "" : startDate,
			(endDate == null) ? "" : endDate
		);
	}

}
