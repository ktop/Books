package books;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/* 
 * Parses CSV file and creates a Book for each entry.
 * Parses all of the information about the books. 
 */
public class BookParser {
	private static final char SEPARATOR = ',';
    private static final char DOUBLE_QUOTE = '"';
    private static final int numValues = 4;
    private static final int currYear = Calendar.getInstance().get(Calendar.YEAR);
    private static final int oldYear = currYear - 10;
    private static final int fiveYears = currYear - 5;
    private List<Book> oldBooks = new ArrayList<Book>();
    private List<Book> booksFiveYears = new ArrayList<Book>();
    private Book highestPrice;
    private Book lowestPrice;
    private float avgPrice;
    private float priceTotal;
    private int booksWithPrice = 0;

    /* 
     * Parses CSV file
     * Returns list of parsed books
     */
	protected List<Book> parse(String file) throws FileNotFoundException {
		List<Book> books = new ArrayList<Book>();
		try {
			Scanner scanner = new Scanner(new File(file));
			if (scanner.hasNext()) {
				// header
				scanner.nextLine();
			}
			while (scanner.hasNext()) {
	            String[] bookInfo = parseLine(scanner.nextLine());
	            // Title, author, year, price
	            if (bookInfo != null) {
	            		Book book = new Book(bookInfo[0], bookInfo[1], Integer.parseInt(bookInfo[2]), Float.parseFloat(bookInfo[3]));
		            books.add(book);
		            if (book.getPrice() != -1.00f) {
		            		booksWithPrice++;
		            		setHighestPrice(book);
			            setLowestPrice(book);
			            setPriceTotal(book.getPrice());
		            }
		            if (book.getYear() != -1) {
			            setOldBooks(book);
			            setBooksFiveYears(book);
		            }
	            }
	        }
	        scanner.close();
		} catch (FileNotFoundException e) {
			throw e;
		}
		if (booksWithPrice > 0) {
			setAvgPrice();
		}

		return books;
	}

	/* 
	 * Parses each line from the CSV file through the Scanner
	 * Returns a String array of the different field values 
	 */
	private String[] parseLine(String line) {
		String[] bookInfo = new String[4];

		// Check for empty lines
        if (line == null || line.length() == 0) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        char[] chars = line.toCharArray();
        int endIndex = line.length() - 1; // indexing from end
        int valueIndex = numValues - 1; // index for resulting String[]

        // Gets price and year
        int separatorCount = 0;
        while (endIndex > 0) {
        		if (separatorCount >= 2) {
        			break;
        		}
        		if (chars[endIndex] == SEPARATOR) {
        			separatorCount++;
        			if (sb.length() == 0) {
        				bookInfo[valueIndex] = "-1";
        			} else {
        				bookInfo[valueIndex] = sb.toString();
        			}
        			valueIndex--;
        			sb.setLength(0);
        		} else {
        			sb.insert(0, chars[endIndex]);
        		}
        		endIndex--;
        }

        // If no author
        if (chars[endIndex] == SEPARATOR) {
        		bookInfo[valueIndex] = sb.toString();
        		valueIndex--;
        		endIndex-=2;
        } else {
        		// Gets author, ignores double-quotes surrounding author
            int doubleQuoteCount  = 0;
            while (endIndex > 0 || doubleQuoteCount >= 2) {
	    	    		if (doubleQuoteCount >= 2) {
	    	    			bookInfo[valueIndex] = sb.toString();
	            			valueIndex--;
	            			endIndex-=2; // skips comma and ending double-quote for title
	            			sb.setLength(0);
	    	    			break;
	    	    		}
	    	    		if (chars[endIndex] == DOUBLE_QUOTE) {
	    	    			doubleQuoteCount++;
	    	    		} else {
	    	    			sb.insert(0, chars[endIndex]);
	    	    		}
	    	    		endIndex--;
	    	    }
        }

    		// Gets title, ignores double-quotes surrounding title
        while (endIndex > 0) {
        		sb.insert(0, chars[endIndex]);
        		endIndex--;
        }
        bookInfo[valueIndex] = sb.toString();

		return bookInfo;
	}

	/* 
	 * Gets book with highest price
	 */
	protected Book getHighestPrice() {
		return highestPrice;
	}

	/* 
	 * Sets book with highest price
	 */
	private void setHighestPrice(Book book) {
		if (highestPrice == null || highestPrice.getPrice() < book.getPrice()) {
			highestPrice = book;
		}
	}

	/* 
	 * Gets book with lowest price
	 */
	protected Book getLowestPrice() {
		return lowestPrice;
	}

	/* 
	 * Sets book with lowest price 
	 */
	private void setLowestPrice(Book book) {
		if (lowestPrice == null || lowestPrice.getPrice() > book.getPrice()) {
			lowestPrice = book;
		}
	}

	/* 
	 * Gets average price from all books 
	 */
	protected float getAvgPrice() {
		return avgPrice;
	}

	/* 
	 * Sets average price from all books 
	 */
	private void setAvgPrice() {
		if (booksWithPrice != 0) {
			avgPrice = priceTotal / booksWithPrice;
			avgPrice = (float) (Math.round(avgPrice * 100.0)/100.0); // rounds to 2 decimals
		} else {
			// Should not get here, but just in case...
			throw new Error("Cannot get average price from 0 books");
		}
	}

	/* 
	 * Sets the total price for all books up to the current book
	 * Used in calculating average price 
	 */
	private void setPriceTotal(float bookPrice) {
		priceTotal += bookPrice;
	}

	/* 
	 * Get list of books older than 10 years 
	 */
	protected List<Book> getOldBooks() {
		return oldBooks;
	}

	/* 
	 * Add to list of books older than 10 years 
	 */
	private void setOldBooks(Book book) {
		if (book.getYear() < oldYear) {
			oldBooks.add(book);
		}
	}

	/* 
	 * Get list of books within the last 5 years
	 */
	protected List<Book> getBooksFiveYears() {
		return booksFiveYears;
	}

	/* 
	 * Add to list of books within the last 5 years 
	 */
	private void setBooksFiveYears(Book book) {
		if (book.getYear() >= fiveYears) {
			booksFiveYears.add(book);
		}
	}
}
