import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import acm.program.ConsoleProgram;

public class Main extends ConsoleProgram {

	private String BOOKS_FILES = "new_books.txt";
	private String BOOKS_DB_NAME = "booksDB.dat";
	private ReadBooksDB rBooksDB;

	public void run() {
		try {
			rBooksDB = new ReadBooksDB (BOOKS_DB_NAME);
			loadFromFiles();
		} catch (IOException ex) {
			println ("Error generating database!");
			System.exit (-1);
		}
		for (;;) {
			printMenu();
			int option = getOption();
			switch (option) {
				case 1:
					listTitles();
					break;
				case 2:
					infoFromOneBook();
					break;
				case 3:
					deleteBook();
					break;
				case 4:
					quit();
					break;
			}
			println ();
		}
	}

	private void printMenu() {
		println ("Options menu:");
		println ("1 - List all titles.");
		println ("2 - Get the information from one book.");
		println ("3 - Delete a book.");
		println ("4 - Quit.");
	}

	private int getOption() {
		int option;
		do {
			option = readInt ("Select an option: ");
		} while (option <= 0 || option > 4);
		return option;
	}

	private void loadFromFiles() throws IOException {
		rBooksDB.reset();
		BufferedReader input = new BufferedReader (new FileReader (BOOKS_FILES));
		String fileName = input.readLine();
		while (fileName != null) {
			BookInfo book = BookInfoReader.readBookFile (fileName);
			rBooksDB.appendBookInfo (book);
			fileName = input.readLine();
		}
		input.close();
	}

	private void listTitles() {
		int numBooks = rBooksDB.getNumBooks();
		println ();
		try {
			for (int i = 0; i < numBooks; i++) {
				BookInfo book = rBooksDB.readBookInfo (i);
				println (book.getTitle());
			}
		} catch (IOException ex) {
			println ("Database error!");
		}
	}

	private void infoFromOneBook() {
		String title = readLine ("Type the title of the book: ");
		try {
			int n = rBooksDB.searchBookByTitle (title);
			if (n != -1) {
				BookInfo book = rBooksDB.readBookInfo (n);
				println (book);
				if (book.isFinished()) {
					int days = book.daysToRead();
					int pages = book.getPages();
					print ("It took " + days + " days to read ");
					print ("with an average of " + (float)pages / days);
					println (" pages per day.");
				}
			} else {
				println ("Book not found.");
			}
		} catch (IOException ex) {
			println ("Database error!");
		}
	}

	private void deleteBook() {
		String title = readLine ("Type the title of the book to delete: ");
		try {
			boolean success = rBooksDB.deleteByTitle (title);
			if (!success) {
				println ("Book not found.");
			}
		} catch (IOException ex) {
			println ("Database error!");
		}
	}

	private void quit() {
		try {
			rBooksDB.close();
			System.exit (0);
		} catch (IOException ex) {
			println ("Error closing database!");
			System.exit (-1);
		}
	}

}
