package com.omelchenkoaleks.notessumin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mNotesRecyclerView;

    private ArrayList<Note> mNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotesRecyclerView = findViewById(R.id.notes_recycler_view);

        mNotes.add(new Note("Парикмахер", "сделать прическу", "понедельник", 0));
        mNotes.add(new Note("Курсы", "програмирование", "вторник", 2));
        mNotes.add(new Note("Пикник", "шашлык", "среда", 2));
        mNotes.add(new Note("Пикник", "шашлык", "среда", 2));
        mNotes.add(new Note("Пикник", "шашлык", "среда", 2));
        mNotes.add(new Note("Пикник", "шашлык", "среда", 1));
        mNotes.add(new Note("Пикник", "шашлык", "среда", 0));
        mNotes.add(new Note("Пикник", "шашлык", "среда", 2));
        mNotes.add(new Note("Охота", "на раков", "понедельник", 2));
        mNotes.add(new Note("Рыбалка", "рыбы наловить", "воскресенье", 2));
        mNotes.add(new Note("Рыбалка", "рыбы наловить", "воскресенье", 1));
        mNotes.add(new Note("Рыбалка", "рыбы наловить", "воскресенье", 2));
        mNotes.add(new Note("Рыбалка", "рыбы наловить", "воскресенье", 1));
        mNotes.add(new Note("Рыбалка", "рыбы наловить", "воскресенье", 2));

        NotesAdapter adapter = new NotesAdapter(mNotes);
        mNotesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNotesRecyclerView.setAdapter(adapter);
    }
}
