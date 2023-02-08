package shop.mtcoding.blog2.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

public class HtmlParseTest {

    @Test
    public void jsoup_test1() {
        try {
            System.out.println("테스트 : " + "==========================");
            Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
            System.out.println("테스트 : " + doc.title());
            System.out.println("테스트 : " + doc);
            System.out.println("테스트 : " + "==========================");
            Elements newsHeadlines = doc.select("#mp-itn b a");
            for (Element headline : newsHeadlines) {
                System.out.println("테스트 : " + "%s\n\t%s" +
                        headline.attr("title") + headline.absUrl("href"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jsoup_test4() throws IOException {
        Document doc = Jsoup.connect("https://kr.investing.com/equities/samsung-electronics-co-ltd").get();
        Elements elements = doc.select("span.text-2xl");
        String stockPrice = elements.text();
        System.out.println(stockPrice);
    }

    @Test
    public void jsoup_test2() {
        String html = "<p>1</p><p><img src=\"data:image/png;base64,iVBORw0KG\"></p><img src=\"data:image/png;base64,iVBORw0KG\">";
        Document doc = Jsoup.parse(html);
        System.out.println("테스트 : " + doc);
        Elements els = doc.select("img");
        System.out.println("테스트 : " + els);
        if (els.size() == 0) {
            // 임시 사진 제공해주기
        } else {
            Element el = els.get(0);
            String img = el.attr("src");
            System.out.println("테스트 : " + img);
        }
    }

    @Test
    public void parse_test1() {
        String html = "<p>1</p><p><img src=\"data:image/png;base64,iVBORw0KG\"></p>";
        String tag = parseEL(html, "img");
        System.out.println(tag);
        String attr = parseAttr(tag, "src");
        System.out.println(attr);
    }

    private String parseEL(String html, String tag) {
        String s1 = html.substring(html.indexOf(tag) - 1);
        return s1.substring(0, s1.indexOf(">") + 1);
    }

    private String parseAttr(String el, String attr) {
        String s1 = el.substring(el.indexOf(attr));

        int begin = s1.indexOf("\"");
        int end = s1.lastIndexOf("\"");

        return s1.substring(begin + 1, end);
    }

}
