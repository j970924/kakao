import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import * as firebase from "firebase";

/**
 * Generated class for the ResetemailPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-resetemail',
  templateUrl: 'resetemail.html',
})
export class ResetemailPage {
  private account : any ={
    email : '',
    newemail : '',
    password : ''
  };

  constructor(public navCtrl: NavController, public navParams: NavParams) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ResetemailPage');
  }

 updateemail(){
  var user = firebase.auth().currentUser;

  user.updateEmail("user@example.com").then(()=>{
    // Update successful.
  }).catch((error)=>{
    // An error happened.
  });
 }
}
