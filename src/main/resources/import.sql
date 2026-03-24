-- Insira categorias primeiro (se houver a tabela)
INSERT INTO tb_categoria (nome) VALUES ('Eletrônicos'), ('Games');

-- Insira produtos (Ajuste os nomes das colunas conforme sua entidade)
INSERT INTO tb_produto (nome, descricao, preco, estoque)
VALUES ('Mouse Gamer', 'RGB 12000 DPI', 150.00, 10);

INSERT INTO tb_produto (nome, descricao, preco, estoque)
VALUES ('Teclado Mecânico', 'Switch Blue ABNT2', 250.00, 5);