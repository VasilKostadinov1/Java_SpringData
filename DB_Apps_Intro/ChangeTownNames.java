import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class ChangeTownNames {

    public static final String UPDATE_TOWNS = "UPDATE towns SET name = UPPER(name)\n" +
            "WHERE country = ?";
    public static final String TOWN_SELECT = "SELECT name FROM towns \n" +
            "WHERE country =  ?";
    public static final String NOT_AFFECTED = "No town names were affected.";
    public static final String COUNT_AFFECTED = "%d town names were affected.";

    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();

        properties.setProperty(Constants.USER_KEY, Constants.USER_VALUE);
        properties.setProperty(Constants.PASSWORD_KEY, Constants.PASSWORD_VALUE);

        Connection connection = DriverManager.getConnection(Constants.JDBC_URL, properties);

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter country name...");

        PreparedStatement update = connection.prepareStatement(UPDATE_TOWNS);
        String country = sc.nextLine();

        update.setString(1, country);

        int countUpdatedTowns = update.executeUpdate();

        if (countUpdatedTowns == 0) {
            System.out.println(NOT_AFFECTED);
            return;
        }
        System.out.printf(COUNT_AFFECTED, countUpdatedTowns);

        PreparedStatement updateTowns = connection.prepareStatement(TOWN_SELECT);

        updateTowns.setString(1, country);

        ResultSet updateSet = updateTowns.executeQuery();

        String[] townArray = new String[countUpdatedTowns];

        for (int i = 0; updateSet.next(); i++) {

            String townToUpper = updateSet.getString("name");
            townArray[i] = townToUpper;
        }
        System.out.println();
        System.out.println(Arrays.toString(townArray));

        connection.close();


    }
}
