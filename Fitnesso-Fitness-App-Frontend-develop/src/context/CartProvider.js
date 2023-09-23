import CartContext from "./cart-context";
import {useContext} from "react";


const CartProvider = props => {

   const cartCxt =  useContext(CartContext);

    return (<CartContext.Provider value={ {items: cartCxt}}>
         {props.children}
    </CartContext.Provider>)
}


export default CartProvider;