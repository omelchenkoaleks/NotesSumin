package com.omelchenkoaleks.notessumin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class AddNoteActivity extends AppCompatActivity {
    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private Spinner mDaysOfWeekSpinner;
    private RadioGroup mPriorityRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mTitleEditText = findViewById(R.id.title_edit_text);
        mDescriptionEditText = findViewById(R.id.description_edit_text);
        mDaysOfWeekSpinner = findViewById(R.id.day_of_week_spinner);
        mPriorityRadioGroup = findViewById(R.id.priority_radio_group);
    }

    public void onClickSaveNote(View view) {
        String title = mTitleEditText.getText().toString().trim();
        String description = mDescriptionEditText.getText().toString().trim();
        String dayOfWeek = mDaysOfWeekSpinner.getSelectedItem().toString();
        int radioButtonId = mPriorityRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioButtonId);
        int priority = Integer.parseInt(radioButton.getText().toString());

        Note note = new Note(title, description, dayOfWeek, priority);
        MainActivity.mNotes.add(note); // здесь добавляем в активити в массив (что там ...)

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
