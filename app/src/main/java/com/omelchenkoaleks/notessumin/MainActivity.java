package com.omelchenkoaleks.notessumin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mNotesRecyclerView;
    private NotesAdapter mAdapter;
    private NotesDatabase mNotesDatabase;

    private final ArrayList<Note> mNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // убираем ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mNotesRecyclerView = findViewById(R.id.notes_recycler_view);

        // получаем базу данных
        mNotesDatabase = NotesDatabase.getInstance(this);

        mAdapter = new NotesAdapter(mNotes);
        mNotesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();
        mNotesRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                // выводим позицию на экран
                Toast.makeText(MainActivity.this,
                        "Номер позиции нажат: " + position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                remove(position);
            }
        });

        /*
            удаление при смахивании влево или вправо
         */
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                remove(viewHolder.getAdapterPosition());
            }
        });

        // ItemTouchHelper нужно прикрепить к RecyclerView
        itemTouchHelper.attachToRecyclerView(mNotesRecyclerView);
    }

    private void remove(int position) {
        Note note = mNotes.get(position);
        mNotesDatabase.mNotesDao().deleteNote(note);
        getData();
        mAdapter.notifyDataSetChanged();
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void getData() {
        // этой строчкой мы получаем все заметки из базы данных
        List<Note> notesFromDB = mNotesDatabase.mNotesDao().getAllNotes();
        mNotes.clear();
        mNotes.addAll(notesFromDB);
    }
}
