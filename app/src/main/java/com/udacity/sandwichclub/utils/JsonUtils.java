package com.udacity.sandwichclub.utils;

import android.util.Log;
import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

  private static final String TAG = JsonUtils.class.getSimpleName();
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
      /* SAMPLE JSON:
      {
	      "name": {
		      "mainName": "Ham and cheese sandwich",
		      "alsoKnownAs": []
	      },
	      "placeOfOrigin": "",
	      "description": "A ham and cheese sandwich is a common type of sandwich. It is made by putting cheese and sliced ham between two slices of bread. The bread is sometimes buttered and/or toasted. Vegetables like lettuce, tomato, onion or pickle slices can also be included. Various kinds of mustard and mayonnaise are also common.",
	      "image": "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG",
	      "ingredients": ["Sliced bread", "Cheese", "Ham"]
      }*/

      JSONObject reader = new JSONObject(json);

      // get the json object corresponding to key "name"
      JSONObject nameJSONObject = reader.getJSONObject(NAME);

      // get the main name associated to key "mainName" inside nameJSONObject
      sandwich.setMainName(nameJSONObject.getString(MAIN_NAME));

      List<String> knownAsList = new ArrayList<>();
      // get the knownAs JsonArray associated to key "alsoKnownAs" inside nameJSONObject
      JSONArray knownAsJsonArray = nameJSONObject.getJSONArray(ALSO_KNOWN_AS);
      if (knownAsJsonArray != null) {
        for (int i = 0; i < knownAsJsonArray.length(); i++) {
          knownAsList.add(knownAsJsonArray.getString(i));
        }
      }
      sandwich.setAlsoKnownAs(knownAsList);

      // populate placeOfOrigin field in sandwich POJO to the value corresponding to key
      // "placeOfOrigin" inside reader JSONObject.
      sandwich.setPlaceOfOrigin(reader.getString(PLACE_OF_ORIGIN));

      // populate description field in sandwich POJO to the value corresponding to key
      // "description" inside reader JSONObject.
      sandwich.setDescription(reader.getString(DESCRIPTION));

      // populate image field in sandwich POJO to the value corresponding to key
      // "image" inside reader JSONObject.
      sandwich.setImage(reader.getString(IMAGE));

      List<String> ingredientList = new ArrayList<>();
      // get the ingredient JsonArray associated to key "ingredients" inside nameJSONObject
      JSONArray ingredientJsonArray = reader.getJSONArray(INGREDIENTS);
      for (int i = 0; i < ingredientJsonArray.length(); i++) {
        ingredientList.add(ingredientJsonArray.getString(i));
      }
      sandwich.setIngredients(ingredientList);
      Log.d(TAG, "Json Contents:" + sandwich.toString());

    } catch (JSONException e) {
      Log.e(TAG,"Failed to parse JSON:\n Exception:",e);
    }

    return sandwich;
  }
}
