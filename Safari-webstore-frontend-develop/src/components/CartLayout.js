import React from "react";
import {
  Grid,
  Container,
} from "semantic-ui-react";
import "../styles/Components/_cart_layout.scss";
import "../styles/Components/_button.scss";
import { Link } from "react-router-dom";
import { monetize } from "../utilities/helperFunction";
import { useCartUpdate } from "../utilities/CartContext";

function CartLayout(props) {
  const updateCartItem = useCartUpdate();
  return (
    <Container fluid padded className="cart-container">
      <h2 className="cart-title">
        Shopping Cart ({props.userCartItem.length} item )
      </h2>
      <Grid padded>
        <Grid.Column mobile={16} tablet={4} computer={8}>
          <p className="table-title">ITEM DESCRIPTON</p>
        </Grid.Column>
        <Grid.Column mobile={16} tablet={4} computer={2}>
          <p className="table-title">QUANTITY</p>
        </Grid.Column>
        <Grid.Column mobile={16} tablet={4} computer={3}>
          <p className="table-title">UNIT PRICE</p>
        </Grid.Column>
        <Grid.Column mobile={16} tablet={4} computer={3}>
          <p className="table-title">SUBTOTAL</p>
        </Grid.Column>
      </Grid>
      {props.userCartItem.map((item) => {
        console.log("Item::",item)
        return (
          <div className="cart-items">
            <Grid padded className="carts cart" key={item.id}>
              <Grid.Column mobile={16} tablet={8} computer={8}>
                <Grid>
                  <Grid.Column mobile={16} tablet={8} computer={3}>
                   
                    <img style={{width:"50px"}} src={item.productImage} alt={item.name} />
                  </Grid.Column>
                  <Grid.Column mobile={16} tablet={8} computer={9} className="">
                    {item.name}
                    <p className="cart-size">
                      Size - {`${item.size},  Color - ${item.color}`}
                    </p>
                    <Grid>
                      <Grid.Column mobile={16} tablet={8} computer={8}>
                        <Link to="/favourites" className="cart-button-link">
                          <p className="cart-favourites">
                            <i class="far fa-heart"></i> MOVE TO FAVOURITES
                          </p>
                        </Link>
                      </Grid.Column>
                      <Grid.Column mobile={16} tablet={8} computer={4}>
                        <Link
                          onClick
                          className="cart-remove"
                          onClick={() => {
                            props.delete(item.id);
                            updateCartItem();
                          }}
                        >
                          {" "}
                          <i class="far fa-times-circle"></i> REMOVE
                        </Link>
                      </Grid.Column>
                    </Grid>
                  </Grid.Column>
                </Grid>
              </Grid.Column>
              <Grid.Column width={2} className="cart-border-left">
                <p>{item.quantity}</p>
              </Grid.Column>
              {/* <Grid.Column width={2} className="cart-border-left">
                <p>{order.quantity}</p>
              </Grid.Column> */}
              <Grid.Column width={3} className="cart-border-left">
                <p>₦{` ${monetize(item.price)}`}</p>
              </Grid.Column>
              <Grid.Column width={3} className="cart-border-left">
                <p>₦{` ${monetize(item.price * item.quantity)}`}</p>
              </Grid.Column>
            </Grid>
          </div>
        );
      })}
      <Grid>
        <Container textAlign="right" className="cart-total-section">
          <span className="cart-total">
            {" "}
            TOTAL:{" "}
            <span className="cart-totals">
              ₦{props.totalPrice != null ? ` ${monetize(props.totalPrice)}` : 0}
            </span>{" "}
          </span>
          <p className="cart-delivery-notice">Delivery fee not included</p>
          <Link to="/" className="cart-button-link">
            <span>
              <button className="cart-button cart-continue-shoping">
                CONTINUE SHOPPING
              </button>
            </span>
          </Link>
          <Link to="/checkout" className="cart-button-link">
            <span>
              <button className="cart-button cart-proceed-checkout">
                PROCEED TO CHECKOUT
              </button>
            </span>
          </Link>
        </Container>
      </Grid>
    </Container>
  );
}
export default CartLayout;
