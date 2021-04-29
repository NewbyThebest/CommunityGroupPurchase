package com.lwj.cgp.buyer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lwj.cgp.data.GoodsData;
import com.lwj.cgp.R;

import java.util.List;

public class GroupRvAdapter extends RecyclerView.Adapter<GroupRvAdapter.MyHolder> {

    private List<GoodsData> mList;
    private OnItemClickListener mOnItemClickListener;

    public GroupRvAdapter(List<GoodsData> list) {
        mList = list;
    }

    public void setList(List<GoodsData> mList) {
        this.mList = mList;
    }

    public interface OnItemClickListener{
        public void onItemClick(GoodsData data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group, parent, false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        GoodsData data = mList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(data);
                }
            }
        });
        Glide.with(holder.photo.getContext()).load(mList.get(position).imgUrl).placeholder(R.drawable.img_default).into(holder.photo);
        Glide.with(holder.img.getContext()).load(mList.get(position).imgUrl).placeholder(R.drawable.img_default).into(holder.img);
    }

    //获取数据源总的条数
    @Override
    public int getItemCount() {
        return mList.size();
    }


    /**
     * 自定义的ViewHolder
     */
    class MyHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView photo;
        ImageView img;
        TextView price;
        TextView peopleCount;
        TextView time;
        Button go;

        public MyHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            photo = itemView.findViewById(R.id.photo);
            img = itemView.findViewById(R.id.img);
            price = itemView.findViewById(R.id.price);
            peopleCount = itemView.findViewById(R.id.people_count);
            time = itemView.findViewById(R.id.time);
            go = itemView.findViewById(R.id.go_group);
        }
    }
}