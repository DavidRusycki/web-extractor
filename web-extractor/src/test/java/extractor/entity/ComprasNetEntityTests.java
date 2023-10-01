package extractor.entity;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import extractor.service.db.ComprasNetService;

public class ComprasNetEntityTests {

	private ComprasNetEntity entity = new ComprasNetEntity();	
	private ComprasNetService service = new ComprasNetService();
	
	@Test
	public void insertData_WithAllInformations_returnTrue() {
		// ARRANGE
		entity.setOrgao("orgao teste do david");
		entity.setUasg(123456);
		entity.setModalidade("modalidade do david");
		entity.setObjeto("construção de api para licitações");
		entity.setDataEdital("a data é amanhã");
		entity.setEndereco("rua jão gomez da silva");
		entity.setTelefone("(47) 40028922");
		entity.setFax("(134) Não sei como é um fax");
		entity.setDataEntregaProposta("amanha até de tarde");
		entity.setUrlEdital("https://localhost:8080/amanhaedia");
		
		// ACT
		entity = service.insert(entity);
		
		// ASSERT
		assertTrue(entity.getIdOportunidade() != null);
	}

}
