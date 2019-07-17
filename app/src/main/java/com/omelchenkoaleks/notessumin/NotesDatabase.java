package com.omelchenkoaleks.notessumin;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/*
    сама база данных

    1, должен быть абстрактным и расширять класс RoomDatabase
    2, во всем приложении нам нужен только один объект базы данных (т.е.
    один и тот же = без изменений) - для этого используем Singleton
    3, помеяаем анотацией @Database и добавляем нужные параметры
    4, нужно получить объект интерфейса NoteDao
 */
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    private static NotesDatabase sNotesDatabase;
    private static final String DB_NAME = "notes2.db";

    // объект для синхронизации
    private static final Object LOCK = new Object();

    public static NotesDatabase getInstance(Context context) {
        /*
            может быть такой случай, что несколько потоков одновременно
            запросят экземпляр этого объекта и если он null -
            могут создать одновременно по экземпляру ... )))

            для предосторожности добавляем блок синхронизации:
                1, создается объект для синхронизации (обязательно final)
                2, оборачиваем нужный метод в блок (если придет второй поток,
                то он будет ждать - потом возьмет уже созданный экземпляр)
         */
        synchronized (LOCK) {
            if (sNotesDatabase == null) {
            /*
                для того, чтобы создать объект базы данных необходимо
                использовать строитель databaseBuilder()
                     параметры:
                        context,
                        наш класс который рассширяет RoomDatabase
                        имя базы данных
                у полученного стоителя вызываем метод build()
             */
                sNotesDatabase = Room.databaseBuilder(
                        context, NotesDatabase.class, DB_NAME).build();

            }
        }
        return sNotesDatabase;
    }

    public abstract NotesDao mNotesDao();
}
