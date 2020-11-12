# empresa-api

Esse projeto ilustra uma api de cadastro de funcionarios de uma empresa. Utilizando o framework Spring.

## Começando

Para executar o projeto, será necessário instalar os seguintes programas:

- [JDK 8: Necessário para executar o projeto Java](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
- [Maven 3.5.3: Necessário para realizar o build do projeto Java](http://mirror.nbtelecom.com.br/apache/maven/maven-3/3.5.3/binaries/apache-maven-3.5.3-bin.zip)
- [STS: Para desenvolvimento do projeto](https://spring.io/tools)

## Desenvolvimento

Para iniciar o desenvolvimento, é necessário clonar o projeto do GitHub num diretório de sua preferência:

```shell
cd "diretorio de sua preferencia"
git clone https://github.com/gleisonfreitas/empresa-api
```

## Configuração

Para executar o projeto, é necessário utilizar o STS, para que o mesmo identifique as dependências necessárias para a execução no repositório .m2 do Maven.

### Construção

Para construir o projeto com o Maven, executar os comando abaixo:

```shell
mvn clean install
```

O comando irá baixar todas as dependências do projeto e criar um diretório *target* com os artefatos construídos, que incluem o arquivo jar do projeto.

### Executando

Após construir o projeto, entre na pasta '...\empresa-api\target', execute os comandos abaixo.

```shell
java -jar empresa-api-0.0.1-SNAPSHOT.jar
```

O comando irá subir o projeto onde será possível acessar a url de documentação.

```shell
http://localhost:8080/swagger-ui.html
```

## Licença

Não se aplica.