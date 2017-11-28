package books;

import static org.junit.Assert.*;

import org.junit.Test;

public class BookTest {
	Book book = new Book("A Book Title", "Ann Author", 2015, 90.20f);

	@Test
	public void getBookShouldGetAllBookInfo() {
		assertEquals(book.getBook(), "A Book Title by Ann Author published in 2015 costing $90.20");
	}

	@Test
	public void getTitleShouldGetBookTitle() {
		assertEquals(book.getTitle(), "A Book Title");
	}

	@Test
	public void getAuthorShouldGetBookAuthor() {
		assertEquals(book.getAuthor(), "Ann Author");
	}

	@Test
	public void getYearShouldGetBookYear() {
		assertEquals(book.getYear(), 2015);
	}

	@Test
	public void getPriceShouldGetBookPrice() {
		assertEquals((float) book.getPrice(), 90.20f, 0.0f);
	}
	
	@Test
	public void handleEmptyStringShouldHandleNoTitleOrAuthor() {
		Book noTitle = new Book("", "Ann Author", 2017, 1.00f);
		Book noAuthor = new Book("Some Title", "", 2017, 1.01f);
		assertEquals(noTitle.getBook(), "[ ] by Ann Author published in 2017 costing $1.00");
		assertEquals(noAuthor.getBook(), "Some Title by [ ] published in 2017 costing $1.01");
	}

	@Test
	public void removeExtraQuotesShouldConvert2DoubleQuotesTo1() {
		Book extraBook = new Book("How to \"\"Read\"\" a Book", "Ann Author", 2017, 1.00f);
		assertEquals(extraBook.getTitle(), "How to \"Read\" a Book");
	}

	@Test
	public void unknownYearShouldHandleUnknownYear() {
		Book noYear = new Book("My Book", "I Wrote", -1, 3.08f);
		assertEquals(Book.unknownYear(noYear.getYear()), "Unknown year");
	}

	@Test
	public void unknownPriceShouldHandleUnknownPrice() {
		Book noPrice = new Book("My Other Book", "I Wrote", 2000, -1.00f);
		assertEquals(Book.unknownPrice(Book.formatPrice(noPrice.getPrice())), "Unknown price");
	}

	@Test
	public void formatPriceShouldFormatPriceCorrectly() {
		assertEquals(Book.formatPrice(book.getPrice()), "$90.20");
	}
}
