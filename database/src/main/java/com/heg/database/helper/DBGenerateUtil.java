package com.heg.database.helper;


import com.heg.database.BuildConfig;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by OnePiece on 16/3/18.
 * implements greendao api  use xml to generate tables.
 *
 */
public class DBGenerateUtil {

    /**
     * Xml tags
     */
    private static final String TAG_DATABASE = "Database";
    private static final String TAG_TABLE = "Table";
    private static final String TAG_ID = "id";
    private static final String TAG_COLUMN = "column";
    private static final String TAG_VERSION = "version";

    /**
     * tags params
     */
    private static final String PARAM_GENERATE_ABLE = "generateAble";
    private static final String PARAM_TABLE_NAME = "tableName";
    private static final String PARAM_COLOUMN_TYPE = "type";
    private static final String PARAM_COLOUMN_NULL_ABLE = "nullAble";
    private static final String PARAM_AUTO_INCREMENT = "autoIncrement";

    /**
     * column type
     */
    private static final String COLOUMN_TYPE_STRING = "string";
    private static final String COLOUMN_TYPE_INT = "int";
    private static final String COLOUMN_TYPE_LONG = "long";
    private static final String COLOUMN_TYPE_BOOL = "boolean";
    private static final String COLOUMN_TYPE_DOUBLE = "double";
    private static final String COLOUMN_TYPE_DATE = "date";


    private static final String OUT_DIR = "database/src/main/java/";// generate file out put dir
    private static final String PACKAGE = ".beans";// the package name of generate file

    public static void main(String[] args) throws Exception {


        generateTables(new FileInputStream(new File("database/src/main/res/xml/tables.xml")));

    }

    /**
     * clean generate file
     */
    private static void deleteGenerateFile() {
        File file = new File((OUT_DIR + BuildConfig.APPLICATION_ID + PACKAGE).replace(".", "/"));
        if (file.exists())
            deleteDirectory(file);
    }

    /**
     * delete directory
     *
     * @param pFile the directory file
     * @return
     */
    private static boolean deleteDirectory(File pFile) {
        if (pFile.exists()) {
            File[] files = pFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (pFile.delete());
    }

    /**
     * use xml to generate TableDao and TableBean
     * @param pInStream tables xml config file input stream
     */

    public static void generateTables(InputStream pInStream) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();
            XMLContentHandler handler = new XMLContentHandler();
            saxParser.parse(pInStream, handler);
            pInStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * the xml parse control class
     */
    public static class XMLContentHandler extends DefaultHandler {

        private String tagName = null;// current tag
        private Entity entity; // current table entity
        private Schema schema; // database schema
        private boolean nullAble; // is column  nullAble
        private String type = ""; // column type

        XMLContentHandler() {
            schema = new Schema(BuildConfig.DATABASE_VERSION, BuildConfig.APPLICATION_ID + PACKAGE);

        }

        @Override
        public void startDocument() throws SAXException {

        }

        @Override
        public void startElement(String pNamespaceURI, String pLocalName, String pName, Attributes pAtts) throws SAXException {

            if (pName.equals(TAG_TABLE)) { // if tag is table then create a new table
                if ("false".equals(pAtts.getValue(PARAM_GENERATE_ABLE))) // if need not to genreate this table then skip
                    endElement(pNamespaceURI, pLocalName, pName);
                else
                    entity = schema.addEntity(pAtts.getValue(PARAM_TABLE_NAME));
            } else if (pName.equals(TAG_COLUMN)) { // if tag is column then add a new column
                type = pAtts.getValue(PARAM_COLOUMN_TYPE);
                nullAble = !"false".equals(pAtts.getValue(PARAM_COLOUMN_NULL_ABLE));
            } else if (pName.equals(TAG_ID)) { // if tag is id then add id to table
                if (entity != null)// if current table is null skip this table
                    if ("true".equals(pAtts.getValue(PARAM_AUTO_INCREMENT))) // if id is auto increment then set it
                        entity.addIdProperty().autoincrement();
                    else
                        entity.addIdProperty();
            }

            this.tagName = pName; // save tag to var
        }

        /**
         * read coloum name and add new coloumn to current table
         */
        @Override
        public void characters(char[] pCh, int pStart, int pLength) throws SAXException {

            /**
             * to deal different type of column type
             */
            if (tagName != null) {
                String content = new String(pCh, pStart, pLength);
                if (tagName.equals(TAG_COLUMN) && entity != null) {
                    if (type.equals(COLOUMN_TYPE_STRING)) {
                        if (nullAble)
                            entity.addStringProperty(content);
                        else
                            entity.addStringProperty(content).notNull();
                    } else if (type.equals(COLOUMN_TYPE_INT)) {
                        if (nullAble)
                            entity.addIntProperty(content);
                        else
                            entity.addIntProperty(content).notNull();
                    } else if (type.equals(COLOUMN_TYPE_LONG)) {
                        if (nullAble)
                            entity.addLongProperty(content);
                        else
                            entity.addLongProperty(content).notNull();
                    } else if (type.equals(COLOUMN_TYPE_DOUBLE)) {
                        if (nullAble)
                            entity.addDoubleProperty(content);
                        else
                            entity.addDoubleProperty(content).notNull();
                    } else if (type.equals(COLOUMN_TYPE_DATE)) {
                        if (nullAble)
                            entity.addDateProperty(content);
                        else
                            entity.addDateProperty(content).notNull();
                    } else if (type.equals(COLOUMN_TYPE_BOOL)) {
                        if (nullAble)
                            entity.addBooleanProperty(content);
                        else
                            entity.addBooleanProperty(content).notNull();
                    } else {//if column type of xml is not illegal then throw exception
                        throw new IllegalArgumentException("coloumn type illegal!\ntag:" + tagName + " type:" + type);
                    }
                }
            }
        }

        @Override
        public void endElement(String pUri, String pLocalName, String pName) throws SAXException {

            if (pName.equals(TAG_TABLE)) {// if end tag is table,then the all column of this table have added
                this.entity = null;
            } else if (pName.equals(TAG_DATABASE)) {// if end tag is database,then all tables of xml have generated
                try {
                    deleteGenerateFile();// if has not exception then delete files of before generated.
                    new DaoGenerator().generateAll(schema, OUT_DIR);// to generate beans and dao class to outDir
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (pName.equals(TAG_COLUMN)) { // if end tag is column then finish this column
                type = "";
            }
            this.tagName = null;

        }
    }

}
