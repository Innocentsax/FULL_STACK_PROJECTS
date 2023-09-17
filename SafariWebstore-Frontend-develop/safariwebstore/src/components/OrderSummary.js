import React, { useState,useEffect, useRef } from "react";
import order from "../styles/order.module.css";
import image1 from "../icons/image1.png";
import image2 from "../icons/image2.png";
import ProductOnSummary from "./ProductOnSummary";
import formatter from "../currencyFormerter";
import usePrevious from "../usePrevious";

export default function OrderSummary(props) {
  let sum = 0;
  let cardDiscount = 0;
  let deliveryFee = 2000;

  
   sum = function(items, prop){
     return items.reduce(function (a, b) {
            return a + b[prop.split(",")[0]]*b[prop.split(",")[1]];
        }, 0);
   };
  
  
  const total = sum(props.cart, 'quantity,price');
 

  const [subTotal, setSubTotal] = useState(total);
  console.log(subTotal);
  const previousSubTotal = usePrevious(subTotal);
  const [prevTotal, setPrevTotal] = useState(0);
  const [childCurrentSubtotal, setChildCurrentSubtotal] = useState();
  const [childPreviousSubtotal, setChildPreviousSubtotal] = useState();
  cardDiscount = 0.05 * subTotal;
  
  const finalTotal = subTotal + deliveryFee - cardDiscount;
  useEffect(() => {
    let newTotal = 0;
    if (childCurrentSubtotal !== childPreviousSubtotal) {
      newTotal = subTotal - childPreviousSubtotal + childCurrentSubtotal;
    }
    return () => {
      setSubTotal(newTotal);
    }
  }, [childCurrentSubtotal,childPreviousSubtotal])
  
  return (
    <>
      <form className={order.container}>
        <div className={order.box}>
          <h4>ORDER SUMMARY</h4>
          <div className={order.content}>
            {props.cart.map(function (product,index) {
              console.log(subTotal)
              return <ProductOnSummary updateSubTotal={() => setPrevTotal(product.price + prevTotal)} subTotal={product.price} key={index} id={product.id}
                                       product={product} setChildCurrentSubtotal={setChildCurrentSubtotal}
                                       setChildPreviousSubtotal={setChildPreviousSubtotal}
                                       childCurrentSubtotal={childCurrentSubtotal}
                                       childPreviousSubtotal={childPreviousSubtotal}/>
            })}
            
          </div>
          <hr className={order.rule} />
          <div className={order.price}>
            <div className={order.priceBreakDown}>
              <small>
                Cart sub-total
              </small>
              <small>
                {formatter.format(subTotal)}
              </small>
            </div>

            <div className={order.priceBreakDown}>
              <small>
                Card discount
              </small>
              <small>
                -{formatter.format(cardDiscount)}
              </small>
            </div>
            <div className={order.priceBreakDown}>
              <small>
                Delivery fee
              </small>
              <small>
                {formatter.format(deliveryFee)}
              </small>
            </div>
          </div>
          <hr className={order.rule} />
          <div className={order.total}>
            <h6>TOTAL</h6>
            <h6>
            {formatter.format(finalTotal)}
            </h6>
          </div>
        </div>
        <div className={order.midRow}>
          <div>
            <small>Is this a gift</small>
            <div>
              <input type="button" value="Yes" />
              <input type="button" value="No" />
            </div>
          </div>
          <p>
            A complementary gift will be included in the package, and prices will be hidden in the receipt
          </p>
        </div>
        <input type="submit" value="PLACE ORDER" className={order.submit} />
      </form>
    </>
  );
}
