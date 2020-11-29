package sashanikitin.voca;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ScrollingWords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        buildUI();
    }

    private void buildUI() {
        Cursor cursor = getContentResolver().query(VocaProvider.URI, null, VocaProvider.WORDS,
                null, null);

        ListView lvContact = (ListView) findViewById(R.id.listStoriesViev);
        LayoutInflater ltInflater = getLayoutInflater();
        LinearLayout linLayout = findViewById(R.id.linear_layout);

        cursor.moveToFirst();
        boolean color = false;
        for (int i = 0; i < cursor.getCount(); i++) {

            View item = ltInflater.inflate(R.layout.activity_scrolling_words, linLayout, false);
            TextView word = (TextView) item.findViewById(R.id.words);
            TextView meaning = (TextView) item.findViewById(R.id.meanings);

            String wordString = cursor.getString(1);

            word.setText(wordString);
            meaning.setText(cursor.getString(2));

            item.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            word.setTextColor(NewStory.colors[color ? 0 : 1]);
            color = !color;
            linLayout.addView(item);
            cursor.moveToNext();
        }
    }

}
