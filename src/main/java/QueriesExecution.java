import java.util.List;

public class QueriesExecution {
    public static void main(String[] args) {
        AlunoDAO alunoDAO = new AlunoDAO();

        List<Aluno> alunos = alunoDAO.list();

        alunos.stream().forEach(System.out::println);

    }
}
