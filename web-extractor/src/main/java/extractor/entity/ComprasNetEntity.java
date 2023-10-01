package extractor.entity;

import java.math.BigInteger;
import java.util.ArrayList;

import extractor.dto.ItemServicoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
@Table(name = "tbcomprasnet")
public class ComprasNetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger idOportunidade;
	
	@NotNull
	@NotBlank
	private String orgao;
	
	@NotNull
	private Integer uasg;
	
	@NotNull
	@NotBlank
	private String modalidade;
	
	@NotNull
	@NotBlank
	private String objeto;
	
	@NotNull
	@NotBlank
	private String dataEdital;
	
	@NotNull
	@NotBlank
	private String endereco;
	
	@NotNull
	@NotBlank
	private String telefone;
	
	private String fax;
	
	@NotNull
	@NotBlank
	private String dataEntregaProposta;
	
//	private ArrayList<ItemServicoEntity> itens = new ArrayList<ItemServicoEntity>();
	
	@NotNull
	@NotBlank
	private String urlEdital;
	
//	public void addItem(ItemServicoEntity entity) {
//		itens.add(entity);
//	}
//	
//	public void removeItem(ItemServicoEntity entity) {
//		itens.remove(entity);
//	}
	
}
