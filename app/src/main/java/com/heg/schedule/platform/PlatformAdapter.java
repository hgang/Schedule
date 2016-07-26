package com.heg.schedule.platform;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heg.database.beans.PlatformBean;
import com.heg.schedule.R;

import java.util.List;

/**
 * Created by hegang on 16/7/12.
 */
public class PlatformAdapter extends RecyclerView.Adapter<PlatformAdapter.ViewHolder> {

    private Context mContext;

    private List<PlatformBean> mData;

    public PlatformAdapter(Context pContext) {
        mContext = pContext;
    }

    public void setData(List<PlatformBean> pData) {
        mData = pData;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_platform, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvName.setText(getItem(position).getName());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public PlatformBean getItem(int position) {
        if (getItemCount() <= 0) {
            return null;
        } else {
            return mData.get(position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvName;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.item_platform_name);
        }
    }
}
