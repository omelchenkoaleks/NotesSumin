package com.omelchenkoaleks.notessumin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private Spinner mDaysOfWeekSpinner;
    private RadioGroup mPriorityRadioGroup;

    private NotesDBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mTitleEditText = findViewById(R.id.title_edit_text);
        mDescriptionEditText = findViewById(R.id.description_edit_text);
        mDaysOfWeekSpinner = findViewById(R.id.day_of_week_spinner);
        mPriorityRadioGroup = findViewById(R.id.priority_radio_group);

        mDBHelper = new NotesDBHelper(this);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }

    public void onClickSaveNote(View view) {
        String title = mTitleEditText.getText().toString().trim();
        String description = mDescriptionEditText.getText().toString().trim();
        String dayOfWeek = mDaysOfWeekSpinner.getSelectedItem().toString();
        int radioButtonId = mPriorityRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());

        if (isFilled(title, description)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesContract.NotesEntry.COLUMN_TITLE, title);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, description);
            contentValues.put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, dayOfWeek);
            contentValues.put(NotesContract.NotesEntry.COLUMN_PRIORITY, priority);

            mSQLiteDatabase.insert(NotesContract.NotesEntry.TABLE_NAME, null, contentValues);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Не заполнены поля!", Toast.LENGTH_SHORT).show();
        }
    }

    /*
        метод, который проверяет заполнены ли все поля = но, т.к.
        Spinner and RadioGroup пустыми быть не могут их нет в этом методе
        в качестве параметров
     */
    private boolean isFilled(String title, String description) {
        return !title.isEmpty() & !description.isEmpty();
    }
}
