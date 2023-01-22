package mavenApiEvaluation;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

import com.google.gson.Gson;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scannerr = new Scanner(System.in);
		boolean menuExit = true;
		while(menuExit) {
			System.out.println("1- Fetch Sections API" );
			System.out.println("2- Fetch Article API" );
			System.out.println("3- Fetch Auther API" );
			System.out.println("4- CREATE TABLE Sections, Article, Auther IN DATABASE " );
			System.out.println("5- INSERT INTO TABLE " );
			System.out.println("6- READE FROM TABLE " );
			System.out.println("7- UPDATE FROM TABLE " );
			System.out.println("8- DELETE FROM TABLE " );
			int option = scannerr.nextInt();
			switch(option) {
			
			case 1 :
				
				try {
					URL url = new URL("https://api.nytimes.com/svc/topstories/v2/books.json?api-key=IO0i2IlGBNmzuUbAmzcAPdzPH5LcPss2");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.connect();
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
						

					for (int i=0; i<(apiInformation.length());i++) {

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
				break;
			case 2:
				try {
					URL url = new URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=romney&facet_field=day_of_week&facet=true&begin_date=20120101&end_date=20120101&api-key=IO0i2IlGBNmzuUbAmzcAPdzPH5LcPss2");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.connect();
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
						

					for (int i=0; i<(apiInformation.length());i++) {

							System.out.println(" ***************************** " + "|");
							System.out.println("Type_of_material : " + apiResult.getResponse().getDocs()[0].getType_of_material());
							System.out.println("Document_type : " + apiResult.getResponse().getDocs()[0].getDocument_type());
							System.out.println("Section_name : " + apiResult.getResponse().getDocs()[0].getSection_name());
							System.out.println("Autor Name : " + apiResult.getResponse().getDocs()[0].getByline().getPerson()[0].getFirstname());
							System.out.println("|" + " ***************************** " + "|");
						
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
				
				break;
			case 3:
				try {
					URL url = new URL("https://api.nytimes.com/svc/books/v3/reviews.json?author=Stephen+King&api-key=gQ0dzaeFD7U9gh53D7tFaAEndTxeIBAu");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.connect();
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
						

					for (int i=0; i<(apiInformation.length());i++) {

							System.out.println(" ***************************** " + "|");
							System.out.println("Author : " + apiResult.getResults()[0].getBook_author());
							System.out.println("|" + " ***************************** " + "|");
						
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
				break;
			case 4:
				// Creating SectionsTable
				String urld = "jdbc:sqlserver://localhost:1433;databaseName=MyDatabase;encrypt=true;trustServerCertificate=true";
				String user = "sa";
				String pass = "root";
				String SectionssqlDB = "CREATE TABLE Sections " + "(id INTEGER Identity(1,1), " + " section TEXT not null, "
						+ " title VARCHAR(50), " + " item_type VARCHAR(50), " +" published_date Date,"+" PRIMARY KEY ( id ))";

				Connection Sectionsconn = null;
				try {

					Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
					DriverManager.registerDriver(driver);
					Sectionsconn = DriverManager.getConnection(urld, user, pass);

					Statement st = Sectionsconn.createStatement();

					int m = st.executeUpdate(SectionssqlDB);
					if (m >= 1) {
						System.out.println("Created Sections table in given database...");
						
					} else {
						System.out.println(" table already Created in given database...");
					}
					Sectionsconn.close();
				}

				catch (Exception ex) {
					System.err.println(ex);
				}

				
				// Creating AuthorTable
				String uurl = "jdbc:sqlserver://localhost:1433;databaseName=MyDatabase;encrypt=true;trustServerCertificate=true";
				String uuse = "sa";
				String upas = "root";
				String AuthorsqlD = "CREATE TABLE Author " + "(id INTEGER Identity(1,1), " + " book_author TEXT not null, "
						+" PRIMARY KEY ( id ))";

				Connection Authorconntion = null;
				try {

					Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
					DriverManager.registerDriver(driver);
					Authorconntion = DriverManager.getConnection(uurl, uuse, upas);

					Statement st = Authorconntion.createStatement();

					int m = st.executeUpdate(AuthorsqlD);
					if (m >= 1) {
						System.out.println("Created Author table in given database...");
						
					} else {
						System.out.println(" table already Created in given database...");
					}
					Authorconntion.close();
				}

				catch (Exception ex) {
					System.err.println(ex);
				}
				
				
				
				
				
				// Creating ArticlesTable
				String url = "jdbc:sqlserver://localhost:1433;databaseName=MyDatabase;encrypt=true;trustServerCertificate=true";
				String use = "sa";
				String pas = "root";
				String ArticlesqlD = "CREATE TABLE Article " + "(id INTEGER Identity(1,1), " + " Type_of_material TEXT not null, "
						+ " Sections_id INTEGER foreign key references Sections(id), " + "Author_id INTEGER foreign key references Author(id), "+" PRIMARY KEY ( id ))";

				Connection Articleconntion = null;
				try {

					Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
					DriverManager.registerDriver(driver);
					Articleconntion = DriverManager.getConnection(url, use, pas);

					Statement st = Articleconntion.createStatement();

					int m = st.executeUpdate(ArticlesqlD);
					if (m >= 1) {
						System.out.println("Created Article table in given database...");
						
					} else {
						System.out.println(" table already Created in given database...");
					}
					Articleconntion.close();
				}

				catch (Exception ex) {
					System.err.println(ex);
				}
				
				
				
				
				
				
				
				
				break;
				
			case 5:
				String urld1 = "jdbc:sqlserver://localhost:1433;databaseName=MyDatabase;encrypt=true;trustServerCertificate=true";
				String user1 = "sa";
				String pass1 = "root";
				URL urll;
				try {
					urll = new URL("http://universities.hipolabs.com/search?country=United+States");

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


				Api[] apiResult = gson.fromJson(apiInformation.toString(), Api[].class);
				for (Api x : apiResult) {
	
				String sqlDBInsert = "INSERT INTO Api (" +" web_pages,"+ " state_province," + " alpha_two_code ," +"name,"+"country,"+"domains "+")VALUES("
				+"'"+x.getWeb_pages()[0]+"','"+x.getState_province()+"','"+x.getAlpha_two_code()+"','"+x.getName()+"','"+x.getCountry()+"','"+x.getDomains()[0]+"')";

				Connection Insertconn = null;
				

					Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
					DriverManager.registerDriver(driver);
					Insertconn = DriverManager.getConnection(urld1, user1, pass1);

					Statement st = Insertconn.createStatement();

					int m = st.executeUpdate(sqlDBInsert);
					if (m >= 1) {
						System.out.println("Created table in given database...");
						
					} else {
						System.out.println(" table already Created in given database...");
					}
					Insertconn.close();
				}


				
			
				}
				}catch (Exception ex) {
					System.err.println(ex);
				}
	
				break;
				
			case 6:
//				System.out.println(" Enter id ?");
//				long id = scannerr.nextInt();
//
//					api.selectById(id);
					
				
				break;
				
			case 7:
//				System.out.println(" Enter id ?");
//				long updateid = scannerr.nextInt();
//				api.UpdateById(updateid);
				
				break;
				
			case 8:
//				System.out.println(" Enter id to be deleted ?");
//				  int deleteId = scannerr.nextInt();
//				  api.deleteById(deleteId);
				
				break;
			
			
			}
			}
			

	}

}
