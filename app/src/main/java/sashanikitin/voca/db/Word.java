package sashanikitin.voca.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "Word")
public class Word {
    static final public String WORD = "Word";
    static final public String MEANING = "Meaning";
    static final public String RATE = "Rate";
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "word")
    String word;

    @ColumnInfo(name = "meaning")
    String meaning;

    @ColumnInfo(name = "rate")
    int rate;

    public Word(String word, String meaning,int rate ) {
        this.word = word;
        this.meaning = meaning;
        this.rate = rate;
        date = java.text.DateFormat.
                getDateTimeInstance().format(new Date(System.currentTimeMillis()));
    }



    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
