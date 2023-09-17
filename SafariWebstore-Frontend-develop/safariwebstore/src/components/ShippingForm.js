import React,{useState,useEffect} from 'react'
import shipping from '../styles/shippingForm.module.css';

import stateLga from "../stateAndLga.json";



export default function ShippingForm() {
    const [lgas, setLgas] = useState([]);
    const handleStateSelect = (e) => {
        let selectedState = e.target.value;
        for (var key in stateLga) {
            if (key === selectedState) {
                setLgas(stateLga[key]);
            }
 
        }       
    }
    const handleCitySelect = (e) => {
        let selectedCity = e.target.value;
    }
    
    
  
    return (
        <>
            <div className={shipping.container}>
                <form className={shipping.form}>
                <div className={shipping.labels}>
                <input type="checkbox" id="setDefault" className={shipping.input}/>
                    <h6>Shipping address</h6>
                </div>
                    <div className={shipping.formGroup}>
                        <label htmlFor="email">Email</label>
                        <input type="email" id="email" className={shipping.input}/>
                    </div>
                    <div className={shipping.formGroup}>
                        <label htmlFor="fullName">Full name</label>
                        <input type="text" id="fullName" className={shipping.input}/>
                    </div>
                    <div className={shipping.formGroup}>
                        <label htmlFor="address">Address</label>
                        <textarea className={shipping.input} id="address"></textarea>
                    </div>
                    {/* <div className={shipping.formGroup}>
                        <label htmlFor="email"></label>
                        <input type="text" className={shipping.input}/>
                    </div> */}
                    <div className={shipping.formGroup}>
                        <label htmlFor="state">State/Province</label>
                        <select id="state" className={shipping.input} onChange={handleStateSelect}>
                             <option>States</option> 
                            {Object.keys(stateLga).map((state,key) => {
                                return <option key={key} value={state}>{state}</option>
                            })}
                        </select>
                    </div>
                    <div className={shipping.formGroup}>
                    <label htmlFor="city">City</label>
                        <select id="city" className={shipping.input} onChange={handleCitySelect}>
                            <option>City</option>
                            {lgas && lgas.map((city, key) => {
                                return <option key={key} value={city}>{city}</option>
                            })}
                        </select>
                    </div>
                    <div className={shipping.formGroup}>
                    <label htmlFor="phoneNumber">Phone Number</label>
                    <input type="tel" id="phoneNumber" className={shipping.input}/>
                    </div>
                    <div className={shipping.labels}>
                    <input type="checkbox" id="setDefault" className={shipping.input}/>
                    <h6>Set default shipping address</h6>
                    </div>
            

                </form>
            </div>
        </>
    )
}
