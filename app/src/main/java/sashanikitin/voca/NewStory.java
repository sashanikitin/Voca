package sashanikitin.voca;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

import sashanikitin.voca.db.Story;
import sashanikitin.voca.db.Word;

public class NewStory extends AppCompatActivity {

    public static int[] colors = new int[]{Color.parseColor("#43b020"),
            Color.parseColor("#FF6700")};

    LinearLayout linLayout;
    LayoutInflater ltInflater;
    EditText editText;
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;

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

        Button speechButton = findViewById(R.id.speech);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }
        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDB();
            }
        });
        ltInflater = getLayoutInflater();
        addItem();
        editText = findViewById(R.id.editTextTextMultiLine);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                Log.i("SAS", "onReadyForSpeech");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.i("SAS", "onBeginningOfSpeech");
            }

            @Override
            public void onRmsChanged(float v) {
            }

            @Override
            public void onBufferReceived(byte[] bytes) {
                Log.i("SAS", "onBufferReceived");
            }

            @Override
            public void onEndOfSpeech() {
                Log.i("SAS", "onEndOfSpeech");
            }

            @Override
            public void onError(int i) {
                Log.i("SAS", "onError");
            }

            @Override
            public void onResults(Bundle bundle) {
                String text;
                Toast.makeText(getApplicationContext(), "Finish", Toast.LENGTH_SHORT).show();
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (editText.getText().toString().isEmpty()) {
                    text = editText.getText().toString() + data.get(0);
                } else {
                    text = editText.getText().toString() + "\n" + data.get(0);
                }
                editText.setText(text);
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                Log.i("SAS", "onPartialResults");
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
                Log.i("SAS", "onEvent");
            }
        });

        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListen();
            }
        });
    }


    protected void startListen() {
        Toast.makeText(getApplicationContext(), "Listerning..", Toast.LENGTH_SHORT).show();

        Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.startListening(speechRecognizerIntent);
    }

    private void saveToDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {

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

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }
}