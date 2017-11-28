package books;

import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) {
		try {
			Books books = new Books("res/books.csv");
			Books books2 = new Books("res/books2.csv");
			Books books3 = new Books("res/books3.csv");
			Books books4 = new Books("res/books4.csv");

			System.out.println(books.getBooksInfo() + "\n\n" + books2.getBooksInfo() 
				+ "\n\n" + books3.getBooksInfo() + "\n\n" + books4.getBooksInfo());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
