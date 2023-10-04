package extractor.dto;

import extractor.entity.ItemServicoEntity;
import lombok.Data;

@Data
public class ItemServicoDTO {

	private String conteudo;
	
	public static ItemServicoEntity toEntity(ItemServicoDTO dto) {
		var entitie = new ItemServicoEntity();
		entitie.setConteudo(dto.getConteudo());
		
		return entitie;
	}
	
}
