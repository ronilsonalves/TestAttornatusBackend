# Test Attornatus Back-end

## The challenge:
To use Spring Boot to create a simple API to management of people.
Allowing the following operations:


1. Create a person
2. Edit a person
3. Consult a person
4. List people
5. Create a person's address
6. List a person's addresses
7. Being able to inform a person's primary address

A person should have the following field structure:
```json
{
  "nome": "Ciclano",
  "dataDeNascimento": "1995-07-18",
  "enderecos": [
    {
      "logradouro": "Avenida Almirante Maximiano Fonseca",
      "cep": "96204-040",
      "numero": 16,
      "cidade": "Rio Grande"
    },
    {
      "logradouro": "Rua Serra de Bragança",
      "cep": "03318-000",
      "numero": 19,
      "cidade": "São Paulo"
    }
  ],
  "enderecoPrincipal": {
    "logradouro": "Rua Serra de Bragança",
    "cep": "03318-000",
    "numero": 19,
    "cidade": "São Paulo"
  }
}
```
## Requirements
 * All the anwsers should return a JSON object
 * Use H2 database

## The API Endpoints
[_**Here**_](https://brpessoasapplicationtest.azurewebsites.net/pessoas) you can see the API in action. _Please, don't abuse of them, it's running in a low instance of Azure App service._
1. Create a person:

``POST /pessoas``

2. Edit a person:

``PUT /pessoas/{pessoaId}``

3. Consult a person:

``GET /pessoas/{pessoaId}``

4. List people:

``GET /pessoas``

5. Create a person's address:

``PUT /pessoas/{pessoaId}/enderecos/criarEndereco``

6. List a person's addresses:

``GET /pessoas/{pessoaId}/enderecos``

7. Being able to inform a person's primary address:

``PUT /pessoas/{pessoaId}/enderecos/{posicaoEndereco}``