package extractor.service.adapter;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.hibernate.property.access.internal.PropertyAccessStrategyNoopImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.util.UriComponentsBuilder;

import extractor.dto.ComprasNetDTO;
import extractor.exception.ScrapPageException;
import extractor.service.properties.ComprasNetPropertiesService;
import lombok.Data;

@Data
public class ComprasNetJsoapScraper extends ScrapingAdapter {
	
	private Integer requests = 0;
	private Document document;
	private ArrayList<String> DownloadUrls = new ArrayList<String>();
	private ArrayList<String> ConsultUrls = new ArrayList<String>();
	private String consultUrl;
	private String downloadUrl;
	
	public ComprasNetJsoapScraper() {
		loadUrls();
	}
	
	private void loadUrls() {
		this.consultUrl = ComprasNetPropertiesService.getConsultUrl();
		this.downloadUrl = ComprasNetPropertiesService.getDownloadUrl();
	}
	
	@Override
	public void injectFirstUrl() {
		ConsultUrls.add(ComprasNetPropertiesService.getConsultUrl());
	}
	
	@Override
	public void scrapPageElements() throws Exception {
		// Aqui é a minha regra de negócio para raspar o ComprasNet
		
		try {
			mountAllDownloadUrls();
			scrapAllOpportunities();
		} catch (Exception e) {
			throw new ScrapPageException("Falha ao realizar scraping" + e.getMessage());
		}
		
	}

	private void mountAllDownloadUrls() throws Exception {
		for (int indice = 0; indice < ConsultUrls.size(); indice++) {
			String actualUrl = ConsultUrls.get(indice);			
			indice = ConsultUrls.size(); 
			
			updateDocumentContent(actualUrl);
			mountUrlListFromPageElements();
			makeNextConsultUrl(actualUrl);
		}
	}
	
	private void updateDocumentContent(String actualUrl) throws Exception {
		System.out.println("Realizando request: " + ++requests);
		document = Jsoup.connect(actualUrl).get();
	}
	
	private void mountUrlListFromPageElements() {
		Elements elements = this.getInputElementsFromItens(document);
		ArrayList<String> clickEventContentList = getListOfOnClickContent(elements);
		ArrayList<String> urls = getUrlsFromClickEventContent(clickEventContentList);
		this.DownloadUrls.addAll(urls);
	}
	
	protected Elements getInputElementsFromItens(Document document) {
		Elements elements = document.select("input[name=itens]");
		
		return elements;
	}
	
	protected ArrayList<String> getListOfOnClickContent(Elements elements) {
		ArrayList<String> list = new ArrayList<String>();
		
		for (Element element : elements) {
			list.add(element.attr("onClick"));
		}
		
		return list;
	}
	
	protected ArrayList<String> getUrlsFromClickEventContent(ArrayList<String> clickContentList) {
		ArrayList<String> newUrls = new ArrayList<String>();
		
		for (String clickContent : clickContentList) {
			String[] contents = clickContent.split("'");
			StringBuilder builder = new StringBuilder();
			builder.append(downloadUrl);
			builder.append(contents[1]);
			
			newUrls.add(builder.toString());
		}
		
		return newUrls;
	}
	
	protected void makeNextConsultUrl(String actualUrl) throws MalformedURLException, URISyntaxException {
		Element element = document.select("input[name=proxima]").first();
		
		if (element != null) {
			Map<String, String> map = getMapOfQueryParams(actualUrl);
			Integer page = Integer.parseInt(map.get("pagina"));
			var builder = UriComponentsBuilder.fromUriString(actualUrl);
			builder.replaceQueryParam("pagina", ++page);
			
			this.ConsultUrls.add(builder.build().toString());
		}
	}
	
	private Map<String, String> getMapOfQueryParams(String url) throws URISyntaxException {
		List<NameValuePair> list = URLEncodedUtils.parse(new URI(url), StandardCharsets.UTF_8.toString());
		Map<String, String> map = list.stream().collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));
		
		return map;
	}
	
	private void scrapAllOpportunities() throws Exception {
		
		System.out.println("scraping opportinities");
		
		for (int indice = 0; indice < DownloadUrls.size(); indice++) {
			String opportunityUrl = DownloadUrls.get(indice);			
			indice = DownloadUrls.size();
			
			updateDocumentContent(opportunityUrl);
			scrapDownloadPage(document);
		}
	}
	
	protected void scrapDownloadPage(Document document) {
		ComprasNetDTO dto = new ComprasNetDTO();
		loadOrgao(dto);
		loadUasg(dto);
		loadModalidade(dto);
		loadObjeto(dto);
//		loadDataEdital(dto);
//		loadEndereco(dto);
//		loadTelefone(dto);
//		loadFax(dto);
//		loadDataEntregaProposta(dto);
//		loadItens(dto);
//		loadUrlEdital(dto);
		
		Element orgao = document.select("body > table:nth-child(3) > tbody > tr:nth-child(2) > td > table:nth-child(2) > tbody > tr:nth-child(1) > td.tex3 > table > tbody > tr:nth-child(1) > td > p").first();
	}
	
	protected void loadOrgao(ComprasNetDTO dto) {
		Element tabela = this.getOrgaoTable();
		Element last = tabela.select("p").last();
		Elements pElements = tabela.select("p");
		//Remove o último pois ele é o código da UASG
		pElements.remove(last);
		
		StringBuilder builder = new StringBuilder();
		for (Element element : pElements) {
			builder.append(element.html()+";");
		}
		
		dto.setOrgao(builder.toString());	
	}
	
	protected void loadUasg(ComprasNetDTO dto) {
		Element tabela = this.getOrgaoTable();
		Element last = tabela.select("p").last();
		String codigo = last.html().replaceAll("[\\D]", "");
		
		
		dto.setUasg(Integer.parseInt(codigo));
	}
	
	protected void loadModalidade(ComprasNetDTO dto) {
		Element element = document.select("body > table:nth-child(3) > tbody > tr:nth-child(2) > td > table:nth-child(2) > tbody > tr:nth-child(2) > td.tex3 > table > tbody > tr > td > p > span.tex3b").first();
		
		dto.setModalidade(element.html());
	}
	
	protected void loadObjeto(ComprasNetDTO dto) {
		Element element = document.select("/html/body/table[2]/tbody/tr[2]/td/table[2]/tbody/tr[2]/td[2]/table/tbody/tr/td/p/text()[1]").first();
		
		dto.setObjeto(element.html());
	}
	
	protected Element getOrgaoTable() {
		Element tabela = document.select("body > table:nth-child(3) > tbody > tr:nth-child(2) > td > table:nth-child(2) > tbody > tr:nth-child(1) > td.tex3 > table > tbody").first();
		
		return tabela;
	}
	
	@Override
	public String getEntitys() {
		// TODO Auto-generated method stub
		return null;
	}

}
