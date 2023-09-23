package extractor.service.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

public class ComprasNetJsoapScraperTests {

	@Test
	public void getInputElementsFromItens_WithThreeElements_returnElements() {
		//Arrange
		var instance = getStandartInstance();
		var document = getDocumentWithThreeInputs();
		
		//Act
		var elements = instance.getInputElementsFromItens(document);
		
		//Assert
		assertEquals(elements.size(), 3);
	}
	
	@Test
	public void getListOfOnClickContent_WithThreeElements_returnsList() {
		//Arrange
		var instance = getStandartInstance();
		var elements = getThreeInputElements();
		
		//Act
		var onClickList = instance.getListOfOnClickContent(elements);
		
		//Assert
		assertEquals(onClickList.size(), 3);
	}
	
	@Test
	public void getUrlsFromClickEventContent_WithThreeElementsInList_returnsList() {
		//Arrange
		var instance = getStandartInstance();
		var elements = getThreeInputElements();
		var onClickList = instance.getListOfOnClickContent(elements);
		
		//Act
		var urls = instance.getUrlsFromClickEventContent(onClickList);
		
		//Assert
		assertEquals(urls.size(), 3);
	}
	
	@Test
	public void getUrlsFromClickEventContent_ContentInList_returnsList() {
		//Arrange
		var instance = getStandartInstance();
		var elements = getThreeInputElements();
		var onClickList = instance.getListOfOnClickContent(elements);
		
		//Act
		var urls = instance.getUrlsFromClickEventContent(onClickList);
		
		//Assert
		assertEquals(this.getListOfLinksToDownload(), urls);
	}
	
	@Test
	public void makeNextConsultUrl_pageWithProxima_returnNewUrl() throws MalformedURLException, URISyntaxException {
		//Arrange
		var instance = getStandartInstance();
		instance.injectFirstUrl();
		Document document = getDocumentWithThreeInputs();
		instance.setDocument(document);
		
		//Act
		instance.makeNextConsultUrl(actualUrlWithParams());
		var list = instance.getConsultUrls();
		var lastUrl = list.get(list.size()-1);
		
		//Assert
		assertEquals("http://comprasnet.gov.br/ConsultaLicitacoes/ConsLicitacaoDia.asp?view=2&pagina=2", lastUrl);
	}
	
	@Test
	public void makeNextConsultUrl_pageWithProximaAndNormalUrl_returnNewUrl() throws MalformedURLException, URISyntaxException {
		//Arrange
		var instance = getStandartInstance();
		instance.injectFirstUrl();
		Document document = getDocumentWithThreeInputs();
		instance.setDocument(document);
		
		//Act
		instance.makeNextConsultUrl(actualNormalUrl());
		var list = instance.getConsultUrls();
		var lastUrl = list.get(list.size()-1);
		
		//Assert
		assertEquals("http://comprasnet.gov.br/ConsultaLicitacoes/ConsLicitacaoDia.asp?pagina=2", lastUrl);
	}
	
	@Test
	public void makeNextConsultUrl_pageWithoutProxima_notIncreaseUrls() throws MalformedURLException, URISyntaxException {
		//Arrange
		var instance = getStandartInstance();
		instance.injectFirstUrl();
		Document document = getDocumentWithoutProxima();
		instance.setDocument(document);
		
		//Act
		instance.makeNextConsultUrl(actualNormalUrl());
		var list = instance.getConsultUrls();
		
		//Assert
		assertEquals(list.size(), 1);
	}
	
	public String actualNormalUrl() {
		return "http://comprasnet.gov.br/ConsultaLicitacoes/ConsLicitacaoDia.asp?pagina=1";
	}
	
	public String actualUrlWithParams() {
		return "http://comprasnet.gov.br/ConsultaLicitacoes/ConsLicitacaoDia.asp?pagina=1&view=2";
	}
	
	public Elements getThreeInputElements() {
		var document = getDocumentWithThreeInputs();
		var elements = document.select("input[name=itens]");
		
		return elements;
	}
	
