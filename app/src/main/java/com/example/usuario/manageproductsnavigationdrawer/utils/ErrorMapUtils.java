package com.example.usuario.manageproductsnavigationdrawer.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.example.usuario.manageproductsnavigationdrawer.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ErrorMapUtils {

    private static Map<String, String> map = null;
    // Error map resource
    private static int hashMapResId= R.xml.map_error;

    // Returns a full map with all key-value pairs from map_error.xml
    public static Map<String, String> getErrorMap(Context context) {
        if (map == null) {
            XmlResourceParser xmlResourceParser = context.getResources().getXml(hashMapResId);

            String key = null;
            String value = null;

            try {
                int eventType = xmlResourceParser.getEventType();

                // XmlPullParser reads tags and marks from any xml
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                        Log.d("utils", "Start document");
                    } else if (eventType == XmlPullParser.START_TAG) {
                        if (xmlResourceParser.getName().equals("map")) {
                            boolean isLinked = xmlResourceParser.getAttributeBooleanValue(null, "linked", false);

                            // isLinked tells whether any map is linked
                            map = isLinked
                                    ? new LinkedHashMap<String, String>()
                                    : new HashMap<String, String>();
                            // Each value from the map is an entry
                        } else if (xmlResourceParser.getName().equals("entry")) {
                            key = xmlResourceParser.getAttributeValue(null, "key");

                            if (null == key) {
                                xmlResourceParser.close();
                                return null;
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (xmlResourceParser.getName().equals("entry")) {
                            // Assigns key-value pairs to our map
                            map.put(key, value);
                            key = null;
                            value = null;
                        }
                    } else if (eventType == XmlPullParser.TEXT) {
                        if (null != key) {
                            value = xmlResourceParser.getText();
                        }
                    }
                    eventType = xmlResourceParser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return map;
    }

}
