package com.aakash.hivehq.assignment

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class Scrapper {

    public static final String HIVERHQ = "hiverhq"

    public static void main(String[] args) {

        // A Set to keep unique sets of URLs
        Set<String> urls = new HashSet<>()

        println "Scrapping https://hiverhq.com/"

        // URL to scrap
        def url = "https://hiverhq.com/"

        // Regex to tokeninze the string of body
        def regex = /?|;|,|' '/

        // Create a Jsoup document
        Document document = Jsoup.connect(url).get()

        // Get List of tokenized words
        def words = document.body().text().toLowerCase()
                .tokenize(regex)

        // Get the links
        Elements links = document.select("a")
        urls.add(url)

        // Get unique urls
        for (Element link : links) {
            def linkUrl = link.attr("abs:href")
            if (linkUrl.contains(HIVERHQ)){
                urls.add(linkUrl)
            }
        }

        // Get words from the urls
        for (String iUrl : urls) {
            Document doc = Jsoup.connect(url).get()

            words.addAll(doc.body().text().toLowerCase().tokenize(regex))

            Elements iLink = doc.select("a")
            for (Element element : iLink) {
                def iLinkUrl = element.attr("abs:href")
                if (iLinkUrl.contains(HIVERHQ)) {
                    urls.add(iLinkUrl)
                }
            }

        }

        // Print top five words with their frequency
        println words.countBy{it}.sort {-it.value}.take(5)
    }
}
