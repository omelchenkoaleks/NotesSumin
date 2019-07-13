package com.omelchenkoaleks.notessumin;

import android.provider.BaseColumns;

/*
    класс ничего не делает, а только хранит информацию о таблице
    в этом классе будет столько вложенных классов, сколько будет таблиц
    в приложении ...
 */
public class NotesContract {
    /*
        1, мы запрещаем от этого класса наследоваться, поэтому final
        2, обычно, чтобы показать что класс хранит таблицу - название
        заканчивается словом Entry
        3, благодаря тому, что мы реализуем интерфейс BaseColumns
        колонка с _id генерируется автоматически
     */
    public static final class NotesEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_DAY_OF_WEEK = "day_of_week";
        public static final String COLUMN_PRIORITY = "priority";

        // в этой таблице будет испльзоваться два типа данных - для
        // них создаем тоже константы
        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";

        // создаем команду, которая будет создавать таблицу
        public static final String CREATE_COMMAND =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        "(" + _ID + " " + TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " " + TYPE_TEXT + ", " +
                        COLUMN_DESCRIPTION + " " + TYPE_TEXT + ", " +
                        COLUMN_DAY_OF_WEEK + " " + TYPE_TEXT + ", " +
                        COLUMN_PRIORITY + " " + TYPE_INTEGER + ")";

        // создаем команду, которая будет удалять таблицу
        public static final String DROP_COMMAND =
                "DROP TABLE IF EXISTS" + TABLE_NAME;
    }
}
