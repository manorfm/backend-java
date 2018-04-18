# ponto eletronico

Caso necessite rodar local, é necessário baixar o lombok.jar e rodar > java -jar lombok.jar

Não deu tempo ainda de fazer a completamente a parte de segurança, pra testes use a api aberta para cadastro do usuário.

projeto foi elaborado com java, com spring boots, spring data, orika e lombok e expoe as seguintes apis rest:

<br>

> link publicado da [API] (https://thawing-inlet-73583.herokuapp.com)

## APIs aberta - POST
> links de cadastro de usuário (https://thawing-inlet-73583.herokuapp.com/api/users) <br>
  com body:
  ```javascript
      {
        "pis": 132551,
        "name": "Manoel Medeiros",
        "password": "12345"
      }
 ```
 <br>
 
> links de cadastro de usuário (https://thawing-inlet-73583.herokuapp.com/api/clockin/create) <br>
  com body:
```javascript
    {
      "pis": 132551,
      "dateTime": "2018-04-18T08:15:50.485"
    }
```
 
 <br>

## APIs fechadas - GET
> links para listar usuários (https://thawing-inlet-73583.herokuapp.com/api/users) <br>
> links para acesso ao usuário pelo id (https://thawing-inlet-73583.herokuapp.com/api/users/${pis}) <br>
> links para acesso aos dados do ponto https://thawing-inlet-73583.herokuapp.com/api/clockin/user/${pis}/year/${year}/month/${month} <br>
<br>

## APIs fechadas - PUT
> links para acesso ao usuário pelo id (https://thawing-inlet-73583.herokuapp.com/api/users/${pis})
 <br>
 
## APIs fechadas - DELETE
> links para deletar o usuário pelo id (https://thawing-inlet-73583.herokuapp.com/api/users/${pis})
