import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PrintAllMinionNames {
    public static final String MINION_NAMES = "SELECT name FROM minions";


    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();

        properties.setProperty(Constants.USER_KEY, Constants.USER_VALUE);
        properties.setProperty(Constants.PASSWORD_KEY, Constants.PASSWORD_VALUE);

        Connection connection = DriverManager.getConnection(Constants.JDBC_URL, properties);

        PreparedStatement statement = connection.prepareStatement(MINION_NAMES);
        ResultSet resultSet = statement.executeQuery();
        List<String> names = new ArrayList<>();

        while (resultSet.next()) {
            names.add(resultSet.getString("name"));
        }

        for (int i = 0; i < names.size() / 2; i++) {

            System.out.println(names.get(i));
            System.out.println(names.get(names.size() - 1 - i));

        }
    }
}
