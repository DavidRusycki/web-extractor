package extractor.service.scraper.comprasnet.jsoap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import extractor.service.properties.ComprasNetPropertiesService;
import extractor.service.scraper.comprasnet.Scraper;

public class UrlEditalScraper extends Scraper {

	@Override
	public void scrap() {
		String scripts = document.select("script").last().html();
		
		String padrao = "'(/ConsultaLicitacoes/Download/Download\\.asp\\?[^']*)'";
        Pattern pattern = Pattern.compile(padrao);
        Matcher matcher = pattern.matcher(scripts);
        String url = "não foi possível obter a url";
        
        if (matcher.find()) {
            url = matcher.group(1);
        }
		
        dto.setUrlEdital(ComprasNetPropertiesService.getBaseUrl()+url);
	}

}
