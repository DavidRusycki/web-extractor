package extractor.entity;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	
	@ElementCollection
	@CollectionTable(name = "tbitemcompras", joinColumns = @JoinColumn(name = "id_oportunidade"))
	@Column(name = "conteudo")
	private Set<String> itens = new HashSet<String>();
	
	@NotNull
	@NotBlank
	private String urlEdital;
	
	@NotNull
	private Boolean visto = false;
	
}
