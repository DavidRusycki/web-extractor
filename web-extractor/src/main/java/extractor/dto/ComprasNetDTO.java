package extractor.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ComprasNetDTO {

	private String orgao;
	private Integer uasg;
	private String modalidade;
	private String objeto;
	private String dataEdital;
	private String endereco;
	private String telefone;
	private String fax;
	private String dataEntregaProposta;
	private ArrayList<ItemServicoDTO> itens;
	private String urlEdital;
	
}
