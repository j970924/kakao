import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import * as firebase from "firebase";
/**
 * Generated class for the SignupPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-signup',
  templateUrl: 'signup.html',
})
export class SignupPage {
  private account : any ={
    name : '',
    email : '',
    password : ''
  };

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad SignupPage');
  }

  signup(){
    firebase.auth()
    .createUserWithEmailAndPassword(this.account.email, this.account.password)
    .then((result)=>{
      console.log(result);
      var user = firebase.auth().currentUser;
      user.updateProfile({
        displayName: this.account.name,
        photoURL: "https://example.com/jane-q-user/profile.jpg"
      }).then(()=> {
        // Update successful.
        console.log('회원가입 성공');
      }).catch((error)=> {
        // An error happened.
        console.log(error.message);
      });
    })
    .catch((error)=>{
      //Handle Errors here.
      //var errorCode = error.code;
      //var errorMessage = error.message;
      // ...
      console.log(error.message);
    });
  }
}
