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