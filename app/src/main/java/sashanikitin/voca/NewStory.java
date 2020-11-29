package sashanikitin.voca;

import android.content.ContentValues;
import android.graphics.Color;

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
import sashanikitin.voca.db.Story;
import sashanikitin.voca.db.Word;

public class NewStory extends AppCompatActivity implements View.OnClickListener {

    public static int[] colors = new int[]{Color.parseColor("#43b020"),
        Color.parseColor("#FF6700")};

    LinearLayout linLayout;
    LayoutInflater ltInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_story_new_word);

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
        TextView word = (TextView) item.findViewById(R.id.word);
        TextView meaning = (TextView) item.findViewById(R.id.meaning);
        TextView rate = (TextView) item.findViewById(R.id.rate);
        item.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        item.setBackgroundColor(colors[color ? 0 : 1]);
        color = !color;
        linLayout.addView(item);
    }

    @Override
    public void onClick(View v) {

    }
}