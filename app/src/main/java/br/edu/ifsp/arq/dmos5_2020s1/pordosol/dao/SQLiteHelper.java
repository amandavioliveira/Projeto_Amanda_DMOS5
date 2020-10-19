package br.edu.ifsp.arq.dmos5_2020s1.pordosol.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "sol.db";

    public static final String TABLE_SOL = "sol";
    public static final String COLUMN_SUNRISE = "sunrise";
    public static final String COLUMN_SUNSET = "sunset";

    private Context mContext;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public Context getContext(){
        return mContext;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql;

        sql = "CREATE TABLE " + TABLE_SOL + " (";
        sql += COLUMN_SUNRISE + " TEXT NOT NULL, ";
        sql += COLUMN_SUNSET + " TEXT NOT NULL); ";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
