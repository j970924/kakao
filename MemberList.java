package com.example.kgitbank.kakao;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MemberList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberlist);
        final Context ctx = MemberList.this;
        final ListView mbrList = findViewById(R.id.mbrList);
        final ItemList query = new ItemList(ctx);
        Log.d("친구목록","*******");

        mbrList.setAdapter(new MemberAdapter(ctx,(ArrayList<Member>) new Main.ListService() {
            @Override
            public List<?> perfome() {
                return query.execute();
            }
        }.perfome()));

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctx, MemberAdd.class));
            }
        });
        //디테일 처리

        mbrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int i, long l) {
                Member m =  (Member)mbrList.getItemAtPosition(i);
                Log.d("선택한 ID",m.seq+"");
                Intent intent = new Intent(ctx, MemberDetail.class);
                intent.putExtra("seq",m.seq+"");
                startActivity(intent);
            }
        });

        mbrList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> p, View v, int i, long l) {
                Member m = (Member)mbrList.getItemAtPosition(i);
                new AlertDialog.Builder(ctx)
                        .setTitle("삭 제 ")
                        .setMessage(" 정말 삭제 ?")
                        .setPositiveButton(
                                android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 삭제 쿼리 실행
                                        Toast.makeText(ctx, "삭제 완료", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(ctx, MemberList.class));
                                    }
                                }
                        )
                        .setNegativeButton(
                                android.R.string.no,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(ctx,"삭제 취소",Toast.LENGTH_LONG).show();
                                    }
                                }
                        ).show();
                return true;
            }
        });
        //삭제 처리

    } // onCreate End

    private class ListQuery extends Main.QueryFactory{
        SQLiteOpenHelper helper;

        public ListQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    } //ListQuery End

    /*
    *  아이템 관련 파트
    * */

    private class ItemList extends ListQuery{
        public ItemList(Context ctx) {
            super(ctx);
        }
        public ArrayList<Member> execute(){
            ArrayList<Member> ls = new ArrayList<>();
            Cursor c = this.getDatabase().rawQuery(
                    "SELECT * FROM MEMBER",null);
            Member m = null;
            if(c != null){
                while(c.moveToNext()){
                    m = new Member();
                    m.setSeq(Integer.parseInt(c.getString(c.getColumnIndex(DBInfo.MBR_SEQ))));
                    m.setName(c.getString(c.getColumnIndex(DBInfo.MBR_NAME)));
                    m.setEmail(c.getString(c.getColumnIndex(DBInfo.MBR_EMAIL)));
                    m.setPhone(c.getString(c.getColumnIndex(DBInfo.MBR_PHONE)));
                    m.setAddr(c.getString(c.getColumnIndex(DBInfo.MBR_ADDR)));
                    m.setPhoto(c.getString(c.getColumnIndex(DBInfo.MBR_PHOTO)));
                    m.setPass(c.getString(c.getColumnIndex(DBInfo.MBR_PASS)));
                    ls.add(m);
                    Log.d("등록된 회원 수는",ls.size()+"");
                }
            } else {
                Log.d("등록된 회원은","없음");
            }
            return ls;
        }
    }
    /*
     * 아이템 관련파트 End
     */

    private class MemberAdapter extends BaseAdapter{
        ArrayList<Member>  ls;
        Context ctx;
        LayoutInflater inflater;

        public MemberAdapter(Context ctx, ArrayList<Member> ls) {
            this.ls = ls;
            this.ctx = ctx;
            this.inflater = LayoutInflater.from(ctx);
        }

        @Override
        public int getCount() {
            return ls.size();
        }

        @Override
        public Object getItem(int i) {
            return ls.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHolder holder;
            if(v==null){
                v = inflater.inflate(R.layout.mbr_item,null);
                holder = new ViewHolder();
                holder.photo = v.findViewById(R.id.photo);
                holder.name = v.findViewById(R.id.name);
                holder.phone = v.findViewById(R.id.phone);
                v.setTag(holder);
            }else{
                holder = (ViewHolder) v.getTag();
            }
            holder.name.setText(ls.get(i).getName());
            holder.phone.setText(ls.get(i).getPhone());
            final ItemPhoto query = new ItemPhoto(ctx);
            query.seq = ls.get(i).seq + "";
            Log.d("시퀀스값",query.seq);
            String s = ((String) new Main.ObjectService() {
                @Override
                public Object perfome() {
                    return query.execute();
                }
            }.perfome()).toLowerCase();
            Log.d("파일명",s);
            holder.photo
                    .setImageDrawable(getResources().getDrawable(
                            getResources().getIdentifier(
                                    ctx.getPackageName() + ":drawable/" + s ,null, null
                            ), ctx.getTheme()));// 포토 불러오는 코드
            return v;
        }
    }

    static class ViewHolder{
        ImageView photo;
        TextView name, phone;
    }

    private class PhotoQuery extends Main.QueryFactory {
        SQLiteOpenHelper helper;
        public PhotoQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getReadableDatabase();
        }
    }

    private class ItemPhoto extends PhotoQuery{
        String seq;

        public ItemPhoto(Context ctx) {
            super(ctx);
        }
        public String execute(){
            Cursor c= getDatabase()
                    .rawQuery(String.format(
                            " SELECT %s FROM %s WHERE %s LIKE '%s' ",
                            DBInfo.MBR_PHOTO,
                            DBInfo.MBR_TABLE,
                            DBInfo.MBR_SEQ,
                            seq
                    ),null);
            String result = "";
            if(c!= null){
                if(c.moveToNext()){
                    result = c.getString(c.getColumnIndex(DBInfo.MBR_PHOTO));
                }
            }
            Log.d("결과값",result);
            return result;
        }
    }
}
