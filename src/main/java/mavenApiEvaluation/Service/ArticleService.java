package mavenApiEvaluation.Service;

import com.google.gson.Gson;
import mavenApiEvaluation.Articles;
import mavenApiEvaluation.Constants;
import mavenApiEvaluation.SQLQueries;
import mavenApiEvaluation.sections;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class ArticleService {
    public void fetchArticleFromUrl() {
        try {
            URL url = new URL(Constants.URL_NYT_API_ARTICLE + Constants.API_NYT_KEY);
            HttpURLConnection conn = Constants.getConnectionByUrl(url, Constants.REQUEST_METHOD_GET);
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

            Articles apiResult = gson.fromJson(apiInformation.toString(), Articles.class);

            for (int i = 0; i < (apiInformation.length()); i++) {

                System.out.println(" ***************************** " + "|");
                System.out.println(
                        "Type_of_material : " + apiResult.getResponse().getDocs()[0].getType_of_material());
                System.out
                        .println("Document_type : " + apiResult.getResponse().getDocs()[0].getDocument_type());
                System.out.println("Section_name : " + apiResult.getResponse().getDocs()[0].getSection_name());
                System.out.println("Autor Name : "
                        + apiResult.getResponse().getDocs()[0].getByline().getPerson()[0].getFirstname());
                System.out.println("|" + " ***************************** " + "|");

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e);
        }
    }

    public void createArticleTable() {

        Connection Articleconntion = null;
        try {

            Driver driver = (Driver) Class.forName(Constants.JDBC_DRIVER_SQL_SERVER).newInstance();
            DriverManager.registerDriver(driver);
            Articleconntion = DriverManager.getConnection(Constants.USER_URL, Constants.USER_NAME, Constants.USER_PASSWORD);



            Statement st = Articleconntion.createStatement();

            int m = st.executeUpdate(SQLQueries.CREATE_TABLE_ARTICLE);
            if (m >= 1) {
                System.out.println("Created Article table in given database...");

            } else {
                System.out.println(" table already Created in given database...");
            }
            Articleconntion.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void insertIntoArtecleTable() {

    }

}
