package com.example.kgitbank.kakao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kgitbank.kakao.util.Album;
import com.example.kgitbank.kakao.util.Email;
import com.example.kgitbank.kakao.util.Phone;

import java.util.ArrayList;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberdetail);
        final Context ctx = MemberDetail.this;
        Intent intent = this.getIntent();
        String seq = intent.getExtras().getString("seq" );
        final ItemDetail query = new ItemDetail(ctx);
        query.seq = seq;
        final Member m  = (Member) new Main.ObjectService() {
            @Override
            public Object perfome() {
                return query.execute();
            }
        }.perfome();
        Log.d("선택한 멤버 정보",m.toString());

        final String spec = m.seq + "/"+m.addr+"/"+m.email+"/"+m.name+"/"+m.pass+"/"+m.photo+"/"+m.phone;
        String[] arr = spec.split("/");

        TextView name = findViewById(R.id.name);
        name.setText(arr[3]);
        TextView email = findViewById(R.id.email);
        email.setText(arr[2]);
        TextView phone = findViewById(R.id.phone);
        phone.setText(arr[6]);
        TextView addr = findViewById(R.id.addr);
        addr.setText(arr[1]);
        TextView pass = findViewById(R.id.pass);
        pass.setText(arr[4]);
        ImageView photo = findViewById(R.id.photo);
        Log.d("프로필사진 :: ",arr[5].toLowerCase());
        photo.setImageDrawable(
                getResources()
                        .getDrawable(
                                getResources()
                                        .getIdentifier(this.getPackageName()+":drawable/"+arr[5].toLowerCase(),null,null), ctx.getTheme()
                        )
        );
        //선택한 멤버 정보를 로그로 출력하기
        findViewById(R.id.listBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, MemberList.class));
            }
        });

        findViewById(R.id.updateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,MemberUpdate.class);
                intent.putExtra("spec",spec);
                startActivity(intent);
            }
        });

        findViewById(R.id.callBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Phone phone = new Phone(ctx, MemberDetail.this);
                phone.setPhoneNum(m.phone);
                phone.dial();
            }
        });

        findViewById(R.id.dialBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.smsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.emailBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email email = new Email();
                email.sendEmail("j970924@naver.com");
            }
        });

        findViewById(R.id.albumBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx,Album.class));
            }
        });

        findViewById(R.id.movieBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.mapBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.musicBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    } //onCreate End
    /*
    * 데이터베이스 영역
    * */
    private class DetailQuery extends Main.QueryFactory{
        Main.SQLiteHelper helper;

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
        String seq;
        public ItemDetail(Context ctx) {
            super(ctx);
        }
        public Member execute(){
           Member m = null;
           Log.d("seq값",seq);
           Cursor c = this.getDatabase().rawQuery(
                   String.format(" SELECT * FROM %s WHERE %s LIKE %s ",
                           DBInfo.MBR_TABLE, DBInfo.MBR_SEQ,seq),null
           );
           if(c!=null){
               m = new Member();
               if(c.moveToNext()) {
                   m.setSeq(Integer.parseInt(c.getString(c.getColumnIndex(DBInfo.MBR_SEQ))));
                   m.setName(c.getString(c.getColumnIndex(DBInfo.MBR_NAME)));
                   m.setEmail(c.getString(c.getColumnIndex(DBInfo.MBR_EMAIL)));
                   m.setPhone(c.getString(c.getColumnIndex(DBInfo.MBR_PHONE)));
                   m.setAddr(c.getString(c.getColumnIndex(DBInfo.MBR_ADDR)));
                   m.setPhoto(c.getString(c.getColumnIndex(DBInfo.MBR_PHOTO)));
                   m.setPass(c.getString(c.getColumnIndex(DBInfo.MBR_PASS)));

                   Log.d("검색된 회원은", m.getName());
               }
           } else {
               Log.d("검색된 회원은","없음");
           }
           return m;
        }
    }

}
