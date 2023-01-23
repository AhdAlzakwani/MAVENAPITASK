package mavenApiEvaluation;

public class SQLQueries {
    public static String getInsertIntoSectionTable(String section,
                                                   String title, String item_type , String published_date){
        String sqlDBInsert = "INSERT INTO Sections (" + " section," + " title," + " item_type ,"
                + "published_date)VALUES('" + section + "','"
                + title + "','"
                + item_type + "','"
                + published_date + "')";

        return sqlDBInsert;
    }

    public static String CREATE_TABLE_ARTICLE = "CREATE TABLE Article " + "(id INTEGER Identity(1,1), "
            +" Type_of_material TEXT not null, " + "document_type TEXT,"
            + " Sections_id INTEGER foreign key references Sections(id), " +
            "Author_id INTEGER foreign key references Author(id), " + " PRIMARY KEY ( id ))";

    public static String CREATE_TABLE_SECTIONS = "CREATE TABLE Sections " + "(id INTEGER Identity(1,1), "
            + " section TEXT not null, " + " title TEXT, " + " item_type VARCHAR(50), "
            + " published_date TEXT," + " PRIMARY KEY ( id ))";


    public static String CREATE_TABLE_AUTHOR = "CREATE TABLE Author " + "(id INTEGER Identity(1,1), "
            + " firstname TEXT not null, " + " middlename TEXT not null, " + " lastname TEXT not null, "
            + " PRIMARY KEY ( id ))";



}
