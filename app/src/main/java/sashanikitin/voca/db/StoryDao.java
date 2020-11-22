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
}
