package com.omelchenkoaleks.notessumin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class MainActivity extends AppCompatActivity {
    private RecyclerView mNotesRecyclerView;
    private NotesAdapter mAdapter;

    private NotesDBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    // static - чтобы не нужно было создавать новый объект активити
    // final - чтобы случайно не присвоить ему новое значение
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

        mDBHelper = new NotesDBHelper(this);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();

        getData();

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

    /*
        для того, чтобы организовать удаление из базы данных:
            1, нужно добавить поле id в объект Note (в данном приложении)
            2, получить это id (мы получаем здесь)
            3, формируем две переменные для параметров в метод delete()
     */
    private void remove(int position) {
        int id = mNotes.get(position).getId();
        String where = NotesContract.NotesEntry._ID + " = ?";
        String[] whereArgs = new String[] {Integer.toString(id)};
        mSQLiteDatabase.delete(NotesContract.NotesEntry.TABLE_NAME, where, whereArgs);

        getData();

        /*
                    каждый раз при добавлении или удалении айтема в
                    RecyclerView нужно сообщить об этом адаптеру
                    используется метод notifyDataSetChanged()
                 */
        mAdapter.notifyDataSetChanged();
    }

    public void onClickAddNote(View view) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    private void getData() {
        // перед получением данных не забываем очистить масив :)
        mNotes.clear();

        /*
            ПРИМЕР: как реализовать выборку заметок только с наивысшим приоритетом:
                1, selection - указываем колонку и условие с подстановкой
                2, selectionArgs - число в данном случае тип String, выше которого
                хотим приоритет
         */
        String selection = NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK + " == ?";
        String[] selectionArgs = new String[] {"1"};

        // Cursor (объект) используется, чтобы получить данные из DB
        Cursor cursor = mSQLiteDatabase.query(NotesContract.NotesEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK); // сортируем по дню недели
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
            int dayOfWeek = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK));
            int priority = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));

            Note note = new Note(id, title, description, dayOfWeek, priority);
            mNotes.add(note);
        }
        cursor.close(); // не забываем закрыть курсор :)
    }
}
