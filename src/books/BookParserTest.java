package books;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.Test;

public class BookParserTest {
	BookParser bookParser = new BookParser();

	@Test
	public void parseShouldParseBooksFromFile() throws FileNotFoundException {
		List<Book> books = bookParser.parse("res/books.csv");
		assertEquals(books.size(), 6);
		assertEquals(books.get(0).getBook(), "The Year’s Best Science Fiction & Fantasy 2017 Edition by Rich Horton published in 2017 costing $19.95");
		assertEquals(books.get(1).getBook(), "The Year's Best Science Fiction: Thirty-Fourth Annual Collection by Gardner Dozois published in 2017 costing $15.17");
		assertEquals(books.get(2).getBook(), "The Science Fiction Hall of Fame, Vol. 1: 1929-1964 by Robert Silverberg published in 2005 costing $21.99");
		assertEquals(books.get(3).getBook(), "Marked by Fate: A Young Adult Science Fiction Collection with Augmented Reality: Read, Watch, Listen. The new ultimate reading experience by Kristin D. Van Risseghem;Rhonda Sermon published in 2017 costing $3.99");
		assertEquals(books.get(4).getBook(), "The Art of Michael Whelan by Michael Whelan published in 1993 costing $7.50");
		assertEquals(books.get(5).getBook(), "Storming the Reality Studio: A Casebook of Cyberpunk & Postmodern Science Fiction by Larry McCaffery published in 1991 costing $22.94");
	}

	@Test
	public void parseShouldSkipEmptyLines() throws FileNotFoundException {
		List<Book> books = bookParser.parse("res/books2.csv");
		assertEquals(books.size(), 2);
		assertEquals(books.get(0).getBook(), "Book Title, 1 by May De-up Prénom published in 2010 costing $15.00");
		assertEquals(books.get(1).getBook(), "\"Book Title,\" 2 by May De-up Prénom published in 2011 costing $15.01");
	}
	
	@Test
	public void parseShouldReturnEmptyListIfNoBooksInFile() throws FileNotFoundException {
		List<Book> books = bookParser.parse("res/books3.csv");
		assertEquals(books.size(), 0);
	}

	@Test
	public void parseShouldShouldHandleNoBooks() throws FileNotFoundException {
		bookParser.parse("res/books3.csv");
		assertEquals(bookParser.getHighestPrice(), null);
		assertEquals(bookParser.getLowestPrice(), null);
		assertEquals(bookParser.getAvgPrice(), 0.0f, 0.0f);
		assertEquals(bookParser.getOldBooks().size(), 0);
		assertEquals(bookParser.getBooksFiveYears().size(), 0);
	}

	@Test
	public void parseShouldHandleBooksWithMissingFields() throws FileNotFoundException {
		List<Book> books = bookParser.parse("res/books4.csv");
		assertEquals(books.size(), 3);
		assertEquals(books.get(0).getBook(), "Book With No Author by [ ] published in 1990 costing $2.03");
		assertEquals(books.get(1).getBook(), "[ ] by Author Untitled Book published in 2007 costing $1.97");
		assertEquals(books.get(2).getBook(), "Very, Very, Very Old Book by Old Author published in Unknown year costing Unknown price");
	}

	@Test(expected = FileNotFoundException.class)
	public void parseThrowsFileNotFoundException() throws FileNotFoundException {
		bookParser.parse("books.csv");
	}

	@Test
	public void getHighestPriceShouldGetHighestPricedBook() throws FileNotFoundException {
		bookParser.parse("res/books.csv");
		assertEquals(bookParser.getHighestPrice().getBook(), "Storming the Reality Studio: A Casebook of Cyberpunk & Postmodern Science Fiction by Larry McCaffery published in 1991 costing $22.94");
	}

	@Test
	public void getLowestPriceShouldGetLowestPricedBook() throws FileNotFoundException {
		bookParser.parse("res/books.csv");
		assertEquals(bookParser.getLowestPrice().getBook(), "Marked by Fate: A Young Adult Science Fiction Collection with Augmented Reality: Read, Watch, Listen. The new ultimate reading experience by Kristin D. Van Risseghem;Rhonda Sermon published in 2017 costing $3.99");
	}

	@Test
	public void getAvgPriceShouldGetAveragePriceOfAllBooks() throws FileNotFoundException {
		bookParser.parse("res/books.csv");
		assertEquals((float)bookParser.getAvgPrice(), 15.26f, 0.0f);
		
		bookParser = new BookParser();
		bookParser.parse("res/books2.csv");
		assertEquals((float)bookParser.getAvgPrice(), 15.01f, 0.0f);
	}

	@Test
	public void getOldBooksShouldGetBooksOlderThan10Years() throws FileNotFoundException {
		bookParser.parse("res/books.csv");
		List<Book> oldBooks = bookParser.getOldBooks();
		assertEquals(oldBooks.size(), 3);
		assertEquals(oldBooks.get(0).getBook(), "The Science Fiction Hall of Fame, Vol. 1: 1929-1964 by Robert Silverberg published in 2005 costing $21.99");
		assertEquals(oldBooks.get(1).getBook(), "The Art of Michael Whelan by Michael Whelan published in 1993 costing $7.50");
		assertEquals(oldBooks.get(2).getBook(), "Storming the Reality Studio: A Casebook of Cyberpunk & Postmodern Science Fiction by Larry McCaffery published in 1991 costing $22.94");
		
		bookParser = new BookParser();
		bookParser.parse("res/books2.csv");
		List<Book> oldBooks2 = bookParser.getOldBooks();
		assertEquals(oldBooks2.size(), 0);
	}

	@Test
	public void getBooksFiveYearsShouldGetBooksInLast5Years() throws FileNotFoundException {
		bookParser.parse("res/books.csv");
		List<Book> booksFiveYears = bookParser.getBooksFiveYears();
		assertEquals(booksFiveYears.size(), 3);
		assertEquals(booksFiveYears.get(0).getBook(), "The Year’s Best Science Fiction & Fantasy 2017 Edition by Rich Horton published in 2017 costing $19.95");
		assertEquals(booksFiveYears.get(1).getBook(), "The Year's Best Science Fiction: Thirty-Fourth Annual Collection by Gardner Dozois published in 2017 costing $15.17");
		assertEquals(booksFiveYears.get(2).getBook(), "Marked by Fate: A Young Adult Science Fiction Collection with Augmented Reality: Read, Watch, Listen. The new ultimate reading experience by Kristin D. Van Risseghem;Rhonda Sermon published in 2017 costing $3.99");
		
		bookParser = new BookParser();
		bookParser.parse("res/books2.csv");
		List<Book> booksFiveYears2 = bookParser.getBooksFiveYears();
		assertEquals(booksFiveYears2.size(), 0);
	}
}
