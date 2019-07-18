package com.omelchenkoaleks.notessumin;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/*
    в этом объекте мы будем делать все запросы к базам данных

    плюс этого объекта - он имеет свой собственный жизненный цикл, на который
    не влияют методы жизненного цикла )))
 */
public class MainViewModel extends AndroidViewModel {
    private static NotesDatabase mNotesDatabase;
    private LiveData<List<Note>> mNotes;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mNotesDatabase = NotesDatabase.getInstance(getApplication());
        mNotes = mNotesDatabase.mNotesDao().getAllNotes();
    }

    public NotesDatabase getNotesDatabase() {
        return mNotesDatabase;
    }

    public LiveData<List<Note>> getNotes() {
        return mNotes;
    }

    public void insertNote(Note note) {
        new InsertTask().execute(note);
    }

    public void deleteNote(Note note) {
        new DeleteTask().execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllTask().execute();
    }

    private static class InsertTask extends AsyncTask<Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length > 0) {
                mNotesDatabase.mNotesDao().insertNote(notes[0]);
            }
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Note, Void, Void> {
        @Override
        protected Void doInBackground(Note... notes) {
            if (notes != null && notes.length > 0) {
                mNotesDatabase.mNotesDao().deleteNote(notes[0]);
            }
            return null;
        }
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            mNotesDatabase.mNotesDao().getAllNotes();
            return null;
        }
    }
}
