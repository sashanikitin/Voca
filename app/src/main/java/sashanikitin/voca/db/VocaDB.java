package sashanikitin.voca.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Story.class, Word.class}, version = 2)

public abstract class VocaDB extends RoomDatabase {
    private static VocaDB INSTANCE;

    /**
     * GuideDatabase.
     *
     * @param context - context
     */
    public static VocaDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (VocaDB.class) {
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(), VocaDB.class, "vocadb")
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .build();
            }
        }
        return INSTANCE;
    }

    /**
     * Function destroyInstance.
     *
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Function StoryDao.
     *
     */
    public abstract StoryDao storyDao();

    /**
     * Function WordDao.
     *
     */
    public abstract WordDao wordDao();




    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}