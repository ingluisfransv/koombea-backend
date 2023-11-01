package com.koombea.scrapping.service;

import com.koombea.scrapping.model.Link;
import com.koombea.scrapping.model.Scrap;
import com.koombea.scrapping.model.ScrappedPage;
import com.koombea.scrapping.repository.ScrappingRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScrappingService {
    @Autowired
    private ScrappingRepository scrappingRepository;

    public ScrappedPage create(Scrap scrap) throws IOException {
        ScrappedPage scrappedPage = new ScrappedPage();
        scrappedPage.setTitle(getTitle(getPageInformation(scrap.getUrl())));
        scrappedPage.setLinks(getLinks(getPageInformation(scrap.getUrl())));
        return scrappingRepository.save(scrappedPage);
    }

    private String getPageInformation(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private String getTitle(String html){
        Document doc = Jsoup.parse(html);
        Element titleElement = doc.select("title").first();
        String title = "";
        if (titleElement != null) {
            title = titleElement.text();
        } else {
            title = "Title not found";
        }
        return title;
    }
    private List<Link> getLinks(String html) {
        Document doc = Jsoup.parse(html);
        Elements links = doc.select("a[href]");
        List<Link> linksArray = new ArrayList<>();
        for (Element link : links) {
            String lnk = link.attr("href");
            String name = link.text();
            Link linkObj = new Link();
            linkObj.setLinkName(name);
            linkObj.setUrl(lnk);
            linksArray.add(linkObj);
        }
        return linksArray;
    }

    public List<ScrappedPage> getAll(){
        return scrappingRepository.findAll();
    }
    public Optional<ScrappedPage> getById(Long id){
        return scrappingRepository.findById(id);
    }

}
