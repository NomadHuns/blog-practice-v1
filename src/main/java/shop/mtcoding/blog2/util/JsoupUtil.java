package shop.mtcoding.blog2.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
}
