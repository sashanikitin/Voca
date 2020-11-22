package sashanikitin.voca.db;

import android.provider.ContactsContract;

import java.sql.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Story")

public class Story {

    static final public String STORY = "Story";

    public Story(String story) {
        this.story = story;
        this.date = java.text.DateFormat.
                getDateTimeInstance().format(new Date(System.currentTimeMillis()));
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "story")
    public String story;

}
