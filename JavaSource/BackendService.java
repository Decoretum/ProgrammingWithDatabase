import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class BackendService {

    public static Connection establishConnection()
    {
        Connection connection = null;
        try {
            //Establishing Connection
            connection = null;
            Class dbClass = Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/softwareengineering",
                "root", ""
            );
            System.out.println("Class found for JDBC Driver: " + dbClass.getName());
        } catch (Exception e)
        {
            System.out.println("Exception: " + e);
        }

        return connection;
    }

    public static void queryCreate()
    {
        
    }

    public static void DatabaseViewing(Connection connection, Scanner scanner)
    {
        try {
        ArrayList<Object> tables = new ArrayList<>();

        //Statements
        Statement s = connection.createStatement();
        ResultSet results;

        String [] tType = {"TABLE"};
        String catalog = connection.getCatalog(); //Corresponds to Database name stored 
        DatabaseMetaData resMeta = connection.getMetaData();
        ResultSet tablesResult = resMeta.getTables(catalog, null, null, tType);

        while (tablesResult.next())
        {
            String tableName = tablesResult.getString(3);   // OR getString("TABLE_NAME");
            tables.add(tableName);
        }

        // Scanner scanner = new Scanner(System.in);
        System.out.println("Choose available Tables");
        System.out.println(tables);

        String chosenTable = scanner.nextLine();

        String q = "DESC " + chosenTable + ";";
        results = s.executeQuery(q);

        //Getting metadata from a query result


        // results = s.executeQuery("SELECT * FROM `" + chosenTable + "` LIMIT 1");
        /* ResultSetMetaData metaData = null;
        String[] resultProperties = null;
         while(results.next())
        {
            metaData = results.getMetaData();
            String[] tableProperties = new String[metaData.getColumnCount()];
            for (int i = 0; i <= metaData.getColumnCount() - 1; i++)
            {
                String name = metaData.getColumnLabel(i+1);
                String type = metaData.getColumnTypeName(i+1);
                tableProperties[i] = name + " " + type;
            }
            resultProperties = tableProperties;
        } */
        
        System.out.println("");
        System.out.println(chosenTable + "\'s Database Table properties");
        while (results.next())
        {
            String fieldName = results.getString("Field");
            String fieldType = results.getString("Type");
            String canNull = results.getString("Null");
            String extra = results.getString("Extra").trim();

            if (extra.equals("") || extra.equals(null))
            System.out.println(chosenTable + " Field: " + fieldName + ", Type: " + fieldType + ", Can be Null: " + canNull);
            
            else
            System.out.println(chosenTable + " Field: " + fieldName + ", Type: " + fieldType + ", Can be Null: " + canNull + " Extra: " + extra);
        }
        
        //Closing resultSet
        results.close();

        } catch (Exception e)
        {
            System.out.println("Exception: " + e);
        }
    }

    public static void main (String args[])
    {
        boolean on = true;
        
            try {
                Connection connection = establishConnection();
                Scanner scanner = new Scanner(System.in);

                while (on == true)
                {   
                System.out.println("System Standby for action:");
                System.out.println("Database Viewing");
                System.out.println("Idle Chat");

                String option = scanner.nextLine().trim();

                if (option.equals("Database Viewing"))
                DatabaseViewing(connection, scanner);
                
                else
                {
                    System.out.println("Bye!");
                    on = false;
                    connection.close();
                    scanner.close();
                }
            }

            } catch (Exception e) {
                System.out.println(e);
            }
    }
    
}