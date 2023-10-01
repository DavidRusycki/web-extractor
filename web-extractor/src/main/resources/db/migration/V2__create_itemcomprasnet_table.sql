CREATE TABLE IF NOT EXISTS tbitemcompras (
    id BIGSERIAL PRIMARY KEY,
    id_oportunidade BIGSERIAL NOT NULL,
	conteudo TEXT NOT NULL
);

ALTER TABLE tbitemcompras ADD CONSTRAINT fk_tbcomprasnet FOREIGN KEY (id_oportunidade) REFERENCES tbcomprasnet(id_oportunidade) ON DELETE CASCADE;
