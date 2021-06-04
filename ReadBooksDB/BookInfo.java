import java.time.LocalDateTime;

import javax.swing.text.DefaultEditorKit.CutAction;

import java.time.Duration;

public class BookInfo {

	private String  title;
	private String author;
	private String series;
	private int pages;
	private boolean finished;
	private String startDate;
	private String   endDate;

	private static final int  TITLE_LIMIT = 32;
	private static final int AUTHOR_LIMIT = 32;
	private static final int SERIES_LIMIT = 32;
	private static final int   DATE_LIMIT = 10;

	public static final int SIZE = 2*TITLE_LIMIT + 2*AUTHOR_LIMIT + 2*SERIES_LIMIT + 4 + 1 + 2*2*DATE_LIMIT;

	public BookInfo (String title, String author, String series, int pages,
	                 boolean finished, String startDate, String endDate) {
		this.title = title;
		this.author = author;
		this.series = series;
		this.pages = pages;
		this.finished = finished;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	// Getters
	public String getTitle     () { return  title;    }
	public String getAuthor    () { return  author;   }
	public String getSeries    () { return  series;   }
	public   int  getPages     () { return  pages;    }
	public boolean isFinished  () { return finished;  }
	public String getStartDate () { return startDate; }
	public String getEndDate   () { return   endDate; }

	public byte[] toBytes() {
		byte[] record = new byte[SIZE];
		int cursor = 0;
		PackUtils.packLimitedString(title, 2*TITLE_LIMIT, record, cursor);
		cursor += 2*TITLE_LIMIT;
		PackUtils.packLimitedString(author, 2*AUTHOR_LIMIT, record, cursor);
		cursor += 2*AUTHOR_LIMIT;
		PackUtils.packLimitedString(series, 2*SERIES_LIMIT, record, cursor);
		cursor += 2*SERIES_LIMIT;
		PackUtils.packInt(pages, record, cursor);;
		cursor += 4;
		PackUtils.packBoolean(finished, record, cursor);
		cursor += 1;
		PackUtils.packLimitedString(startDate, 2*DATE_LIMIT, record, cursor);
		cursor += 2*DATE_LIMIT;
		PackUtils.packLimitedString(endDate, 2*DATE_LIMIT, record, cursor);
		
		return record;
	}

	public static BookInfo fromBytes (byte[] record) {
		int cursor = 0;
		String title = PackUtils.unpackLimitedString(2*TITLE_LIMIT, record, cursor);
		cursor += 2*TITLE_LIMIT;
		String autor = PackUtils.unpackLimitedString(2*AUTHOR_LIMIT, record, cursor);
		cursor += 2*AUTHOR_LIMIT;
		String series = PackUtils.unpackLimitedString(2*SERIES_LIMIT, record, cursor);
		cursor += 2*SERIES_LIMIT;
		int pages = PackUtils.unpackInt(record, cursor);
		cursor += 4;
		Boolean finished = PackUtils.unpackBoolean(record, cursor);
		cursor += 1;
		String start = PackUtils.unpackLimitedString(2*DATE_LIMIT, record, cursor);
		cursor *= 2*DATE_LIMIT;
		String end = PackUtils.unpackLimitedString(2*DATE_LIMIT, record, cursor);

		return new BookInfo (title,autor,series,pages,finished,start,end);
	}

	public int daysToRead() {
		if (finished) {
			LocalDateTime start = LocalDateTime.parse (startDate + "T00:00:00");
			LocalDateTime  end  = LocalDateTime.parse (  endDate + "T00:00:00");
			Duration duration = Duration.between (start, end);
			return (int)duration.toDays() + 1;
		} else {
			return -1;
		}
	}

	public String toString() {
		String result = title
		              + " (" + series + ")"
		              + " by " + author
		              + " with " + pages + " pages.";
		if (finished) {
			result += " Read from " + startDate + " to " + endDate + ".";
		} else if (!startDate.isEmpty()) {
			result += " Started on " + startDate + ".";
		}
		return result;
	}

}
