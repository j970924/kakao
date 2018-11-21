package com.example.kgitbank.kakao;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Member;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context ctx = MemberDetail.this;
        setContentView(R.layout.memberdetail);
        ItemDetail itemDetail = new ItemDetail(ctx);
    } //onCreate End

    private class DetailQuery extends Main.QueryFactory{
        SQLiteOpenHelper helper;

        public DetailQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }

    private class ItemDetail extends  DetailQuery{
        public ItemDetail(Context ctx) {
            super(ctx);
        }
    }
}
