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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.util.UriComponentsBuilder;

import extractor.exception.ScrapPageException;
import extractor.service.properties.ComprasNetPropertiesService;
import lombok.Data;

@Data
public class ComprasNetJsoapScraper extends ScrapingAdapter {
	
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
		for (String actualUrl : ConsultUrls) {
			updateDocumentContent(actualUrl);
			mountUrlListFromPageElements();
			makeNextConsultUrl(actualUrl);
		}
	}
	
	private void updateDocumentContent(String actualUrl) throws Exception {
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
		for (String opportunityUrl : DownloadUrls) {
			updateDocumentContent(opportunityUrl);
			
		}
	}
	
	@Override
	public String getEntitys() {
		// TODO Auto-generated method stub
		return null;
	}

}
