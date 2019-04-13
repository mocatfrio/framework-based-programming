# Simple API Access Program

## Tools
1. Spring Tools Suite 4
2. JDK 1.8
3. Public API from [**The Library Genesis API**](http://garbage.world/posts/libgen/)
   
    ```bash
    curl -s 'http://libgen.io/json.php?ids=1&fields=Title,Author,Year,Publisher,City,Pages,Topic,Language,CoverUrl,Issn' | json_pp
    ```
    ```json
    [
    {
        "author" : "Philip Anderson",
        "language" : "English",
        "publisher" : "McGraw-Hill Medical",
        "coverurl" : "0/7b2a4d53fde834e801c26a2bab7e0240.jpg",
        "issn" : "",
        "title" : "Handbook of Clinical Drug Data",
        "topic" : "Biology",
        "year" : "2001",
        "pages" : "1163",
        "city" : ""
    }
    ]
    ```
    Source: https://github.com/toddmotto/public-apis#books

## Steps
1. Make a POJO (Plain Old Java Object) of JSON
    ```java
    public class Book {
      private String title;
      private String author;
      private String issn;
      private String publisher;
      private String city;
      private String year;
      private int pages;
      private String language;
      private String topic;
      @SerializedName("coverurl")
      private String coverUrl;
      ...
      // setter and getter
      ...
      @Override
      public String toString(){
          return "Book's Description\n" + "=======================================" + 
              "\n\t Title = " + title +
              "\n\t Author = " + author +
              "\n\t ISSN = " + issn +
              "\n\t Publisher = " + publisher + 
              "\n\t City = " + city + 
              "\n\t Year = " + year + 
              "\n\t Pages = " + pages + 
              "\n\t Language = " + language + 
              "\n\t Topic = " + topic + 
              "\n\t Cover URL = " + coverUrl + "\n";
      }
    }
    ```
2. Catch on User's input 
    ```java
    String id;

    BufferedReader idReader = new BufferedReader(new InputStreamReader(System.in));
    
    System.out.println("### The Library Genesis API ###");
    System.out.print("Input Book's ID : ");
    id = idReader.readLine();
    ```
3. API connection based on User's input (book's id)
    ```java
    URL url = new URL("http://libgen.io/json.php?ids=" + id + "&fields=Title,Author,Year,Publisher,City,Pages,Topic,Language,CoverUrl,Issn");
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    connection.connect();
    ```
4. Using GSON library, parse the JSON response into POJO
    ```java
    try(BufferedReader jsonReader  = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
       Gson gson = new GsonBuilder().create();
       Book[] book = gson.fromJson(jsonReader, Book[].class);
       System.out.println(book[0]);
    }
    ```
      
## Example

![ss1](/img/ss1.png)

## Full Code

[Simple API Access Program](/simple-api-access-program)