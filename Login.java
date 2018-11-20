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

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final Context ctx = Login.this;
        final EditText ID = findViewById(R.id.etID);
        final EditText PW = findViewById(R.id.etPass);

        findViewById(R.id.btLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validation  유효성체크
                if(ID.getText().length()!=0 && PW.getText().length()!=0){
                    String id = ID.getText().toString();
                    String pw = PW.getText().toString();
                    final ItemExist query = new ItemExist(ctx);
                    query.id = id;
                    query.pw = pw;
                   new Main.ExecuteService() {
                        @Override
                        public void perfome() {
                            if(query.execute()){
                                startActivity(new Intent(ctx, MemberList.class));
                            }else {
                                startActivity(new Intent(ctx,Login.class));
                            }
                        }
                    }.perfome();
                    startActivity(new Intent(ctx, MemberList.class));
                }else{
                    startActivity(new Intent(ctx,Login.class));
                }
            }
        });

        findViewById(R.id.btCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    //OnCreate End
    private class LoginQuery extends Main.QueryFactory{
        SQLiteOpenHelper helper;

        public LoginQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    } //LoginQuery End

    private class ItemExist extends LoginQuery{
        String id, pw;
        public ItemExist(Context ctx) {
            super(ctx);
        }
        public boolean execute(){
            return super
                    .getDatabase()
                    .rawQuery(String.format(
                            " SELECT * FROM %s " +
                                    "WHERE %s LIKE '%s' AND '%s' LIKE '%s'",
                            DBInfo.MBR_TABLE, DBInfo.MBR_SEQ, id, DBInfo.MBR_PASS, pw
                    ),null).moveToNext();
        }
    }
}
