# JDBC
https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/
___

Para acessar o banco de dados do mysql usamos o comando mysql
seguido do usuário, nome do banco e senha. No exemplo, o usuário
é "*root*", o banco é "*digital_innovation_one*" e a senha é 
solicitada com "*-p*". Exemplo: 
`mysql -u 'root' digital_innovation_one -p`.

<br>
Para alterar o password de um usuário basta o comando a seguir (já logado no banco):

```roomsql
ALTER USER 'user-name'@'localhost' IDENTIFIED BY 'NEW_USER_PASSWORD';

FLUSH PRIVILEGES;
```

## How to create an user on MySQL
To create a new user you need to login to the database as the root user, then
run the folowing query:

```roomsql
CREATE USER 'reguser'@'localhost'
    IDENTIFIED BY 'password';
```
Then, you can grant its privileges:

```roomsql
GRANT ALL
    ON *.*
    TO 'reguser'@'localhost'
    WITH GRANT OPTION;
```

___
## Aula 3: Consultas com JBDC
**Interfaces**:
1. Statement -> executar SQL comuns
2. PreparedStatement -> executar SQL parametrizável (preferível)
3. CallableStatement -> executar stored procedures

**Métodos**:
1. execute
2. executeQuery
3. executeUpdate

**_ResultSet_** é um objeto do Java que contém os dados da consulta 
e **getInt()**, **getFloat()** e **getString()** são os métodos de busca e **next()** é
o método utilizado para percorrer os campos do ResultSet.

Uma prática comum é a criação de uma classe para acessar determinada
tabela nomeando esta classe com o padrão "TabelaDAO.java", onde Tabela é
o nome da tabela a ser acessada e DAO é uma sigla para _Data Access Object_,
que tem como métodos o famoso **CRUD** (Create, Read, Update, Delete).
___
### Exercício sobre conexão com o BD
1) Criar outro usuário do banco de dados e se conectar 
através da API JDBC.
2) Explorar os métodos da classe DriveManager e da
interface Connection através da IDE ou documentação.
3) Configurar outro banco de dados (ex: PostgreSQL)
e tentar se conectar usando a API JDBC.
   *Já configurei o driver do PostgreSQL no build.gradle.*

