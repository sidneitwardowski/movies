
# Outsera Movies

Projeto desenvolvido em **Spring Boot** com o objetivo de analisar vencedores de premiações de filmes, identificando produtores com menor e maior intervalo de tempo entre vitórias.

## Tecnologias Utilizadas
- **Java**: Versão 17
- **Spring Boot**: Versão 3.4.2
- **Lombok**: Versão: 1.18.36
- **Maven**: Para gerenciamento de dependências

## Funcionalidades
- Leitura e gravação no banco de dados H2DB de um arquivo CSV na inicialização do sistema.
- Análise e cálculo dos intervalos entre vitórias de produtores.
- Exposição de uma API REST para consulta dos rankings.

## Leitura do CSV
O projeto faz a leitura de um arquivo CSV ao iniciar a aplicação. Esse arquivo contém as informações sobre os filmes indicados e vencedores de prêmios.
O arquivo deve ser inserido na pasta resources do projeto (main/resources) e o nome do arquivo deve constrar na propriedade no `application.properties`, conforme o exemplo abaixo:

**file.name** 
```properties
file.name=movielist.csv
```

A estrutura esperada para o arquivo CSV é:
```
year;title;studios;producers;winner
```

### Exemplo:
```csv
year;title;studios;producers;winner
1980;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes
1980;Cruising;Lorimar Productions, United Artists;Jerry Weintraub;
1980;The Formula;MGM, United Artists;Steve Shagan;
```

## Endpoints da API

### Ranking de Produtores
Retorna o ranking de produtores com menor e maior intervalo entre vitórias.

**GET** `/v1/producers/ranking`

**Resposta:**
```json
{
  "min": [
    {
      "producer": "Nome do Produtor",
      "interval": 1,
      "previousWin": 1980,
      "followingWin": 1981
    }
  ],
  "max": [
    {
      "producer": "Nome do Produtor",
      "interval": 10,
      "previousWin": 1970,
      "followingWin": 1980
    }
  ]
}
```

## Acessando o Swagger

O projeto utiliza o **Springdoc OpenAPI** para gerar a documentação interativa da API com o **Swagger UI**.

Após iniciar a aplicação, você pode acessar a documentação da API através do navegador no seguinte endereço:

```
http://localhost:8080/swagger
```

A partir dessa interface, você poderá:
- Visualizar os endpoints disponíveis
- Consultar os modelos de request e response
- Realizar requisições diretamente pela interface do Swagger UI
## Como Executar o Projeto

### Pré-requisitos
- **Java 17** instalado na máquina
- **Maven** configurado no PATH

### Executando o Projeto

2. Compile e execute o projeto:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

3. A aplicação estará disponível em:
    ```
    http://localhost:8080
    ```

### Executando Testes
Para rodar os testes, utilize o comando:
```bash
mvn test
```

## Estrutura do Projeto
```bash
src
├── main
│   ├── java
│   │   └── com
│   │       └── movies
│   │           └── outsera
│   │               ├── controller
│   │               ├── dto
│   │               ├── model
│   │               ├── repository
│   │               └── service
│   └── resources
│       ├── movielist.csv
│       └── application.properties
└── test
    ├── java
    │   └── com
    │       └── movies
    │           └── outsera
    │               └── controller
    └── resources
        ├── movielist-test.csv
        └── application-test.properties
```

