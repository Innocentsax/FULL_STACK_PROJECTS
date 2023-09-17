import React from 'react'
import delivery from '../styles/deliverymethod.module.css'

export default function DeliveryMethod() {
    return (
        <>
           <div className={delivery.container}>
               <div className={delivery.topRow}>
               <input type="checkbox" id="setDefault" className={delivery.input}/>
                    <h6>Delivery Method</h6>
               </div>

               <div className={delivery.bottomRow}>
               <input type="radio" id="setDefault" className={delivery.input}/>
               <small>&#x20A6; 2000</small>
               <small>Delivery fee</small>
               <small>Door delivery</small>

               </div>
           
               </div> 
        </>
    )
}
