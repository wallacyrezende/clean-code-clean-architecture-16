# API de Cadastro de Usuários - Aplicativo Similar ao Uber

Bem-vindo à API de cadastro de usuários para um aplicativo semelhante ao Uber. Esta API permite o cadastro de usuários que serão utilizados na plataforma.

## Documentação Swagger

A documentação completa da API pode ser encontrada no [Swagger UI](http://localhost:8080/swagger-ui.html). Acesse para explorar os endpoints e interagir com a API.

## Banco de Dados

A aplicação utiliza o banco de dados H2 em memória para armazenar os dados dos usuários. O schema do banco de dados é o `cccat16`. Você pode encontrar a estrutura do banco no arquivo `resources/h2/schema.sql`.

## Executando a Aplicação

Para executar a aplicação, certifique-se de ter o JDK e o Maven instalados em seu ambiente. Em seguida, execute os seguintes comandos:

bash
mvn clean install
mvn spring-boot:run

A aplicação estará disponível em http://localhost:8080.

## Endpoints Principais
POST /signup: Endpoint para cadastrar um novo usuário na plataforma.

## Executando Testes
Para executar os testes da aplicação, utilize o seguinte comando:

mvn test

Isso executará todos os testes unitários e de integração da API.

Sinta-se à vontade para explorar e utilizar esta API para o cadastro de usuários em seu aplicativo similar ao Uber. Se precisar de mais informações ou tiver alguma dúvida, consulte a documentação do Swagger.