import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import * as firebase from "firebase";
import { SignupPage } from '../signup/signup';
import { ResetemailPage } from '../resetemail/resetemail';
//import { LoaderProvider } from '../../providers/loader/loader';

/**
 * Generated class for the LoginPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {
  private account : any ={
    email : '',
    password : ''
  };
  constructor(public navCtrl: NavController,
     public navParams: NavParams,
     //private loader: LoadingProvider
     ) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad LoginPage');
  }

  login(){
    //this.loader.show();
    firebase.auth().signInWithEmailAndPassword(
      this.account.email, 
      this.account.password)
      .then((result)=>{
        console.log(result);
      })
      .catch((error)=> {
      // Handle Errors here.
      //var errorCode = error.code;
      var errorMessage = error.message;
      // ...
      console.log(errorMessage);
    });
    //this.loader.hide();
  }

  signup(){
    this.navCtrl.push(SignupPage);
  }

  resetemail(){
    this.navCtrl.push(ResetemailPage);
  }
}
