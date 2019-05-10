package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

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
            return;
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

    /**
     * @param sandwich Details of sandwich item clicked
     */
    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownTextView = (TextView) findViewById(R.id.also_known_tv);
        TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        TextView originTextView = (TextView) findViewById(R.id.origin_tv);
        TextView descriptionTextView = (TextView) findViewById(R.id.description_tv);

        List<String> list = sandwich.getAlsoKnownAs();
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            if (i == listSize - 1)
                alsoKnownTextView.append(list.get(i));
            else
                alsoKnownTextView.append(list.get(i) + ", ");
        }
        if (listSize == 0) {
            alsoKnownTextView.setText(R.string.data_not_available);
        }


        list.clear();
        list = sandwich.getIngredients();
        listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            if (i == listSize - 1)
                ingredientsTextView.append(list.get(i));
            else
                ingredientsTextView.append(list.get(i) + ", ");
        }
        if (listSize == 0) {
            ingredientsTextView.setText(R.string.data_not_available);
        }


        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin.isEmpty()) {
            originTextView.setText(R.string.data_not_available);
        } else {
            originTextView.setText(sandwich.getPlaceOfOrigin());
        }


        String description = sandwich.getDescription();
        if (description.isEmpty()) {
            descriptionTextView.setText(R.string.data_not_available);
        } else {
            descriptionTextView.setText(sandwich.getDescription());
        }
    }
}
