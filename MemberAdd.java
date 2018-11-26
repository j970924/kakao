package com.example.kgitbank.kakao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.ETC1;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MemberAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberadd);
        final Context ctx = MemberAdd.this;
        final ImageView photo = findViewById(R.id.photo);
        final EditText profileName = findViewById(R.id.profileName);
        final EditText name = findViewById(R.id.name);
        final EditText email = findViewById(R.id.email);
        final EditText phone = findViewById(R.id.phone);
        final EditText addr = findViewById(R.id.addr);

        findViewById(R.id.imgBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Member member = new Member();
                final ItemAdd query = new ItemAdd(ctx);
                member.setName(name.getText().toString());
                member.setEmail(email.getText().toString());
                member.setPhone(phone.getText().toString());
                member.setAddr(addr.getText().toString());
                member.setPass("1");
                member.setPhoto("photo_"+ member.getSeq() +"1");
                query.member = member;

            }
        });

        findViewById(R.id.listBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    } // onCreate End

    private class AddQuery extends Main.QueryFactory{
        Main.SQLiteHelper helper;

        public AddQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }

    private class ItemAdd extends  AddQuery{
        Member member;
        public ItemAdd(Context ctx) {
            super(ctx);
            member = new Member();
        }

        public void execute(){
            String sql = String.format(
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
                    member.name, member.email, member.pass, member.addr, member.phone, member.photo
                    );
            getDatabase().execSQL(sql);
        }
    }

}
