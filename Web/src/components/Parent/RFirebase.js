import {initializeApp} from "firebase/app"
import {getDatabase} from "firebase/database"


function StartFirebase (){
    const firebaseConfig = {
        apiKey: "AIzaSyDOTYKOzQ7pOj4mzazVrJILFR-H-CJ-HpM",
        authDomain: "the-special-school.firebaseapp.com",
        databaseURL: "https://the-special-school-default-rtdb.firebaseio.com",
        projectId: "the-special-school",
        storageBucket: "the-special-school.appspot.com",
        messagingSenderId: "714378399466",
        appId: "1:714378399466:web:b30734337fd24faa855193",
        measurementId: "G-4CFV3DYWRR"
      };
      
      const app = initializeApp(firebaseConfig);
    
      return getDatabase(app);
}

export default StartFirebase