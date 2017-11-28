package books;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class BooksTest {
	Books books;

	@Test
	public void getBooksShouldGetListOfAllBooks() throws FileNotFoundException {
		books = new Books("res/books.csv");
		assertEquals(books.getBooks().size(), 6);
		assertEquals(books.getBooks().get(0).getBook(), "The Year’s Best Science Fiction & Fantasy 2017 Edition by Rich Horton published in 2017 costing $19.95");
		assertEquals(books.getBooks().get(1).getBook(), "The Year's Best Science Fiction: Thirty-Fourth Annual Collection by Gardner Dozois published in 2017 costing $15.17");
		assertEquals(books.getBooks().get(2).getBook(), "The Science Fiction Hall of Fame, Vol. 1: 1929-1964 by Robert Silverberg published in 2005 costing $21.99");
		assertEquals(books.getBooks().get(3).getBook(), "Marked by Fate: A Young Adult Science Fiction Collection with Augmented Reality: Read, Watch, Listen. The new ultimate reading experience by Kristin D. Van Risseghem;Rhonda Sermon published in 2017 costing $3.99");
		assertEquals(books.getBooks().get(4).getBook(), "The Art of Michael Whelan by Michael Whelan published in 1993 costing $7.50");
		assertEquals(books.getBooks().get(5).getBook(), "Storming the Reality Studio: A Casebook of Cyberpunk & Postmodern Science Fiction by Larry McCaffery published in 1991 costing $22.94");
	}

	@Test
	public void getBooksInfoShouldGetAllSpecialInfoOnBooks() throws FileNotFoundException {
		books = new Books("res/books.csv");
		assertEquals(books.getBooksInfo(), "Highest priced book: Storming the Reality Studio: A Casebook of Cyberpunk & Postmodern Science Fiction by Larry McCaffery published in 1991 costing $22.94\n" + 
				"Lowest priced book: Marked by Fate: A Young Adult Science Fiction Collection with Augmented Reality: Read, Watch, Listen. The new ultimate reading experience by Kristin D. Van Risseghem;Rhonda Sermon published in 2017 costing $3.99\n" + 
				"Average price: $15.26\n" + 
				"Books older than 10 years: \n\t" + 
				"The Science Fiction Hall of Fame, Vol. 1: 1929-1964\n\t" + 
				"The Art of Michael Whelan\n\t" + 
				"Storming the Reality Studio: A Casebook of Cyberpunk & Postmodern Science Fiction\n" + 
				"Authors in the last 5 years: \n\t" + 
				"Rich Horton\n\t" + 
				"Gardner Dozois\n\t" + 
				"Kristin D. Van Risseghem;Rhonda Sermon");
	}
	
	@Test
	public void getBooksInfoShouldHandleNoBooks() throws FileNotFoundException {
		books = new Books("res/books3.csv");
		assertEquals(books.getBooksInfo(), "No books");
	}

	@Test(expected = FileNotFoundException.class)
	public void parseThrowsFileNotFoundException() throws FileNotFoundException {
		new Books("books.csv");
	}

}
