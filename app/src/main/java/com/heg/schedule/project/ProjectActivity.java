package com.heg.schedule.project;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.heg.database.beans.ProjectBean;
import com.heg.schedule.base.BaseActivity;
import com.heg.schedule.R;
import com.heg.schedule.base.listener.OnItemClickListener;
import com.heg.schedule.db.DBHelper;
import com.heg.schedule.widget.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ProjectActivity extends BaseActivity implements OnItemClickListener<ProjectBean> {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.tvAllMoney)
    TextView mTvAllMoney;
    private ProjectAdapter mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_project;
    }

    @Override
    public void init() {
        mAdapter = new ProjectAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        new ProjectTask().execute();
    }

    @OnClick(R.id.fab)
    public void onClick() {
        startActivity(new Intent(ProjectActivity.this, ProjectEditActivity.class));
    }

    @Override
    public void onItemClick(View v, ProjectBean obj) {
        Intent intent = new Intent(ProjectActivity.this, ProjectEditActivity.class);
        intent.putExtra(ProjectEditActivity.PARAM_PROJECT, obj);
        startActivity(intent);
    }

    class ProjectTask extends AsyncTask<Void, Void, List<ProjectBean>> {

        @Override
        protected List<ProjectBean> doInBackground(Void... params) {
            return DBHelper.loadProjects();
        }

        @Override
        protected void onPostExecute(List<ProjectBean> pPlatformBeen) {
            super.onPostExecute(pPlatformBeen);
            mAdapter.setData(pPlatformBeen);

            double all = 0;
            for (ProjectBean bean : pPlatformBeen) {
                all += bean.getPrincipal();
            }
            mTvAllMoney.setText(all + "");
        }
    }

}
