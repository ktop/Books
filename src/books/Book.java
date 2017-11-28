package books;

import java.util.regex.Pattern;

/* 
 * Book object which has information about the book
 */
public class Book {
	private String title;
	private String author;
	private int year;
	private float price;

	/* 
	 * Book constructor
	 */
	protected Book(String title, String author, int year, float price) {
		this.title = removeExtraQuotes(title);
		this.author = author;
		this.year = year;
		this.price = price;
	}

	/* 
	 * Get all book information
	 */
	protected String getBook() {
		return handleEmptyString(title) + " by " + handleEmptyString(author) + " published in " + unknownYear(year) + " costing " + unknownPrice(formatPrice(price));
	}

	/* 
	 * Get book title 
	 */
	protected String getTitle() {
		return title;
	}

	/* 
	 * Get book author 
	 */
	protected String getAuthor() {
		return author;
	}

	/* 
	 * Get book publishing year
	 */
	protected int getYear() {
		return year;
	}

	/* 
	 * Get book price
	 */
	protected float getPrice() {
		return price;
	}
	
	/* 
	 * Handle empty string like title or author and replaces it with [ ]
	 */
	protected static String handleEmptyString(String str) {
		if (str.length() == 0) {
			return "[ ]";
		}
		return str;
	}
	
	/* 
	 * Remove extra double-quotes from String
	 */
	private String removeExtraQuotes(String title) {
		Pattern p = Pattern.compile("[\\\"\\\"]+");
		return p.matcher(title).replaceAll("\"");
	}

	/* 
	 * Handles unknown year
	 */
	protected static String unknownYear(int year) {
		if (year == -1) {
			return "Unknown year";
		}
		return "" + year;
	}

	/* 
	 * Handles unknown price
	 */
	protected static String unknownPrice(String price) {
		if (price.equals("$-1.00")) {
			return "Unknown price";
		}
		return price;
	}

	/* 
	 * Formats book price to 2 decimal places when printed
	 */
	protected static String formatPrice(float price) {
		return "$" + String.format("%.02f", price);
	}

}
