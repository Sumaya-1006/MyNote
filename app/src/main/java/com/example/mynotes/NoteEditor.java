package com.example.mynotes;

import static com.example.mynotes.MainActivity.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;

public class NoteEditor extends AppCompatActivity {
    EditText editText;
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",-1);

        if (noteId !=-1){
            editText.setText(MainActivity.notes.get(noteId));

        }else{
            notes.add("");
            noteId = notes.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                notes.set(noteId,String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.mynotes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();

                if(set==null){
                    notes.add("Write Here");
                }else{
                    notes = new ArrayList<>(set);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}