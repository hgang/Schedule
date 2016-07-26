package com.heg.schedule.db;

import com.heg.database.beans.PlatformBean;
import com.heg.database.beans.PlatformBeanDao;
import com.heg.database.beans.ProjectBean;
import com.heg.database.beans.ProjectBeanDao;
import com.heg.database.helper.DBOpenHelper;
import com.heg.schedule.App;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by hegang on 16/7/14.
 */
public class DBHelper {

    public static List<String> getPlatForms() {
        PlatformBeanDao dao = DBOpenHelper.getDaoSession(App.getApp()).getPlatformBeanDao();
        List<PlatformBean> list = dao.loadAll();
        List<String> ret = new ArrayList<>();
        if (list != null) {
            for (PlatformBean bean : list) {
                ret.add(bean.getName());
            }
        }
        return ret;
    }

    public static List<ProjectBean> queryAfter(long query) {
        Date date = new Date(query);
        ProjectBeanDao dao = DBOpenHelper.getDaoSession(App.getApp()).getProjectBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        qb.whereOr(ProjectBeanDao.Properties.End_time.lt(date), ProjectBeanDao.Properties.End_time.isNull());
        return qb.list();
    }

    public static List<ProjectBean> loadProjects() {
        ProjectBeanDao dao = DBOpenHelper.getDaoSession(App.getApp()).getProjectBeanDao();
        QueryBuilder qb = dao.queryBuilder();
        return qb.orderAsc(ProjectBeanDao.Properties.End_time).list();
    }
}
