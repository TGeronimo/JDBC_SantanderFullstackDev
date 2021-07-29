# JDBC
https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/
___

Para acessar o banco de dados do mysql usamos o comando mysql
seguido do usuário, nome do banco e senha. No exemplo, o usuário
é "*root*", o banco é "*digital_innovation_one*" e a senha é 
solicitada com "*-p*". Exemplo: 
`mysql -u 'root' digital_innovation_one -p`.

<br>
___
### Exercício sobre conexão com o BD
1) Criar outro usuário do banco de dados e se conectar 
através da API JDBC.
2) Explorar os métodos da classe DriveManager e da
interface Connection através da IDE ou documentação.
3) Configurar outro banco de dados (ex: PostgreSQL)
e tentar se conectar usando a API JDBC.
   *Já configurei o driver do PostgreSQL no build.gradle.*