package em.topquiz.org.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import em.topquiz.org.R;
import em.topquiz.org.model.User;

public class MainActivity extends AppCompatActivity {

    private TextView mGretingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private User mUser;
    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private SharedPreferences mPreferences;
    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(GAME_ACTIVITY_REQUEST_CODE==requestCode &&RESULT_OK==resultCode){
            //Fetch the score from the intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            //stocke ds les preference du telephone le score ous la clé "score"
            mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("MainActivity::onCreate()");

        mUser=new User();

        mPreferences=getPreferences(MODE_PRIVATE);

        mGretingText = findViewById(R.id.activity_main_greting_txt);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);

        mPlayButton.setEnabled(false);  //le btn est désactivé

        greetUser();

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //User clicked the button
                String firstName = mNameInput.getText().toString();
                mUser.setPrenom(firstName);
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME, mUser.getPrenom()).apply();

                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                //  je crée l'acivité du jeu et je lui associe l'identifiant 42, ainsi si cette activité me renvoi un result
                //  alors je pourrai verifier que c'est bien elle qui l'envoit avant de le stocker
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    private void greetUser() {
        String firstname = mPreferences.getString(PREF_KEY_FIRSTNAME, null);

        if (null != firstname) {
            int score = mPreferences.getInt(PREF_KEY_SCORE, 0);

            String fulltext = "Welcome back, " + firstname
                    + "!\nYour last score was " + score
                    + ", will you do better this time?";
            mGretingText.setText(fulltext);
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("MainActivity::onDestroy()");
    }


}
