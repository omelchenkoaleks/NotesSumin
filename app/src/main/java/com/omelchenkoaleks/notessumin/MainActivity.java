package com.omelchenkoaleks.notessumin;

import android.content.Intent;
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

    // static - чтобы не нужно было создавать новый объект активити
    // final - чтобы случайно не присвоить ему новое значение
    public static final ArrayList<Note> mNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotesRecyclerView = findViewById(R.id.notes_recycler_view);

        if (mNotes.isEmpty()) {
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
