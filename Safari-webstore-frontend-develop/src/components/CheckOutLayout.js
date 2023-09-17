import React, { useEffect, useState } from "react";
import productApis from "../apis/ProductApi";
import { monetize } from "../utilities/helperFunction";
import jwt_decode from "jwt-decode";
import { getTokenn } from "../utilities/TokenStorageUtils";
import OrderApi from "../apis/OrderApi";
import { Link } from "react-router-dom";
import dateDelivered from "../utilities/customHook";

import {
  Grid,
  Container,
  Segment,
  Button,
  Checkbox,
  Form,
} from "semantic-ui-react";
import "../styles/Components/_checkout_layout.scss";
import { getToken } from "../utilities/TokenStorageUtils";

const state = [
  { key: "Edo", text: "Edo", value: "Edo" },
  { key: "Lagos", text: "Lagos", value: "Lagos" },
  { key: "Abuja", text: "Abuja", value: "Abuja" },
  { key: "Osun", text: "Osun", value: "Osun" },
  { key: "Oyo", text: "Oyo", value: "Oyo" },
  { key: "Ogun", text: "Ogun", value: "Ogun" },
  { key: "Ondo", text: "Ondo", value: "Ondo" },
  { key: "Ekiti", text: "Ekiti", value: "Ekiti" },
  { key: "Kwara", text: "Kwara", value: "Kwara" },
  { key: "Anambra", text: "Anambra", value: "Anambra" },
  { key: "Enugu", text: "Enugu", value: "Enugu" },
  { key: "Imo", text: "Imo", value: "Imo" },
];

const city = [
  { key: "Edo", text: "Edo", value: "Edo" },
  { key: "Lagos", text: "Lagos", value: "Lagos" },
  { key: "Abuja", text: "Abuja", value: "Abuja" },
  { key: "Porthacourt", text: "Porthacourt", value: "Porthacourt" },
  { key: "Aba", text: "Aba", value: "Aba" },
];

