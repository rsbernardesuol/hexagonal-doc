# O que é DESIGN PATTERNS

**Uma solução para um problema que existe (só aplicar quando o problema existir)**

<b>Caso de Uso:</b> Imagine um cruzando gerando alto índice de acidentes, quais design patterns poderiam ser aplicados? 
 - <b>Semáforo:</b> É um padrão da industria então pode ser considerado um design-pattern, é muito utilizado no cruzamento de apenas 2(duas) ruas e tem custo baixo.
 - <b>Rotatória:</b> É um padrão da industria então pode ser considerado um design-pattern, é muito utilizado no cruzamento de várias vias e tem custo elevado.



# GUIDELINE

Hexagonal além de ser um Design Pattern é um guideline, ou seja uma diretriz, traz principios pré-definidados onde o objetivo é colher resultados previamente direcionados. 



# Para que serve?

Projetar e construir aplicativos de software, estabelecendo uma arquitetura moderna, robusta e altamente flexível, orientadas pelas premissas básicas da filosofia de desenvolvimento ágil:

1. Desenvolvimento orientado a TDD.

2. Foco nos requisitos de negócio.

3. Adiar decisões técnicas o máximo possível.



# Quando surgiu?

Elaborado e documentado em 2005 por Alistair Cockburn (https://en.wikipedia.org/wiki/Alistair_Cockburn)

- Um dos autores do manifesto agil 2001.

- Autor da metodologia Cristal Clear.

Foi idealizado para que as equipes de desenvolvimento pudessem aplicar ideias/premissas de ágil na elaboração da arquitetura de software. Perdeu força nos anos seguintes mas ressurgiu como um interesse global a partir de 2015.



# Qual objetivo?

Foco no requisito de negócio, ignorando dependências externas técnicas e infra estruturais (ex: interface grafica e banco de dados). A ideia é criar uma arquitetura que possa ser executada por usuários, programas e testes automatizados.
Essa arquitetura também é chamada de "Port and Adapters Patterns".



# Arquitetura Hexagonal

![Hexagonal](hexagonal2.png)



# Isolamento

A arquitetura hexagonal aplica <b>SoC(Separation of concerns)</b> e estabelece o princípio de modularizar a solução em <b>3 áreas distintas e isoladas:</b>

1. Centro como hexágono 
   - Toda a Lógica de negocio.
   - Pode conter código Orientado a Objetos, Orientado a Aspectros ou  Programação funcional.
   - Totalmente agnostico a qualquer tecnologia, framewark e infraestrutura de interfaces (gráficas, comunicações) e dispositivos externos.
   - Pode ter dependências de framewarks de serviços gerais, ex: Logg, IoC etc.
   

2. Lado superior esquerdo, fora do hexágono
   - Lado intercambiável e flexível
   - Onde o ator externo irá interagir e estimular a solução
   - Conterá <b>código de tecnologia específica</b> que irá disparar eventos na solução.

3. Lado inferior direito, fora do hexágono.
   - Lado intercambiável e flexível
   - Fornecerá os <b>serviços de infraestrutura</b> que a solução precisa para existir.
   - Conterá <b>código de tecnologia específica</b>, normalmente código que interage com o banco de dados, faz chamadas http etc.



# Atores

Fora do hexágono, temos qualquer coisa do mundo real com a qual o aplicativo interage. Essas coisas incluem seres humanos, outros aplicativos ou qualquer dispositivo de hardware ou software. Eles são chamados de atores.

1. Ator Primário Condutor
   - A interação é acionada pelo ator. 
   - É aquele que interage com o aplicativo para atingir um objetivo. Exemplos: Suites de test


2. Ator Secundário Conduzido
   - A interação é acionada pelo hexágono. 
   - Um ator secundários fornece funcionalidades necessárias ao hexágono para processar a lógica de negócios.
     Exemplos: banco de dados relacionais, nosql, serviços web http, stmp, sistema de arquivos e etc.

![Atores](atores.png)



# Dependências

A arquitetura hexagonal estabelece o seguinte princípio de dependências: "<b>somente de fora para dentro!</b>":

1. Lado esquerdo, os atores primários dependem do hexágono.

2. Lado direito, os atores secundários dependem do hexágono.

3. O centro, o hexágono não depende de ninguém, só dele mesmo.



# Portas

Hexagono 100% Isolado, a comunicação "de fora para dentro" deve ser feito através de uma metáfora chamada "porta". 

1. Portas primárias Condutor (Driver)
   - São os <b>casos de uso</b>. 
   - Devem estar <b>dentro do hexágono</b>.
   - Agnósticos a tecnologias.
   - Redirecionam as chamadas externas para dentro das operações de negócio.


2. Portas secundárias Conduzida (Driven)
   - Devem estar <b>fora do hexágono</b>.
   - Utilizam <b>tecnologia específica</b>.
   - Convertem chamada de negócio em alguma necessidade infraestrutural e externa a solução.



# Adaptadores

Funcionam como um <b>"adaptador de tomada"</b> que fazem <b>"ponte"</b> para o que <b>hexágono</b> possa ter input de dados do lado esquerdo e ter acesso aos serviços de infraestrutura do lado direito. 


![Adaptadores](adaptadores.png)

1. Adaptador Condutor (Driver)
   - Converte uma <b>solicitação de tecnologia específica</b> em uma <b>solicitação agnóstica e pura de sistema</b>.
   - Faz integração do lado <b>de fora para dentro</b> do hexágono.
   - São classes OOP que usam <b>frameworks e tecnologias específicas</b>.
   - Repassam as operações para a porta primária.
   - Para cada porta condutora, deve haver pelo <b>menos dois adaptadores</b> (um para testar o comportametno via TDD e outro usando tecnologia real).
   Ex: Suite de testes, GUI de um aplicativo, Fila de Mensageria, Classes de teste junit, etc...


2. Adaptador Conduzido (Driven)
   - Converte chamadas <b>de dentro da solução para fora</b>
   - Faz integração <b>de dentro para fora</b> do hexágono.
   - São classes OOP que usam <b>frameworks e tecnologias específicas</b>.
   - Para cada porta conduzida, dev haver pelo <b>menos dois adaptadores</b> (um para o dispositivo do mundo real e outro simulado/mock)
   Ex: Classe DAO via JDBC, Classes cliente consumidor de um rest, Classe envio de sms, etc ...
   
   

# Fluxo de Execução

<b>Na teroria</b>

1. Lado esquerdo, os atores primários dependem do hexágono.

2. Lado direito, os atores secundários dependem do hexágono.

3. O centro, o hexágono não depende de ninguém, só dele mesmo.


<b>Na prática</b>

1. O centro, o <b>hexágono depende do lado direito</b>.

2. Lado direito, os <b>atores secundários não dependem</b> do hexágono.


<b>Como resolver isso?</b>

<b>Inversão de Controle (Inversion of Control - IoC)</b>

É um padrão arquitetural, uma técnica de arquitetura de software usada para <b>inverter uma linha de dependência em um bloco arquitetural</b>. Se o componente A [->depende->] B, usando IoC é possível fazer inverter a seta, fazendo com que A [<- dependa IoC<-] B. 

A arquitetura hexagonal aplica IoC, estabelecendo o princípio modular que <b>o lado de fora direito tem dependência ao hexágono via IoC!</b>

   
   
# Opções de Design?

<b>Você ﬁca livre para organizar</b> seu projeto da forma que quiser, balanceando os <b>prós e contras</b> de cada opção, usando seu <b>know-how</b> e <b>os recursos</b> que sua plataforma oferece, desde que: cada opção não <b>fure</b> os princípios hexagonal.

<b>Organização do Projeto</b>

1. Um projeto com os 3 módulos, separado por pacotes simples.
2. Um projeto com os 3 módulos, separado por java modules 9.
3. Três projetos diferentes, uma para cada módulo, usado jar manual.
4. Três projetos diferentes, uma para cada módulo, usando jar via maven system.
5. Três projetos diferentes, uma para cada módulo, usando jar via maven repositorio local jfrog - https://jfrog.com/open-source/
7. N projetos diferentes, uma para cada módulo, um para cada front-end, um para cada back-service: database, nosql, sms, webservice etc, usando jar manual, maven system ou maven repositório local jfrog.

<b>Organização de Pacotes</b>

1. Por tipos (Type)
2. Por camada (Layer)
3. Por serviço (Feature)
4. Por metáforas hexagonal (Ports, Adapters, etc)
5. Miscelânea

<b>Implementação das regras de negócio dentro do hexágono</b>

1. Transactions Script - EAA Pattern (https://martinfowler.com/eaaCatalog/transactionScript.html).

2. Domain Model - EAA Pattern (https://martinfowler.com/eaaCatalog/domainModel.html).

3. Service Layer - EAA Pattern (https://martinfowler.com/eaaCatalog/serviceLayer.html).

4. Anemic Domain Model - EAA Pattern (https://martinfowler.com/bliki/AnemicDomainModel.html).

5. Domain Driven Design - DDD (Eric Evans - https://www.oreilly.com/library/view/domain-driven-design-tackling/0321125215/).

6. Clean Architecture - Entities e Use Cases (Uncle Bob - https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).


<b>Organização das Portas Primárias -  Casos de Usos</b>

   - Uma porta primária é uma interface que você vai colocar para fora do hexágono.

   - Alguma classe controladora dentro do hexágono vai implementar e responder por aquela interface.
 
 
 Existem formas diferentes de como que você expõe isso:
 
 1. Uma única interface com várias operações distintas (Utilizada em projetos menores).
   Ex: 4 regras de negócios distintas e uma única interface

 2. Várias interfaces, cada uma agrupando operações relacionadas.
   Ex: Hexágono faz venda, compra e aluguel. Você vai ter uma interface para cada com seu eventos (Venda->verPreco, Venda->gerarOrcamento) 

 3. Uso do padrão de projeto: Command Bus (Interfaces dinâmicas e flexíveis)
    - Usado em operações que contém alterações.
    - Forma dinâmica em tempo de execução para descobrir o que o usuário de fora está querendo fazer (Programação diânica ou Reflection).

 4. Uso do padrão de projeto: Command Query (Interfaces dinâmicas e flexíveis). 
    - Usado em operações que contém apenas resposta de consulta e não vai alterar nada.
    - Forma dinâmica em tempo de execução para descobrir o que o usuário de fora está querendo fazer (Programação diânica ou Reflection).


<b>Transferência de dados</b>

A interfaces de casos de uso podem ser projetados usando as seguintes opções:

1. Variáveis simples máximo de 4 [Joshua Bloch Java Effective item 40].
2. Padrão de Projeto Parameter Object (https://refactoring.guru/introduce-parameter-object).
3. Padrão de Projeto Value Object (https://deviq.com/value-object/).
4. Expor objeto de domínio como parâmetro.
5. Objetos expansíveis como HashMap.
6. Padrão de projeto “Typesafe Heterogeneous Container Pattern” (https://fernandofranzini.wordpress.com/2013/02/28/generics-item-2
9/).
7. Objeto dinâmicos no uso de linguagens dinâmicas: groovy Expando (https://mrhaki.blogspot.com/2009/10/groovy-goodness-expando-as-
dynamic-bean.html).


<b>Uso de objetos polimórﬁcos de backservices</b>

1. Fazer IoC desses objetos em qualquer outro objeto dentro do hexágono, <b>por livre demanda</b>. Assim todos estes terão dependência com os backservices e deverão entrar no gerenciamento IoC. Isso pode ter prós e contras, dependendo de como foi organizado o hexágono.

2. <b>Limitar</b> o IoC desses objetos <b>somente nas implementações das portas primárias de caso de uso</b>. Assim, somente esses objetos terão dependência com
os backservices e deverão entrar no gerenciamento IoC. Outros objetos, de outros serviços dentro do hexágono, não terão dependência com o backservices e
poderão funcionar fora do IoC. Isso pode ter prós e contras dependendo de como foi organizado o hexágono.

# Sequência de Desenvolvimento?

  - PASSO 1 - Hexágono (Centro - Build 1: Adaptadores Testes -> Sistema <- Adaptadores Mocks)
    - O ponto de partida é implementar o hexágono como uma caixa preta, com as interfaces de portas deﬁnidas em torno dela, tanto no lado esquerdo, quanto     no lado direito. Ou seja, implementar, testar e validar os serviços do sistema, sem tela, sem infraestrutura, usando TDD e mocks.
    
  - PASSO 2 - Front-End (Lado Esquerdo - Build 2: Adaptadores Interface Gráfica -> Sistema <- Adaptadores Mocks.)
    - Implementar os adaptadores primários condutores, plugando na porta do hexágono. Ou seja, implementar, testar e validar a tecnologia e frameworks de front-end, sem serviços de infraestrutura.
  
  - PASSO 3 - Back-Services (Lado Direito - Build 3: Adaptadores Interface Gráﬁca -> Sistema <- Adaptadores Serviços Homologação)
    - Implementar os adaptadores secundários dirigidos, plugando na porta do hexágono. Ou seja, implementar, testar e validar a tecnologia e frameworks de referente as serviços necessários para a solução.
  
  - PASSO 4 - Build de Produção (Build 4: Conﬁgurar o ambiente de produção e fazer o build oﬁcial ﬁnal)
  
  
# Projeto prático - Transferência Bancária?  

Vamos desenvolver, testar e executar um projeto hexagonal completo com Java. 

Ele será pequeno, mas suﬁciente para implementar todo o ciclo e as decisões mais fundamentais sobre esse padrão arquitetural.



![Hexagonal](hexagonal_impl.png)


<b>Protótipo:</b>

![Prototipo](prototipo.png)


<b>Base de Dados Relacional:</b>

```
create table conta (
numero integer primary key,
saldo decimal (10,2),
correntista varchar(200)
);

```

<b>Ferramentas utilizadas</b>

Intellij, Tdd, JUnit, Sintaxe Java, OOP, Polimorﬁsmo, DbC, Spring e JavaFX.

# Decisões de Design: Transferência Bancária

<b>Projeto</b>

 - 3 projetos separados: sistema.jar (hexagono), servicos.jar (lado direito) e desktop (lado esquerdo).
 - Dependências frameworks via maven.
 - Dependências hexagonal via maven system local.
 - Organização de pacotes misto de DDD e metáforas hexagonal.
 - Organização de negócio via EAA: Modelo de Domínio e Serviço de Domínio.
 - Organização das Portas Primárias: várias interfaces, cada uma agrupando operações relacionadas. Mas no projeto teremos apenas 1 grupo.
 - Transferência de dados: variáveis simples.
 - Consumo de objetos de backservices somente nos objetos de portas, deixando a regra de negócio puro POJO sem dependência com o mundo externo. 

<b>Tecnologias</b>

 - Dependências frameworks java via maven.
 - Banco de dados relacional usaremos HSQDB. Para desenvolvimento e homologação vamos usar em modo "embedded database". Para produção vamos usar em modo    "local jvm".
 - Front-End usaremos desktop via JavaFX.
 - Provedor de persistência Spring JDBC.
 - Provedor de transação Spring Transaction.
 - Provedor de IoC Spring IoC.
 - Validação de negócio manual, nenhum framework.
 - Builds IoC via Java Conﬁg manual, nenhum framework. 


# Códigos Fonte

<b>Parte1: Projeto Sistema (Hexágono)</b>

https://github.com/rsbernardesuol/hexagonal-doc/tree/main/sistema


<b>Parte 2: Projeto Front-End (Lado Esquerdo)</b>

https://github.com/rsbernardesuol/hexagonal-doc/tree/main/desktop


<b>Parte 3: Projeto Serviços (Lado Direito)</b>

https://github.com/rsbernardesuol/hexagonal-doc/tree/main/servicos



# Quando não usar?

Projetos <b>temporais</b>, de <b>menor porte</b> ou <b>legados</b>, no qual não se tenha previsão de alteração de tipo de front-end ou de back services.

Projetos que fazem uso de <b>regras de negócio dentro de banco de dados</b>, amarrados na tecnologia, marca e provedor proprietário.


# Quando usar?

Projetos de <b>médio</b> e <b>grande porte</b>, que supostamente possuem um <b>longo ciclo de vida</b>, e que precisaram ser modiﬁcados muitas vezes durante sua vida útil, que sofreram de <b>erosão arquitetural</b> e <b>dívida técnica</b>.
Pois em curto prazo, o investimento se reverte em lucro pela quantidade e velocidade das mudanças.

Projetos de <b>qualquer porte</b> que precisam ser desenvolvidos sem <b>amarrações</b> com <b>front-end</b> e <b>back services</b>.


# Conclusão

Não existe bala de prata. A arquitetura hexagonal tem se destacado como uma ótima opção de arquitetura, pois o custo é baixo (poucos pontos negativos) e retorno é alto é rápido (muitos pontos positivos).

É uma das várias opções de padrões arquiteturais encontradas no mercado hoje: DDD, Onion e Clean Architecture.

Analise cada um e faça sua decisão baseada em prós e contras, contexto e necessidade.


# BIBLIOGRAFIA


![Livro1](livro1.jpg)

![Livro2](livro2.jpg)

![Livro2](livro3.jpg)
