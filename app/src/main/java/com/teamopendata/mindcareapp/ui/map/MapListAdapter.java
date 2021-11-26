package com.teamopendata.mindcareapp.ui.map;

import android.annotation.SuppressLint;
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

import java.util.List;

public class MapListAdapter extends RecyclerView.Adapter<MapListAdapter.ViewHolder> {

    private List<MedicalInstitution> medicalList;
    private boolean[] bookmarkStatus;
    private Context context;

    public MapListAdapter(List<MedicalInstitution> list) { medicalList = list; bookmarkStatus = new boolean[list.size()];};

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.item_map_list,parent,false);
        MapListAdapter.ViewHolder viewHolder = new MapListAdapter.ViewHolder(view);

        return viewHolder;
    }

    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String name = medicalList.get(position).name;
        String type = medicalList.get(position).type;
        String address = medicalList.get(position).address;

        holder.tvName.setText(name);
        holder.tvType.setText(type);
        holder.tvAddress.setText(address);

        holder.btnBookmark.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(bookmarkStatus[position] == false){
                    buttonClicked(holder.btnBookmark);
                    Toast.makeText(v.getContext(), "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    bookmarkStatus[position] = true;
                }
                else if(bookmarkStatus[position] == true){
                    buttonDefault(holder.btnBookmark);
                    Toast.makeText(v.getContext(), "즐겨찾기가 취소되었습니다.", Toast.LENGTH_SHORT).show();
                    bookmarkStatus[position] = false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicalList.size();
    }

    public void setArrayData(MedicalInstitution mediData) { medicalList.add(mediData); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvType;
        public TextView tvAddress;
        public ImageButton btnBookmark;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_map_name);
            tvType = itemView.findViewById(R.id.tv_map_type);
            tvAddress = itemView.findViewById(R.id.tv_map_address);
            btnBookmark = itemView.findViewById(R.id.ib_map_bookmark_star);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                MapListSubInfoDialog subInfoDialog = new MapListSubInfoDialog(context,medicalList.get(pos));
                subInfoDialog.callFunction();
            });
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