	public Document getDocumentWithThreeInputs() {
		String html = "<html>"
						+ "<head><title>First parse</title></head>"
					  	+ "<body>"
					  		+ "<p>Parsed HTML into a doc.</p>"
					  		+ "<input type=\"button\" name=\"itens\" value=\"Itens e Download\" class=\"texField2\" onclick=\"javascript:VisualizarItens(document.Form1,'?coduasg=120630&amp;modprp=2&amp;numprp=82023');\" onmouseover=\"window.status='Itens da Licitação e Download do Edital';return true;\" title=\"Clique para ver os itens ou para fazer o Download do Edital\">"
						  	+ "<input type=\"button\" name=\"itens\" value=\"Itens e Download\" class=\"texField2\" onclick=\"javascript:VisualizarItens(document.Form2,'?coduasg=120632&amp;modprp=2&amp;numprp=122023');\" onmouseover=\"window.status='Itens da Licitação e Download do Edital';return true;\" title=\"Clique para ver os itens ou para fazer o Download do Edital\">"
						  	+ "<input type=\"button\" name=\"itens\" value=\"Itens e Download\" class=\"texField2\" onclick=\"javascript:VisualizarItens(document.Form3,'?coduasg=120632&amp;modprp=2&amp;numprp=132023');\" onmouseover=\"window.status='Itens da Licitação e Download do Edital';return true;\" title=\"Clique para ver os itens ou para fazer o Download do Edital\">"
						  	+ "<input type=\"button\" name=\"random\" value=\"Itens e Download\" class=\"texField2\" onclick=\"javascript:VisualizarItens(document.Form3,'?coduasg=120632&amp;modprp=2&amp;numprp=132023');\" onmouseover=\"window.status='Itens da Licitação e Download do Edital';return true;\" title=\"Clique para ver os itens ou para fazer o Download do Edital\">"
						  	+ "<input type=\"button\" name=\"proxima\" value=\"Proximas\" class=\"texField2\" onclick=\"javascript:PaginarItens('Proxima');\">"
				  		+ "</body>"
			  		+ "</html>";
		Document document = Jsoup.parse(html);
				
		return document;
	}
	
	public Document getDocumentWithoutProxima() {
		String html = "<html>"
						+ "<head><title>First parse</title></head>"
					  	+ "<body>"
					  		+ "<p>Parsed HTML into a doc.</p>"
					  		+ "<input type=\"button\" name=\"itens\" value=\"Itens e Download\" class=\"texField2\" onclick=\"javascript:VisualizarItens(document.Form1,'?coduasg=120630&amp;modprp=2&amp;numprp=82023');\" onmouseover=\"window.status='Itens da Licitação e Download do Edital';return true;\" title=\"Clique para ver os itens ou para fazer o Download do Edital\">"
						  	+ "<input type=\"button\" name=\"itens\" value=\"Itens e Download\" class=\"texField2\" onclick=\"javascript:VisualizarItens(document.Form2,'?coduasg=120632&amp;modprp=2&amp;numprp=122023');\" onmouseover=\"window.status='Itens da Licitação e Download do Edital';return true;\" title=\"Clique para ver os itens ou para fazer o Download do Edital\">"
						  	+ "<input type=\"button\" name=\"itens\" value=\"Itens e Download\" class=\"texField2\" onclick=\"javascript:VisualizarItens(document.Form3,'?coduasg=120632&amp;modprp=2&amp;numprp=132023');\" onmouseover=\"window.status='Itens da Licitação e Download do Edital';return true;\" title=\"Clique para ver os itens ou para fazer o Download do Edital\">"
						  	+ "<input type=\"button\" name=\"random\" value=\"Itens e Download\" class=\"texField2\" onclick=\"javascript:VisualizarItens(document.Form3,'?coduasg=120632&amp;modprp=2&amp;numprp=132023');\" onmouseover=\"window.status='Itens da Licitação e Download do Edital';return true;\" title=\"Clique para ver os itens ou para fazer o Download do Edital\">"
						  	+ "<input type=\"button\" name=\"teste\" value=\"Proximas\" class=\"texField2\" onclick=\"javascript:PaginarItens('Proxima');\">"
				  		+ "</body>"
			  		+ "</html>";
		Document document = Jsoup.parse(html);
				
		return document;
	}
	
	public ArrayList<String> getListOfLinksToDownload() {
		ArrayList<String> list = new ArrayList<String>();
		
		list.add("http://comprasnet.gov.br/ConsultaLicitacoes/download/download_editais_detalhe.asp?coduasg=120630&modprp=2&numprp=82023");
		list.add("http://comprasnet.gov.br/ConsultaLicitacoes/download/download_editais_detalhe.asp?coduasg=120632&modprp=2&numprp=122023");
		list.add("http://comprasnet.gov.br/ConsultaLicitacoes/download/download_editais_detalhe.asp?coduasg=120632&modprp=2&numprp=132023");
		
		return list;
	}
	
	public ComprasNetJsoapScraper getStandartInstance() {
		return new ComprasNetJsoapScraper();
	}
	
}
