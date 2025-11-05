<h1 align="center"> iZoo</h1>

<!-- <p align="center">
  <strong>Interface web desenvolvida em React para o sistema iZoo - Projeto Integrador PIE4 (IFTM)</strong>
</p>

<p align="center">
  <a href="https://react.dev/"><img src="https://img.shields.io/badge/React-18.2.0-61dafb?style=flat&logo=react&logoColor=white"></a>
  <a href="https://vitejs.dev/"><img src="https://img.shields.io/badge/Vite-5.0+-646CFF?style=flat&logo=vite&logoColor=white"></a>
  <a href="https://www.npmjs.com/"><img src="https://img.shields.io/badge/npm-10+-CB3837?style=flat&logo=npm&logoColor=white"></a>
  <a href="LICENSE"><img src="https://img.shields.io/badge/Licença-MIT-green.svg?style=flat"></a>
</p>

--- -->

## Sobre o Projeto

**iZoo** é a interface web do projeto **PIE4 - Instituto Federal do Triângulo Mineiro (IFTM)**, desenvolvida para facilitar a interação com o sistema **iZoo**, uma plataforma voltada à **gestão, exibição e controle de informações sobre zoonoses**.

A ideia surgiu a partir da necessidade de facilitar o trabalho de agentes de saúde e oferecer mais transparência para a população.  
Cada residência cadastrada recebe um **QR Code exclusivo**, que permite registrar e consultar **inspeções e ocorrências** realizadas no local.

Além disso, qualquer cidadão pode acessar o **mapeamento das inspeções** em sua região, visualizando informações atualizadas diretamente pelo sistema — tudo de forma **intuitiva, responsiva e integrada ao backend**.

> 💡 Em resumo: o iZoo torna o controle de zoonoses mais eficiente, sustentável e acessível, unindo tecnologia e saúde pública.

---

## Tecnologias Utilizadas

Este projeto foi construído com as seguintes ferramentas e bibliotecas:

| Categoria                           | Tecnologia / Biblioteca                                                                                                                             | Descrição                                                                 |
| ----------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------- |
| **Framework principal**             | [Spring Boot](https://spring.io/projects/spring-boot)                                                                                               | Framework Java que simplifica a criação de aplicações web e APIs REST.    |
| **Ambiente de build**               | [Maven](https://maven.apache.org/)                                                                                                                  | Ferramenta de automação de build e gerenciamento de dependências Java.    |
| **Banco de dados**                  | [spring-boot-starter-data-mongodb](https://spring.io/projects/spring-data-mongodb)                                                                  | Integração com MongoDB, facilitando operações CRUD e repositórios.        |
| **Estilização / Camada Web**        | [spring-boot-starter-web](https://spring.io/projects/spring-boot)                                                                                   | Fornece suporte para construção de aplicações web RESTful.                |
| **Servidor local**                  | [spring-boot-starter-validation](https://spring.io/guides/gs/validating-form-input/)                                                                | Inclui servidor embutido (Tomcat) e suporte à validação de dados.         |
| **Validação de token (sessão)**     | [spring-session-data-mongodb](https://docs.spring.io/spring-session/reference/)                                                                     | Gerencia sessões de usuário e tokens usando MongoDB como armazenamento.   |
| **Variáveis de ambiente**           | [spring-dotenv](https://github.com/paulschwarz/spring-dotenv)                                                                                       | Carrega variáveis de ambiente a partir de arquivos `.env` para o Spring.  |
| **Segurança (autenticação/autorização)** | [spring-boot-starter-security](https://spring.io/projects/spring-security)                                                                         | Implementa autenticação, autorização e controle de acesso a endpoints.    |
| **Envio de e-mails**                | [spring-boot-starter-mail](https://docs.spring.io/spring-boot/reference/io/email.html)                                                              | Fornece suporte integrado para envio de e-mails através de SMTP.          |
| **Geração e validação de JWTs**     | [jjwt](https://github.com/jwtk/jjwt)                                                                                                                | Biblioteca para criação e verificação de tokens JWT para autenticação.    |
| **Documentação da API**             | [springdoc-openapi-starter-webmvc-ui](https://springdoc.org/)                                                                                       | Gera automaticamente documentação interativa da API (Swagger UI).         |

## Principais Recursos Técnicos

- Sistema de leitura através do **QR Code** para registro de inspeções
- Geração de **QR Codes dinâmicos**.
- Visualização geográfica através de **mapas interativos (Leaflet)**.
- Feedbacks instantâneos com **notificações (React Toastify)**.
- Interface **totalmente responsiva** desenvolvida com Tailwind CSS.
- Integração com **API backend iZoo**.

---

## 📁 Estrutura de Pastas

```
IFTM_iZoo_PIE4-backend/
├
└── em construção

```

---
