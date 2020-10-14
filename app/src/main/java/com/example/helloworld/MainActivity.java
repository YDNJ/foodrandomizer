package com.example.helloworld;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static java.lang.Math.random;
import static java.nio.charset.StandardCharsets.*;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.helloworld.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This whole mess is to create a file if no file is present
        // I don't like this method at all
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                String content = readFile();
            } catch (FileNotFoundException e) {
                /*String content = "\nApfel\nBirne\nZitrone\nOrange\nBanane\nKokosnuss\nTomate\nZwiebel" +
                        "\nErdbeere\nBlaubeere\nErdnuss\nMandel\nCashew\nParanuss\nHimbeer\nSaltaninen" +
                        "\nRosinen\nCranberries\nSpaghetti\nFusilli\nTortellini\nPenne\nLinguini" +
                        "\nSpirelli\nAnchovies\nMakrele\nThunfisch\nLachs\nForelle\nHailbutt\nSardine" +
                        "\nKidneybohnen\nErbsen\nBohnen\nGurken\nSojasprossentriebe\n";
                createFile(content);*/
                createFile("\n");
            }
        }
    }

    public void changeActivity(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        /*EditText editText = (EditText) findViewById(R.id.editTextMessage);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);*/
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createFile(String fileContents){
        String filename = "myfile";
        try (FileOutputStream fos = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(fileContents.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String readFile() throws FileNotFoundException {
        FileInputStream fis = getApplicationContext().openFileInput("myfile");
        InputStreamReader inputStreamReader = new InputStreamReader(fis, UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
            return stringBuilder.toString();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getRandomFood(View view) {
        TextView textView = findViewById(R.id.textView2);
        try {
            String content = "";
            try {
                content = readFile();
            } catch (FileNotFoundException e) {
                // Error occurred when opening raw file for reading
            }
            String[] pos = content.split("\n");

            String item = "";
            while (item.equals(""))
            {
                item = pos[(int)(random() * pos.length)];
            }
            textView.setText(item);
        } catch (Exception e) {
            textView.setText("List is empty!");
        }
    }
}