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

import java.util.ArrayList;

public class MemberDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context ctx = MemberDetail.this;
        final ImageView profile = findViewById(R.id.profile);
        final TextView name = findViewById(R.id.name);
        final TextView email = findViewById(R.id.email);
        final TextView phone = findViewById(R.id.phone);
        final TextView addr = findViewById(R.id.addr);
        setContentView(R.layout.memberdetail);
        ItemDetail itemDetail = new ItemDetail(ctx);
        Intent intent = this.getIntent();
        String seq = intent.getExtras().getString("seq");
        final ItemDetail query = new ItemDetail(ctx);
        query.seq = seq;
        Member m  = (Member) new Main.ObjectService() {
            @Override
            public Object perfome() {
                return query.execute();
            }
        }.perfome();
        Log.d("선택한 멤버 정보",m.toString());

        String spec = m.seq + "/"+m.addr+"/"+m.email+"/"+m.name+"/"+m.pass+"/"+m.photo+"/"+m.phone;
        String[] arr = spec.split("/");
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

    static class ViewHolder{
        ImageView profile;
        TextView name, phone, email, addr;
    }

    public View getView(View v){
        ViewHolder holder;
        if(v==null){
            holder.name = v.findViewById(R.id.name);
        }
    }
}
