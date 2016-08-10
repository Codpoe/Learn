package com.app.learn.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.learn.R;

import java.util.List;

/**
 * Created by Codpoe on 2016/3/31.
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    /**
     * 定义接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    /**
     * 使用接口
     */
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 数据
     */
    public List<String> datas;
    public RvAdapter(List<String> datas) {
        this.datas = datas;
    }

    /**
     * 创建新 View
     * @param parent
     * @param viewType
     * @return ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.rv_item_view_layout, parent, false));
        return viewHolder;
    }

    /**
     * 将数据与界面进行绑定
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_1.setText(datas.get(position));

        //如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return true;
                }
            });
        }
    }

    /**
     * 获取数据的数量
     * @return
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 自定义 ViewHolder, 持有每个 Item 所有的界面元素
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_1;
        TextView tv_2;

        public ViewHolder(View view) {
            super(view);
            tv_1 = (TextView) view.findViewById(R.id.rv_item_tv_1);
            tv_2 = (TextView) view.findViewById(R.id.rv_item_tv_2);
        }
    }

    /**
     * 增加数据
     * @param position
     * @param mString
     */
    public void addItem(int position, String mString) {
        datas.add(position, mString);
        notifyItemInserted(position);
    }

    /**
     * 删除数据
     * @param position
     */
    public void removeItem(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }
}
