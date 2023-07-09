import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class _Paralel extends Thread {
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/aliosman?useSSL=false&serverTimezone=UTC";
    public static final String JDBC_USERNAME = "root";
    public static final String JDBC_PASSWORD = "60235236";

    public void saveUserDataInformation() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String query = "INSERT INTO user_data (username, password, email) VALUES (?, ?, ?)";
            Scanner scanner = new Scanner(System.in);

            System.out.println("Username giriniz");
            String username = scanner.nextLine();
            System.out.println("Password giriniz");
            String password = scanner.nextLine();
            System.out.println("Email giriniz");
            String email = scanner.nextLine();

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, email);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUserSecretInformation() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String query = "INSERT INTO user_secret (secret_information) VALUES (?)";
            Scanner scanner = new Scanner(System.in);

            System.out.println("Secret information giriniz");
            String secretInformation = scanner.nextLine();

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, secretInformation);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // MultiThread
    public static void main(String[] args) throws InterruptedException {
        _Paralel parallelService1 = new _Paralel();
        parallelService1.saveUserDataInformation();

        _Paralel parallelService2 = new _Paralel();
        parallelService2.saveUserSecretInformation();

        parallelService1.start();
        parallelService1.join();

        parallelService2.start();
        parallelService2.join();
    }
}