package com.heg.schedule.project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heg.database.beans.ProjectBean;
import com.heg.schedule.R;
import com.heg.schedule.base.listener.OnItemClickListener;
import com.heg.baselib.utils.DateUtil;

import java.util.List;

/**
 * Created by hegang on 16/7/12.
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private Context mContext;

    private List<ProjectBean> mData;

    private OnItemClickListener<ProjectBean> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<ProjectBean> pOnItemClickListener) {
        mOnItemClickListener = pOnItemClickListener;
    }

    public ProjectAdapter(Context pContext) {
        mContext = pContext;
    }

    public void setData(List<ProjectBean> pData) {
        mData = pData;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_project, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProjectBean bean = getItem(position);
        holder.mTvPlatformName.setText(bean.getPlatform_name());
        holder.mTvProjectName.setText(bean.getName());
        holder.mTvProjectPrincipal.setText(bean.getPrincipal() + "");
        holder.mTvProjectEndTime.setText(DateUtil.dateToString(bean.getEnd_time(), DateUtil.DF_YYYY_MM_DD));
        holder.mTvProjectName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public ProjectBean getItem(int position) {
        if (getItemCount() <= 0) {
            return null;
        } else {
            return mData.get(position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTvPlatformName, mTvProjectName, mTvProjectPrincipal, mTvProjectEndTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvPlatformName = (TextView) itemView.findViewById(R.id.item_platform_name);
            mTvProjectName = (TextView) itemView.findViewById(R.id.item_project_name);
            mTvProjectPrincipal = (TextView) itemView.findViewById(R.id.item_project_principal);
            mTvProjectEndTime = (TextView) itemView.findViewById(R.id.item_project_end_time);
        }
    }
}
