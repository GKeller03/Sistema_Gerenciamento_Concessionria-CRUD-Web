# AutoHub — Concessionária Online

> Sistema web completo de gerenciamento de concessionária com controle de estoque, vendas, oficina e autenticação por níveis de acesso — desenvolvido em Java puro com Servlets, JSP e arquitetura MVC.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/Apache%20Tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black)
![JSP](https://img.shields.io/badge/JSP-007396?style=for-the-badge&logo=java&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-4479A1?style=for-the-badge&logo=databricks&logoColor=white)
![MVC](https://img.shields.io/badge/Pattern-MVC-blueviolet?style=for-the-badge)
![Command Pattern](https://img.shields.io/badge/Pattern-Command-orange?style=for-the-badge)

---

## 📸 Preview

| Login | Cadastro Cliente | Cadastro Admin |
|-------|-----------------|----------------|
| ![Login](screenshots/login.png) | ![Cadastro Cliente](screenshots/cadastro-cliente.png) | ![Cadastro Admin](screenshots/cadastro-admin.png) |

| Dashboard Admin | Dashboard Cliente | Estoque |
|----------------|-------------------|---------|
| ![Admin](screenshots/dashboard-admin.png) | ![Cliente](screenshots/dashboard-cliente.png) | ![Estoque](screenshots/estoque.png) |

| Controle de Oficina | Histórico | Área de Compras |
|--------------------|-----------|----------------|
| ![Oficina](screenshots/oficina.png) | ![Histórico](screenshots/historico.png) | ![Compras](screenshots/pedido.png) |

---

## 📋 Sobre o Projeto

O **AutoHub** é uma aplicação web para gerenciamento de concessionárias desenvolvida em Java puro, que cobre todo o ciclo de um sistema real. O projeto inclui autenticação com controle de sessão, dois perfis de usuário (Administrador e Cliente) com dashboards distintos, CRUD completo de veículos, fluxo de compra e gerenciamento de oficina com histórico de manutenções.

A aplicação segue o padrão MVC com separação clara entre camadas, utiliza conceitos de orientação a objetos como herança, além do Command Pattern para organizar as ações de negócio. Conta também com integração com MySQL via JDBC, proteção de rotas no servidor, controle de estado das entidades e uma interface moderna em dark theme construída com HTML e CSS puro.


---

## ✨ Funcionalidades

### 👤 Autenticação e Controle de Acesso
- Login com e-mail e senha
- Cadastro de novos usuários com campos distintos por perfil: CPF e telefone para Cliente, cargo para Administrador
- Dois perfis: Administrador e Cliente
- Dashboards distintos — cada perfil vê apenas o que lhe compete
- Proteção de rotas no servidor — redirecionamento automático por nível de acesso

### 🚗 Gestão de Estoque — Administrador
- Cadastro de veículos com modelo, preço, placa, cor e ano
- Listagem em cards com status dinâmico: `Disponível` · `Vendido` · `Manutenção`
- Edição e exclusão de veículos

### 🔧 Controle de Oficina — Administrador
- Tabela de veículos em reparo ativo com entrada, serviço, revisão e status
- Botão Finalizar para encerrar a manutenção e atualizar o status do veículo
- **Ver Histórico** — lista completa de manutenções concluídas com data de saída e status final

### 🛒 Área de Compras — Cliente
- Dashboard com acesso a Visualizar Estoque e Fazer Pedido
- Catálogo de veículos disponíveis com preço, ano, cor e status
- Botão Comprar Agora para registrar o pedido diretamente

---

## 🛠️ Tecnologias e Bibliotecas

| Tecnologia | Versão | Uso |
|-----------|--------|-----|
| **Java** | JDK 25 | Linguagem principal |
| **Jakarta Servlets** | 9.0 | Controladores HTTP |
| **JSP** | Jakarta EE | Views dinâmicas no servidor |
| **JDBC** | — | Acesso ao banco de dados |
| **MySQL Connector/J** | 9.6.0 | Driver de conexão com MySQL |
| **MySQL** | 8.0 | Banco de dados relacional |
| **Apache Tomcat / TomEE** | — | Servidor de aplicação |
| **HTML / CSS** | — | Interface dark theme personalizada |

---

## ⚙️ Pré-requisitos

- [JDK 11+](https://www.oracle.com/java/technologies/downloads/)
- [Apache Tomcat 10+](https://tomcat.apache.org/download-10.cgi)
- [MySQL 8.0+](https://dev.mysql.com/downloads/)
- [Eclipse IDE for Enterprise Java Developers](https://www.eclipse.org/downloads/)

---

## 🚀 Como Executar

### 1. Clone o repositório

```bash
git clone https://github.com/GKeller03/AutoHub-Concessionaria-CRUD-Web.git
cd AutoHub-Concessionaria-CRUD-Web
```

### 2. Configure o banco de dados

Execute o script abaixo no MySQL Workbench ou via terminal:

```sql
CREATE DATABASE autohub;
USE autohub;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario ENUM('Administrador', 'Cliente') NOT NULL
);

CREATE TABLE carro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    modelo VARCHAR(100) NOT NULL,
    placa VARCHAR(10) UNIQUE NOT NULL,
    cor VARCHAR(30),
    ano INT,
    preco DECIMAL(10,2),
    status ENUM('Disponível', 'Vendido', 'Manutenção') DEFAULT 'Disponível'
);

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_carro INT NOT NULL,
    data_pedido DATE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    FOREIGN KEY (id_carro) REFERENCES carro(id)
);

CREATE TABLE manutencao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_carro INT NOT NULL,
    descricao TEXT,
    data_entrada DATE NOT NULL,
    data_saida DATE,
    em_revisao BOOLEAN DEFAULT TRUE,
    status_final VARCHAR(50),
    FOREIGN KEY (id_carro) REFERENCES carro(id)
);

INSERT INTO usuario (nome, email, senha, tipo_usuario)
VALUES ('Admin', 'admin@autohub.com', 'admin123', 'Administrador');

INSERT INTO usuario (nome, email, senha, tipo_usuario)
VALUES ('Cliente Teste', 'cliente@autohub.com', 'cliente123', 'Cliente');
```

### 3. Configure a conexão com o banco

Edite o arquivo `src/util/DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/autohub";
private static final String USER = "root";
private static final String PASSWORD = "sua_senha";
```

### 4. Importe e execute no Eclipse

1. `File > Import > Existing Projects into Workspace`
2. Selecione a pasta do projeto clonado
3. Confirme que o **Apache Tomcat** está configurado em `Window > Preferences > Server`
4. Clique com o botão direito no projeto → `Run As > Run on Server`
5. Acesse: `http://localhost:8080/autohub`

---

## 👨‍💻 Autor

**Gabriel Keller**
- LinkedIn: [(https://www.linkedin.com/in/-gabriel-keller/)]
- Email: gabrielkeller03052005@gmail.com

<p align="center">

