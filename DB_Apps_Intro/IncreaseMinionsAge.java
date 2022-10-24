import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class IncreaseMinionsAge {

    public static final String UPDATE_MINION_AGE = "UPDATE minions SET age = age + 1, name = LOWER(name) WHERE id = ?";
    public static final String SELECT_ALL_MINIONS = "SELECT name, age FROM minions";

    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();

        properties.setProperty(Constants.USER_KEY, Constants.USER_VALUE);
        properties.setProperty(Constants.PASSWORD_KEY, Constants.PASSWORD_VALUE);

        Connection connection = DriverManager.getConnection(Constants.JDBC_URL, properties);

        Scanner sc = new Scanner(System.in);

        String[] input = sc.nextLine().split("\\s+");

        for (String id : input) {

            PreparedStatement updateMinion = connection.prepareStatement(UPDATE_MINION_AGE);

            updateMinion.setInt(1, Integer.parseInt(id));

            updateMinion.executeUpdate();
        }

        PreparedStatement selectMinions = connection.prepareStatement(SELECT_ALL_MINIONS);
        ResultSet printMinions = selectMinions.executeQuery();

        while(printMinions.next()) {
            System.out.println(printMinions.getString("name") + " " + printMinions.getInt("age"));
        }
        connection.close();
    }
}
