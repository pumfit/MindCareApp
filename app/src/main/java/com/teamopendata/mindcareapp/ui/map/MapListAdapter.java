package com.teamopendata.mindcareapp.ui.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.teamopendata.mindcareapp.MindChargeDB;
import com.teamopendata.mindcareapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapListAdapter extends RecyclerView.Adapter<MapListAdapter.ViewHolder> {
    public static Map<String, Integer> colormap = new HashMap() {
        {
            put("광역형정신건강증진센터", Integer.valueOf(R.drawable.ic_icon_location_fill_red));
            put("기초정신건강증진센터", Integer.valueOf(R.drawable.ic_icon_location_fill_orange));
            put("자살예방센터", Integer.valueOf(R.drawable.ic_icon_location_fill_yellow));
            put("중독관리통합지원센터", Integer.valueOf(R.drawable.ic_icon_location_fill_green));
            put("사회복귀시설", Integer.valueOf(R.drawable.ic_icon_location_fill_blue));
            put("정신의료기관", Integer.valueOf(R.drawable.ic_icon_location_fill_navy));
            put("정신요양시설", Integer.valueOf(R.drawable.ic_icon_location_fill_purple));
        }
    };

    public List<MedicalInstitution> bookmarkList;
    public boolean[] bookmarkStatus;
    public Context context;
    public List<MedicalInstitution> medicalList;

    public MapListAdapter(List<MedicalInstitution> list) {
        this.medicalList = list;
        this.bookmarkStatus = new boolean[list.size()];
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        new bookmarkThread().start();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.item_map_list,parent,false);
        MapListAdapter.ViewHolder viewHolder = new MapListAdapter.ViewHolder(view);

        return viewHolder;
    }


    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String name = this.medicalList.get(position).name;
        String type = this.medicalList.get(position).type;
        String address = this.medicalList.get(position).address;

        holder.tvName.setText(name);
        holder.tvType.setText(type);
        holder.tvAddress.setText(address);
        holder.ivIcon.setImageResource(colormap.get(this.medicalList.get(position).type).intValue());
        if (this.bookmarkStatus[position]) {
            buttonClicked(holder.btnBookmark);
        }
        holder.btnBookmark.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View v) {
                if (!MapListAdapter.this.bookmarkStatus[position]) {
                    MapListAdapter.this.buttonClicked(holder.btnBookmark);
                    MapListAdapter mapListAdapter = MapListAdapter.this;
                    new bookmarkInsertThread((MedicalInstitution) mapListAdapter.medicalList.get(position)).start();
                    Toast.makeText(v.getContext(), "즐겨찾기에 추가되었습니다.",1).show();
                    MapListAdapter.this.bookmarkStatus[position] = true;
                } else if (MapListAdapter.this.bookmarkStatus[position]) {
                    MapListAdapter.this.buttonDefault(holder.btnBookmark);
                    MapListAdapter mapListAdapter2 = MapListAdapter.this;
                    new bookmarkDeleteThread((MedicalInstitution) mapListAdapter2.medicalList.get(position)).start();
                    Toast.makeText(v.getContext(), "즐겨찾기가 취소되었습니다.", 1).show();
                    MapListAdapter.this.bookmarkStatus[position] = false;
                }
            }
        });
    }

    public int getItemCount() {
        return this.medicalList.size();
    }

    /* renamed from: com.teamopendata.mindcareapp.ui.map.MapListAdapter$ViewHolder */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton btnBookmark;
        public ImageView ivIcon;
        public TextView tvAddress;
        public TextView tvName;
        public TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvName = (TextView) itemView.findViewById(R.id.tv_map_name);
            this.tvType = (TextView) itemView.findViewById(R.id.tv_map_type);
            this.tvAddress = (TextView) itemView.findViewById(R.id.tv_map_address);
            this.btnBookmark = (ImageButton) itemView.findViewById(R.id.ib_map_bookmark_star);
            this.ivIcon = (ImageView) itemView.findViewById(R.id.iv_map_marker);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                MapListSubInfoDialog subInfoDialog = new MapListSubInfoDialog(context,medicalList.get(pos),bookmarkStatus[pos]);
                subInfoDialog.callFunction();
            });
        }
    }

    public void checkBookmarkList() {
        for (int i = 0; i < this.medicalList.size(); i++) {
            for (int j = 0; j < this.bookmarkList.size(); j++) {
                if (this.medicalList.get(i).id == this.bookmarkList.get(j).id) {
                    this.bookmarkStatus[i] = true;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void buttonClicked(ImageButton button) {
        button.setImageDrawable(button.getResources().getDrawable(R.drawable.starfilled));
    }

    /* access modifiers changed from: private */
    public void buttonDefault(ImageButton button) {
        button.setImageDrawable(button.getResources().getDrawable(R.drawable.star));
    }

    /* renamed from: com.teamopendata.mindcareapp.ui.map.MapListAdapter$bookmarkInsertThread */
    class bookmarkInsertThread extends Thread {
        private MedicalInstitution medi;

        bookmarkInsertThread(MedicalInstitution medi2) {
            this.medi = medi2;
        }

        public void run() {
            MindChargeDB.getInstance(MapListAdapter.this.context).getBookMarkDao().insert(new BookMark(this.medi.id));
        }
    }

    class bookmarkDeleteThread extends Thread {
        private MedicalInstitution medi;

        bookmarkDeleteThread(MedicalInstitution medi2) {
            this.medi = medi2;
        }

        public void run() {
            MindChargeDB.getInstance(MapListAdapter.this.context).getBookMarkDao().deleteById(this.medi.id);
        }
    }

    class bookmarkThread extends Thread {
        bookmarkThread() {
        }

        public void run() {
            MapListAdapter.this.bookmarkList = MindChargeDB.getInstance(MapListAdapter.this.context).getBookMarkDao().getBookmarkList();
            MapListAdapter.this.checkBookmarkList();
        }
    }
}
