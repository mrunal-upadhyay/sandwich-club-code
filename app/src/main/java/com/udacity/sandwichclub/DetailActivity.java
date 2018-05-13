package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

  public static final String EXTRA_POSITION = "extra_position";
  private static final int DEFAULT_POSITION = -1;
  @BindView(R.id.origin_tv)
  TextView originTv;
  @BindView(R.id.also_known_tv)
  TextView alsoKnownAsTv;
  @BindView(R.id.ingredients_tv)
  TextView ingredientsTv;
  @BindView(R.id.description_tv)
  TextView descriptionTv;
  @BindView(R.id.image_iv)
  ImageView ingredientsIv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    ButterKnife.bind(this);

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
    originTv.setText(sandwich.getPlaceOfOrigin());
    alsoKnownAsTv.setText(getKnownAsString(sandwich));
    ingredientsTv.setText(getIngredientsString(sandwich));
    descriptionTv.setText(sandwich.getDescription());
  }

  /**
   * Parse the ingredients list from sandwich POJO and return a string formed
   * by appending the elements of ingredients list.
   *
   * @return String
   */

  @NonNull
  private String getIngredientsString(Sandwich sandwich) {
    String ingredientsStr = new String("");

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

    return ingredientsStr;
  }

  /**
   * Parse the alsoKnownAs list from sandwich POJO and return a string formed
   * by appending the elements of alsoKnownAs list.
   *
   * @return String
   */
  @NonNull
  private String getKnownAsString(Sandwich sandwich) {
    String alsoKnownAsStr = new String("");

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

    return alsoKnownAsStr;
  }
}
