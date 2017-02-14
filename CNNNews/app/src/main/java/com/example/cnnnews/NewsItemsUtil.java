package com.example.cnnnews;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Allen on 2/13/17.
 */

public class NewsItemsUtil {
    public static class NewsItemsSAXParser {
        public static ArrayList<NewsItem> parseNewsItemUsingSAX(InputStream inputStream) throws IOException, SAXException, XmlPullParserException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            ArrayList<NewsItem> newsItems = new ArrayList<>();
            NewsItem newsItem = null;
            parser.setInput(inputStream, "UTF-8");
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")) {
                            newsItem = new NewsItem();
                        } else if (parser.getName().equals("title")) {
                            if (newsItem != null) {
                                newsItem.setTitle(parser.nextText().trim());
                            }
                        } else if (parser.getName().equals("media:content")) {

                                if (parser.getAttributeValue(null, "height").trim() == parser.getAttributeValue(null, "width").trim()) {
                                    //Log.d("inimage", parser.getAttributeValue(null, "url"));
                                    newsItem.setImageLink((parser.getAttributeValue(null, "url")).toString());
                                }

                        } else if (parser.getName().equals("description") && newsItem != null) {
                            newsItem.setDescription(parser.nextText().trim());
                        } else if (parser.getName().equals("pubDate") && newsItem != null) {
                            newsItem.setPubDate(parser.nextText().trim());
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item"))
                            newsItems.add(newsItem);
                        break;
                    default:
                        break;
                }
                event = parser.next();
            }
            return newsItems;
        }
//
//    @Override
//    public void startDocument() throws SAXException {
//        super.startDocument();
//        newsItems=new ArrayList<>();
//    }
//
//    @Override
//    public void characters(char[] ch, int start, int length) throws SAXException {
//        sb.append(ch,start,length);
//    }
//
//    @Override
//    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        super.startElement(uri, localName, qName, attributes);
//        newsItem = new NewsItem();
//        if(localName.equals("media:content")){
//            if(attributes.getValue("height").trim()==attributes.getValue("width").trim()) {
//                if(attributes.getValue("width").trim()=="300") {
//                    newsItem.setImageLink(attributes.getValue("url").trim());
//                }
//            }
//        }else if(localName.equals("title")){
//            Log.d("title",sb.toString().trim());
//            newsItem.setTitle(sb.toString().trim());
//            sb.setLength(0);
//        }else if(localName.equals("description")){
//            Log.d("desc",sb.toString().trim());
//            newsItem.setDescription(sb.toString().trim());
//            sb.setLength(0);
//        }else if(localName.equals("pubDate")){
//            newsItem.setPubDate(sb.toString().trim());
//            sb.setLength(0);
//        }
//    }
//
//    @Override
//    public void endElement(String uri, String localName, String qName) throws SAXException {
//        super.endElement(uri, localName, qName);
//        if(localName.equals("item")){
//            newsItems.add(newsItem);
//        }
//    }
//
//    @Override
//    public void endDocument() throws SAXException {
//        super.endDocument();
//    }

    }
}
