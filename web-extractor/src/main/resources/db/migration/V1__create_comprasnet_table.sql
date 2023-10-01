CREATE TABLE IF NOT EXISTS tbcomprasnet (
    id_oportunidade BIGSERIAL PRIMARY KEY,
    orgao VARCHAR(500) NOT NULL,
	uasg INTEGER NOT NULL,
	modalidade VARCHAR(100) NOT NULL,
	objeto TEXT NOT NULL,
	data_edital VARCHAR(200),
	endereco TEXT NOT NULL,
	telefone VARCHAR(100) NOT NULL,
	fax VARCHAR(100),
	data_entrega_proposta VARCHAR(100) NOT NULL,
	url_edital TEXT NOT NULL
);