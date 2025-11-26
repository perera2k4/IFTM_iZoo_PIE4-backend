<div align="center">
  <img src="if-logo-colorida.png" alt="Logo da Instituição" width="450"/>
  <h3>Instituto Federal de Educação, Ciência e Tecnologia do Triângulo Mineiro - Campus Ituiutaba</h3>
  <p><em>Gradução em Tecnólogia em <u>Análise e Desenvolvimento de Sistemas</u></em></p>
  <p>Projeto Integrador PIE4</p>
</div>

<h1 align="center"> iZoo - Backend API</h1>

<p align="center">
  <strong>API RESTful desenvolvida com Spring Boot para o sistema iZoo</strong>
</p>

<p align="center">
  <a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring%20Boot-3.4.1-6DB33F?style=flat&logo=spring-boot&logoColor=white"></a>
  <a href="https://www.java.com/"><img src="https://img.shields.io/badge/Java-17-007396?style=flat&logo=openjdk&logoColor=white"></a>
  <a href="https://www.mongodb.com/"><img src="https://img.shields.io/badge/MongoDB-latest-47A248?style=flat&logo=mongodb&logoColor=white"></a>
  <a href="LICENSE"><img src="https://img.shields.io/badge/Licença-MIT-green.svg?style=flat"></a>
</p>


## Sobre o Projeto

**iZoo Backend** é a API RESTful do projeto **PIE4 - Instituto Federal do Triângulo Mineiro (IFTM)**, desenvolvida para gerenciar e controlar informações sobre **zoonoses**.

A API fornece endpoints seguros para:
- **Gestão de usuários** com três níveis de acesso (ADMIN, AGENT, USER)
- **Gerenciamento de endereços** com suporte a geolocalização
- **Controle de inspeções sanitárias** com diferentes níveis de gravidade
- **Recuperação de senha** via e-mail com código de verificação
- **Autenticação JWT** com tokens seguros
- **Soft delete** para preservação de histórico

Cada residência cadastrada recebe um **QR Code exclusivo**, permitindo que agentes de saúde registrem inspeções e que cidadãos consultem o histórico de ocorrências em tempo real.

> 💡 Em resumo: o iZoo Backend torna o controle de zoonoses mais eficiente e seguro, unindo tecnologia e saúde pública.

---

## Tecnologias Utilizadas

Este projeto foi construído com as seguintes ferramentas e bibliotecas:

