CP5 - JAVA
Deploy: https://springboot-java5-1.onrender.com



Este projeto consiste no desenvolvimento de uma aplicação de gestão para uma loja de brinquedos. Foi implementado em Java utilizando o Spring Boot como framework principal. A aplicação permite a autenticação de usuários, a realização de operações de vendas, verificação de estoque e outras funcionalidades fundamentais para o gerenciamento de uma loja. A comunicação entre cliente e servidor utiliza Sockets TCP para troca de mensagens, e criptografia RSA para garantir a segurança dessas comunicações.

Funcionalidades principais:

Autenticação de Usuário:
O sistema conta com um sistema de login para diferentes tipos de usuários (administradores e usuários regulares, cada um com permissões diferentes.). A tela de login permite que usuários entrem no sistema com suas credenciais (nome de usuário e senha), sendo verificada a autenticidade das informações.
O ADMIN tem controle total sobre o sistema, enquanto o USER tem permissões limitadas.

Integração com PostgreSQL:
Toda a persistência de dados, como informações sobre usuários, brinquedos e vendas, é feita utilizando o PostgreSQL. O banco de dados relacional foi escolhido para garantir que todas as operações de CRUD fossem realizadas de forma confiável e escalável. O Spring Data JPA foi usado para gerenciar a interação com o banco de dados de forma eficiente.
A aplicação utiliza o PostgreSQL para armazenamento de dados persistentes. O Spring Boot, através do Spring Data JPA, facilita a comunicação com o banco de dados, gerando queries automaticamente com base nas entidades Java.

Essa configuração define a conexão com o banco de dados PostgreSQL, onde o Spring Data JPA cuida das operações de CRUD.

Uso de Sockets TCP para Comunicação
A comunicação entre o cliente e o servidor ocorre via Sockets TCP. O cliente se conecta ao servidor da loja de brinquedos utilizando sockets, e todas as trocas de mensagens (como login e operações de venda) são feitas por esse canal seguro.
O Cliente estabelece a conexão via TCP com o servidor.
O Servidor fica à escuta em uma determinada porta, aguardando conexões de clientes.
Exemplo de código no cliente para conexão via Socket TCP:


Criptografia RSA para Comunicação Segura
Para garantir a segurança na comunicação, a aplicação utiliza RSA (Rivest–Shamir–Adleman) para criptografia assimétrica. O RSA é utilizado para criptografar mensagens enviadas entre o cliente e o servidor, garantindo que as credenciais e informações sensíveis sejam protegidas contra interceptações.
O Cliente gera um par de chaves RSA (pública e privada) usando o RSAKeyGenerator.
O Servidor e o Cliente trocam mensagens criptografadas usando suas respectivas chaves públicas e privadas.


CRUD (Create, Read, Update, Delete):
As operações de CRUD para os brinquedos foram implementadas com o uso de Spring Data JPA. A entidade Brinquedo é mapeada para o banco de dados, e os repositórios Spring permitem a execução de operações como salvar, atualizar, buscar e deletar registros no PostgreSQL.
A aplicação permite operações de CRUD sobre os brinquedos da loja, onde os administradores podem:
Criar novos brinquedos no catálogo.
Ler (listar) os brinquedos existentes.
Atualizar informações dos brinquedos, como nome, preço e quantidade em estoque.
Excluir brinquedos do catálogo quando necessário.

Com essa estrutura, as operações de CRUD são realizadas de forma automática, utilizando o repositório para consultar o banco de dados.


Gestão de Estoque:
Após a autenticação, o usuário tem acesso ao gerenciamento de brinquedos no estoque. A verificação de disponibilidade de produtos e sua quantidade é facilitada pelo sistema.

Processamento de Vendas:
O sistema permite que o usuário realize o processo de venda, selecionando brinquedos específicos e definindo a quantidade, a forma de pagamento e o responsável pela venda.
Desenvolvimento do Aplicativo

O projeto foi estruturado em diversas camadas, seguindo o padrão de arquitetura MVC (Model-View-Controller), onde:

Model: Representa as entidades do sistema (brinquedos, usuários, etc.).
Controller: Trata as requisições HTTP e conecta a interface com a lógica de negócios.
Service: Contém a lógica de negócios.
Repository: Responsável pela interação com o banco de dados.
Tela de Login e Autenticação
A tela de login foi implementada com o Thymeleaf, um motor de templates para gerar conteúdo HTML dinâmico. Quando o usuário tenta acessar a aplicação, ele é redirecionado para a página de login.

Exemplo de Código: Controlador de Login
No backend, o controlador LoginController lida com a requisição para a página de login:


Implementação da Autenticação
O sistema de login foi implementado usando Spring Security, o que permite autenticação baseada em credenciais (nome de usuário e senha). As senhas são criptografadas antes de serem armazenadas no banco de dados, garantindo segurança adicional.

Esta configuração garante que a aplicação redirecione para a página de login quando necessário e permita o acesso a outros recursos apenas após a autenticação bem-sucedida.


Gerenciamento de Usuários
A aplicação gerencia diferentes tipos de usuários com diferentes permissões:
ADMIN: Possui acesso completo ao sistema, incluindo a gestão de vendas e estoque.
USER: Possui acesso limitado às operações, como consulta de estoque e visualização de brinquedos.


Considerações Finais
Este projeto da Loja de Brinquedos foi projetado para oferecer uma solução completa para gerenciamento de vendas, controle de estoque e autenticação de usuários. A combinação de Spring Boot, PostgreSQL, Sockets TCP, e Criptografia RSA cria um sistema seguro e escalável. O uso de Spring Data JPA simplifica o gerenciamento dos dados, enquanto o Spring Security garante que o acesso seja devidamente controlado.

Se necessário, o projeto pode ser expandido para incluir mais funcionalidades, como relatórios de vendas e integração com APIs externas para pagamentos.







