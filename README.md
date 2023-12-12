# Documentação da solução do candidato

## Opções técnicas para as questões sugeridas:

### Escolha de arquitetura

Por se tratar de um projeto com apenas uma classe, optei por criar uma API monolítica dividida em camadas. Além das tradicionais Repositories, Services e Controllers, implementei o uso de DTOs na camada de controle. Foram criados dois perfis, um teste com o banco em memória H2, e um de conexão com o docker-compose do desafio. Devido ao tempo para implementar solução, implementei apenas testes unitários por meio do Mockito e Junit para todos os métodos de Service e Controller. Poderia ter implementado uma dependência como o Jacoco para acompanhamento da cobertura de testes, mas essa, assim como outras boas práticas iriam demandar ainda mais tempo, conforme explicarei em tópico seguinte. Para o controle de transação, não utilizei a melhor solução possível, mas pelo escopo do projeto, achei razoável permitir que apenas uma thread acessasse uma operação de cada vez com a notação @Transactional. Já para o tópico extra a fim de que fossem evitados ifs, utilizei os streams e optional que o Java fornece. 

### Métodos de CRUD

Segui todos os padrões sugeridos para o desafio, inclusive nas respostas às requisições nos endpoints. Em relação à operação de transação, foi criado um TransacaoController, porém ressalta-se que não foi persistida em banco, utilizando por injeção o CartaoService. Faço também a observação de que optei por String para o número do cartão em vez de um número específico. Poderia ter colocado número, controlado pelas validações @Length do JPA, entre outras práticas. Ou, ainda mais específico, ter mantido string e ter colocado alguma validação em uma camada para que fosse analisado se o usuário digitou apenas números. No entanto, seria mais um tópico a consumir tempo e que não foi exatamente especificado. 

### Boas práticas que não foram implementadas

* Segurança: Apesar de as senhas em nenhum momento estarem expostas na camada de view, isto é, visíveis à aplicação, no banco são armazenadas sem criptografia, o que seria um comprometimento de segurança. Numa aplicação comercial certamente seria utilizado o Spring Security com mais uma camada de autenticação também. 

* DTO na camada de service: Também é uma formalização pela qual não optei apenas por questão de tamanho do código e tempo. 

* Jacoco: É uma dependência bem útil para acompanhamento de testes e cobertura no Spring Boot. 

* Implementação das exceptions: No geral acho mais interessante ter um @ControllerAdvice, que vai direcionar a aplicação a uma ação toda vez que disparar determinada exceção. Essa escolha é feita em nível de controle, e é mais prática em termos de manutenção. É boa prática criar um erro padrão que é retornado no JSON. Comecei a fazer a classe Standard Error em cima desse padrão, porém, por se tratarem de retornos mais simples nas exigências técnicas, não utilizei. A classe foi mantida apenas como símbolo da ideia. 

## Como testar a API

1. Primeiro deve ser criada a imagem do docker-compose no computador por meio do comando "docker compose up -d". Como optei pelo MYSQL, utilizei o comando "docker exec -it mysql mysql" para iniciar o container. 

2. Observe o application properties: Existe o profile htest para o H2 e o mysqlnodocker para o docker-compose proposto. É interessante testar em ambos.

3. Implementei o swagger, que é um bom auxiliar de testes, tornando desnecessário o uso de um postman por exemplo. Basta acessar "http://localhost:8096/swagger-ui/index.html". Implementei a aplicação na porta 8096 porque não é incomum a porta 8080 estar ocupada. 

4. Quem testa a API não pode se esquecer de antes de fazer operações de saldo, alterar o saldo via PUT.

![image](https://github.com/enrocha0312/desafio_vrbeneficios_elumini/assets/48969751/3f96f65c-ff0c-48af-b661-3c59a57e6da4)
![image](https://github.com/enrocha0312/desafio_vrbeneficios_elumini/assets/48969751/eda9c153-8f09-4c4b-a9db-647c65be903e)

