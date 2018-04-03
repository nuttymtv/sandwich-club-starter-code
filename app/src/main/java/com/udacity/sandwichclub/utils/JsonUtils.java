package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * This method parses JSON String data into a detail Sandwich object
     *
     * @param jsonStringObj JSON String from the mock service
     *
     * @return Sandwich object
     *
     * @throws JSONException If JSON data cant be parsed
     *
     * */
    public static Sandwich parseSandwichJson(String jsonStringObj) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStringObj);
        JSONObject name = jsonObject.getJSONObject("name");

        String origin = jsonObject.getString("placeOfOrigin");
        String desc = jsonObject.getString("description");
        String img = jsonObject.getString("image");
        String mainName = name.getString("mainName");

        List<String> asKnowAsName = getStringFromArrayObj(name.getJSONArray("alsoKnownAs"));
        List<String> ingrs = getStringFromArrayObj(jsonObject.getJSONArray("ingredients"));
        return new Sandwich(mainName,asKnowAsName,origin,desc,img,ingrs);
    }

    private static List<String> getStringFromArrayObj (JSONArray jsonArray) throws JSONException {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            items.add(jsonArray.getString(i));
        }
        return items;
    }

}
