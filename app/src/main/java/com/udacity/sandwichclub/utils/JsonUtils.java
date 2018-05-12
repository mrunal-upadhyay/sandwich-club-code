package com.udacity.sandwichclub.utils;

import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

  private static final String NAME = "name";
  private static final String MAIN_NAME = "mainName";
  private static final String ALSO_KNOWN_AS = "alsoKnownAs";
  private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
  private static final String DESCRIPTION = "description";
  private static final String IMAGE = "image";
  private static final String INGREDIENTS = "ingredients";

  public static Sandwich parseSandwichJson(String json) {
    Sandwich sandwich = new Sandwich();
    try {
      JSONObject reader = new JSONObject(json);

      JSONObject nameObject = reader.getJSONObject(NAME);
      sandwich.setMainName(nameObject.getString(MAIN_NAME));
      List<String> knownAsList = new ArrayList<>();
      JSONArray knownAsJsonArray = nameObject.getJSONArray(ALSO_KNOWN_AS);
      if (knownAsJsonArray != null) {
        for (int i = 0; i < knownAsJsonArray.length(); i++) {
          knownAsList.add(knownAsJsonArray.getString(i));
        }
      }

      sandwich.setAlsoKnownAs(knownAsList);
      sandwich.setPlaceOfOrigin(reader.getString(PLACE_OF_ORIGIN));
      sandwich.setDescription(reader.getString(DESCRIPTION));
      sandwich.setImage(reader.getString(IMAGE));
      List<String> ingredientList = new ArrayList<>();
      JSONArray ingredientJsonArray = reader.getJSONArray(INGREDIENTS);
      for (int i = 0; i < ingredientJsonArray.length(); i++) {
        ingredientList.add(ingredientJsonArray.getString(i));
      }
      sandwich.setIngredients(ingredientList);
      Log.d("TAG", "Json Contents:" + sandwich.toString());

    } catch (JSONException e) {
      e.printStackTrace();
    }

    return sandwich;
  }
}
