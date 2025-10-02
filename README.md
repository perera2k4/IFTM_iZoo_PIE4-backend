```
http://localhost:8080/swagger-ui.html - Interface para testar endpoints
http://localhost:8080/v3/api-docs - OpenAPI em formato JSON
http://localhost:8080/v3/api-docs.yaml - OpenAPI em formato YAML
```

## 📖 Endpoints Documentados

### 👤 **Usuários** (`/usuario`)

#### Endpoints Públicos

- `POST /usuario/registro` - Registrar novo usuário
- `POST /usuario/login` - Fazer login e obter token JWT

#### Endpoints Protegidos (Requer Token JWT)

- `GET /usuario/{id}` - Buscar usuário por ID
- `GET /usuario` - Listar todos os usuários (apenas ADMIN)
- `GET /usuario/cargo?cargo={cargo}` - Buscar usuários por cargo (apenas ADMIN)
- `PUT /usuario/{id}` - Atualizar usuário completo
- `PATCH /usuario/{id}` - Atualizar usuário parcial
- `DELETE /usuario/{id}` - Deletar usuário (apenas ADMIN)

### 📍 **Endereços** (`/endereco`)

#### Todos os endpoints requerem autenticação JWT

##### CRUD Básico

- `GET /endereco` - Listar todos os endereços
- `GET /endereco/{id}` - Buscar endereço por ID
- `POST /endereco` - Criar novo endereço
- `PUT /endereco/{id}` - Atualizar endereço completo
- `PATCH /endereco/{id}` - Atualizar endereço parcial
- `DELETE /endereco/{id}` - Deletar endereço

##### Busca Avançada

- `GET /endereco/cidade/{cidade}` - Buscar por cidade
- `GET /endereco/estado/{estado}` - Buscar por estado (UF)
- `GET /endereco/buscar?cidade={cidade}&estado={estado}` - Buscar por cidade e estado
- `GET /endereco/com-localizacao` - Buscar endereços com coordenadas GPS

## 🔐 Autenticação

### Como usar a autenticação no Swagger:

1. **Registre um usuário** usando `POST /usuario/registro`
2. **Faça login** usando `POST /usuario/login` para obter o token
3. **Configure a autenticação** no Swagger:
   - Clique no botão "🔒 Authorize" no topo da página
   - Insira: `Bearer SEU_TOKEN_AQUI`
   - Clique em "Authorize"

### Exemplo de Token:

```
Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 🎯 Exemplos de Uso

### 📝 Registro de Usuário

```json
{
  "login": "bruno.pereira",
  "senha": "MinhaSenh@123",
  "cargo": "ADMIN",
  "email": "bruno@email.com",
  "telefone": "+5534999887766"
}
```

### 🔑 Login

```json
{
  "login": "bruno.pereira",
  "senha": "MinhaSenh@123"
}
```

### 📍 Criação de Endereço

```json
{
  "rua": "Rua das Flores",
  "numero": "123",
  "bairro": "Centro",
  "cidade": "Uberlândia",
  "estado": "MG",
  "latitude": -18.9111,
  "longitude": -48.2611
}
```

## ⚙️ Configurações

### Cargos Disponíveis:

- `ADMIN` - Administrador (acesso total)
- `USER` - Usuário comum
- `SUPERVISOR` - Supervisor
- `FUNCIONARIO` - Funcionário

### Formato do Telefone:

- Padrão: `+55XX9XXXXXXXX`
- Exemplo: `+5534999887766`

## 🚀 Como Iniciar

1. **Configure as variáveis de ambiente** no arquivo `.env`
2. **Inicie a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```
3. **Acesse a documentação:**
   ```
   http://localhost:8080/swagger-ui.html
   ```
