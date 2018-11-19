package com.example.kgitbank.kakao;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Context ctx = Main.this;
        findViewById(R.id.moveLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(ctx,Login.class);
                startActivity(intent);
                */
                startActivity(new Intent(ctx, Login.class));
            }
        });
    } //onCreate End
    static interface ExecuteService {
        public void perfome();
    }
    static interface  ListService{
        public List<?> perfome();
    }
    static interface ObjectService{
        public Object perfome();
    }
    static abstract class QueryFactory{
        Context ctx; // Alt + Insert 해서 Constructor 바로 만듦
        public QueryFactory(Context ctx) {
            this.ctx = ctx;
        }
        public abstract SQLiteDatabase getDatabase();
    }
    static class SQLiteHelper extends SQLiteOpenHelper{

        public SQLiteHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory,
                            int version) {
            super(context, DBInfo.DBNAME , null, 1);
            this.getWritableDatabase();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = String.format(
                    " CREATE TABLE IF NOT EXIST %s " +
                            " ( %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "   %s TEXT, " +
                            "   %s TEXT, " +
                            "   %s TEXT, " +
                            "   %s TEXT, " +
                            "   %s TEXT, " +
                            "   %s TEXT, " +
                                    ")" ,
                    DBInfo.MBR_TABLE,
                    DBInfo.MBR_SEQ,
                    DBInfo.MBR_NAME,
                    DBInfo.MBR_EMAIL,
                    DBInfo.MBR_PASS,
                    DBInfo.MBR_ADDR,
                    DBInfo.MBR_PHONE,
                    DBInfo.MBR_PHOTO
            );
            Log.d("실행할 쿼리 :: " , sql);
            db.execSQL(sql);
            Log.d("================================","쿼리실행");
            String[] names = {"강동원","윤아", "임수정","박보검","송중기"};
            String[] emails = {"kang@naver.com", "yun@naver.com", "lim@naver.com", "park@naver.com", "song@naver.com"};
            String[] addr = {"대구", "서울","부산","광주","전주"};

            for(int i = 0; i < names.length; i++){
                Log.d("입력하는 이름 :: ",names[i]);
                db.execSQL(String.format(
                        " INSERT INTO %s " +
                                " ( %s ," +
                                "   %s ," +
                                "   %s ," +
                                "   %s ," +
                                "   %s ," +
                                "   %s " +
                                ")VALUES(" +
                                "'%s', " +
                                "'%s', " +
                                "'%s', " +
                                "'%s', " +
                                "'%s', " +
                                "'%s' " +
                                ")",

                        DBInfo.MBR_TABLE, DBInfo.MBR_NAME, DBInfo.MBR_EMAIL, DBInfo.MBR_PASS, DBInfo.MBR_ADDR, DBInfo.MBR_PHONE, DBInfo.MBR_PHOTO,
                        names[i], emails[i], '1', addr[i], "010-1234-567"+i,"PHOTO_"+i+1
                ));
            }
            Log.d("****************","친구등록완료");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
