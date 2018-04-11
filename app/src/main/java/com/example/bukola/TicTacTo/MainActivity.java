package com.example.bukola.TicTacTo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.example.bukola.TicTacTo.R.id.RGtwoPlayer;

public class MainActivity extends AppCompatActivity {

    Button one_player, two_player, play, highScore;
    boolean singleSelected = true;
    RadioButton easy, difficult, singleDevice, twoDevice;
    RadioGroup singlePlayer, twoPlayer;
    SharedPreferences sharedPrefs;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefs = getSharedPreferences("NAMES", MODE_PRIVATE);
        two_player_names.p1Name = sharedPrefs.getString("PLAYER1", two_player_names.p1Name);
        two_player_names.p2Name = sharedPrefs.getString("PLAYER2", two_player_names.p2Name);
        TwoDevice2P_names.MyName = sharedPrefs.getString("MYNAME", TwoDevice2P_names.MyName);

        one_player = (Button) findViewById(R.id.one_player);
        two_player = (Button) findViewById(R.id.two_player);
        play = (Button) findViewById(R.id.play);
        highScore = (Button) findViewById(R.id.highScores);
        highScore.setVisibility(View.INVISIBLE);
        easy = (RadioButton) findViewById(R.id.easyRBtn);
        easy.setChecked(true);
        difficult = (RadioButton) findViewById(R.id.difficultRBtn);
        singleDevice = (RadioButton) findViewById(R.id.singleRBtn);
        singleDevice.setChecked(true);
        twoDevice = (RadioButton) findViewById(R.id.difficultRBtn);

        singlePlayer = (RadioGroup) findViewById(R.id.RGsinglePlayer);
        singlePlayer.setVisibility(View.VISIBLE);
        twoPlayer = (RadioGroup) findViewById(RGtwoPlayer);
        twoPlayer.setVisibility(View.INVISIBLE);
        two_player.setAlpha((float) 0.3);
        one_player.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one_player.setAlpha((float) 1);
                        two_player.setAlpha((float) 0.3);
                        singleSelected = true;
                        singlePlayer.setVisibility(View.VISIBLE);
                        twoPlayer.setVisibility(View.INVISIBLE);
                        highScore.setVisibility(View.INVISIBLE);
                    }
                }
        );
        two_player.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        one_player.setAlpha((float) 0.3);
                        two_player.setAlpha((float) 1);
                        singleSelected = false;
                        singlePlayer.setVisibility(View.INVISIBLE);
                        twoPlayer.setVisibility(View.VISIBLE);
                        highScore.setVisibility(View.VISIBLE);
                    }
                }
        );
        play.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (singleSelected) {
                            if (easy.isChecked()) {
                                Intent intent;
                                intent = new Intent(MainActivity.this, one_player_easy.class);
                                startActivity(intent);
                            } else if (difficult.isChecked()) {
                                Intent intent;
                                intent = new Intent(MainActivity.this, one_player_difficult.class);
                                startActivity(intent);
                            }
                        } else {
                            if (singleDevice.isChecked()) {
                                Intent intent;
                                intent = new Intent(MainActivity.this, two_player_names.class);
                                startActivity(intent);
                            } else {
                                Intent intent;
                                intent = new Intent(MainActivity.this, TwoDevice2P_names.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
        );

        highScore.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, highscores2p.class);
                        startActivity(intent);
                    }
                }
        );

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.bukola.TicTacTo/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences.Editor edit = sharedPrefs.edit();
        edit.putString("PLAYER1", two_player_names.p1Name);
        edit.putString("PLAYER2", two_player_names.p2Name);
        edit.putString("MYNAME", TwoDevice2P_names.MyName);
        edit.commit();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.example.bukola.TicTacTo/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
