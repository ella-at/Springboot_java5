CP5 - JAVA
Deploy: https://springboot-java5-1.onrender.com

Integrantes:
Marcela Morera - RM 552051
Nathalia Braga - RM 552221
Daniele Vargas - RM 99400
Felipe


Este projeto consiste no desenvolvimento de uma aplicação de gestão para uma loja de brinquedos. Foi implementado em Java utilizando o Spring Boot como framework principal. A aplicação permite a autenticação de usuários, a realização de operações de vendas, verificação de estoque e outras funcionalidades fundamentais para o gerenciamento de uma loja. A comunicação entre cliente e servidor utiliza Sockets TCP para troca de mensagens, e criptografia RSA para garantir a segurança dessas comunicações.

Funcionalidades principais:


Integração com PostgreSQL:
Toda a persistência de dados, como informações sobre usuários, brinquedos e vendas, é feita utilizando o PostgreSQL. O banco de dados relacional foi escolhido para garantir que todas as operações de CRUD fossem realizadas de forma confiável e escalável. O Spring Data JPA foi usado para gerenciar a interação com o banco de dados de forma eficiente.
A aplicação utiliza o PostgreSQL para armazenamento de dados persistentes. O Spring Boot, através do Spring Data JPA, facilita a comunicação com o banco de dados, gerando queries automaticamente com base nas entidades Java.

![image](https://github.com/user-attachments/assets/49ed084b-12cb-45cb-86ec-40f3c23c4f99)

Essa configuração define a conexão com o banco de dados PostgreSQL, onde o Spring Data JPA cuida das operações de CRUD.


Uso de Sockets TCP para Comunicação

A comunicação entre o cliente e o servidor ocorre via Sockets TCP. O cliente se conecta ao servidor da loja de brinquedos utilizando sockets, e todas as trocas de mensagens (como login e operações de venda) são feitas por esse canal seguro.
O Cliente estabelece a conexão via TCP com o servidor.
O Servidor fica à escuta em uma determinada porta, aguardando conexões de clientes.
Exemplo de código no cliente para conexão via Socket TCP:

![image](https://github.com/user-attachments/assets/890e50ac-dbe6-4188-bdc1-281a6374797d)


Criptografia RSA para Comunicação Segura

Para garantir a segurança na comunicação, a aplicação utiliza RSA (Rivest–Shamir–Adleman) para criptografia assimétrica. O RSA é utilizado para criptografar mensagens enviadas entre o cliente e o servidor, garantindo que as credenciais e informações sensíveis sejam protegidas contra interceptações.
O Cliente gera um par de chaves RSA (pública e privada) usando o RSAKeyGenerator.
O Servidor e o Cliente trocam mensagens criptografadas usando suas respectivas chaves públicas e privadas.

![image](https://github.com/user-attachments/assets/d99ad539-add3-4757-ae26-534723154f2d)


CRUD (Create, Read, Update, Delete):

As operações de CRUD para os brinquedos foram implementadas com o uso de Spring Data JPA. A entidade Brinquedo é mapeada para o banco de dados, e os repositórios Spring permitem a execução de operações como salvar, atualizar, buscar e deletar registros no PostgreSQL.
A aplicação permite operações de CRUD sobre os brinquedos da loja, onde os administradores podem:
Criar novos brinquedos no catálogo.
Ler (listar) os brinquedos existentes.
Atualizar informações dos brinquedos, como nome, preço e quantidade em estoque.
Excluir brinquedos do catálogo quando necessário.

![image](https://github.com/user-attachments/assets/4ae55874-755b-4366-8bce-2d834d8824c9)
![image](https://github.com/user-attachments/assets/5cd5e132-b0ec-4036-9d5a-9b09fb83b9ce)
![image](https://github.com/user-attachments/assets/8b59ea7b-a1ea-49cf-a3e7-d2cee9b39c5a)

![image](https://github.com/user-attachments/assets/b9ffc87c-e62b-4aa5-acaa-23c74c8e2c8b)
![image](https://github.com/user-attachments/assets/8cc36f3d-6071-4a1f-bfe8-0e4316709038)

Com essa estrutura, as operações de CRUD são realizadas de forma automática, utilizando o repositório para consultar o banco de dados.



Processamento de Vendas:
O sistema permite que o usuário realize o processo de venda, selecionando brinquedos específicos e definindo a quantidade, a forma de pagamento e o responsável pela venda.
![image](https://github.com/user-attachments/assets/6660d070-27df-44f0-8f45-0213757e702f)
![image](https://github.com/user-attachments/assets/cdf4e03a-2351-4241-90d8-27270df1940b)
![image](https://github.com/user-attachments/assets/fe712045-3088-4ddc-8c33-a9bc7bf33b72)


Desenvolvimento do Aplicativo
O projeto foi estruturado em diversas camadas, seguindo o padrão de arquitetura MVC (Model-View-Controller), onde:
Model: Representa as entidades do sistema (brinquedos, usuários, etc.).
Controller: Trata as requisições HTTP e conecta a interface com a lógica de negócios.
Service: Contém a lógica de negócios.
Repository: Responsável pela interação com o banco de dados.

Tela de Login e Autenticação
A tela de login foi implementada com o Thymeleaf, um motor de templates para gerar conteúdo HTML dinâmico. Quando o usuário tenta acessar a aplicação, ele é redirecionado para a página de login.
![image](https://github.com/user-attachments/assets/67582124-2b49-4d98-9319-6a4fde745a43)
![image](https://github.com/user-attachments/assets/4c25386e-9d5f-4c35-a77e-c0908bcbc5bb)


Controlador de Login
No backend, o controlador LoginController lida com a requisição para a página de login:
![image](https://github.com/user-attachments/assets/5f3a28c9-c20c-47be-be90-4f2d3cc6005b)


Implementação da Autenticação e Gerenciamento de Usuários

O sistema de autenticação é baseada em credenciais (nome de usuário, senha). As senhas são criptografadas antes de serem armazenadas no banco de dados, garantindo segurança adicional. Contando tambem com a entrada do login diferenciada para cada tipo de usuario (administradores e usuários regulares, cada um com permissões diferentes.). 

![image](https://github.com/user-attachments/assets/c8822e86-aa2f-4961-8279-a253c6a3ea0a)
![image](https://github.com/user-attachments/assets/db79ac70-c23c-4b3b-9309-c274a9c8141b)

Esta configuração garante que a aplicação redirecione para a página de login quando necessário e permita o acesso a outros recursos apenas após a autenticação bem-sucedida. A aplicação gerencia diferentes tipos de usuários com diferentes permissões:
ADMIN: Possui acesso completo ao sistema, incluindo a gestão de vendas e estoque.
USER: Possui acesso limitado às operações, como consulta da lista de itens cadastrados.



Considerações Finais

Este projeto da Loja de Brinquedos foi projetado para oferecer uma solução completa para gerenciamento de vendas, controle de estoque e autenticação de usuários. A combinação de Spring Boot, PostgreSQL, Sockets TCP, e Criptografia RSA cria um sistema seguro e escalável. O uso de Spring Data JPA simplifica o gerenciamento dos dados, enquanto o Spring Security garante que o acesso seja devidamente controlado.

Se necessário, o projeto pode ser expandido para incluir mais funcionalidades, como integração com APIs externas para pagamentos.







