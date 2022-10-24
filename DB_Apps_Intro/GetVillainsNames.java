import java.sql.*;
import java.util.Properties;

public class GetVillainsNames {

    private final  static  String GET_VILLAIN_NAMES =
            "select v.name, count(distinct mv.villain_id) as  minions_count " +
            " from villains as v" +
            " join minions_villains mv on  v.id = mv.villain_id" +
            " group by  mv.villain_id" +
            " having minions_count > ?" +
            " order by minions_count";

    private final  static String COLUMN_LABEL_NAME = "name";
    private final  static String COLUMN_LABEL_COUNT = "minions_count";
    private final  static String PRINT_FORMAT = "%s %d";

    public static void main(String[] args) throws SQLException {

        final Connection connection = Utils.getSQLConnection();

        final PreparedStatement statement = connection.prepareStatement(GET_VILLAIN_NAMES);

        statement.setInt(1, 15);

        final ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            final  String villainName = resultSet.getString(COLUMN_LABEL_NAME);
            final  int minionsCount = resultSet.getInt(COLUMN_LABEL_COUNT);

            System.out.printf(PRINT_FORMAT, villainName, minionsCount);
        }
        connection.close();


    }
}
