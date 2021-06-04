import java.io.RandomAccessFile;
import java.io.IOException;

public class ReadBooksDB {

	private RandomAccessFile booksDB;
	private int numBooks;

	public ReadBooksDB (String fileName) throws IOException {
		booksDB = new RandomAccessFile (fileName, "rw");
		numBooks = (int)booksDB.length() / BookInfo.SIZE;
	}

	public int getNumBooks() {
		return numBooks;
	}

	public void close() throws IOException {
		booksDB.close();
	}

	public void reset() throws IOException {
		booksDB.setLength (0);
		numBooks = 0;
	}

	public void writeBookInfo (int n, BookInfo book) throws IOException {
		booksDB.seek(n * BookInfo.SIZE);
		byte[] array = book.toBytes();
		booksDB.write(array); 
	}

	public BookInfo readBookInfo (int n) throws IOException {
		booksDB.seek(n * BookInfo.SIZE);
		byte[] array = new byte[BookInfo.SIZE];
		booksDB.read(array);
		return BookInfo.fromBytes(array);

	}

	public void appendBookInfo (BookInfo book) throws IOException {
		writeBookInfo (numBooks, book);
		numBooks++;
	}

	public int searchBookByTitle (String title) throws IOException {
		throw new UnsupportedOperationException ("TO-DO");
	}

	public boolean deleteByTitle (String title) throws IOException {
		throw new UnsupportedOperationException ("TO-DO");
	}

}
