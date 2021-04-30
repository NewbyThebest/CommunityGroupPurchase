package com.lwj.cgp.seller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lwj.cgp.R;
import com.lwj.cgp.data.GoodsData;

import java.util.List;

public class SellerHomeRvAdapter extends RecyclerView.Adapter<SellerHomeRvAdapter.MyHolder> {

    private List<GoodsData> mList;
    private OnItemClickListener mOnItemClickListener;

    public SellerHomeRvAdapter(List<GoodsData> list) {
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
                .inflate(R.layout.item_home, parent, false);
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
        holder.title.setText(data.title);
        holder.price.setText("￥"+data.price);
        holder.seller.setText(data.detail);
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
        ImageView img;
        TextView price;
        TextView seller;

        public MyHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);
            price = itemView.findViewById(R.id.price);
            seller = itemView.findViewById(R.id.seller);
        }
    }
}