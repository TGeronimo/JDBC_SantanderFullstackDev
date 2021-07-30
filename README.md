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
### Exercício sobre conexão com o BD
1) Criar outro usuário do banco de dados e se conectar 
através da API JDBC.
2) Explorar os métodos da classe DriveManager e da
interface Connection através da IDE ou documentação.
3) Configurar outro banco de dados (ex: PostgreSQL)
e tentar se conectar usando a API JDBC.
   *Já configurei o driver do PostgreSQL no build.gradle.*