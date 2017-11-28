package books;

import java.io.FileNotFoundException;
import java.util.List;

/* 
 * List of parsed books.
 */
public class Books {
	private List<Book> books;
	private BookParser bookParser;

	/* 
	 * Books constructor 
	 * Throws error if CSV not found
	 */
	protected Books(String file) throws FileNotFoundException {
		try {
			bookParser = new BookParser();
			books = bookParser.parse(file);
		} catch(FileNotFoundException e) {
			throw e;
		}
	}

	/* 
	 * Gets list of books
	 */
	protected List<Book> getBooks() {
		return books;
	}

	/* 
	 * Creates String of special information about the books
	 */
	protected String getBooksInfo() {
		if (books.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("Highest priced book: " + bookParser.getHighestPrice().getBook() + "\n");
			sb.append("Lowest priced book: " + bookParser.getLowestPrice().getBook() + "\n");
			sb.append("Average price: " + Book.formatPrice(bookParser.getAvgPrice()) + "\n");
			sb.append("Books older than 10 years: \n\t");
			for (Book book : bookParser.getOldBooks()) {
				sb.append(Book.handleEmptyString(book.getTitle()) + "\n\t");
			}
			sb.setLength(sb.length() - 1);
			sb.append("Authors in the last 5 years: \n\t");
			for (Book book : bookParser.getBooksFiveYears()) {
				sb.append(Book.handleEmptyString(book.getAuthor()) + "\n\t");
			}
			sb.setLength(sb.length() - 2);
			return sb.toString();
		}
		return "No books";
	}
}
