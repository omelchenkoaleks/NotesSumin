package com.omelchenkoaleks.notessumin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

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

    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        // убираем ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mTitleEditText = findViewById(R.id.title_edit_text);
        mDescriptionEditText = findViewById(R.id.description_edit_text);
        mDaysOfWeekSpinner = findViewById(R.id.day_of_week_spinner);
        mPriorityRadioGroup = findViewById(R.id.priority_radio_group);
    }

    public void onClickSaveNote(View view) {
        String title = mTitleEditText.getText().toString().trim();
        String description = mDescriptionEditText.getText().toString().trim();

        /*
            т.к. метод getSelectedItemPosition() возвращает позицию с нуля, то
            в объект ContentValues мы добавляем 1 (то есть если метод вернет
            нам понедельник - это ноль, но мы добавляем единицу = 1 и так далее :) )
         */
        int dayOfWeek = mDaysOfWeekSpinner.getSelectedItemPosition();

        int radioButtonId = mPriorityRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());

        if (isFilled(title, description)) {
            Note note = new Note(title, description, dayOfWeek, priority);
            mMainViewModel.insertNote(note);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.warning_fill_fields, Toast.LENGTH_SHORT).show();
        }
    }

    /*
        метод, который проверяет заполнены ли все поля = но, т.к.
        Spinner and RadioGroup пустыми быть не могут их нет в этом методе
        в качестве параметров
     */
    private boolean isFilled(String title, String description) {
        return !title.isEmpty() && !description.isEmpty();
    }
}
