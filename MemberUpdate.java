package com.example.kgitbank.kakao;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberupdate);
        final Context ctx = MemberUpdate.this;
        final String[] arr = getIntent().getStringExtra("spec").split("/");
        final String seq = arr[0];
        // m.seq+"/"+m.addr+"/"+m.email+"/"+m.name+"/"+m.pass+"/"+m.photo+"/"+m.phone
        // 수정 전 부분

        final EditText name = findViewById(R.id.name);
        name.setHint(arr[3]);
        final EditText email = findViewById(R.id.email);
        email.setHint(arr[2]);
        final EditText phone = findViewById(R.id.phone);
        phone.setHint(arr[6]);
        final EditText addr = findViewById(R.id.addr);
        addr.setHint(arr[1]);
        final EditText pass = findViewById(R.id.pass);
        pass.setHint(arr[4]);
        final ImageView photo = findViewById(R.id.photo);
        Log.d("프로필사진 :: ",arr[5].toLowerCase());
        photo.setImageDrawable(
                getResources()
                        .getDrawable(
                                getResources()
                                        .getIdentifier(this.getPackageName()+":drawable/"+arr[5].toLowerCase(),null,null), ctx.getTheme()
                        )
        );

        // 수정 이후

        findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Member member = new Member();
                final ItemUpdate query = new ItemUpdate(ctx);
                // member 에 값을 넣는데, 만약 EditText 가 NULL 이라면
                // 배열에 있는 값이라도 member에 할당해야 한다.
                // if(name.getText() == null) 일때 null 은 주소값으로 비교, 입력되지 않았을때의 비교 역할을 수행하지 않음.
                member.setSeq(Integer.parseInt(seq));
                member.setName((name.getText().equals(" "))? arr[2] : name.getText().toString());
                member.setEmail((email.getText().equals(" "))? arr[2] : email.getText().toString());
                member.setPhone((phone.getText().equals(" "))? arr[6] : phone.getText().toString());
                member.setAddr((addr.getText().equals(" "))? arr[1] : addr.getText().toString());
                member.setPass((pass.getText().equals(" "))? arr[4] : pass.getText().toString());
                
                query.member = member;

                new Main.ExecuteService() {
                    @Override
                    public void perfome() {
                            query.execute();
                    }
                }.perfome();
                startActivity(new Intent(ctx, MemberDetail.class));
            }
        });
        findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    } //onCreate End

    private class UpdateQuery extends Main.QueryFactory{
        Main.SQLiteHelper helper;

        public UpdateQuery(Context ctx) {
            super(ctx);
            helper = new Main.SQLiteHelper(ctx);
        }

        @Override
        public SQLiteDatabase getDatabase() {
            return helper.getWritableDatabase();
        }
    }

    private class ItemUpdate extends UpdateQuery{
        Member member;
        public ItemUpdate(Context ctx) {
            super(ctx);
            member = new Member();
            // 인스턴스 변수는 반드시 생성자 내부에서 초기화한다.
            // 로직은 반드시 에어리어 내부에서 이뤄진다.
            // 에어리어 내부는 CPU 를 뜻한다.
            // 필드는 RAM 영역을 뜻한다.
            // c 데니스 리치 1971 -> 절차지향
            // PC 스티브 워즈니악 1975
            // 자바 제임스 고슬링 1995 -> 객체지향
            // 객체는 속성과 기능의 집합이다
            // 속성 : 램
            // 기능 : CPU
        }

        public void execute(){
            String sql = (String.format(
                    " UPDATE %s SET " +
                            " %s = '%s' ," +
                            " %s = '%s' ," +
                            " %s = '%s' ," +
                            " %s = '%s' ," +
                            " %s = '%s' ," +
                            " %s = '%s' " +
                            " WHERE %s LIKE '%s' ",
                    DBInfo.MBR_TABLE,
                    DBInfo.MBR_ADDR, member.addr,
                    DBInfo.MBR_EMAIL, member.email,
                    DBInfo.MBR_NAME, member.name,
                    DBInfo.MBR_PASS, member.pass,
                    DBInfo.MBR_PHOTO, member.photo,
                    DBInfo.MBR_PHONE, member.phone,
                    DBInfo.MBR_SEQ, member.seq
            ));
            Log.d("SQL :::",sql);
            getDatabase().execSQL(sql);
        }
    }
}