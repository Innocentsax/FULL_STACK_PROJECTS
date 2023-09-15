const BASEURL = "http://localhost:8080/api/v1/"



export default  {
    ///ADMIN APIs
    ADMIN_PRODUCT_OPERATIONS_API : `${BASEURL}admin/products/`, 
    //PUT -> ; PATCH -> upload image ; DELETE -> delete product ; PUT -> update product
    ADMIN_GET_ORDERS_API : `${BASEURL}orders/`,


    //CART APIs
    CUSTOMER_CART_OPERATIONS_API : `${BASEURL}customer/carts/`, // POST
   
    //CHECKOUT APIs
    CUSTOMER_CHECKOUT_API : `${BASEURL}customer/checkout`,

    //FAVORITE APIs
    //---

    //SIGN IN APIs
    SIGN_IN_CONTROLLER_API : `${BASEURL}login`,
    SIGN_OUT_CONTROLLER_API : `${BASEURL}log-out`,


    //CREATE ACCOUNT APIs
    CREATE_ACCOUNT_API : `${BASEURL}users`,
    ADD_ADDRESS_TO_ACCOUNT_API : `${BASEURL}users`,


    //PRODUCT APIs
    FETCH_PRODUCTS_API : `${BASEURL}products/`
}