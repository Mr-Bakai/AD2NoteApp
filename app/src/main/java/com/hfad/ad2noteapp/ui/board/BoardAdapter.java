package com.hfad.ad2noteapp.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.hfad.ad2noteapp.OnItemClickListener;
import com.hfad.ad2noteapp.R;
import com.hfad.ad2noteapp.models.BoardData;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private String[] titles =  new String[]{"Fast", "Free", "Powerful"};

    private ArrayList<BoardData> list = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public BoardAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.pager_board, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addDataToBoard(ArrayList<BoardData> boardData) {
        this.list = boardData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textTitle;
        private TextView textDesc;
        private Button btnStart;
        private LottieAnimationView lt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
            imageView = itemView.findViewById(R.id.imageView);

            lt = itemView.findViewById(R.id.animationView);

            btnStart = itemView.findViewById(R.id.btnStart);
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(getAdapterPosition());
                }
            });
        }

        public void bind(int position) {
            btnStart.setVisibility(View.GONE);
            show(position);
        }

        private void show(int position) {
            if (position == 2){
                btnStart.setVisibility(View.VISIBLE);
            }

            BoardData bd =  list.get(position);
            //imageView.setImageResource(bd.getImageResourceId());
            textTitle.setText(bd.getName());
            textDesc.setText(bd.getDesc());
            lt.setAnimation(bd.getImageResourceId());
        }
    }
}