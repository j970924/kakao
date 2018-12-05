import { Component } from '@angular/core';
import { NavController, AlertController } from 'ionic-angular';
import * as firebase from "firebase";

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
  private userName : any;
  private userEmail : any;
  private userId : any;

  constructor(public navCtrl: NavController,
    private alertCtrl: AlertController) {
    this.initPage();
  }
  initPage(){
    var user = firebase.auth().currentUser;
    console.log(user);
    if(user){
      this.userName = user.displayName;
      this.userEmail = user.email;
      this.userId = user.uid;
    }else{
      console.log('로그인된 사용자가 없습니다.');
    }
  }

  logout(){
    let confirm = this.alertCtrl.create({
      title: '로그아웃',
      message: '로그아웃하시겠습니까?',
      buttons: [{
        text: '아니오',
        handler: ()=>{
          console.log('로그아웃 취소');
        }
      },{
        text: '예',
        handler: ()=>{
          console.log('로그아웃 확인');
          firebase.auth().signOut()
          .then(()=>{
            console.log('로그아웃 실행..');
          })
          .catch((error)=>{
            console.log(error);
          });
        }
      }]
    });
    confirm.present();
  }
}