### Exercícios de Query
1. Crie uma tabela no BD chamada **curso** que terá como colunas: _id_, _nome_, _duracao_horas_. (No BD, a nomenclatura utilizada é snake_case)
2. Crie um classe em Java chamada **Curso** que terá os mesmos atributos que a tabela criada no BD. (duracao_horas em camelCase)
3. Crie **CursoDAO** que será responsável por conectar ao BD para realizar as operações CRUD (Create, Read, Update, Delete).
4. Testar os métodos do **cursoDAO** em uma classe que tenha o método _main_.
___
### Solução 
#### **_Classe DAO com os métodos do CRUD_**
```java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

//    ======= CREATE =======

   public void create(Curso curso){
      try (Connection connection = ConnectionFactory.getConnection()){
         String query = "INSERT INTO curso(id, nome, duracao_horas) VALUES (?, ?, ?);";
         PreparedStatement preStm = connection.prepareStatement(query);
         preStm.setInt(1, curso.getId());
         preStm.setString(2, curso.getNome());
         preStm.setInt(3, curso.getDuracaoHoras());

//            Retornar o número de linhas afetadas
         int rowsAffected = preStm.executeUpdate();
         System.out.println("Inserção bem sucedida! " + rowsAffected + " linhas afetadas.");

      } catch (SQLException sqlException) {
         System.out.println("FALHOU ALGUMA COISA");
         sqlException.printStackTrace();
      }
   }

//    ======= READ =======

   public List<Curso> read() {
      List<Curso> cursoList = new ArrayList<>();

      try (Connection connection = ConnectionFactory.getConnection()) {
         String query = "SELECT * FROM curso;";

         PreparedStatement prepStm = connection.prepareStatement(query);
         ResultSet resultSet = prepStm.executeQuery();

         while (resultSet.next()) {
            Curso curso = new Curso(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getInt("duracao_horas")
            );

            cursoList.add(curso);
         }

      } catch (SQLException sqlException) {
         System.out.println("FALHA ao consultar banco de dados!");
      }

      return cursoList;
   }

   //    ======= READ WHERE =======

   public Curso readWhere(int duracaoHoras) {

      Curso cursoWhere = new Curso();

      try (Connection connection = ConnectionFactory.getConnection()) {
         String query = "SELECT * FROM curso WHERE duracao_horas = ?;";

         PreparedStatement prepStm = connection.prepareStatement(query);
         prepStm.setInt(1, duracaoHoras);

         ResultSet resultSet = prepStm.executeQuery();

         if (resultSet.next()) {
            cursoWhere.setId(resultSet.getInt("id"));
            cursoWhere.setNome(resultSet.getString("nome"));
            cursoWhere.setDuracaoHoras(resultSet.getInt("duracao_horas"));
         }

      } catch (SQLException throwables) {
         throwables.printStackTrace();
      }

      return cursoWhere;
   }


//    ======= UPDATE =======

   public void update(Curso curso) {
      try (Connection connection = ConnectionFactory.getConnection()) {
         String query = "UPDATE curso SET id = ? WHERE duracao_horas = ?;";
         PreparedStatement prepStmt = connection.prepareStatement(query);
         prepStmt.setInt(1, curso.getId());
         prepStmt.setInt(2, curso.getDuracaoHoras());

         int rowsAffected = prepStmt.executeUpdate();
         System.out.println("ALTERAÇÃO BEM SUCEDIDA! " + rowsAffected + " linhas afetadas.");

      } catch (SQLException sqlException) {
         System.out.println("FALHA ao atualizar a tabela!");
         sqlException.printStackTrace();
      }
   }


//    ======= DELETE =======

   public void delete(int id) {
      String query = "DELETE FROM curso WHERE id = ?";

      try (Connection connection = ConnectionFactory.getConnection()){
         PreparedStatement prepStm = connection.prepareStatement(query);
         prepStm.setInt(1, id);

         int rowsAffected = prepStm.executeUpdate();
         System.out.println("ALTERAÇÃO BEM SUCEDIDA! " + rowsAffected + " linhas removidas.");

      } catch (SQLException sqlException) {
         System.out.println("FALHA na remoção do registro!");
         sqlException.printStackTrace();
      }
   }

}
```
#### _**Classe Curso com o modelo do BD**_
```java
public class Curso {
    private int id;
    private String nome;
    private int duracaoHoras;

    public Curso(int id, String nome, int duracaoHoras) {
        this.id = id;
        this.nome = nome;
        this.duracaoHoras = duracaoHoras;
    }

    public Curso() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDuracaoHoras(int duracaoHoras) {
        this.duracaoHoras = duracaoHoras;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracaoHoras() {
        return duracaoHoras;
    }

    @Override
    public String toString() { // Construí o método pela IDE com Alt+Insert
        return "Curso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", duracaoHoras=" + duracaoHoras +
                '}';
    }
}
```
#### Fabricação da conexão com o BD
```java
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private ConnectionFactory() {
        throw new UnsupportedOperationException();
    }

    public static Connection getConnection() {
        Connection connection = null;

        try (InputStream inputStream = ConnectionFactory.class
                .getClassLoader().getResourceAsStream("connection.properties")) {

            Properties properties = new Properties();
            properties.load(inputStream);

//            Define os dados de acesso ao banco de dados
            String driver = properties.getProperty("jdbc.driver");
            String dataBaseAddress = properties.getProperty("db.address");
            String dataBaseName = properties.getProperty("db.name");
            String user = properties.getProperty("db.user.login");
            String password = properties.getProperty("db.user.password");

//            Criando uma String com o endereço de acesso, baseado nos dados acima coletados
//            de connection.properties
            String connectionUrl = "jdbc:" +
                    driver + "://" +
                    dataBaseAddress + "/" +
                    dataBaseName;

            try {
                connection = DriverManager.getConnection(connectionUrl, user, password);

            } catch (SQLException sqlException) {
                System.out.println("FALHA AO CONECTAR AO BANCO DE DADOS!");
                throw new RuntimeException();
            }

        } catch (IOException e) {
            System.out.println("FALHA ao criar arquivo de propriedade de conexão");
        }

        return connection;
    }
}
```
#### Classe **_Creation_** para inserir instâncias na tabela
```java
public class Creation {
    public static void main(String[] args) {
        CursoDAO cursoDAO = new CursoDAO();

//        CREATE =========================================
        Curso insereCurso1 = new Curso(
                1,
                "Orientação a Objetos com Java",
                8
        );
        cursoDAO.create(insereCurso1);

        Curso insereCurso2 = new Curso(
                1,
                "Banco de dados relacional MySQL",
                6
        );
        cursoDAO.create(insereCurso2);

        Curso insereCurso3 = new Curso(
                1,
                "Algoritmos e Estruturas de Dados",
                40
        );
        cursoDAO.create(insereCurso3);

        Curso insereCurso4 = new Curso(
                1,
                "Framework Spring Boot",
                10
        );
        cursoDAO.create(insereCurso4);


    }
}
```
#### Classe _**Reading**_ para leitura da tabela
```java
import java.util.List;

public class Reading {
    public static void main(String[] args) {
        CursoDAO cursoDAO2 = new CursoDAO();

        List<Curso> cursoList = cursoDAO2.read();
        cursoList.forEach(System.out::println);
    }

}
```
#### Classe _**Updating**_ para atualização dos dados na tabela
```java
public class Updating {
    public static void main(String[] args) {
        CursoDAO cursoDAO3 = new CursoDAO();

        Curso atualizaCurso = cursoDAO3.readWhere(6);
        atualizaCurso.setId(2);

        Curso atualizaCurso2 = cursoDAO3.readWhere(40);
        atualizaCurso2.setId(3);

        Curso atualizaCurso3 = cursoDAO3.readWhere(10);
        atualizaCurso3.setId(4);

        cursoDAO3.update(atualizaCurso);
        cursoDAO3.update(atualizaCurso2);
        cursoDAO3.update(atualizaCurso3);

//        Exibe a tabela com os dados atualizados
        cursoDAO3.read().forEach(System.out::println);

    }

}
```
#### Classe **_Deleting_** para remover instância da tabela
```java
public class Deleting {
    public static void main(String[] args) {
        CursoDAO cursoDAO4 = new CursoDAO();

        cursoDAO4.delete(1);
        cursoDAO4.read().forEach(System.out::println);
    }
}

```