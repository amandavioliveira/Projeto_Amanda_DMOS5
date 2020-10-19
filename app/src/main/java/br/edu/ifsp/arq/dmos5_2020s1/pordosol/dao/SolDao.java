package br.edu.ifsp.arq.dmos5_2020s1.pordosol.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.arq.dmos5_2020s1.pordosol.model.Sol;

public class SolDao {
    private SQLiteDatabase mSqLiteDatabase;
    private SQLiteHelper mHelper;

    public SolDao(Context context) {
        mHelper = new SQLiteHelper(context);
    }

    public void adicionar(Sol sol) throws NullPointerException{
        if(sol == null){
            throw new NullPointerException();
        }

        ContentValues valores = new ContentValues();
        valores.put(SQLiteHelper.COLUMN_SUNRISE, sol.getSunrise());
        valores.put(SQLiteHelper.COLUMN_SUNSET, sol.getSunset());
        mSqLiteDatabase = mHelper.getWritableDatabase();

        mSqLiteDatabase.insert(SQLiteHelper.TABLE_SOL, null, valores);

        mSqLiteDatabase.close();
    }

    public List<Sol> recuperaTodos(){
        List<Sol> solList;
        Sol sol;
        Cursor mCursor;
        solList = new ArrayList<>();

        String colunas[] = new String[]{
                SQLiteHelper.COLUMN_SUNRISE,
                SQLiteHelper.COLUMN_SUNSET
        };

        mSqLiteDatabase = mHelper.getReadableDatabase();

        mCursor = mSqLiteDatabase.query(
                SQLiteHelper.TABLE_SOL,
                colunas,
                null,
                null,
                null,
                null,
                null
        );

        while (mCursor.moveToNext()){
            sol = new Sol(
                    mCursor.getString(0),
                    mCursor.getString(1)
            );
            solList.add(sol);
        }

        mCursor.close();
        mSqLiteDatabase.close();
        return solList;
    }
}
