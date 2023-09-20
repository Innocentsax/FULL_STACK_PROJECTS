import preloader from '../components/Images/preloader.gif'
import active from '../components/Images/tick-circle.png'
import inactive from '../components/Images/inactive.png'
import smsedit from '../components/Images/sms-edit.png';
import frame from '../components/Images/frame.png';

export const PORT = 8082; //REGISTRATION
export const PORTb = 8084; //BILLING
export const PORTPROFILE = 8083; //Profile
export const loader = preloader;
export const activeIcon = active;
export const frameImg = frame;

export const sms = smsedit;

export const inact = inactive;
export const TOKEN = localStorage.getItem("TOKEN")


// const Session =localStorage.getItem("session")

export const validateUser=()=>{
    if(localStorage.getItem("session")!=null){
        window.location.replace("/dashboard")
    }
}

export const config = {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("TOKEN")}`,
      'Content-Type': 'application/json', 
    },
  };





