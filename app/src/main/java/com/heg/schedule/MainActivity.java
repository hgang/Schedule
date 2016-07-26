package com.heg.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.heg.database.beans.ProjectBean;
import com.heg.schedule.db.DBHelper;
import com.heg.schedule.platform.PlatformActivity;
import com.heg.schedule.project.ProjectActivity;
import com.heg.schedule.setting.SettingsActivity;
import com.heg.baselib.utils.DateUtil;
import com.heg.baselib.utils.LogCat;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
    }

    private EditText etQueryTime;
    private Button btnQuery;
    private TextView tvAvailableMoney;
    private TextView tvAllAvailableMoney;

    private void init() {

        etQueryTime = (EditText) findViewById(R.id.etQueryTime);
        btnQuery = (Button) findViewById(R.id.btn_query);
        tvAvailableMoney = (TextView) findViewById(R.id.tvAvailableMoney);
        tvAllAvailableMoney = (TextView) findViewById(R.id.tvAllAvailableMoney);
        btnQuery.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_projects) {
            // Handle the camera action
            goTo(ProjectActivity.class);
        } else if (id == R.id.nav_platform) {
            goTo(PlatformActivity.class);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_settings) {
            goTo(SettingsActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goTo(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    @Override
    public void onClick(View v) {
        if (v == btnQuery) {
            query();
        }
    }

    private void query() {
        if (TextUtils.isEmpty(etQueryTime.getText()))
            return;
        long offset = 24 * 60 * 60 * 1000;
        long query = DateUtil.formatDateTime(etQueryTime.getText().toString(), DateUtil.DF_YYYY_MM_DD).getTime();
        query = query - offset;
        List<ProjectBean> list = DBHelper.queryAfter(query);
        double amount = 0, interst = 0;
        if (list != null) {
            for (ProjectBean bean : list) {
                LogCat.d(bean.toString());
                amount += bean.getPrincipal();
                interst += getInterst(query, bean);
            }
        }

        tvAvailableMoney.setText(amount + "");
        tvAllAvailableMoney.setText((amount + interst) + "");
    }

    private static final long day = 24 * 60 * 60 * 1000;

    private double getInterst(long time, ProjectBean bean) {
        double ret = 0;
        long days = 0;
        if (bean.getEnd_time() == null) {
            days = (time - bean.getStart_time().getTime()) / day;
        } else {
            days = (bean.getEnd_time().getTime() - bean.getStart_time().getTime()) / day;
        }
        return getProfit(bean.getPrincipal(), days, bean.getInterest_rate());
    }

    private double getProfit(double principal, long days, double rate) {
        return principal * rate * days / 365.0d;
    }
}
