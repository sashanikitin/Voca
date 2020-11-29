package sashanikitin.voca;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ScrollingStories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        buildUI();
    }

    private void buildUI() {
        Cursor cursor = getContentResolver().query(VocaProvider.URI, null, VocaProvider.STORIES,
                null, null);
        ListView lvContact = (ListView) findViewById(R.id.listStoriesViev);
        LayoutInflater ltInflater = getLayoutInflater();
        LinearLayout linLayout = findViewById(R.id.linear_layout);

        cursor.moveToFirst();
        boolean color = false;
        for (int i = 0; i < cursor.getCount(); i++) {

            View item = ltInflater.inflate(R.layout.activity_scrolling_stories, linLayout, false);
            TextView story = (TextView) item.findViewById(R.id.story);
            TextView date = (TextView) item.findViewById(R.id.date);
            String storySt = cursor.getString(1);
            date.setText(cursor.getString(0));
            cursor.moveToNext();
            if (storySt.isEmpty()) continue;
            story.setText(storySt);

            item.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            story.setTextColor(NewStory.colors[color ? 0 : 1]);
            color = !color;
            linLayout.addView(item);

        }
    }

}
