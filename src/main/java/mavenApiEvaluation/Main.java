package mavenApiEvaluation;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.google.gson.Gson;
import mavenApiEvaluation.Service.ArticleService;
import mavenApiEvaluation.Service.AuthorService;
import mavenApiEvaluation.Service.SectionService;

public class Main {

    public static SectionService sectionService = new SectionService();

    public static ArticleService articleService = new ArticleService();
    public static AuthorService authorService = new AuthorService();

    public static void main(String[] args) {
        Scanner scannerMenu = new Scanner(System.in);
        boolean exitFlagMainMenu = true;
        while (exitFlagMainMenu) {

            for (String x : Constants.getMenuArray()) {
                System.out.println(x);
            }

            Integer optionForMainMenu = scannerMenu.nextInt();
            switch (optionForMainMenu) {

                case 1:
                    sectionService.fetchSectionFromUrl();
                    break;
                case 2:
                    articleService.fetchArticleFromUrl();
                    break;
                case 3:
                    authorService.fetchAuthorFromUrl();
                    break;
                case 4:
                    // Creating SectionsTable
                   sectionService.createSectionTable();
                    // Creating ArticlesTable
                    articleService.createArticleTable();
                    // Creating AuthorTable
                    authorService.createAuthorTable();

                    break;

                case 5:

                    // INSERT DATA INTO SECTION TABLE
                    String urld1 = "jdbc:sqlserver://localhost:1433;databaseName=MyDatabase;encrypt=true;trustServerCertificate=true";
                    String user1 = "sa";
                    String pass1 = "root";

                    try {
                        URL urll = new URL(
                                "https://api.nytimes.com/svc/topstories/v2/books.json?api-key=IO0i2IlGBNmzuUbAmzcAPdzPH5LcPss2");
                        HttpURLConnection connn = (HttpURLConnection) urll.openConnection();
                        connn.setRequestMethod("GET");
                        connn.connect();
                        StringBuilder apiInformation = new StringBuilder();
                        int responseCode = connn.getResponseCode();
                        if (responseCode != 200) {
                            throw new RuntimeException("HttpresponseCode");

                        } else {
                            Scanner scanner = new Scanner(urll.openStream());
                            while (scanner.hasNext()) {
                                apiInformation.append(scanner.nextLine());
                            }
                            scanner.close();
//					System.out.println(apiInformation);
                            Gson gson = new Gson();

                            sections apiResult = gson.fromJson(apiInformation.toString(), sections.class);
                            Connection Insertconn = null;

                            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                                    .newInstance();
                            DriverManager.registerDriver(driver);
                            Insertconn = DriverManager.getConnection(urld1, user1, pass1);

                            for (int i = 0; i < apiResult.getResults().length; i++) {

                                String sqlDBInsert = "INSERT INTO Sections (" + " section," + " title," + " item_type ,"
                                        + "published_date)VALUES('" + apiResult.getResults()[i].getSections() + "','"
                                        + apiResult.getResults()[i].getBook_title() + "','"
                                        + apiResult.getResults()[i].getPublished_date() + "','"
                                        + apiResult.getResults()[i].getItem_type() + "')";

                                SQLQueries.getInsertIntoSectionTable(apiResult.getResults()[i].getSections(),
                                        apiResult.getResults()[i].getBook_title(), apiResult.getResults()[i].getPublished_date()
                                , apiResult.getResults()[i].getItem_type());

                                Statement st = Insertconn.createStatement();

                                int m = st.executeUpdate(sqlDBInsert);
                                if (m >= 1) {
                                    System.out.println("Date Inserted in given database...");

                                } else {
                                    System.out.println(" Date Inserted already Created in given database...");
                                }
                                Insertconn.close();
                            }

                        }
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }

                    break;

                case 6:
                    // INSERT DATA INTO Author TABLE
                    String authorurld1 = "jdbc:sqlserver://localhost:1433;databaseName=MyDatabase;encrypt=true;trustServerCertificate=true";
                    String authoruser1 = "sa";
                    String authorpass1 = "root";

                    try {
                        URL urll = new URL(
                                "https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=romney&facet_field=day_of_week&facet=true&begin_date=20120101&end_date=20120101&api-key=IO0i2IlGBNmzuUbAmzcAPdzPH5LcPss2");
                        HttpURLConnection connn = (HttpURLConnection) urll.openConnection();
                        connn.setRequestMethod("GET");
                        connn.connect();
                        StringBuilder apiInformation = new StringBuilder();
                        int responseCode = connn.getResponseCode();
                        if (responseCode != 200) {
                            throw new RuntimeException("HttpresponseCode");

                        } else {
                            Scanner scanner = new Scanner(urll.openStream());
                            while (scanner.hasNext()) {
                                apiInformation.append(scanner.nextLine());
                            }
                            scanner.close();
//					System.out.println(apiInformation);
                            Gson gson = new Gson();
                            Connection Insertconn = null;

                            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                                    .newInstance();
                            DriverManager.registerDriver(driver);
                            Insertconn = DriverManager.getConnection(authorurld1, authoruser1, authorpass1);

                            Statement st = Insertconn.createStatement();
                            author apiResult = gson.fromJson(apiInformation.toString(), author.class);

                            for (int i = 0; i < apiResult.getResponse().getDocs().length; i++) {


                                String sqlDBInsert = "INSERT INTO Author (" + " firstname," + " middlename,"
                                        + " lastname)VALUES('"
                                        + apiResult.getResponse().getDocs()[i].getByline().getPerson()[0].getFirstname()
                                        + "','"
                                        + apiResult.getResponse().getDocs()[i].getByline().getPerson()[0].getMiddlename()
                                        + "','"
                                        + apiResult.getResponse().getDocs()[i].getByline().getPerson()[0].getLastname()
                                        + "')";

                                int m = st.executeUpdate(sqlDBInsert);
                                if (m >= 1) {
                                    System.out.println("Date Inserted in given database...");

                                } else {
                                    System.out.println(" Date Inserted already Created in given database...");
                                }
                                Insertconn.close();
                            }

                        }
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }

                    break;

                case 7:
                    // INSERT DATA INTO Author TABLE
                    String Sectionsurld1 = "jdbc:sqlserver://localhost:1433;databaseName=MyDatabase;encrypt=true;trustServerCertificate=true";
                    String Sectionsuser1 = "sa";
                    String Sectionspass1 = "root";

                    try {
                        URL urll = new URL(
                                "https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=romney&facet_field=day_of_week&facet=true&begin_date=20120101&end_date=20120101&api-key=IO0i2IlGBNmzuUbAmzcAPdzPH5LcPss2");
                        HttpURLConnection connn = (HttpURLConnection) urll.openConnection();
                        connn.setRequestMethod("GET");
                        connn.connect();
                        StringBuilder apiInformation = new StringBuilder();
                        int responseCode = connn.getResponseCode();
                        if (responseCode != 200) {
                            throw new RuntimeException("HttpresponseCode");

                        } else {
                            Scanner scanner = new Scanner(urll.openStream());
                            while (scanner.hasNext()) {
                                apiInformation.append(scanner.nextLine());
                            }
                            scanner.close();
//					System.out.println(apiInformation);
                            Gson gson = new Gson();
                            Connection Insertconn = null;

                            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                                    .newInstance();
                            DriverManager.registerDriver(driver);
                            Insertconn = DriverManager.getConnection(Sectionsurld1, Sectionsuser1, Sectionspass1);

                            Statement st = Insertconn.createStatement();
                            author apiResult = gson.fromJson(apiInformation.toString(), author.class);

                            for (int i = 0; i < apiResult.getResponse().getDocs().length; i++) {


                                System.out.println("Enter Section Name : ");
                                String sections = scannerMenu.next();


                                System.out.println("Enter Author FirstName : ");
                                String autthor = scannerMenu.next();

                                String sqlDBInsert = "INSERT INTO Article (" + " type_of_material," + " document_type,"
                                        + "Sections_id,Author_id)VALUES('"
                                        + apiResult.getResponse().getDocs()[0].getType_of_material()
                                        + "','"
                                        + apiResult.getResponse().getDocs()[0].getDocument_type()
                                        + "'," + "(SELECT id FROM Sections Where section='" + sections + "'"
                                        + ")," + "(SELECT id FROM Author Where firstname='" + autthor + "'))";


                                int m = st.executeUpdate(sqlDBInsert);
                                if (m >= 1) {
                                    System.out.println("Date Inserted in given database...");

                                } else {
                                    System.out.println(" Date Inserted already Created in given database...");
                                }
                                Insertconn.close();
                            }

                        }
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }

                    break;

                case 8:
                    String urrl = "jdbc:sqlserver://localhost:1433;databaseName=MyDatabase;encrypt=true;trustServerCertificate=true";

                    String userr = "sa";
                    String pasrs = "root";

                    String sqlDB = "SELECT TOP 5 * FROM Sections JOIN Article ON Sections.id = Article.Sections_id;";

                    Connection conn = null;
                    try {

                        Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
                        DriverManager.registerDriver(driver);
                        conn = DriverManager.getConnection(urrl, userr, pasrs);

                        Statement st = conn.createStatement();

                        ResultSet m = st.executeQuery(sqlDB);

                        if (m.next()) {

                            do {

                                System.out.println("id : " + m.getInt(1));
                                System.out.println("section :" + m.getString(2));
                                System.out.println("title :" + m.getString(3));
                                System.out.println("item_type :" + m.getString(4));
                                System.out.println("published_date :" + m.getString(5));
                                System.out.println("Artical_id :" + m.getInt(6));
                                System.out.println("Type_of_material :" + m.getString(7));
                                System.out.println("document_type :" + m.getString(8));
                                System.out.println("Section_id :" + m.getInt(9));
                                System.out.println("Author_id :" + m.getInt(10));


                                System.out.println("*********************************");


                            } while (m.next());

                        } else {
                            System.out.println("No such user id is already registered");
                        }

                        conn.close();
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }


                    break;
                case 9:
                    String curl = "jdbc:sqlserver://localhost:1433;databaseName=MyDatabase;encrypt=true;trustServerCertificate=true";
                    String cuser = "sa";
                    String cpass = "root";
                    String countSqlDB = "SELECT COUNT(*) FROM Article INNER JOIN Author ON Article.Author_id = Author.id";
                    Connection conntions = null;
                    try {
                        Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
                        DriverManager.registerDriver(driver);
                        conntions = DriverManager.getConnection(curl, cuser, cpass);

                        Statement st = conntions.createStatement();

                        ResultSet m = st.executeQuery(countSqlDB);

                        if (m.next()) {

                            do {
                                System.out.println("COUNT : " + m.getInt(1));
                                System.out.println("*********************************");


                            } while (m.next());

                        } else {
                            System.out.println("No such user id is already registered");
                        }

                        conntions.close();
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }


                    break;

                case 10:

                    String topurrl = "jdbc:sqlserver://localhost:1433;databaseName=MyDatabase;encrypt=true;trustServerCertificate=true";

                    String topuserr = "sa";
                    String toppasrs = "root";

                    String topsqlDB = "SELECT TOP 10 * FROM  Article ";

                    Connection topconn = null;
                    try {

                        Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
                        DriverManager.registerDriver(driver);
                        topconn = DriverManager.getConnection(topurrl, topuserr, toppasrs);

                        Statement st = topconn.createStatement();

                        ResultSet m = st.executeQuery(topsqlDB);

                        if (m.next()) {

                            do {


                                System.out.println("Artical_id :" + m.getInt(1));
                                System.out.println("Type_of_material :" + m.getString(2));
                                System.out.println("document_type :" + m.getString(3));
                                System.out.println("Section_id :" + m.getInt(4));
                                System.out.println("Author_id :" + m.getInt(5));


                                System.out.println("*********************************");


                            } while (m.next());

                        } else {
                            System.out.println("No such user id is already registered");
                        }

                        topconn.close();
                    } catch (Exception ex) {
                        System.err.println(ex);
                    }

                    break;


            }
        }

    }

}