| Categoria                           | Tecnologia / Biblioteca                                                                                                                             | Descrição                                                                 |
| ----------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------- |
| **Framework principal**             | [Spring Boot](https://spring.io/projects/spring-boot)                                                                                               | Framework Java que simplifica a criação de aplicações web e APIs REST.    |
| **Ambiente de build**               | [Maven](https://maven.apache.org/)                                                                                                                  | Ferramenta de automação de build e gerenciamento de dependências Java.    |
| **Banco de dados**                  | [spring-boot-starter-data-mongodb](https://spring.io/projects/spring-data-mongodb)                                                                  | Integração com MongoDB, facilitando operações CRUD e repositórios.        |
| **Camada Web**                      | [spring-boot-starter-web](https://spring.io/projects/spring-boot)                                                                                   | Fornece suporte para construção de aplicações web RESTful.                |
| **Validação de dados**              | [spring-boot-starter-validation](https://spring.io/guides/gs/validating-form-input/)                                                                | Inclui servidor embutido (Tomcat) e suporte à validação de dados.         |
| **Gestão de sessões**               | [spring-session-data-mongodb](https://docs.spring.io/spring-session/reference/)                                                                     | Gerencia sessões de usuário e tokens usando MongoDB como armazenamento.   |
| **Variáveis de ambiente**           | [spring-dotenv](https://github.com/paulschwarz/spring-dotenv)                                                                                       | Carrega variáveis de ambiente a partir de arquivos `.env` para o Spring.  |
| **Segurança**                       | [spring-boot-starter-security](https://spring.io/projects/spring-security)                                                                         | Implementa autenticação, autorização e controle de acesso a endpoints.    |
| **Envio de e-mails**                | [spring-boot-starter-mail](https://docs.spring.io/spring-boot/reference/io/email.html)                                                              | Fornece suporte integrado para envio de e-mails através de SMTP.          |
| **Tokens JWT**                      | [jjwt](https://github.com/jwtk/jjwt)                                                                                                                | Biblioteca para criação e verificação de tokens JWT para autenticação.    |
| **Documentação da API**             | [springdoc-openapi-starter-webmvc-ui](https://springdoc.org/)                                                                                       | Gera automaticamente documentação interativa da API (Swagger UI).         |

---

## Principais Recursos Técnicos

### Segurança
- **Autenticação JWT** com tokens de longa duração
- **Controle de acesso baseado em roles** (ADMIN, AGENT, USER)
- **Senhas criptografadas** com BCrypt
- **Recuperação de senha** com código de 6 dígitos (válido por 15 minutos)
- **CORS configurado** para permitir requisições do frontend

### Gestão de Dados
- **Soft delete** para usuários, endereços e inspeções
- **Histórico completo** de registros deletados
- **Validação de dados** em tempo real
- **Timestamps automáticos** (createdAt, updatedAt)

### Funcionalidades da API
- **CRUD completo** para usuários, endereços e inspeções
- **Busca avançada** por filtros diversos
- **Geolocalização** com latitude e longitude
- **Sistema de inspeções** com status e gravidade
- **Envio de e-mails** automático para recuperação de senha

---

## 📁 Estrutura de Pastas

```
backend.izoo/
├── src/
│   ├── main/
│   │   ├── java/com/backend/izoo/
│   │   │   ├── config/              # Configurações (Segurança, JWT, Swagger, CORS)
│   │   │   │   ├── ConfigSeguranca.java
│   │   │   │   ├── ConfigSwagger.java
│   │   │   │   ├── FiltroAutenticacaoJWT.java
│   │   │   │   └── TokenJWT.java
│   │   │   │
│   │   │   ├── controller/          # Controladores REST
│   │   │   │   ├── UsuarioController.java
│   │   │   │   ├── EnderecoController.java
│   │   │   │   ├── InspecaoController.java
│   │   │   │   └── RecuperarSenhaController.java
│   │   │   │
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── UsuarioDTO.java
│   │   │   │   ├── EnderecoDTO.java
│   │   │   │   ├── InspecaoDTO.java
│   │   │   │   ├── SolicitacaoLoginDTO.java
│   │   │   │   ├── RespostaLoginDTO.java
│   │   │   │   ├── RecuperarSenhaDTO.java
│   │   │   │   ├── SolicitacaoRecuperarSenhaDTO.java
│   │   │   │   └── ValidarTokenDTO.java
│   │   │   │
│   │   │   ├── model/               # Entidades do banco de dados
│   │   │   │   ├── Usuario.java
│   │   │   │   ├── UsuarioDeletado.java
│   │   │   │   ├── Endereco.java
│   │   │   │   ├── EnderecoDeletado.java
│   │   │   │   ├── Inspecao.java
│   │   │   │   ├── InspecaoDeletado.java
│   │   │   │   └── TokenRecuperarSenha.java
│   │   │   │
│   │   │   ├── repositories/        # Repositórios MongoDB
│   │   │   │   ├── UsuarioRepository.java
│   │   │   │   ├── UsuarioDeletadoRepository.java
│   │   │   │   ├── EnderecoRepository.java
│   │   │   │   ├── EnderecoDeletadoRepository.java
│   │   │   │   ├── InspecaoRepository.java
│   │   │   │   ├── InspecaoDeletadoRepository.java
│   │   │   │   └── TokenRecuperarSenhaRepository.java
│   │   │   │
│   │   │   └── service/             # Lógica de negócio
│   │   │       ├── UsuarioService.java
│   │   │       ├── EnderecoService.java
│   │   │       ├── InspecaoService.java
│   │   │       ├── EmailService.java
│   │   │       └── RecuperarSenhaService.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/                        # Testes unitários e integração
│
├── .env.example                     # Exemplo de variáveis de ambiente
├── .gitignore
├── Dockerfile                        # Container Docker
├── pom.xml                          # Dependências Maven
├── LICENSE
└── README.md
```

---

## 🚀 Como Executar o Projeto

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:
- [Java 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi)
- [MongoDB](https://www.mongodb.com/try/download/community) ou MongoDB Atlas (cloud)
- [Git](https://git-scm.com/)

### Instalação

1. **Clone o repositório:**
```bash
git clone https://github.com/seu-usuario/IFTM_iZoo_PIE4-backend.git
cd IFTM_iZoo_PIE4-backend/backend.izoo
```

2. **Configure as variáveis de ambiente:**
```bash
cp .env.example .env
```

3. **Edite o arquivo `.env` com suas credenciais:**
```properties
# MongoDB
MONGODB_URI=mongodb://localhost:27017/izoo
MONGODB_DATABASE=izoo

# JWT
JWT_SECRET=sua_chave_secreta_muito_forte_aqui
JWT_EXPIRATION=86400000

# Email (SMTP)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=seu-email@gmail.com
MAIL_PASSWORD=sua-senha-de-aplicativo
MAIL_FROM=seu-email@gmail.com
```

4. **Instale as dependências:**
```bash
mvn clean install
```

5. **Execute o projeto:**
```bash
mvn spring-boot:run
```

6. **Acesse a documentação da API:**
   - Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - API Docs: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🐳 Executando com Docker

### Build da imagem:
```bash
docker build -t izoo-backend .
```

### Executar o container:
```bash
docker run -p 8080:8080 \
  -e MONGODB_URI=mongodb://host.docker.internal:27017/izoo \
  -e JWT_SECRET=sua_chave_secreta \
  izoo-backend
```

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

**Bruno Pereira**
- GitHub: [@perera2k4](https://github.com/perera2k4)
- Email: dev.bruno.carvalho@gmail.com

---

## 📞 Suporte

Se você tiver alguma dúvida ou problema, abra uma [issue](https://github.com/seu-usuario/IFTM_iZoo_PIE4-backend/issues) no GitHub.
