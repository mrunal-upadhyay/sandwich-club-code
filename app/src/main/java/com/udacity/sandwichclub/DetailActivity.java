package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

  public static final String EXTRA_POSITION = "extra_position";
  private static final int DEFAULT_POSITION = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    ImageView ingredientsIv = findViewById(R.id.image_iv);

    Intent intent = getIntent();
    if (intent == null) {
      closeOnError();
    }

    int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
    if (position == DEFAULT_POSITION) {
      // EXTRA_POSITION not found in intent
      closeOnError();
      return;
    }

    String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
    String json = sandwiches[position];
    Sandwich sandwich = JsonUtils.parseSandwichJson(json);
    if (sandwich == null) {
      // Sandwich data unavailable
      closeOnError();
      return;
    }

    populateUI(sandwich);
    Picasso.with(this)
        .load(sandwich.getImage())
        .into(ingredientsIv);

    setTitle(sandwich.getMainName());
  }

  private void closeOnError() {
    finish();
    Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
  }

  private void populateUI(Sandwich sandwich) {

    TextView originTv = findViewById(R.id.origin_tv);
    TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);
    TextView ingredientsTv = findViewById(R.id.ingredients_tv);
    TextView descriptionTv = findViewById(R.id.description_tv);

    originTv.setText(sandwich.getPlaceOfOrigin());

    String alsoKnownAsStr = new String();
    if (sandwich.getAlsoKnownAs().size() > 1) {
      for (int i = 0; i < sandwich.getAlsoKnownAs().size() - 1; i++) {
        alsoKnownAsStr = alsoKnownAsStr + sandwich.getAlsoKnownAs().get(i) + ", ";
      }
      alsoKnownAsStr =
          alsoKnownAsStr + sandwich.getAlsoKnownAs().get(sandwich.getAlsoKnownAs().size() - 1)
              + ", ";
    } else if (sandwich.getAlsoKnownAs().size() == 1) {
      alsoKnownAsStr =
          alsoKnownAsStr + sandwich.getAlsoKnownAs().get(sandwich.getAlsoKnownAs().size() - 1);
    }

    alsoKnownAsTv.setText(alsoKnownAsStr);

    String ingredientsStr = new String();

    if (sandwich.getIngredients().size() > 1) {
      for (int i = 0; i < sandwich.getIngredients().size() - 1; i++) {
        ingredientsStr = ingredientsStr + sandwich.getIngredients().get(i) + ", ";
      }
      ingredientsStr = ingredientsStr + sandwich.getIngredients()
          .get(sandwich.getIngredients().size() - 1);
    } else if (sandwich.getIngredients().size() == 1) {
      ingredientsStr = ingredientsStr + sandwich.getIngredients()
          .get(sandwich.getIngredients().size() - 1);
    }
    ingredientsTv.setText(ingredientsStr);

    descriptionTv.setText(sandwich.getDescription());

  }
}
