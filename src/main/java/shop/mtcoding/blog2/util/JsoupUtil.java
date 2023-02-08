package shop.mtcoding.blog2.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.ui.Model;

public class JsoupUtil {

    public static String thumbnail(String html, String tag, String attr, String defalut) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select(tag);
        Element element = elements.first();
        if (element != null) {
            return element.attr(attr);
        } else {
            return defalut;
        }
    }

    private static String stockMarket() throws IOException {
        Document doc = Jsoup.connect("https://kr.investing.com/equities/samsung-electronics-co-ltd").get();
        Elements elements = doc.select("span.text-2xl");
        String stockPrice = elements.text();
        System.out.println(stockPrice);

        StringBuilder sb = new StringBuilder();
        sb.append("삼성전자 현재주가 : ");
        sb.append(stockPrice);
        sb.append("원");
        return sb.toString();
    }

    public static void stockMarket(Model model) throws IOException {
        String stock = JsoupUtil.stockMarket();
        model.addAttribute("stockmarket", stock);
    }

}
