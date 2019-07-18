package com.omelchenkoaleks.notessumin;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/*
    DAO - data access object (объект доступа к данным)

    1, его необходимо пометить анотацией @Dao
    2, он содержит методы для доступа к базе данных (для работы с db)
 */
@Dao
public interface NotesDao {
    /*
        возвращает все данные, которые содериж база данных

        метод вызывается при запросе к базе данных, поэтому его нужно
        пометить анотацией @Query() и в скобках указать нужный запрос:
            у нас это "выбираем все из базы и сортируем по дням недели"

        ДОБАВЛЯЕМ LiveData = это решает нам две проблемы:
            1, мы удалили метод, который нам ради тестирования помогал работать с данными
            в главном потоке (что не рекомендуется) - LiveData работает с данными в другом потоке
            2, сейчас при добавлении или удалении данных мы вручную берем все данные в базе сначала,
            а потом добавляем их в основной список = LiveData будет наблюдать за изменениями и
            самостоятельно проводить эти операции )))

            КАК ЭТО РАБОТАЕТ:
                1, оборачиваем List<Note> в LiveData - теперь метод getAllNotes() будет возвращать
                не список записей, а объект LiveData, содержащий список записей
    */
    @Query("SELECT * FROM notes ORDER BY dayOfWeek")
    LiveData<List<Note>> getAllNotes();

    /*
        добавляет данные в базу данных

        метод вставляет данные, поэтому помечаем его анотацией @Insert
     */
    @Insert
    void insertNote(Note note);

    /*
        удаляет данные (заметку)

        метод удаляет данные, поэтому помечаем его анотацией @Delete
     */
    @Delete
    void deleteNote(Note note);

    /*
        удаляем все данные

        метод удаляет все заметки (данные), поэтому анотация @Query
        с указание запроса
     */
    @Query("DELETE FROM notes")
    void deleteAllNotes();
}
