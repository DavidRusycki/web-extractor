package extractor.service.scraper.adapter;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import extractor.dto.BaseDto;
import extractor.dto.ComprasNetDTO;
import extractor.exception.ScrapPageException;
import extractor.service.db.ComprasNetService;
import extractor.service.properties.ComprasNetPropertiesService;
import extractor.service.scraper.comprasnet.Scraper;
import extractor.service.scraper.comprasnet.jsoap.DataEditalScraper;
import extractor.service.scraper.comprasnet.jsoap.DataEntregaPropostaScraper;
import extractor.service.scraper.comprasnet.jsoap.EnderecoScraper;
import extractor.service.scraper.comprasnet.jsoap.FaxScraper;
import extractor.service.scraper.comprasnet.jsoap.ItensScraper;
import extractor.service.scraper.comprasnet.jsoap.ModalidadeScraper;
import extractor.service.scraper.comprasnet.jsoap.ObjetoScraper;
import extractor.service.scraper.comprasnet.jsoap.OrgaoScraper;
import extractor.service.scraper.comprasnet.jsoap.TelefoneScraper;
import extractor.service.scraper.comprasnet.jsoap.UasgScraper;
import extractor.service.scraper.comprasnet.jsoap.UrlEditalScraper;
import lombok.Data;

@Data
public class ComprasNetJsoapScraper extends ScrapingAdapter {
	
	public static final Logger logger = LoggerFactory.getLogger(ComprasNetJsoapScraper.class);
	
	private Integer requests = 0;
	private Document document;
	private ArrayList<String> DownloadUrls = new ArrayList<String>();
	private ArrayList<String> ConsultUrls = new ArrayList<String>();
	private String consultUrl;
	private String downloadUrl;
	private ArrayList<BaseDto> dtos = new ArrayList<BaseDto>();
	private List<Scraper> scrapers = new ArrayList<Scraper>();
	
	public ComprasNetJsoapScraper() {
		loadUrls();
		loadScrapers();
	}
	
	private void loadUrls() {
		this.consultUrl = ComprasNetPropertiesService.getConsultUrl();
		this.downloadUrl = ComprasNetPropertiesService.getDownloadUrl();
	}
	
	private void loadScrapers() {
		scrapers.add(new DataEditalScraper());
		scrapers.add(new DataEntregaPropostaScraper());
		scrapers.add(new EnderecoScraper());
		scrapers.add(new FaxScraper());
		scrapers.add(new ItensScraper());
		scrapers.add(new ModalidadeScraper());
		scrapers.add(new ObjetoScraper());
		scrapers.add(new OrgaoScraper());
		scrapers.add(new TelefoneScraper());
		scrapers.add(new UasgScraper());
		scrapers.add(new UrlEditalScraper());
	}
	
	@Override
	public void injectFirstUrl() {
		ConsultUrls.add(ComprasNetPropertiesService.getConsultUrl());
	}
	
	@Override
	public void scrapPageElements() throws Exception {
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
			
			updateDocumentContent(actualUrl);
			mountUrlListFromPageElements();
			makeNextConsultUrl(actualUrl);
		}
	}
	
	private void updateDocumentContent(String actualUrl) throws Exception {
		logger.info("Realizando request: " + ++requests);
		document = Jsoup.parse(new URL(actualUrl).openStream(), "ISO-8859-1", actualUrl);
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
		logger.info("scraping opportinities");
		
		for (int indice = 0; indice < DownloadUrls.size(); indice++) {
			String opportunityUrl = DownloadUrls.get(indice);
			
			updateDocumentContent(opportunityUrl);
			scrapDownloadPage(document);
		}
		logger.info("Finalizando scraping das páginas.");
	}
	
	protected void scrapDownloadPage(Document document) {
		ComprasNetDTO dto = new ComprasNetDTO();
		
		for (Scraper scraper : scrapers) {
			scraper.setDto(dto);
			scraper.setDocument(document);
			
			try {
				scraper.scrap();				
			} catch (Exception e) {
				logger.error("Falha ao realizar scraping da informação"+e.getMessage());
			}
			
		}
        
		dtos.add(dto);
	}
	
	@Override
	public List<BaseDto> getDtos() {
		return dtos;
	}

}
