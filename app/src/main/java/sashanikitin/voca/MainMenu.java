package sashanikitin.voca;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button newStory = findViewById(R.id.newStory);
        newStory.setOnClickListener(this);

        Button listStories = findViewById(R.id.listStories);
        listStories.setOnClickListener(this);

        Button vocabulary = findViewById(R.id.vocabulary);
        vocabulary.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.newStory:
                 intent = new Intent(this,NewStory.class);
                startActivity(intent);
                break;
            case R.id.listStories:
                 intent = new Intent(this,ScrollingStories.class);
                startActivity(intent);
                break;
            case R.id.vocabulary:
                 intent = new Intent(this,ScrollingWords.class);
                startActivity(intent);
                break;
        }
        
    }
}