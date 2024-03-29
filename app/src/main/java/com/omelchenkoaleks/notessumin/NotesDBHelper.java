package com.omelchenkoaleks.notessumin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "notes.db";

    /*
        повышаем версию базы данных, потому что произвели в ней изменения =
        теперь сработает метод onUpgrade старая будет удалена, а новая создана
     */
    public static final int DB_VERSION = 2;

    public NotesDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotesContract.NotesEntry.CREATE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // для того, чтобы обновить db нужно ее удалить и создать новую
        db.execSQL(NotesContract.NotesEntry.DROP_COMMAND);
        onCreate(db);
    }
}
