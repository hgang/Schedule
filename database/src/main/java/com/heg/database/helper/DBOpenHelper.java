package com.heg.database.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.heg.database.BuildConfig;
import com.heg.database.beans.DaoMaster;
import com.heg.database.beans.DaoSession;
import com.heg.database.beans.ProjectBeanDao;

/**
 * Created by YangChao on 16/3/18.
 * DBOpenHelper used to control database interface.
 */

public class DBOpenHelper extends DaoMaster.OpenHelper {


    /**
     * this enum is used to define coloumn type in database
     */
    enum DataBaseColoumnType {
        COLUMN_TYPE_INT,
        COLUMN_TYPE_TEXT,
        COLUMN_TYPE_BOOLEAN,
        COLUMN_TYPE_FLOAT,
        COLUMN_TYPE_DATE

    }


    private DaoSession mDaoSession;
    private static DBOpenHelper instances;
    private DaoMaster mDaoMaster;
    private static SQLiteDatabase db;

    private DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }


    /**
     * this is single tonge used to get instance of dataBaseHelper
     *
     * @param context applicationContext
     * @return DBOpenHelper
     */
    public static DBOpenHelper getInstances(Context context) {
        init(context);
        return instances;
    }

    public static DaoSession getDaoSession(Context context){
        return getInstances(context).getSession();
    }

    public static void init(Context context) {
        if (instances == null)
            instances = new DBOpenHelper(context.getApplicationContext(), BuildConfig.DATABASE_NAME, null);
        if (db == null || !db.isOpen())
            db = instances.getWritableDatabase();
    }

    /**
     * get the operate master
     * @return operate master
     */
    private DaoMaster getMaster() {
        if (mDaoMaster == null)
            mDaoMaster = new DaoMaster(db);
        return mDaoMaster;
    }

    /**
     * get the operate session
     * @return operate session
     */
    public DaoSession getSession() {
        if (mDaoSession == null)
            mDaoSession = getMaster().newSession();
        return mDaoSession;
    }

    /**
     * database upgrade
     * @param db database
     * @param oldVersion database version of before
     * @param newVersion database version of upgrade
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*//sample  add coloumn
        if (!isColumnExist(db, EventDao.TABLENAME, EventDao.Properties.Version.columnName))
            addColumn(db, EventDao.TABLENAME, EventDao.Properties.Version.columnName, DataBaseColoumnType.COLUMN_TYPE_TEXT);
        //sample add table
        EventDao.createTable(db, true);
*/

        ProjectBeanDao.dropTable(db,true);
        ProjectBeanDao.createTable(db,true);
    }

    /**
     * when add column use this to judgement column is exist
     * @param pDb databse
     * @param pTableName table name
     * @param pColumnName column name
     * @return
     */
    private boolean isColumnExist(SQLiteDatabase pDb, String pTableName,
                                  String pColumnName) {
        boolean result = false;
        if (pTableName == null) {
            return false;
        }

        try {
            Cursor cursor;
            String sql = "select count(1) as c from sqlite_master where type ='table' and name ='"
                    + pTableName.trim()
                    + "' and sql like '%"
                    + pColumnName.trim() + "%'";
            cursor = pDb.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * add a new column
     * @param pDb database
     * @param pTableName table name
     * @param pColumn column name
     * @param pColumnType column type
     * @param pNullAble nullable
     */
    private void addColumn(SQLiteDatabase pDb, String pTableName, String pColumn, DataBaseColoumnType pColumnType, boolean pNullAble) {
        String nullAbleStr = pNullAble ? "" : " NOT NULL ";
        pDb.execSQL(" ALTER TABLE " + pTableName
                + " ADD Column " + pColumn
                + getColumnType(pColumnType) + nullAbleStr);
    }

    /**
     * add a new column
     * @param pDb database
     * @param pTableName table name
     * @param pColumn column name
     * @param pColumnType column type
     */
    private void addColumn(SQLiteDatabase pDb, String pTableName, String pColumn, DataBaseColoumnType pColumnType) {
        addColumn(pDb, pTableName, pColumn, pColumnType, true);
    }

    /**
     * transfer coloumn type on database
     * @param pColumnType input column type
     * @return databse column type
     */
    private String getColumnType(DataBaseColoumnType pColumnType) {
        switch (pColumnType) {
            case COLUMN_TYPE_INT:
            case COLUMN_TYPE_BOOLEAN:
            case COLUMN_TYPE_DATE:
                return " INTEGER ";
            case COLUMN_TYPE_TEXT:
                return " TEXT ";
            case COLUMN_TYPE_FLOAT:
                return " REAL ";
            default:
                return " TEXT ";
        }
    }


}