function CheckOutLayout() {
  const [cart, setCart] = useState([]);

  const [totalCartPrice, setTotalCartPrice] = useState(0);

  const [deliveryFee, setDeliveryFee] = useState(0);

  const [payOnDelivery, setPayOnDelivery] = useState(false);

  const [isGift, setIsgift] = useState(false);

  const [giftButtonText, setGiftButtonText] = useState("No");

  const [arrayOfId, setArrayOfIds] = useState([]);

  const [paymentType, setPaymentType] = useState("");

  const [itemQuantity, setItemQuantity] = useState(0);

  const [defaultShippingAddress, setDefaultShippingAddress] = useState(false);

  const [cardPercentage, setCardPercentage] = useState(0);

  //CLASSNAME initialization
  const giftActiveClass = isGift ? "gift-yes " : "gift-no";

  // handling states for selection
  const [stateLocation, setStateLocation] = useState("");
  const [province, setProvince] = useState("");

  //initializing data **************************************
  const intitialState = {
    email: "",
    fullName: "",
    address: "",
    phoneNumber: "",
  };
  const [formData, setFormData] = useState(intitialState);
  //initializing data **************************************

  //CALCULATION totalPrice**************************************

  let total = totalCartPrice - cardPercentage + deliveryFee;
  //CALCULATION totalPrice**************************************

  useEffect(async () => {
    const cartItem = await productApis.getCartItem();

    setCart(cartItem);

    const totalPrice = cartItem.length
      ? cartItem[cartItem.length - 1].total
      : 0;
    setTotalCartPrice(totalPrice);

    const arrayIds = cartItem.map((each) => each.id);

    //set arrayIds

    setArrayOfIds(arrayIds);

    //  qty= cartItem.quantity;
    // setitemQuantity(qty);
  }, []);

  const handleOnChangeOnShippingAddress = (evt, data) => {
    let checked = data.checked;
    checked
      ? setDefaultShippingAddress(true)
      : setDefaultShippingAddress(false);
  };

  const incrementQuality = (product) => {
    const exist = cart.find(
      (eachProduct) => eachProduct.productId === product.productId
    );

    if (exist) {
      let total = 0;

      const updatedCart = cart.map((item) => {
        total += item.price * Number(item.quantity);
        return item.productId === exist.productId
          ? { ...exist, quantity: Number(exist.quantity) + 1, total }
          : item;
      });

      setTotalCartPrice(total);

      setCart(updatedCart);
    }
  };

  const decrementQuality = (product) => {
    const exist = cart.find(
      (eachProduct) => eachProduct.productId === product.productId
    );

    if (exist && exist.quantity !== 1) {
      let total = 0;

      const updatedCart = cart.map((item) => {
        total += item.price * Number(item.quantity);
        return item.productId === exist.productId
          ? { ...exist, quantity: Number(exist.quantity) - 1, total }
          : item;
      });

      setTotalCartPrice(total);

      setCart(updatedCart);
    }
  };

  const handleCardPercentage = () => {
    let cardPercent = totalCartPrice * 0.05;
    setCardPercentage(cardPercent);
  };

  const handlePaymentDelivery = (e) => {
    if (e.target.value === "delivery") {
      setPayOnDelivery(true);
      setDeliveryFee(2000);
      setPaymentType("Pay on delivery");
      setCardPercentage(0);
    } else {
      setPayOnDelivery(false);
      setDeliveryFee(0);
      setPaymentType("Pay with card");
      handleCardPercentage();
    }
  };

  //ISGIFT BUTTON **************************************
  const handleClick = () => {
    giftButtonText === "Yes"
      ? setGiftButtonText("No")
        ? setIsgift(true)
        : setIsgift(false)
      : setGiftButtonText("Yes")
      ? setIsgift(false)
      : setIsgift(true);
  };
  //ISGIFT BUTTON **************************************

  /**  Handling form submission
   *
   * ***/

  const handleOnchange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleOnSubmit = async (event) => {
    event.preventDefault();
    console.log(arrayOfId);
    const idArray = [];
    for (let i = 0; i < arrayOfId.length; i++) {
      idArray.push(arrayOfId[i]);
    }

    const clearFields = () => {
      setDeliveryFee(0);
      setTotalCartPrice(0);
      setDeliveryFee(0);
      setPayOnDelivery(false);
      setGiftButtonText("No");
      setArrayOfIds([]);
      setPaymentType("");
      setDefaultShippingAddress(false);
      formData.email = "";
      formData.fullName = "";
      formData.phoneNumber = "";
      formData.address = "";
    };

    const data = {
      cardDiscount: cardPercentage,
      cartItemIds: idArray,
      costOfProducts: totalCartPrice,
      dateOrdered: dateDelivered(),
      dateDelivered: "null",
      deliveryFee: deliveryFee,
      deliveryMethod: "card",
      isGift: isGift,
      paymentType: paymentType,
      price: 0,
      quantity: itemQuantity,
      shippingAddress: {
        address: formData.address,
        city: province,
        email: formData.email,
        fullName: formData.fullName,
        isDefaultShippingAddress: defaultShippingAddress,
        phone: formData.phoneNumber,
        state: stateLocation,
      },
      status: "PENDING",
      totalCost: total,
    };
    console.log("data", data);

    const result = await OrderApi.placeUserOrders(data);
    console.log("Result", result);

    //save orderId in the localstorage
    localStorage.setItem("order", JSON.stringify(result));

    //redirect to new page
    window.location.href = "/confirm-order/" + result.data.orderId;
  };

  return (
    <Container fluid className="checkout-container">
      <Grid padded className="checkout">
        <Grid.Column
          mobile={16}
          tablet={8}
          widescreen={8}
          computer={8}
          largeScreen={6}
        >
          <Segment>
            <Grid className="delivery-method">
              <Grid.Column width={3}>
                <img
                  src="./images/shipping-address-icon.png"
                  alt="shipping-address-icon"
                  className="delivery-check-icon checkicon"
                />
              </Grid.Column>
              <Grid.Column width={13}>
                <h4 className="">Shipping Address</h4>
              </Grid.Column>
            </Grid>

            <Form>
              <Form.Field>
                <label>Email</label>
                <input name="email" onChange={handleOnchange} />
              </Form.Field>

              <Form.Field>
                <label>Full Name</label>
                <input name="fullName" onChange={handleOnchange} />
              </Form.Field>

              <Form.TextArea
                label="Address"
                name="address"
                onChange={handleOnchange}
              />
              <Form.Select
                fluid
                label="State/Province"
                name="province"
                onChange={(e) => {
                  setProvince(e.target.firstChild.innerText);
                }}
                options={state}
              />

              <Form.Select
                fluid
                label="State"
                name="state"
                onChange={(e) => {
                  setStateLocation(e.target.firstChild.innerText);
                }}
                options={city}
              />

              <Form.Field>
                <label>Phone Number</label>
                <input name="phoneNumber" onChange={handleOnchange} />
              </Form.Field>
              <Form.Field>
                <Checkbox
                  toggle
                  label="Set as Default Shipping Address"
                  onClick={(evt, data) =>
                    handleOnChangeOnShippingAddress(evt, data)
                  }
                />
              </Form.Field>
            </Form>
          </Segment>
        </Grid.Column>

        {/* Midle Column starts */}

        <Grid.Column
          mobile={16}
          tablet={8}
          widescreen={8}
          computer={8}
          largeScreen={5}
        >
          <Segment>
            <Grid className="delivery-method">
              <Grid.Column width={3}>
                <img
                  src="./images/check.png"
                  alt=""
                  className="delivery-check-icon"
                ></img>
              </Grid.Column>
              <Grid.Column width={13}>
                <h4 className="">Delivery Method</h4>
              </Grid.Column>
            </Grid>
            <Grid>
              <Grid.Column width={5}>
                <Form.Group inline>
                  <Form.Radio
                    className="checkout-amount"
                    label="₦2, 000"
                    value="2000"
                    checked={payOnDelivery}
                  />
                </Form.Group>
              </Grid.Column>
              <Grid.Column width={11}>
                <span className="door door-fee">Delivery fee</span>{" "}
                <span className="door door-delivery">Door Delivery</span>
              </Grid.Column>
            </Grid>
          </Segment>
          <Segment onChange={handlePaymentDelivery}>
            <h4 className="order-summary">Payment Methods</h4>
            <Form.Group inline>
              <label>
                <input
                  className="checkout-amount-checkbox"
                  type="radio"
                  value="card"
                  name="payment"
                />
                <span className="payment-method-text">Pay with card</span>
              </label>
            </Form.Group>
            <p className="checkout-discount">
              (Get 5% off total price and money back guarantee)
            </p>
            <p className="checkout-redirect-notice">
              You will be redirected to Paystack payment gateway
            </p>
            <Form.Group inline>
              <label>
                <input
                  className="checkout-amount-checkbox"
                  type="radio"
                  value="delivery"
                  name="payment"
                />
                <span className="payment-method-text">Pay on delivery</span>
              </label>
            </Form.Group>
            <li className="checkout-pod-notice">
              Kindly note that we will only accept POS payment option on
              delivery
            </li>
            <li className="checkout-pod-notice pod">
              You have to make payment before opening package
            </li>
            <li className="checkout-pod-notice pod pod-p">
              Once the seal is broken, item can only be returned if damaged or
              defective{" "}
            </li>
          </Segment>
        </Grid.Column>
        <Grid.Column
          mobile={16}
          tablet={16}
          widescreen={8}
          computer={8}
          largeScreen={5}
        >
          <Segment>
            <h4 className="order-summary">ORDER SUMMARY</h4>

            {cart.map((product) => (
              <Grid key={product.productId}>
                <Grid.Column width={5}>
                  <img
                    style={{ width: "50px" }}
                    src={product.productImage}
                    alt={product.productName}
                    className="order-section-img"
                  />
                </Grid.Column>
                <Grid.Column width={11}>
                  <h4>{product.productName}</h4>
                  <span>Size: {product.size}</span>
                  <p>
                    {product.quantity} x ₦{monetize(product.price.toFixed(2))}
                  </p>
                  <span>
                    Qty
                    <button
                      onClick={() => {incrementQuality(product)
                      setItemQuantity(product.quantity)
                      console.log("qty", product.quantity)

                      }}
                      className=" qty qtyplus"
                    >
                      +
                    </button>
                    <input className="inputqty" value={product.quantity} />
                    <button
                      onClick={() => {
                        decrementQuality(product)
                      setItemQuantity(product.quantity)
                      console.log("qty", product.quantity)
                      }
                      }
                      className="qty qtyminus"
                    >
                      -
                    </button>
                  </span>
                </Grid.Column>
              </Grid>
            ))}

            <div className="order-summary-divider" />
            <Grid>
              <Grid.Column width={8}>
                <Container textAlign="left">
                  <p>Cart sub-total</p>
                  <p>Card discount</p>
                  <p>Delivery fee</p>
                </Container>
              </Grid.Column>
              <Grid.Column width={8}>
                <Container textAlign="right">
                  <p>₦ {monetize(totalCartPrice.toFixed(2))}</p>
                  <p>- ₦ {monetize(cardPercentage.toFixed(2))}</p>
                  <p>{deliveryFee}</p>
                </Container>
              </Grid.Column>
            </Grid>
            <div className="order-summary-divider-totals" />
            <Grid className="cart-checkout-totals">
              <Grid.Column width={8}>
                <Container textAlign="left">
                  <p>TOTAL</p>
                </Container>
              </Grid.Column>
              <Grid.Column width={8}>
                <Container textAlign="right">
                  <p className="checkout-total-cost">
                    ₦ {monetize(total.toFixed(2))}
                  </p>
                </Container>
              </Grid.Column>
            </Grid>
          </Segment>
          <Grid className="gift-section">
            <Grid.Column width={8}>
              <p className="gift-notice">Is this a gift?</p>
            </Grid.Column>
            <Grid.Column width={8}>
              <span>
                <button
                  className={giftActiveClass}
                  value="yes"
                  onClick={handleClick}
                >
                  {giftButtonText}
                </button>
              </span>{" "}
            </Grid.Column>
            <p className="gift-package">
              A complimentary gift receipt will be included in the package, and
              prices will be hidden on the receipt.
            </p>

            <button onClick={handleOnSubmit} className="place-order">
              PLACE ORDER
            </button>
          </Grid>
        </Grid.Column>
      </Grid>
    </Container>
  );
}

export default CheckOutLayout;
