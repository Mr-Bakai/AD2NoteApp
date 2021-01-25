package com.hfad.ad2noteapp.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.ad2noteapp.App;
import com.hfad.ad2noteapp.OnItemClickListener;
import com.hfad.ad2noteapp.R;
import com.hfad.ad2noteapp.models.Note;
import com.hfad.ad2noteapp.ui.board.BoardAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private ArrayList<Note> list;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public NoteAdapter(Context context) {
        list = new ArrayList<>();
        this.context = context;
    }

    public ArrayList<Note> getList() {
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Note note) {
        list.add(0,note);
        Log.e("qweqwe", "addItem: " + note.getTitle() );
        notifyItemInserted(list.indexOf(0));

        //notifyItemInserted(list.size() - 1); // here you will get the last position
        //notifyItemInserted(list.indexOf(note)); these are the same
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Note getItem(int position) {
     return list.get(position);
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void setList(List<Note> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setItem(Note note, int position) {
        this.list.set(position, note);
        Log.e("ololo", "setItem: " + note.getTitle() );
        notifyItemInserted(position);
    }

    //=====================================ViewHolder========================================
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textTitle;
        private TextView textDAte;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.longClick(getAdapterPosition());
                    return true;
                }
            });

            textTitle = itemView.findViewById(R.id.textTitle);
            textDAte = itemView.findViewById(R.id.textDate);

        }
        public void bind(Note note) {
            textTitle.setText(note.getTitle());
            textDAte.setText(note.getDate());
        }
    }
}