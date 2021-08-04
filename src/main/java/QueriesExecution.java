import java.util.List;

public class QueriesExecution {
    public static void main(String[] args) {
        AlunoDAO alunoDAO = new AlunoDAO();

//        ============= CONSULTA =====================
        List<Aluno> alunos = alunoDAO.list();

//        alunos.stream().forEach(System.out::println);

//        ============= CONSULTA COM FILTRO =====================
//        Aluno alunoParaConsulta = alunoDAO.getById(3);
//        System.out.println(alunoParaConsulta);

//        ============= INSERÇÃO =====================

//        Aluno insereAluno = new Aluno(
//                "Thiago",
//                40,
//                "SP"
//        );
//        alunoDAO.create(insereAluno);


        //        ============== DELETE =====================

//        alunoDAO.delete(7);
//
////        Aqui tivemos que chamar novamente o método list() de AlunoDAO, pois senão utilizaria a instância
////        inicial da primeira consulta novamente.
//        alunoDAO.list().stream().forEach(System.out::println); // verificar se deletou


        //        ============== DELETE =====================

        Aluno alunoAtualizar = alunoDAO.getById(5);
        alunoAtualizar.setNome("Tales");
        alunoAtualizar.setIdade(13);

        alunoDAO.update(alunoAtualizar);
        alunoDAO.list().stream().forEach(System.out::println); // verificar se atualizou

    }
}
