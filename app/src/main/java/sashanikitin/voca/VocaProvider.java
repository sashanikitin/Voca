package sashanikitin.voca;

import android.content.ContentProvider;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.sql.Date;

import sashanikitin.voca.db.Story;
import sashanikitin.voca.db.VocaDB;
import sashanikitin.voca.db.Word;

public class VocaProvider extends ContentProvider {

    public static final String STORIES = "stories";
    public static final String WORDS = "words";

    final static public Uri URI = Uri
            .parse("content://sashanikitin.voca");
    private VocaDB db;

    public VocaProvider() {
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        if (values.containsKey(Story.STORY)) {
            Story story = new Story(values.getAsString(Story.STORY));
            db.storyDao().insertStory(story);
        } else {
            Word word = new Word(values.getAsString(Word.WORD), values.getAsString(Word.MEANING),
                    values.getAsInteger(Word.RATE));
            db.wordDao().insertWord(word);
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        db = VocaDB.getInstance(this.getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.i("SAS", "sas");
        switch (selection) {
            case STORIES:
                return getAllStories();
            case WORDS:
                return getAllWords();
        }
        return getAllStories();
    }

    private Cursor getAllStories() {
        return db.storyDao().findAllCursor();
    }

    private Cursor getAllWords() {
        return db.wordDao().findAllCursor();
    }

    private Cursor getStoryAndWords(String selection) {
        return null;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}