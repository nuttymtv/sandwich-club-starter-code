package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.lang.invoke.CallSite;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    Sandwich sandwich = null;
    TextView tvDescription;
    TextView tvOrigin;
    TextView tvAsKnownAs;
    TextView tvIngredient;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        tvDescription = findViewById(R.id.description_tv);
        tvOrigin = findViewById(R.id.origin_tv);
        tvAsKnownAs = findViewById(R.id.also_known_tv);
        tvIngredient = findViewById(R.id.ingredients_tv);
        progressBar = findViewById(R.id.img_progress);

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

        try {
            progressBar.setVisibility(View.VISIBLE);
            sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI();
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError() {

                        }
                    });

            setTitle(sandwich.getMainName());
        } catch (Exception e ){
            e.printStackTrace();
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        tvDescription.setText(sandwich.getDescription().equals("") ? "no information" : sandwich.getDescription());
        tvOrigin.setText(sandwich.getPlaceOfOrigin().equals("") ? "no information" : sandwich.getPlaceOfOrigin());
        addTextToViewFromList(tvAsKnownAs,sandwich.getAlsoKnownAs());
        addTextToViewFromList(tvIngredient,sandwich.getIngredients());

    }

    /**
     * Helper function for appending array of text to the TextView
     * @param tv a reference to a TextView to get the text append to
     * @param data a List of sting
     * */
    private void addTextToViewFromList (TextView tv , List<String> data){
        if (data != null){
            if (data.size() == 0 ) {
                tv.setText(R.string.no_info);
                return;
            }
            for (int i = 0; i < data.size(); i++) {
                if (i == data.size() - 1 ){
                    tv.append((data.get(i)));
                }else{
                    tv.append((data.get(i) + "\n\n"));
                }
            }

        }
    }
}
