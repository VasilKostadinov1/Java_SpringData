import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class getVillainsNames_2 {

    public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    public static final String DB_NAME = "minions_DB";

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        Connection connection = getConnection(scanner);

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT v.name, COUNT(DISTINCT mv.minion_id) AS number " +
                        "FROM villains AS v " +
                        "JOIN minions_villains mv on v.id = mv.villain_id " +
                        "GROUP BY v.name " +
                        "HAVING number > ? " +
                        "ORDER BY number DESC; ");

        preparedStatement.setInt(1,15);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int age = resultSet.getInt("number");
            System.out.printf("%s %d %n", name, age);
        }

    }
    private static Connection getConnection(Scanner scanner) throws SQLException {
        System.out.println("Enter user:");
        String root = scanner.nextLine();
        System.out.println("Enter password:");
        String Vasil123 = scanner.nextLine();
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "Vasil123");
        return DriverManager
                .getConnection(CONNECTION_STRING+ DB_NAME, properties);
    }
}
