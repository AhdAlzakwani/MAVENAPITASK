package mavenApiEvaluation.Service;

import com.google.gson.Gson;
import mavenApiEvaluation.Constants;
import mavenApiEvaluation.SQLQueries;
import mavenApiEvaluation.author;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class AuthorService {
    public void fetchAuthorFromUrl() {
        try {
            URL url = new URL(Constants.URL_NYT_API_AUTHOR + Constants.API_NYT_KEY);
            HttpURLConnection conn = Constants.getConnectionByUrl(url,Constants.REQUEST_METHOD_GET);
            StringBuilder apiInformation = new StringBuilder();
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpresponseCode");

            } else {
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    apiInformation.append(scanner.nextLine());
                }

            }

            Gson gson = new Gson();

            author apiResult = gson.fromJson(apiInformation.toString(), author.class);

            for (int i = 0; i < (apiInformation.length()); i++) {

                System.out.println(" ***************************** " + "|");
                System.out.println("Author Firstname: "
                        + apiResult.getResponse().getDocs()[i].getByline().getPerson()[0].getFirstname());
                System.out.println("Author Middlename: "
                        + apiResult.getResponse().getDocs()[i].getByline().getPerson()[0].getMiddlename());
                System.out.println("Author Lastname: "
                        + apiResult.getResponse().getDocs()[i].getByline().getPerson()[0].getLastname());
                System.out.println("|" + " ***************************** " + "|");

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e);
        }

    }

    public void createAuthorTable() {


        Connection Authorconntion = null;
        try {

            Driver driver = (Driver) Class.forName(Constants.JDBC_DRIVER_SQL_SERVER)
                    .newInstance();
            DriverManager.registerDriver(driver);
            Authorconntion = DriverManager.getConnection(Constants.USER_URL, Constants.USER_NAME, Constants.USER_PASSWORD);

            Statement st = Authorconntion.createStatement();

            int m = st.executeUpdate(SQLQueries.CREATE_TABLE_AUTHOR);
            if (m >= 1) {
                System.out.println("Created Author table in given database...");

            } else {
                System.out.println(" table already Created in given database...");
            }
            Authorconntion.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }

    }
}
