# 🎬 CineReview API

API RESTful desenvolvida com Spring Boot para gerenciamento de filmes, listas e críticas de cinema.

## 🚀 Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA**
* **H2 Database** (Banco em memória para desenvolvimento)
* **Springdoc OpenAPI** (Documentação automática)

## 🛠️ Como Executar o Projeto

### Pré-requisitos
* Java 17 instalado
* Maven instalado

### Passo a Passo
1.  Clone o repositório:
    ```bash
    git clone [https://github.com/luan-sampaio/CineReview.git](https://github.com/luan-sampaio/CineReview.git)
    ```
2.  Entre na pasta:
    ```bash
    cd cinereview-api
    ```
3.  Execute a aplicação:
    ```bash
    ./mvnw spring-boot:run
    ```
4.  Acesse a documentação da API (Swagger):
    * [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 📚 Estrutura da API

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/filmes` | Lista todos os filmes |
| POST | `/api/filmes` | Cadastra um novo filme |
| POST | `/api/reviews` | Adiciona uma crítica a um filme |
| GET | `/api/listas` | Consulta listas de usuários |

## 🏗️ Decisões de Arquitetura

* **Banco H2:** Utilizado para facilitar testes rápidos sem necessidade de instalação de SGBD externo. Os dados são resetados a cada reinicialização.
* **UUID:** Utilizado como identificador único para Filmes e Reviews para garantir escalabilidade e segurança na geração de IDs.
* **Camada de Service:** Toda regra de negócio (como verificar se um filme existe antes de criar review) está isolada nos Services, mantendo os Controllers limpos.

## 🤝 Contribuindo

Sinta-se à vontade para abrir Issues ou enviar Pull Requests.

classDiagram
class Filme {
+String id
+String titulo
+Float notaMedia
}
class Review {
+String id
+Float nota
+String comentario
}
class Lista {
+Long id
+String nome
}

    Filme "1" --> "*" Review : recebe
    Lista "*" --> "*" Filme : contem