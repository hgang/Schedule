package com.heg.widget.adpter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by hegang on 16/7/2.
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter {
    protected List<T> mDate;

    public void setDate(List<T> pDate) {
        mDate = pDate;
        notifyDataSetChanged();
    }

    public T getItem(int positon) {
        if (mDate == null) {
            return null;
        }
        if (positon < 0 || positon >= mDate.size())
            return null;
        return mDate.get(positon);
    }

    @Override
    public int getItemCount() {
        return mDate == null ? 0 : mDate.size();
    }
}
