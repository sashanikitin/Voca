package sashanikitin.voca.db;

import java.util.List;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface StoryDao {
    /**
     * insertWord.
     *
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    public void insertStory(Story story);

    /**
     * findByAllSubscribedCursor.
     *
     */
    @Query("SELECT( SELECT COUNT(*) + 1" +
            " FROM story WHERE date < t.date OR (date = t.date )) as _id, story," +
            " date From story t ORDER BY t.date ")
    public Cursor findAllWithNumberCursor();

    @Query("SELECT * From story  ORDER BY date ")
    public Cursor findAllCursor();
}
