import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {

    public static void main(String[] args) {

//        Definir parâmetros para se conectar ao banco de dados
        String driver = "mysql";
        String dataBaseAddress = "localhost";
        String dataBaseName = "digital_innovation_one";
        String user = "root";
        String password = "math!hack0";

//        Construção da string de conexão
        StringBuilder sd = new StringBuilder("jdbc:")
                .append(driver).append("://")
                .append(dataBaseAddress).append("/")
                .append(dataBaseName);

        String urlConnection = sd.toString();

//        Método atual com "try with resources" fecha a conexão automaticamente
//        Criar conexão usando o DriveManager, passando como parâmetros a string
//        de conexão, o nome do usuário e a senha do mesmo.
        try (Connection conn = DriverManager.getConnection(urlConnection, user,password)){
            System.out.println("SUCESSO!");
        } catch (SQLException throwables) {
            System.out.println("FALHA!");
        }

//        Método antes Java 7
//        try {
//            conn = DriverManager.getConnection(urlConnection, "root","math!hack0");
//            System.out.println("SUCESSO!");
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            System.out.println("FALHA!");
//        } finally {
//            conn.close();
//        }
    }
}
