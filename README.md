# CSV Book Parser Assignment

## Problem Statement
Start with this data set right here:

```
"Title","Author Name","Published Year","Price"

"The Year’s Best Science Fiction & Fantasy 2017 Edition","Rich Horton",2017,19.95

"The Year's Best Science Fiction: Thirty-Fourth Annual Collection","Gardner Dozois",2017,15.17

"The Science Fiction Hall of Fame, Vol. 1: 1929-1964","Robert Silverberg",2005,21.99

"Marked by Fate: A Young Adult Science Fiction Collection with Augmented Reality: Read, Watch, Listen. The new ultimate reading experience","Kristin D. Van Risseghem;Rhonda Sermon",2017,3.99

"The Art of Michael Whelan","Michael Whelan",1993,7.50

"Storming the Reality Studio: A Casebook of Cyberpunk & Postmodern Science Fiction","Larry McCaffery",1991,22.94
```

Write a program which parses this CSV file, and then has a function which tells me (feel free to mix and match tasks, I’d pick at least three):

- What is the highest priced book, and how much does it cost?
- What is the lowest priced book, and how much does it cost?
- What is the average price of books?
- What is the list of books more than 10 years old?
- What is the list of all of the authors in the last five years?

Observe the following constraints:

- You may not use any dependencies
- You must parse the CSV file into a data structure
- You must write a unit test which verifies your results

## Environment
- Mac
- Eclipse Oxygen
- Java 8
- JUnit 4

## What to run
I have created a Main.java which will parse 4 CSV files from res/ directory. Running that application will print information on each set of books to the console.

I also have JUnit test which will test most of the functions.

## Approach
I'll preface this by saying that I may have made this more difficult than you had originally intended because I started considering all of these cases for books and how CSV handles certain formats, but I have my solution and hopefully it's good.

I started this assignment by planning out how I was going to parse a CSV file. I assumed that based on the information I was given, I can expect the CSV file and other similar CSV file that my program would be tested against to contain 4 fields, title, author, year, and price, with however many entries. Each field is separated by a comma and the title and year, which are String types, are surrounded by double-quotes.

One approach I considered but dismissed is splitting each entry by the comma separator, which sounds simple enough, but one issue with this is if the title has commas in it. I also needed to be careful with the title and author since I would want to strip the surrounding double-quotes before adding it to my data structure.

I ended up deciding that I needed to make a custom parsing function, since for the year and price, I can distinguish easily as they are separated by commas, and then the author and title which I will need to strip the surrounding double-quotes as well as ignore any other commas that are not separating the two.

After getting all of the book information out of the file, getting each piece of information from the problem statement wasn't too difficult to calculate.

## Design
I decided to create a Book class to hold each entries' book information. I chose a List as the data structure to hold all of the books because I wanted to be able to iterate over the books and be able to access the books easily. The List allows me to get all of the information requested from the problem statement, so the simple data structure worked the best here.

The Books class is where the List of type Book is stored and it has one other function which will get the specific information requested from problem statement. Call with `books.getBooksInfo()` (I've included a Main.java to run through some scenarios as well). The Books class creates and instance of the BookParser class which handles all of the parsing and calculating. I have thought about moving the calculating into the Books class so that all the BookParser class does is parse the file, however, I decided that it would be better to do the calculations while it is parsing each line rather than iterate through the list of books afterwards to do the calculation.

For the parsing function, I used a scanner to parse in each line of the file, and then I would parse each line to get the 4 pieces of information from each line. I decided to manually do the parsing myself here because using Java's indexOf with substring and replace functions, the runtime would be way slower than if I parsed it myself. The way I parsed was to turn each line into a char array and iterate starting from the end. Starting from the end allowed me to get the price and year easily since I know they are separated by commas with no chance of extra commas being there. Next would be the author and I need to iterate until I saw two double-quotes. Lastly, because we started from the end, I know that the author has a preceding comma and then the title in quotes, I can get the title and ignore the surrounding quotes. This allows me to handle any extra commas or double-quotes that may be in the title itself.

From there, getting the parsing done, I keep track of the book prices and books older than 10 years and authors publishing within the last 5 years.

## Edge cases & Limitations
Some edge cases I've tried to handle are if certain fields in the CSV are empty, like no title or no author. I decided to handle no title or author by replacing the blank with "[ ]" so that the user knows there's nothing there and replacing no year or price with "Unknown year" and "Unknown price" respectively.

For the calculating average price, in the case where there is no price on a book, I decided to not add it into the calculations of the average. The same goes for getting the list of books older than 10 years and authors publishing within the last 5 years, I didn't include books with no years in these lists.

I handled if the CSV has no entries, then it would print "No books".

For limitations, this parser is custom to the specific 4 fields in that order with their specific types. It would not be able to handle if any of the fields change types or an extra field is added. Another limitation is how CSV uses quotes to delimit special characters like commas. My program won't be able to replace those extra delimiting quotes, however I did try to at least handle if a book title deliberately had delimiting quotes for double-quotes in the title.

One improvement I would have liked to make is if we have several CSV files that we want parse, being able to combine all of the information parsed would be better than having individual information from each file like get the average price on all of the books from all files. Another improvement is of course to do a cleaner parsing. My current parsing gets the job done for this current set of CSV files on books, but is not very scalable. If we can ensure there are no additional commas in the values themselves, then it would be easily possible to make a better parsing function. 
