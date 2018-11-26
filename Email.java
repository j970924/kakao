package com.example.kgitbank.kakao.util;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

public class Email {

    public void sendEmail(String email){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"+email));
        intent.setType("text/plain");
        intent.putExtra(intent.EXTRA_EMAIL,email);
        intent.putExtra(intent.EXTRA_SUBJECT,"HELLO");
        intent.putExtra(intent.EXTRA_TEXT,"안녕 !!!!");
    }
}
