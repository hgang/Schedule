package com.heg.widget.adpter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Adapter基类
 *
 * @author by hegang
 * @version 1.0 ,2016/4/7 13:24
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    private List<T> mDatas;

    public void refresh(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    protected abstract View inflateItemView();

    protected abstract ViewHolder newHolderInstance();

    public List<T> getDatas() {
        return mDatas;
    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return null == mDatas ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return 0 == getCount() ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflateItemView();
            holder = newHolderInstance();
            holder.initView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bind(getItem(position), position);
        return convertView;
    }

    public static abstract class ViewHolder<T> {

        public abstract void initView(View convertView);

        public abstract void bind(T item, int position);
    }
}
