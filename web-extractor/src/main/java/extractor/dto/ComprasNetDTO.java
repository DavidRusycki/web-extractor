package extractor.dto;

import java.util.ArrayList;
import java.util.stream.Collectors;

import extractor.entity.ComprasNetEntity;
import extractor.entity.ItemServicoEntity;
import lombok.Data;

@Data
public class ComprasNetDTO extends BaseDto {

	private String orgao;
	private Integer uasg;
	private String modalidade;
	private String objeto;
	private String dataEdital;
	private String endereco;
	private String telefone;
	private String fax;
	private String dataEntregaProposta;
	private ArrayList<ItemServicoDTO> itens = new ArrayList<ItemServicoDTO>();
	private String urlEdital;
	private Boolean visto;
	
	public void addItem(ItemServicoDTO dto) {
		itens.add(dto);
	}
	
	public void removeItem(ItemServicoDTO dto) {
		itens.remove(dto);
	}
	
	public static ComprasNetEntity toEntity(ComprasNetDTO dto) {
		var entity = new ComprasNetEntity();
		
		entity.setOrgao(dto.getOrgao());
		entity.setUasg(dto.getUasg());
		entity.setModalidade(dto.getModalidade());
		entity.setObjeto(dto.getObjeto());
		entity.setDataEdital(dto.getDataEdital());
		entity.setEndereco(dto.getEndereco());
		entity.setTelefone(dto.getTelefone());
		entity.setFax(dto.getFax());
		entity.setDataEntregaProposta(dto.getDataEntregaProposta());
		entity.setUrlEdital(dto.getUrlEdital());
		
		var itens = dto.itens.stream().map((item) -> ItemServicoDTO.toEntity(item).getConteudo()).collect(Collectors.toList());
		entity.getItens().addAll(itens);
		
		return entity;
	}
	
}
