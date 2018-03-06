package com.example.son_auto.appdiary_1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.son_auto.appdiary_1.model.PageDiary;

import java.util.ArrayList;

/**
 * Created by Son-Auto on 3/5/2018.
 */

public class DiaryDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbdiary_name";
    public static final String TABLE_NAME = "tbdiary_name";

    private static final String DIARY_ID = "tbdiary_id";
    private static final String DIARY_CONTENT = "tbdiary_content";
    private static final String DIARY_EMOTION = "tbdiary_emotion";
    private static final String DIARY_BACKGROUND = "tbdiary_background";

    private Context context;

    public DiaryDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + TABLE_NAME + " (" +
                DIARY_ID + " integer primary key autoincrement, " +
                DIARY_CONTENT + " ntext," +
                DIARY_EMOTION + " text, " +
                DIARY_BACKGROUND + " text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addDiary(PageDiary pageDiary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DIARY_CONTENT, pageDiary.getContent());
        values.put(DIARY_EMOTION, pageDiary.getEmotion());
        values.put(DIARY_BACKGROUND, pageDiary.getBackground());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<PageDiary> getAllPageDiary() {
        ArrayList<PageDiary> listPage = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                PageDiary p = new PageDiary();
                p.setId(Integer.parseInt(cursor.getString(0)));
                p.setContent(cursor.getString(1));
                p.setEmotion(cursor.getString(2));
                p.setBackground(cursor.getString(3));

                listPage.add(p);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listPage;
    }
}
