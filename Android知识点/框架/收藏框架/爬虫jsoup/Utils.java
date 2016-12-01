package cn.hxxd;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Utils {

	public static String getTxtCity() {
		StringBuilder sb = new StringBuilder();
		Document doc = getDocument("http://www.aibang.com/beijing/cities-bus");
		Elements elements1 = doc.getElementsByTag("ul");
		Elements elements2 = elements1.get(1).getElementsByTag("li");
		for (int i = 0; i < elements2.size(); i++) {
			sb.append(elements2.get(i).text() + "\n");
		}
		return sb.toString();
	}

	public static String getTxt(String url) {
		Document doc = getDocument(url);
		Elements elements1 = doc.getElementsByTag("body");
		return elements1.get(0).text();
	}

	public static void main(String[] args) {
		getImageUrl("http://www.58pic.com/");
	}

	public static String getTabStr(String url) {
		StringBuilder sb = new StringBuilder();
		Document doc = getDocument(url);
		Elements elements1 = doc.getElementsByTag("table");
		for (int i = 0; i < elements1.size(); i++) {
			sb.append(elements1.get(i).text() + "\n");
		}
		return sb.toString();
	}

	public static String getImageUrl(String url) {
		if (url == null) {
			url = "http://www.58pic.com/";
		}
		StringBuilder sb = new StringBuilder();
		Document doc = getDocument(url);
		Elements e1 = doc.select("[src$=.jpg]"); // a with href
		System.out.println(e1.get(1));
		String linkHref = null;
		for (Element link : e1) {
			linkHref = link.attr("data-url");
			// <img class="scrollLoading show-img"
			// data-url="http://pic.qiantucdn.com/58pictopic/middle/14/73/21/5821637d99845.jpg!qt226">
			if (linkHref.lastIndexOf("!qt226") != -1) {
				linkHref = linkHref.substring(0, linkHref.length() - 6);
			}
			System.out.println(linkHref);
		}
		return sb.toString();
	}

	public static Document getDocument(String url) {
		try {
			return Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
