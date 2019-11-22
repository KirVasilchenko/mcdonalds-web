import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try (
                final Connection connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
                Statement statement = connection.createStatement();
                ) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
