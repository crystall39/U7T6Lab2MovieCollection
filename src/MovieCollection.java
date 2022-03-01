import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else if (option.equals("r"))
    {
      listHighestRated();
    }
    else if (option.equals("h"))
    {
      listHighestRevenue();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }

  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortStrings(ArrayList<String> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      String string = listToSort.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && string.compareTo(listToSort.get(possibleIndex - 1)) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, string);
    }
  }

  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }
  
  private void searchCast()
  {
    System.out.print("Enter a person to search for (first or last name): ");
    String searchName = scanner.nextLine();

    // prevent case sensitivity
    searchName = searchName.toLowerCase();

    ArrayList<String> cast = new ArrayList<>();
    ArrayList<String> lowerCast = new ArrayList<>();
    for (int i = 0; i < movies.size(); i++)
    {
      String[] castMember = movies.get(i).getCast().split("\\|");
      String[] lowerCastMember = movies.get(i).getCast().toLowerCase().split("\\|"); // list of lower cases
      for (int j = 0; j <castMember.length; j++)
      {
        cast.add(castMember[j]);
      }
      for (int j = 0; j < lowerCastMember.length; j++)
      {
        lowerCast.add(lowerCastMember[j]);
      }

    }
    for (int j = 0; j < lowerCast.size(); j++)
    {
      if (lowerCast.get(j).indexOf(searchName) == -1) // tom not found
      {
        lowerCast.remove(j);
        cast.remove(j);
        j--;
      }
    }
    // removes duplicates
    for (int k = 0; k < cast.size(); k++)
    {
      for (int l = k + 1; l < cast.size() - 1; l++)
      {
        if (cast.get(k).equals(cast.get(l)))
        {
          cast.remove(l);
          l--;
        }
      }
    }

    sortStrings(cast);

    // now, display them all to the user
    for (int i = 0; i < cast.size(); i++)
    {
      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + cast.get(i));
    }

    System.out.println("Which would you like to see all movies for?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    // for all movies, if their cast member includes the name, display them
    ArrayList<Movie> moviesList = new ArrayList<>();
    for (int i = 0; i < movies.size(); i++)
    {
      if (movies.get(i).getCast().indexOf(cast.get(choice - 1)) != -1)
      {
        moviesList.add(movies.get(i));
      }
    }

    sortResults(moviesList);

    // display movies in movieList
    for (int i = 0; i < moviesList.size(); i++)
    {
      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + moviesList.get(i).getTitle());
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = moviesList.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void searchKeywords()
  {
    System.out.print("Enter a keyword search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String keyword = movies.get(i).getKeywords().toLowerCase();

      if (keyword.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listGenres()
  {
    // adds all genres to an array list
    ArrayList<String> allGenres = new ArrayList<>();
    for (int i = 0; i < movies.size(); i++)
    {
      String[] genres = movies.get(i).getGenres().split("\\|");
      for (int j = 0; j < genres.length; j++)
      {
        allGenres.add(genres[j]);
      }
    }

    // remove duplicates
    for (int k = 0; k < allGenres.size(); k++)
    {
      for (int l = k + 1; l < allGenres.size() - 1; l++)
      {
        if (allGenres.get(k).equals(allGenres.get(l)))
        {
          allGenres.remove(l);
          l--;
        }
      }
    }

    // organize
    sortStrings(allGenres);

    // print
    for (int i = 0; i < allGenres.size(); i++)
    {

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + allGenres.get(i));
    }
  }
  
  private void listHighestRated()
  {
    /* TASK 6: IMPLEMENT ME! */
  }
  
  private void listHighestRevenue()
  {
    /* TASK 6: IMPLEMENT ME! */
  }

  private void importMovieList(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      movies = new ArrayList<Movie>();

      while ((line = bufferedReader.readLine()) != null)
      {
        // import all cells for a single row as an array of Strings,
        // then convert to ints as needed
        String[] moviesFromCSV = line.split(",");

        // pull out the data for this cereal
        String title = moviesFromCSV[0];
        String cast = moviesFromCSV[1];
        String director = moviesFromCSV[2];
        String tagline = moviesFromCSV[3];
        String keywords = moviesFromCSV[4];
        String overview = moviesFromCSV[5];
        int runtime = Integer.parseInt(moviesFromCSV[6]);
        String genres = moviesFromCSV[7];
        double userRating = Double.parseDouble(moviesFromCSV[8]);
        int year = Integer.parseInt(moviesFromCSV[9]);
        int revenue = Integer.parseInt(moviesFromCSV[10]);

        // create Cereal object to store values
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        // adding Cereal object to the arraylist
        movies.add(nextMovie);
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());
    }
  }
}