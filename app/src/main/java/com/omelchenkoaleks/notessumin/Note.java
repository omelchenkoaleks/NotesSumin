package com.omelchenkoaleks.notessumin;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/*
    класс хранит заметку

    для того, чтобы хранить этот объект в базе данных =
    1, его нужно пометить анотацией @Entity и указать название таблицы
    2, каждая таблица обязана содержать уникльный id - указываем, что это
    первичный ключ - и что он должен генерироваться автоматически
    3, этот объект должен содержать конструктор в которомы мы инициализиреум
    все поля (должен быть public) и, должен содержать гетеры и сетеры
    4, заметки мы будем создавать иногода также сами, но не знаем пока какой
    у них должен быть id = поэтому создаем конструктор в котором будут
    инициализированы все поля кроме id
    5, чтобы использовать этот класс в базе данных он должен содержать только
    один конструктор, который инициализирует все поля (наш созданный без id
    будет мешать) поэтому добавляем ему анотацию @Ignore
 */
@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private int dayOfWeek;
    private int priority;

    public Note(int id, String title, String description, int dayOfWeek, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    @Ignore
    public Note(String title, String description, int dayOfWeek, int priority) {
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
    }

    // метод, который преобразовывает число дня недели в строку
    public static String getDayAsString(int position) {
        switch (position) {
            case 1:
                return "Понедельник";
            case 2:
                return "Вторник";
            case 3:
                return "Среда";
            case 4:
                return "Четверг";
            case 5:
                return "Пятница";
            case 6:
                return "Суббота";
            case 7:
                return "Воскресенье";
            default:
                return "Понедельник";
        }
    }
}
