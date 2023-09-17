import React, { useState,useEffect, useRef } from "react";
import usePrevious from "../usePrevious";
import order from "../styles/order.module.css";
import image1 from "../icons/image1.png";
import image2 from "../icons/image2.png";
import formatter from "../currencyFormerter";

export default function ProductOnSummary(props) {

    const [currentSubtotal, setCurrentSubTotal] = useState((props.product.quantity * props.product.price));
  
    const previousSubtotal = usePrevious(currentSubtotal);
    const [currentPrice, setCurrentPrice] = useState(props.product.price);
 
    
    const currentQuantity = useRef(props.product.quantity);
    useEffect(() => {
        // props.computeSubTotal(currentSubtotal, previousSubtotal);
        props.updateSubTotal((currentSubtotal))
        props.setChildCurrentSubtotal(currentSubtotal);
        props.setChildPreviousSubtotal(previousSubtotal);
    }, [currentSubtotal, previousSubtotal]);
  
  

    const handleIncrement = () => {
      let currentVal = currentQuantity.current.value;
      let increasedVal = parseInt(currentVal) + parseInt(1);
      currentQuantity.current.value = increasedVal;
        setCurrentSubTotal((currentPrice * increasedVal));

        props.setChildCurrentSubtotal(currentSubtotal);
        props.setChildPreviousSubtotal(previousSubtotal);
         
        
  }
    

  const handleDecrement = ()=> {
    
      let currentVal = currentQuantity.current.value;
      let increasedVal = parseInt(currentVal) - parseInt(1);
    if (currentVal >= 2) {
        currentQuantity.current.value = increasedVal;
        setCurrentSubTotal((currentPrice * increasedVal));
    
        props.setChildCurrentSubtotal(currentSubtotal);
        props.setChildPreviousSubtotal(previousSubtotal);
       
        
    }
      
    
  }
    return (
        <>
            <div id={props.product.id} className={order.productRow}>
              <img src={image2} className={order.productImage} alt="product" />
              <div className={order.productDetails}>
                    <h6>{props.product.productName}</h6>
                    <small>{ props.product.size}</small>
                <small>{formatter.format(currentSubtotal)}</small>
                <div className={order.control}>
                  <small>Qty</small>
                  <input type="button" value="+" className={order.inputs} onClick={handleIncrement} />
                  <input ref={currentQuantity} type="number" defaultValue={props.product.quantity} className={order.inputs} min="1" max="1000" />
                  <input type="button" value="-" className={order.inputs} onClick={handleDecrement} />
                </div>
              </div>
                </div>
        </>
    )
}
// function usePrevious(value) {
//   // The ref object is a generic container whose current property is mutable ...
//   // ... and can hold any value, similar to an instance property on a class
//   const ref = useRef();
//   // Store current value in ref
//   useEffect(() => {
//     ref.current = value;
//   }, [value]); // Only re-run if value changes
//   // Return previous value (happens before update in useEffect above)
//   return ref.current;
// }
