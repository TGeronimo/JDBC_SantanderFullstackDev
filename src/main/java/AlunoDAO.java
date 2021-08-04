import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO { // DAO stands for Data Access Object

//    1. Read
    public List<Aluno> list() {
//        Criar uma lista que irá retornar alunos após consultar o banco

        List<Aluno> alunos = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getConnection()) {

            PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM aluno;");
            ResultSet resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) { // next() acessa a tabela e percorre cada instância/tupla/linha
                Aluno aluno = new Aluno(
                  resultSet.getInt("id"),
                  resultSet.getString("nome"),
                  resultSet.getInt("idade"),
                  resultSet.getString("Estado")
                );

                alunos.add(aluno);
            }

        } catch (Exception e) {

        }

        return alunos;
    }

//    1.1 Filtered Read
    public Aluno getById(int id) {

//        Preparar objeto Aluno para receber os dados do banco de dados
        Aluno aluno = new Aluno();

        try(Connection conn = ConnectionFactory.getConnection()) {

//            Preparar consulta SQL.
            String sql = "SELECT * FROM aluno WHERE id = ?;"; // Fica como "?" mesmo!

//            Preparar statement com os parâmetros recebidos
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id); // Aqui resolvemos o "?" da query

//            Executa consulta e armazena o retorno no objeto rs
            ResultSet rs = stmt.executeQuery();

//            Guardar valores recebidos da tabela aluno no objeto aluno
            if (rs.next()) { // next() acessa a tabela e percorre cada instância/tupla/linha
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setIdade(rs.getInt("idade"));
                aluno.setEstado(rs.getString("estado"));
            }

        } catch (SQLException sqlException) {
            System.out.println("Listagem de alunos FALHOU!");
            sqlException.printStackTrace();

        }

        return aluno;
}



//    2. Create
    public void create(Aluno aluno) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO aluno(nome, idade, estado) VALUES (?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, aluno.getNome());
            ps.setInt(2, aluno.getIdade());
            ps.setString(3, aluno.getEstado());

//            Executa a inserção e armazena o número de linhas afetadas
            int rowsAffected = ps.executeUpdate();
            System.out.println("Inserção bem sucedida: " + rowsAffected + " linha(s) afetada(s).");

        } catch (SQLException se) {
            System.out.println("Inserção FALHOU");
            se.printStackTrace();
        }
    }

//    3. Delete
    public void delete(int id){

        try (Connection connection = ConnectionFactory.getConnection()) {

            String sql = "DELETE FROM aluno WHERE id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            System.out.println("Delete bem sucedido: " + rowsAffected + " linha(s) afetada(s).");


        } catch (SQLException sqlException) {
            System.out.println("FALHA ao deletar");
            sqlException.printStackTrace();
        }

    }

//    4. Update
    public void update(Aluno aluno){
        try (Connection conn = ConnectionFactory.getConnection()) {

            String sqlQuery = "UPDATE aluno SET nome = ?, idade = ? WHERE id = ?";

            PreparedStatement pst = conn.prepareStatement(sqlQuery);
            pst.setString(1, aluno.getNome());
            pst.setInt(2, aluno.getIdade());
            pst.setInt(3, aluno.getId());

            int rowsAffected = pst.executeUpdate();

        } catch (SQLException sqe) {
            System.out.println("FALHA na atualizção!");
            sqe.printStackTrace();
        }


    }
}
