package com.heg.schedule.platform;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.heg.database.beans.PlatformBean;
import com.heg.database.helper.DBOpenHelper;
import com.heg.schedule.base.BaseActivity;
import com.heg.schedule.widget.DividerItemDecoration;
import com.heg.schedule.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PlatformActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    private PlatformAdapter mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_platform;
    }

    @Override
    public void init() {
        mAdapter = new PlatformAdapter(this);
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
        new PlatformTask().execute();
    }

    @OnClick(R.id.fab)
    public void onClick() {
        startActivity(new Intent(PlatformActivity.this, PlatformEditActivity.class));
    }

    class PlatformTask extends AsyncTask<Void, Void, List<PlatformBean>> {

        @Override
        protected List<PlatformBean> doInBackground(Void... params) {
            return DBOpenHelper.getInstances(PlatformActivity.this).getSession().getPlatformBeanDao().loadAll();
        }

        @Override
        protected void onPostExecute(List<PlatformBean> pPlatformBeen) {
            super.onPostExecute(pPlatformBeen);
            mAdapter.setData(pPlatformBeen);
        }
    }

}
