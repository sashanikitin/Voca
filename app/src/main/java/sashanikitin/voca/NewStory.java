package sashanikitin.voca;

import android.content.ContentValues;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import sashanikitin.voca.db.Story;
import sashanikitin.voca.db.VocaDB;
import sashanikitin.voca.db.Word;

public class NewStory extends AppCompatActivity implements View.OnClickListener {

    int[] colors = new int[2];
    LinearLayout linLayout;
    LayoutInflater ltInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_story_new_word);

        colors[0] = Color.parseColor("#559966CC");
        colors[1] = Color.parseColor("#55336699");

        linLayout = (LinearLayout) findViewById(R.id.newWordLin);
        Button addWord = findViewById(R.id.add_word);
        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDB();
            }
        });
        ltInflater = getLayoutInflater();
        addItem();

    }

    private void saveToDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EditText editText = findViewById(R.id.editTextTextMultiLine);
                editText.getText();
                ContentValues cv = new ContentValues();
                cv.put(Story.STORY, editText.getText().toString());
                getContentResolver().insert(VocaProvider.URI, cv);
                ViewGroup viewGroup = linLayout;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    ViewGroup view = (ViewGroup) viewGroup.getChildAt(i);
                    cv = new ContentValues();
                    AutoCompleteTextView autoCompleteText = (AutoCompleteTextView) view.getChildAt(0);
                    String w = autoCompleteText.getText().toString();

                    autoCompleteText = (AutoCompleteTextView) view.getChildAt(1);
                    String m = autoCompleteText.getText().toString();

                    autoCompleteText = (AutoCompleteTextView) view.getChildAt(2);
                    String r = autoCompleteText.getText().toString();

                    if (m.length() > 0 && w.length() > 0) {
                        cv.put(Word.WORD, w);
                        cv.put(Word.MEANING, m);
                        cv.put(Word.RATE, r);
                        getContentResolver().insert(VocaProvider.URI, cv);
                    }
                }
            }
        }).start();
    }

    private boolean color;

    private void addItem() {
        View item = ltInflater.inflate(R.layout.new_word_item, linLayout, false);
        TextView tvName = (TextView) item.findViewById(R.id.tvName);
        TextView tvPosition = (TextView) item.findViewById(R.id.tvPosition);
        TextView tvSalary = (TextView) item.findViewById(R.id.tvSalary);
        item.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        item.setBackgroundColor(colors[color ? 0 : 1]);
        color = !color;
        linLayout.addView(item);
    }

    @Override
    public void onClick(View v) {

    }
}