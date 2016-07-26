package com.heg.database.beans;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.heg.database.beans.PlatformBean;
import com.heg.database.beans.ProjectBean;

import com.heg.database.beans.PlatformBeanDao;
import com.heg.database.beans.ProjectBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig platformBeanDaoConfig;
    private final DaoConfig projectBeanDaoConfig;

    private final PlatformBeanDao platformBeanDao;
    private final ProjectBeanDao projectBeanDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        platformBeanDaoConfig = daoConfigMap.get(PlatformBeanDao.class).clone();
        platformBeanDaoConfig.initIdentityScope(type);

        projectBeanDaoConfig = daoConfigMap.get(ProjectBeanDao.class).clone();
        projectBeanDaoConfig.initIdentityScope(type);

        platformBeanDao = new PlatformBeanDao(platformBeanDaoConfig, this);
        projectBeanDao = new ProjectBeanDao(projectBeanDaoConfig, this);

        registerDao(PlatformBean.class, platformBeanDao);
        registerDao(ProjectBean.class, projectBeanDao);
    }
    
    public void clear() {
        platformBeanDaoConfig.getIdentityScope().clear();
        projectBeanDaoConfig.getIdentityScope().clear();
    }

    public PlatformBeanDao getPlatformBeanDao() {
        return platformBeanDao;
    }

    public ProjectBeanDao getProjectBeanDao() {
        return projectBeanDao;
    }

}
