package com.lwj.cgp.buyer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lwj.cgp.data.ChatData;
import com.lwj.cgp.R;

import java.util.List;

public class ChatRvAdapter extends RecyclerView.Adapter<ChatRvAdapter.MyHolder> {

    private List<ChatData> mList;
    private OnItemClickListener mOnItemClickListener;

    public ChatRvAdapter(List<ChatData> list) {
        mList = list;
    }

    public void setList(List<ChatData> mList) {
        this.mList = mList;
    }

    public interface OnItemClickListener{
        public void onItemClick(ChatData data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ChatData data = mList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(data);
                }
            }
        });
        Glide.with(holder.photo.getContext()).load(mList.get(position).imgUrl).placeholder(R.drawable.img_default).into(holder.photo);
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

        TextView name;
        ImageView photo;
        TextView msg;

        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);
            photo = itemView.findViewById(R.id.photo);
            msg = itemView.findViewById(R.id.img);

        }
    }
}