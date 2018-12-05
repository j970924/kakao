import { Component } from '@angular/core';
import { Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import * as firebase from "firebase";

  // Initialize Firebase
  // TODO: Replace with your project's customized code snippet
  var config = {
    apiKey: "AIzaSyAgcG8TMCpWiLqRU2Ug3V1AZPvPVKrnm_k",
    authDomain: "itbank18-1543315083159.firebaseapp.com",
    databaseURL: "https://itbank18-1543315083159.firebaseio.com",
    projectId: "itbank18-1543315083159",
    storageBucket: "itbank18-1543315083159.appspot.com",
    messagingSenderId: "543460390097"
  };

import { HomePage } from '../pages/home/home';
import { LoginPage } from '../pages/login/login';
import { SignupPage } from '../pages/signup/signup';

@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  rootPage;

  constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen) {
    platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      statusBar.styleDefault();
      splashScreen.hide();
    });
    firebase.initializeApp(config);
    firebase.auth().onAuthStateChanged((user)=>{
      if (user) {
        // User is signed in.
        this.rootPage = HomePage;
      } else {
        // User is signed out.
        this.rootPage = LoginPage;
      }
    });
    
  }
}

