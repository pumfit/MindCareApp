package com.teamopendata.mindcareapp.ui.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.R;

import java.util.ArrayList;

public class MapListAdapter extends RecyclerView.Adapter<MapListAdapter.ViewHolder> {

    private ArrayList<String> arrayList;
    private boolean[] bookmarkStatus;

    public MapListAdapter(ArrayList<String> list) { arrayList = list; bookmarkStatus = new boolean[list.size()];};

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.item_map_list,parent,false);
        MapListAdapter.ViewHolder viewHolder = new MapListAdapter.ViewHolder(view);

        return viewHolder;
    }

    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String text = arrayList.get(position);
        holder.textView1.setText(text);

        holder.bookmarkBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(bookmarkStatus[position] == false){
                    buttonClicked(holder.bookmarkBtn);
                    Toast.makeText(v.getContext(), "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    bookmarkStatus[position] = true;
                }
                else if(bookmarkStatus[position] == true){
                    buttonDefault(holder.bookmarkBtn);
                    bookmarkStatus[position] = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayData(String strData) { arrayList.add(strData); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView1;
        public TextView textView2;
        public ImageButton bookmarkBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textTitle);
            textView2 = itemView.findViewById(R.id.textType);
            bookmarkBtn = itemView.findViewById(R.id.imageButton);
        }

    }

    private void buttonClicked(android.widget.ImageButton button ) {
        //button.setBackgroundResource(R.drawable.buttonselected);
        //button.setTextColor(getResources().getColor(R.color.white, getTheme()));
        button.setImageDrawable(button.getResources().getDrawable(R.drawable.starfilled));
    }

    //!--버튼 다시 누를 때 메소드
    private  void buttonDefault(android.widget.ImageButton button){
        //button.setBackgroundResource(R.drawable.button);
        button.setImageDrawable(button.getResources().getDrawable(R.drawable.star));
    }

}
