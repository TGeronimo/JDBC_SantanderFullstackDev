import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

//    1. Read
    public List<Aluno> list() {
//        Criar uma lista que irá retornar alunos após consultar o bando

        List<Aluno> alunos = new ArrayList<>();

        try(Connection conn = ConnectionFactory.getConnection()) {

            PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM aluno;");
            ResultSet resultSet = prepareStatement.executeQuery();
            resultSet.next();

            while (resultSet.next()) {
                Aluno aluno = new Aluno(
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

////    1.1 Filtered Read
//    public List<Aluno> getById(int id) {
//
//    }
//
////    2. Create
//    public void create(Aluno aluno) {
//
//    }
//
////    3. Delete
//    public void delete(Aluno aluno){
//
//    }
//
////    4. Update
//    public void update(Aluno aluno){
//
//    }
}
