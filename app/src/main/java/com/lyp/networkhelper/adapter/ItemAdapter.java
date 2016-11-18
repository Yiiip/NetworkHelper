package com.lyp.networkhelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyp.networkhelper.R;
import com.lyp.networkhelper.bean.ItemBean;

import java.util.List;

/**
 * Created by lyp on 2016/9/23.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ItemBean> mData;

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public ItemAdapter(Context context, List<ItemBean> data) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ItemBean bean = mData.get(position);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView, position); //如果用holder.getAdapterPosition()则在装饰后计算含有头部时的位置
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, holder.getAdapterPosition());
                    return false;
                }
            });
        }

        holder.name.setText(bean.getName());
        holder.content.setText(bean.getContent());
        holder.time.setText(bean.getTime());
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, time, content;

        public ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.itemTime);
            content = (TextView) itemView.findViewById(R.id.itemContent);
            name = (TextView) itemView.findViewById(R.id.itemName);
        }
    }
}
