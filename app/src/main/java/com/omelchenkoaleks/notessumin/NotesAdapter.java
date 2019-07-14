package com.omelchenkoaleks.notessumin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter
        extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private ArrayList<Note> mNotes;
    private OnNoteClickListener mOnNoteClickListener;

    public NotesAdapter(ArrayList<Note> notes) {
        mNotes = notes;
    }

    /*
        у RecyclerView нет метода наподобие onItemClickListener,
        поэтому его нужно создать самостоятельно, чтобы была возможность
        обрабатывать клики пользователя

        для этого в адаптере:
         1, создается интерфейс
         2, создается объект этого слушателя (интерфейса)
         3, и добавляется на него сетер

        что дальше и как это работает?
            1, когда мы кликаем на какую-то заметку то этой заметкой является
            по сути itemView, который передается в конструкторе ViewHolder
            2, у любого itemView можно вызвать метод setOnClickListener
            3, в методе onClick можно проверить существует ли объект
            нашего созданного слушателя и вызвать его метод (куда в качестве
            аргумента передается ...)
            4, в MainActivity нужно установить наш слушатель
     */
    interface OnNoteClickListener {
        void onNoteClick(int position);
        void onLongClick(int position); // для удаления при долгом нажатии
    }

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        mOnNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = mNotes.get(position);
        holder.mTitleTextView.setText(note.getTitle());
        holder.mDescriptionTextView.setText(note.getDescription());
        holder.mDayOfWeekTextView.setText(Note.getDayAsString(note.getDayOfWeek()));

        /*
            получаем приоритет (priority) из заметки (объекта) и уже в зависимости от
            приоритета будем устанавливать цвет
         */
        int colorId;
        int priority = note.getPriority();
        switch (priority) {
            case 1:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_red_light);
                break;
            case 2:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_orange_dark);
                break;
            default:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_green_light);
                break;
        }
        holder.mTitleTextView.setBackgroundColor(colorId);


    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private TextView mDayOfWeekTextView;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.title_text_view);
            mDescriptionTextView = itemView.findViewById(R.id.description_text_view);
            mDayOfWeekTextView = itemView.findViewById(R.id.day_of_week_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnNoteClickListener != null) {
                        mOnNoteClickListener.onNoteClick(getAdapterPosition());
                    }
                }
            });

            // добавляем слушателя на долгое нажатие для удаления
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnNoteClickListener != null) {
                        mOnNoteClickListener.onLongClick(getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }
}
