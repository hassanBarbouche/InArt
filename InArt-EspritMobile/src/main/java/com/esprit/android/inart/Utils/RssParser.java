package com.esprit.android.inart.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Xml;

import com.esprit.android.inart.News;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mariem on 01/12/2015.
 */
public class RssParser {
    private Bitmap image = null;
    private String imageUrl;
    private String url="http://www.music-news.com/rss/UK/news";
    private boolean inUrl = false;
    private Bitmap getBitmap(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setDoInput(true);

            connection.connect();
            InputStream input = connection.getInputStream();

            Bitmap bitmap = BitmapFactory.decodeStream(input);
            input.close();
            return bitmap;
        }
        catch (IOException ioe)
        { ioe.printStackTrace(); }
        return null;
    }
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
    private static final String ns = null;

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    public ArrayList<News> parse(InputStream in) throws XmlPullParserException, IOException {

        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in, null);
        parser.nextTag();
        ArrayList<News> entries = new ArrayList<News>();

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        parser.nextTag();
        parser.require(XmlPullParser.START_TAG, ns, "channel");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("item")) {
                String title = "";
                String description="";
                 String content = "";
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    name = parser.getName();
                    if (name.equals("title")) {
                        title = readText(parser);
                    }

                    else if (name.equals("description")) {
                        description = readText(parser);
                       // System.out.println(description);
                    }
                    else if (name.equals("enclosure")) {
                         content=parser.getAttributeValue(null,"url");
                      //  System.out.println(content);
                       // content = readText(parser);
                      // content = readText(parser.getAttributeValue(null,"url"));
                        parser.nextTag();
                        //parser.require(XmlPullParser.END_TAG, null, "enclosure");
                        //entries.add(new News(title,content));
                    }

                    else
                        skip(parser);
                }
                entries.add(new News(title,content,description));
            }  else
                skip(parser);

        }
        return entries;
    }

//    private Image parseImage(XmlPullParser parser)throws XmlPullParserException,IOException{
//        Image image =new Image();
//        Log.d(TAG,"parseImage tag"+parser.getName());
//
//
//        return image;
//    }

}
