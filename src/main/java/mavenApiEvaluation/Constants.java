package mavenApiEvaluation;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;
import java.net.URL;
public class Constants {

    public static List<String> getMenuArray() {


        return Arrays.asList("1- Fetch Sections API",
                "2- Fetch Article API",
                "3- Fetch Auther API",
                "4- CREATE TABLE Sections, Article, Auther IN DATABASE ",
                "5- INSERT INTO Sections TABLE ",
                "6- INSERT INTO Auther TABLE ",
                "7- INSERT INTO Article TABLE ",
                "8- What are the top 5 sections with the most articles? ",
                "9- How many articles were written by each author? ",
                "10-What are the top 10 articles with the most views? "
        );

    }

    public static HttpURLConnection getConnectionByUrl(URL url, String requestMethod) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(requestMethod);
        conn.connect();
        return  conn;
    }

    public static String URL_NYT_API_BOOKS = "https://api.nytimes.com/svc/topstories/v2/books.json?";

    public static String URL_NYT_API_ARTICLE = "https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=romney&facet_field=day_of_week&facet=true&begin_date=20120101&end_date=20120101&api-";
    public static String URL_NYT_API_AUTHOR = "https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=romney&facet_field=day_of_week&facet=true&begin_date=20120101&end_date=20120101&";
    public static String API_NYT_KEY = "api-key=IO0i2IlGBNmzuUbAmzcAPdzPH5LcPss2";

    public static String REQUEST_METHOD_GET = "GET";
    public static String USER_URL = "jdbc:sqlserver://localhost:1433;databaseName=MyDatabase;encrypt=true;trustServerCertificate=true";
    public static String USER_NAME = "sa";
    public static String USER_PASSWORD = "root";
    public static String JDBC_DRIVER_SQL_SERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";



}
