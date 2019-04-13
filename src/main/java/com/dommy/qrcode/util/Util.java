package com.dommy.qrcode.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import com.dommy.qrcode.sql.SQLiteHelper;
import com.dommy.qrcode.sql.token;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;



public class Util {
    private static SQLiteHelper sqLiteHelper;
    public static void getToast(Context context,String text){
        Looper.prepare();
        android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    //length用户要求产生字符串的长度
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    /**
     * Insert sql lite.
     *
     * @param content 页面
     * @param str 插入内容
     * @param user    查找的用户
     */
    public static void insertSqlLite(Context content,String str,String user){
        sqLiteHelper = new SQLiteHelper(content,SQLiteHelper.TB_NAME,null,1);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.execSQL("insert into "+SQLiteHelper.TB_NAME+"("+token.USER+","+token.CONTENT+") values("
                +user+","+Util.sqliteEscape(str)+")");
        //insert（）方法中第一个参数是表名，第二个参数是表示给表中未指定数据的自动赋值为NULL。第三个参数是一个ContentValues对象
        db.close();
    }
    public static void alterTable(Context context){
        sqLiteHelper = new SQLiteHelper(context,SQLiteHelper.TB_NAME,null,1);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.execSQL("alter table "+SQLiteHelper.TB_NAME+" add column "+token.USER+" varchar");
    }
    /**
     * Find by id boolean.
     *
     * @param  context 页面
     * @param user 查找的用户
     * @return the boolean
     */
    public static boolean findById(Context context, String user){
        boolean f = false;
        sqLiteHelper = new SQLiteHelper(context,SQLiteHelper.TB_NAME,null,1);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+SQLiteHelper.TB_NAME+" where "+token.USER+"="+user, null);
        while (cursor.moveToNext()) {
            f=true;
            //String name= cursor.getString(1);//列序号从0开始，0，1,2,3....
        }
        cursor.close();
        db.close();
        return f;
    }
    public static String findByName(Context context,String user){
        sqLiteHelper = new SQLiteHelper(context,SQLiteHelper.TB_NAME,null,1);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select token from "+SQLiteHelper.TB_NAME+" where "+token.USER+"="+user, null);
        return cursor.getString(1);
    }
    /**
     * Update sql.
     *
     * @param context 页面
     * @param str token内容
     * @param user    匹配用户
     */
    public static void updateSQL(Context context,String str,String user){
        sqLiteHelper = new SQLiteHelper(context,SQLiteHelper.TB_NAME,null,1);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.execSQL("UPDATE "+SQLiteHelper.TB_NAME+" SET "+token.CONTENT+"='"+Util.sqliteEscape(str)+"' WHERE user="+user);
        db.close();
    }
    public static void saveUser(Context context,String user,String password){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", user);
        editor.putString("password", password);
        editor.commit();
    }
    public static Map<String,String> getUser(Context context){
        SharedPreferences sharedPreferences =context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);//(key,若无数据需要赋的值)
        String password = sharedPreferences.getString("password", null);//(key,若无数据需要赋的值)
        Map<String,String> map=new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        return map;
    }
    //字符转字节
    public static byte[] strToByteArray(String str) {
        if (str == null) {
            return null;
        }
        byte[] byteArray = str.getBytes();
        return byteArray;
    }
    //字节转字符
    public static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }
    public static String sqliteEscape(String keyWord){
        keyWord = keyWord.replace("/", "//");
        keyWord = keyWord.replace("'", "''");
        keyWord = keyWord.replace("[", "/[");
        keyWord = keyWord.replace("]", "/]");
        keyWord = keyWord.replace("%", "/%");
        keyWord = keyWord.replace("&","/&");
        keyWord = keyWord.replace("_", "/_");
        keyWord = keyWord.replace("(", "/(");
        keyWord = keyWord.replace(")", "/)");
        return keyWord;
    }
    public static void deleteDatabse(Context context) {
        sqLiteHelper = new SQLiteHelper(context,SQLiteHelper.TB_NAME,null,1);
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        db.delete(SQLiteHelper.TB_NAME, "1", new String[] {});
        Log.d("Database stuff", "Database table succesfully deleted");
        db.close();
    }
}
