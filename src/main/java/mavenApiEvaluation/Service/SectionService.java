package mavenApiEvaluation.Service;

import com.google.gson.Gson;
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

public class SectionService {

    public void fetchSectionFromUrl() {
        try {
            URL url = new URL(Constants.URL_NYT_API_BOOKS + Constants.API_NYT_KEY);
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

            sections apiResult = gson.fromJson(apiInformation.toString(), sections.class);

            for (int i = 0; i < (apiInformation.length()); i++) {

                System.out.println(" ***************************** " + "|");
                System.out.println("Sections : " + apiResult.getResults()[i].getSections());
                System.out.println("Book_title : " + apiResult.getResults()[i].getBook_title());
                System.out.println("Published_date : " + apiResult.getResults()[i].getPublished_date());
                System.out.println("Item_type : " + apiResult.getResults()[i].getItem_type());
                System.out.println("|" + " ***************************** " + "|");

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e);
        }
    }
    public void createSectionTable() {


        Connection Sectionsconn = null;
        try {

            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                    .newInstance();
            DriverManager.registerDriver(driver);
            Sectionsconn = DriverManager.getConnection(Constants.USER_URL, Constants.USER_NAME, Constants.USER_PASSWORD);

            Statement st = Sectionsconn.createStatement();

            int m = st.executeUpdate(SQLQueries.CREATE_TABLE_SECTIONS);
            if (m >= 1) {
                System.out.println("Created Sections table in given database...");

            } else {
                System.out.println(" table already Created in given database...");
            }
            Sectionsconn.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }




    }

    public void insertIntoSectionTable(){
        try {
            URL urll = new URL(Constants.URL_NYT_API_ARTICLE + Constants.API_NYT_KEY);
            HttpURLConnection connn = Constants.getConnectionByUrl(urll, Constants.REQUEST_METHOD_GET);
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
                Connection insertConnection = null;

                Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                        .newInstance();
                DriverManager.registerDriver(driver);
                insertConnection = DriverManager.getConnection(Constants.USER_URL, Constants.USER_NAME, Constants.USER_PASSWORD);

                for (int i = 0; i < apiResult.getResults().length; i++) {


                    String insertSerctionTable = SQLQueries.getInsertIntoSectionTable(apiResult.getResults()[i].getSections(),
                            apiResult.getResults()[i].getBook_title(), apiResult.getResults()[i].getPublished_date()
                            , apiResult.getResults()[i].getItem_type());

                    Statement st = insertConnection.createStatement();

                    int m = st.executeUpdate(insertSerctionTable);
                    if (m >= 1) {
                        System.out.println("Date Inserted in given database...");

                    } else {
                        System.out.println(" Date Inserted already Created in given database...");
                    }
                    insertConnection.close();
                }

            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
