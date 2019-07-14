package com.omelchenkoaleks.notessumin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mNotesRecyclerView;
    private NotesAdapter mAdapter;

    private NotesDBHelper mDBHelper;

    // static - чтобы не нужно было создавать новый объект активити
    // final - чтобы случайно не присвоить ему новое значение
    private final ArrayList<Note> mNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotesRecyclerView = findViewById(R.id.notes_recycler_view);

        mDBHelper = new NotesDBHelper(this);
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        database.delete(NotesContract.NotesEntry.TABLE_NAME, null, null);

        // Cursor (объект) используется, чтобы получить данные из DB
        Cursor cursor = database.query(NotesContract.NotesEntry.TABLE_NAME,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            String dayOfWeek = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));

            Note note = new Note(title, description, dayOfWeek, priority);
            mNotes.add(note);
        }

        mAdapter = new NotesAdapter(mNotes);
        mNotesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        /*
                    каждый раз при добавлении или удалении айтема в
                    RecyclerView нужно сообщить об этом адаптеру
                    используется метод notifyDataSetChanged()
                 */
        mNotes.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }
}
