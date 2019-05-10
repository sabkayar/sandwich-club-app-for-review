package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    /**
     * @param json Json string
     * @return Sandwich object
     */
    public static Sandwich parseSandwichJson(String json) {
        if (json.isEmpty()) {
            return null;
        }

        Sandwich sandwich = null;
        try {
            // Get reference to JSONObject on json string
            JSONObject json1 = new JSONObject(json);
            // Create object of Sandwich
            sandwich = new Sandwich();
            // Get reference to JSONObject on string received using name key
            JSONObject json2 = new JSONObject(json1.getString("name"));
            // Set the mainName of sandwich
            sandwich.setMainName(json2.getString("mainName"));
            // Get reference of JSONArray using alsoKnownAs key from json2 object
            JSONArray jsonArray = new JSONArray(json2.getString("alsoKnownAs"));
            // Create a new list for fetching details from jsonArray
            List<String> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add((String) jsonArray.get(i));
            }
            // Set the list of alsoKnowAs names
            sandwich.setAlsoKnownAs(list);
            // Set the placeOfOrigin
            sandwich.setPlaceOfOrigin(json1.getString("placeOfOrigin"));
            // Set the description
            sandwich.setDescription(json1.getString("description"));
            //set the image url
            sandwich.setImage(json1.getString("image"));

            // Initialise jsonArray with ingredients
            jsonArray = new JSONArray(json1.getString("ingredients"));
            list.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add((String) jsonArray.get(i));
            }
            // Set list of ingredients
            sandwich.setIngredients(list);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return sandwich;
    }
}
