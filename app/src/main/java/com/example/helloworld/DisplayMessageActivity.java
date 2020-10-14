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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        //Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.textView3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                textView.setText(readFile());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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
    public void addFile(View view) throws FileNotFoundException {
        String content = "";
        try {
            content = readFile();
        } catch (FileNotFoundException e) {
            // Error occurred when opening raw file for reading
        }
        EditText editText = (EditText) findViewById(R.id.editTextMessage);
        String message = editText.getText().toString();
        content += message;
        createFile(content);
        TextView textView = findViewById(R.id.textView3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                textView.setText(readFile());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        editText.setText("");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void removeFile(View view) throws FileNotFoundException {
        String content = "";
        try {
            content = readFile();
        } catch (FileNotFoundException e) {
            // Error occurred when opening raw file for reading
        }
        EditText editText = (EditText) findViewById(R.id.editTextMessage);
        String message = editText.getText().toString();
        //TextView textView = (TextView) findViewById(R.id.textView3);
        //String message = textView.getText().toString();

        if (!(message.equals("")))
            createFile(content.replace("\n" + message + "\n", "\n"));
        TextView textView = findViewById(R.id.textView3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                textView.setText(readFile());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        editText.setText("");
    }
}