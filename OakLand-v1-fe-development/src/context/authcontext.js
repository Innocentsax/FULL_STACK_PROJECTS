import React, { createContext, useCallback, useEffect, useState } from "react";
import {
  apiGet,
  apiGetAuthorization,
  apiPost,
  apiPostAuthorization,
  apiPut,
  apiDeleteAuthorization
} from "../utils/api/axios";
import {
  errorNotification,
  successNotification,
} from "../components/Notification";
import jwt_decode from "jwt-decode";
import Swal from 'sweetalert2';
import { decodeJwt, redirectToUserPage } from "../utils/roleUrlRouter";

export const dataContext = createContext();

const DataProvider = ({ children }) => {
  const [getUser, setGetUser] = React.useState({});
  const [getTrx, setGetTrx] = React.useState([]);
  const [getWallet, setGetWallet] = React.useState({});
  const [getAddressbook, setGetAddressbook] = React.useState([]);
  const [getIsDefault, setIsDefault] = React.useState(false);
  const [getAddress, setGetAddress] = React.useState({});
  const [newAddress, setNewAddress] = React.useState({});

  const [cartItems, setCartItems] = React.useState(null);
  const [verifyReg, setVerifyReg] = React.useState({});
  const [getTransDetail, setGetTransDetail] = React.useState({});
  const [getWalletDetails, setGetWalletdetails] = React.useState({});
  const [getTransactions, setGetTransactions] = React.useState([]);
  const[pageNumber, setPageNumber] = useState(0)
  const[pageElementSize, setPageElementSize] = useState(0)
  const[totalPages, setTotalPages] = useState(0)
  const[totalElements, setTotalElements] = useState(0)
  const[numOfElements, setNumOfElements] = useState(0)
  const [pickupCentersInState, setPickupCentersInState] = useState([]);
  const [allStates, setAllStates] = useState([]);
  const [pickupCenterByEmail, setPickupCenterByEmail] = useState(null);
  const[localStorageValue, setLocalStorageValue] = useState(false);
  const [openOrders, setOpenOrders] = useState(null);
  const [openOrdersPageNumber, setOpenOrdersPageNumber] = useState(0);
  const[openOrdersPageElementSize, setOpenOrdersPageElementSize] = useState(0)
  const[openOrdersTotalPages, setOpenOrdersTotalPages] = useState(0)
  const[openOrdersTotalElements, setOpenOrdersTotalElements] = useState(0)
  const[openOrdersNumOfElements, setOpenOrdersNumOfElements] = useState(0)
  const [orderStatus, setOrderStatus] = useState("");
  const [closedOrders, setClosedOrders] = useState(null);
  const [closedOrdersPageNumber, setClosedOrdersPageNumber] = useState(0);
  const[closedOrdersPageElementSize, setClosedOrdersPageElementSize] = useState(0)
  const[closedOrdersTotalPages, setClosedOrdersTotalPages] = useState(0)
  const[closedOrdersTotalElements, setClosedOrdersTotalElements] = useState(0)
  const[closedOrdersNumOfElements, setClosedOrdersNumOfElements] = useState(0)
  const [bestSelling, setBestSelling] = useState([]);
  const [newArrival, setNewArrival] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
// localStorage.getItem("role") === "CUSTOMER" || localStorage.length == 0)
  const [showNavbar, setShowNavbar] = useState(true)
  const[itemCount, setItemCount] = useState(0)



  /**==============Registration======= **/
  const registerConfig = async (formData) => {
    try {
      const registerData = {
        firstName: formData.firstName,
        lastName: formData.lastName,
        email: formData.email,
        password: formData.password,
        gender: formData.gender,
        date_of_birth: formData.date_of_birth,
        phoneNumber: formData.phoneNumber,
        address: formData.address,
      };
      await apiPost("customer/signup", registerData).then((res) => {
        successNotification(res.data.data);
        console.log(res.data.data);
        setTimeout(() => {
          window.location.href = "/check-mail";
        }, 1500);
      });
    } catch (err) {
      errorNotification(err.response.data.message);
      console.log(err.response.data.message);
    }
  };


 const ForgottenConfig = async (formData) => {
   try {
     const registerData = { 
       email: formData.email,
     };
     await apiPost("auth/forgot-password-request", registerData).then((res) => {
       successNotification(res.data);
       console.log(res.data.data);
       setTimeout(() => {
         window.location.href = "/forgotpassword";
       }, 1500);
     });
   } catch (err) {
     errorNotification(err.response.data.message);
     console.log(err.response.data.message);
   }
 };



  /**==============OTP Verification ======= **/
  const OTPConfig = async (formData, signature) => {
    try {
      const otpData = {
        otp: formData,
      };

      await apiPost(`/users/verify/${signature}`, otpData).then((res) => {
       
        setTimeout(() => {
          window.location.href = "/login";
        }, 2000);
      });
    } catch (err) {
    }
  };


  /**==============Login ======= **/
  const LoginConfig = async (formData, location, navigate) => {
    try {
      const LoginData = {
        email: formData.email,
        password: formData.password,
      };
      await apiPost("auth/login", LoginData)
        .then((res) => {
          if(res.data.message === 'Login Successful'){
            successNotification(res.data.message);
            console.log(res.data.message);
            const jwtInfo = decodeJwt(res.data.data);   
            localStorage.setItem("signature", res.data.data);
            localStorage.setItem("role", jwtInfo.roles);
            GetUser()

            setLocalStorageValue(localStorage.getItem("signature"))
            redirectToUserPage(location, navigate, jwtInfo.roles)
          }
          else{
            successNotification(res.data.message);
            setTimeout(() => {
              window.location.href = "/login"
            }, 1500);
          }  
        })
        .catch((err) => {
          console.log(err.response.data);
          errorNotification(err.response.data);
        });
    } catch (err) {
      console.log(err.response.data.message);
      errorNotification(err.response.data.message);
    }
  };

  /**==============Admin Login ======= **/

  const AdminLoginConfig = async (formData, location, navigate) => {
    try {
      const LoginData = {
        email: formData.email,
        password: formData.password,
      };
      await apiPost("auth/login", LoginData)
        .then((res) => {
          if(res.data.message === 'Login Successful'){
            successNotification(res.data.message);
            console.log(res.data.message);
            const jwtInfo = decodeJwt(res.data.data);   
            localStorage.setItem("signature", res.data.data);
            localStorage.setItem("role", jwtInfo.roles);
            setLocalStorageValue(localStorage.getItem("signature"))
            redirectToUserPage(location, navigate, jwtInfo.roles)
          }
          else{
            setTimeout(() => {
              errorNotification(res.data.message);
              window.location.href = "/admin/login"            
            }, 1500);
          }  
        })
        .catch((err) => {
          console.log(err.response.data);
          errorNotification(err.response.data);
        });
    } catch (err) {
      console.log(err.response.data.message);
      errorNotification(err.response.data.message);
    }
  };

   /**============= Add to Cart ======= **/
    const AddToCartConfig = async (productId, data) => {
      try {
      await apiPostAuthorization(`customer/cart/item/add/${productId}`, data)
        .then((res) => {
          successNotification(res.data);
          console.log(res.data);
          setItemCount(itemCount + 1);
      })
        .catch((err) => {
          console.log(err.response.data.message);
          errorNotification(err.response.data.message);
        });
    } catch (err){
      console.log(err.response.data.message);
      }
    };


   /**============= Remove Item From Cart ======= **/
   const RemoveItemFromCartConfig = async (itemId) => {
    try {
    await apiDeleteAuthorization(`customer/cart/item/delete/${itemId}`)
      .then((res) => {
        successNotification(res.data);
        console.log(res.data);
        setItemCount(itemCount - 1)
        GetAllCartItems()
        // setTimeout(() => {
        //   window.location.href = "/shopping-cart";
        // }, 2000);
    })
      .catch((err) => {
        console.log(err.response.data.message);
        errorNotification(err.response.data.message);
      });
  } catch (err){
    console.log(err.response.data.message);
    }
  };

     /**============= Increase Item Quantity in cart ======= **/
     const IncreaseItemQuantityConfig = async (productId) => {
      try{
        await apiPut(`customer/cart/item/add-to-quantity/${productId}`)
          .then((res) => {
            successNotification(res.data);
            const index = cartItems.items.findIndex(item=>item.product.id === productId)
            setCartItems(prev=>{
              prev.items[index].orderQty = prev.items[index].orderQty + 1
              return prev;
            });
            
            GetAllCartItems()
            // setTimeout(() => {
            //   window.location.href = "/shopping-cart";
            // }, 2000);
            console.log(res.data);

        })
          .catch((err) => {
            console.log(err.response.data.message);
            errorNotification(err.response.data.message);
          });
      } catch (err){
        console.log(err.response.data.message);
      }
    }

     /**============= Decrease Item Quantity in cart ======= **/
     const ReduceFromItemQuantityConfig = async (productId) => {
      try {
      await apiPut(`customer/cart/item/reduce-quantity/${productId}`)
        .then((res) => {
          successNotification(res.data);
          GetAllCartItems()
          // setTimeout(() => {
          //   window.location.href = "/shopping-cart";
          // }, 2000);
          console.log(res.data);
      })
        .catch((err) => {
          console.log(err.response.data.message);
          errorNotification(err.response.data.message);
        });
    } catch (err){
      console.log(err.response.data.message);
      }
    };


    /**=============Get all Cart Items ======= **/
  const GetAllCartItems = async () => {
    try {
      await apiGetAuthorization(`customer/cart/view`).then((res) => {
        setCartItems(res.data);
        setItemCount(res.data.items.length)
        console.log("cart",res.data.items);
        
      });
    } catch (err) {
      console.log(err.response.data.message);
    }
  };

   /**============= Clear Cart ======= **/
   const ClearCartConfig = async () => {
    try {
    await apiDeleteAuthorization(`customer/cart/clear`)
      .then((res) => {
        successNotification(res.data);
        setItemCount(0)
        // GetAllCartItems()
        // setTimeout(() => {
        //   window.location.href = "/shopping-cart";
        // }, 2000);
        setCartItems(null)
        console.log(res.data);
    })
      .catch((err) => {
        console.log(err.response.data.message);
        errorNotification(err.response.data.message);
      });
  } catch (err){
    console.log(err.response.data.message);
    }
  };

  /**==============Logout ======= **/
  const Logout = () => {
    localStorage.clear();
    window.location.href = "/login";
  };

  // ===================Get User========================
  const GetUser = async () => {
    try {
      await apiGetAuthorization(`customer/view-profile`).then((res) => {
        setGetUser(res.data);
        //console.log(res.data);
      });
    } catch (err) {
      console.log(err);
    }
  };


  const GetWallet = async () => {
    try {
      await apiGetAuthorization(`customer/wallet/info`).then((res) => {
        setGetWallet(res.data.data);
        //console.log(res.data.data);
      });
    } catch (err) {
      console.log(err);
    }
  };

  // ==================Update profile=================
  const updateUserConfig = async (formData) => {
    try {
      const updateData = {
        firstName: formData.firstName,
        lastName: formData.lastName,
        email: formData.email,
        date_of_birth: formData.date_of_birth,
        phone: formData.phone,
        address: formData.address
      };
      await apiPut("customer/edit-profile", updateData).then((res) => {
        successNotification(res.data);
        console.log(res.data);
      });
    } catch (err) {
      errorNotification(err.response.data.message);
      console.log(err.response.data.message);
    }
  };

  // ==============Update password=====================

  const updatePasswordConfig = async (passwordData) => {
    try {
      const updatePasswordData = {
        oldPassword: passwordData.currentPassword,
        newPassword: passwordData.newPassword,
        confirmNewPassword: passwordData.confirmNewPassword
      };
      await apiPut("auth/update-password", updatePasswordData).then((res) => {
        successNotification(res.data.message);
        console.log(JSON.stringify(res.data));
      });
    } catch (err) {
      errorNotification(err.response.data.message);
      console.log(err.response.data);
    }
  };



  // =================New Address====================

  const CreateAddress = async (formData) => {
    try{
      const addressData = {
        fullName: formData.fullName,
        phone: formData.phone,
        emailAddress: formData.email,
        street: formData.street,
        state: formData.state,
        country: formData.country
      }
      await apiPostAuthorization('address/new', addressData).then((res) => {
        successNotification(res.data)
        console.log(res.data)
        setTimeout(() => {
          window.location.href = "/addressbook";
        }, 500);
      })
    }catch(err){
      errorNotification(err.response.data)
      console.log(err.response.data)
    }
  }

  /**=============Get Addressbook ======= **/
  const GetAddressbook = async () => {
    try {
      await apiGetAuthorization("address/get").then((res) => {
        setGetAddressbook(res.data);
        console.log(res.data);
        
      });
    } catch (err) {
      console.log(err);
    }
  };

  // =================Get Address=================================

  const GetAddress = async (id) => {
    try{
      await apiGetAuthorization(`address/view?id=${id}`).then((res) => {
        setGetAddress(res.data)
        console.log(res.data)
      })
    }catch(err) {
      console.log(err.response.data)
    }
  }

 // =================Delete Address=================================

 const DeleteAddress = (id) => {
  try{
    apiDeleteAuthorization(`address/delete?id=${id}`).then((res) => {
      successNotification(res.data)
      console.log(res.data)
    })
  }catch(err){
    errorNotification(err.response.data)
    console.log(err.response.data)
  }
  setTimeout(() => {
    window.location.href = "/addressbook";
  }, 500);
}

    // =================Update Address=================================

    const UpdateAddress = async (id, formData) => {
      const addressData = {
        fullName: formData.fullName,
        phone: formData.phone,
        emailAddress: formData.emailAddress,
        street: formData.street,
        state: formData.state,
        country: formData.country
      }
      try{
        await apiPut(`address/update?id=${id}`, addressData).then((res) => {
          successNotification(res.data);
          console.log(res.data)
        })
      }catch(err) {
        errorNotification(err.response.data);
        console.log(err.response.data)
      }
      setTimeout(() => {
        window.location.href = "/addressbook";
      }, 500);
    }

// ====================Set Default======================

const SetDefault = (id) => {
  try{
    apiGetAuthorization(`address/setDefault?id=${id}`).then((res) => {
      setIsDefault(res.data)
      console.log(res.data)
      successNotification(res.data)
    })
  }catch(err){
    errorNotification(err.response.data)
    console.log(err.response.data)
  }
}

// ====================Process Payment======================

const ProcessPayment = async (formData) => {
  const paymentForm = {
    amount: formData.amount
  }

  try{
    await apiPostAuthorization('pay', paymentForm).then((res) => {
      setTimeout(() => {
        window.open(res.data.data.authorization_url, '_blank', 'noreferrer');
      }, 1500);
    })
  }catch(err){
    errorNotification(err.response.data)
    console.log(err.response.data)
  }
  
}


// ====================Confirm Payment======================

const FinalizePayment = async (reference) => {
  try{
    await apiGet(`finalizeTrans?reference=${reference}`).then((res) => {
      setGetTransDetail(res.data)
      console.log(res.data)
    })

  }catch(err){
    setGetTransDetail(err.response.data)
    console.log(err.response.data)
  }
}


// ====================Get Wallet Info======================

const WalletDetails = async () => {
  try{
    await apiGetAuthorization('customer/wallet/info').then((res) => {
      setGetWalletdetails(res.data.data);
      console.log(res.data.data);
    })
  }catch(err){
    console.log(err.response.data)
  }
}


// ===========================GET WALLET TRX===================================

const FetchTrx = useCallback(() => {
    try{
       apiGetAuthorization(`customer/wallet/transactions?pageNo=${pageNumber}`).then((res) => {
        const data = res.data
                    setGetTrx(data.content)
                    setPageNumber(data.number)
                    setPageElementSize(data.size)
                    setTotalPages(data.totalPages)
                    setTotalElements(data.totalElements)
                    setNumOfElements(data.numberOfElements)
        console.log(getTrx)
      })
    }catch(err){
      console.log(err.response.data.message)
    }

}, [pageNumber])


useEffect(() => {
  FetchTrx()
}, [FetchTrx])


   // ====================Get All Open Orders======================
   const GetAllOpenOrders = useCallback(() => {
    try{
      apiGetAuthorization(`customer/order/pickup-status?status=YET_TO_BE_PICKED_UP&pageNo=${openOrdersPageNumber}`).then((res) => {
        const data = res.data;
        setOpenOrders(data.content);
        setOpenOrdersPageNumber(data.number)
        setOpenOrdersPageElementSize(data.size)
        setOpenOrdersTotalPages(data.totalPages)
        setOpenOrdersTotalElements(data.totalElements)
        setOpenOrdersNumOfElements(data.numberOfElements)
        console.log(res.data);
      })
    }catch(err){
      console.log(err.response.data);
    }
  }, [openOrdersPageNumber])

  useEffect(() => {
    GetAllOpenOrders();
  }, [GetAllOpenOrders])


     // ====================Get All Closed Orders======================
     const GetAllClosedOrders = useCallback(() => {
      try{
        apiGetAuthorization(`customer/order/pickup-status?status=PICKED_UP&pageNo=${closedOrdersPageNumber}`).then((res) => {
          const data = res.data;
          setClosedOrders(data.content);
          setClosedOrdersPageNumber(data.number)
          setClosedOrdersPageElementSize(data.size)
          setClosedOrdersTotalPages(data.totalPages)
          setClosedOrdersTotalElements(data.totalElements)
          setClosedOrdersNumOfElements(data.numberOfElements)
          console.log(res.data);
        })
      }catch(err){
        console.log(err.response.data);
      }
    }, [closedOrdersPageNumber])
  
    useEffect(() => {
      GetAllClosedOrders();
    }, [GetAllClosedOrders])

// const FetchTrx = async () => {
//   try{
//     await apiGetAuthorization('customer/wallet/transactions').then((res) => {
//       setGetTrx([...res.data.content]);
//       console.log(res.data.content)
//       console.log(getTrx)
//     })
//   }catch(err){
//     console.log(err.response.data.message)
//   }
// }



  // ====================VerifyRegistration======================
  const VerifyReg = async (token) => {
    setIsLoading(true)
    try{
      await apiGet(`customer/verifyRegistration?${token}`).then((res) => {
        setVerifyReg(res.data)
        console.log(res.data)
        setIsLoading(false)
      })

    }catch(err){
      setVerifyReg(err.response.data)
      console.log(err.response.data)
      setIsLoading(false)
    }
  }

  // ====================Get Pickup Center State======================
  const GetPickUpCentersByStateConfig = async (pickup) => {
    if(pickup === "") return "";
    
    try{
        const {data} = await apiGetAuthorization(`pickup/state/${pickup}`);
        setPickupCentersInState(data)
        console.log(data)
    } catch (error) {
        console.log(error)
    }
  }

    // ====================Get All States======================
    const GetAllStatesConfig = async () => {
      try{
        await apiGetAuthorization(`state/view-all`).then((res) => {
          setAllStates(res.data);
          console.log(res.data);
        })
      }catch(err){
        console.log(err.response.data);
      }
    }

    // ===================Get Pickup Center By Email ================//
    const GetPickupCenterByEmailConfig = async (email) => {
      try{
        await apiGetAuthorization(`pickup/${email}`).then((res) => {
          setPickupCenterByEmail(res.data);
          console.log(res.data);
        })
      }catch(err){
        console.log(err.response.data);
      }
    }

    /**==============Save New Order======= **/
  const OrderConfig = async (formData) => {
    try {
      const saveOrderData = {
        grandTotal: formData.grandTotal,
        pickupCenterEmail: formData.pickupCenterEmail,
      };
      await apiPostAuthorization(`customer/order/new`, saveOrderData).then((res) => {
        Swal.fire({title: res.data,
          text: 'Your payment was received successfully',
          icon: 'success',
          confirmButtonText: 'Okay',
          confirmButtonColor: '#28a745',
          background: '#e9f5e9',
          padding: '1.25rem',
          borderRadius: '.25rem',
          customClass: {
            title: 'text-xl font-medium',
            content: 'text-base font-light',
            confirmButton: 'bg-green-500 text-white px-5 py-2',
          },
        });
        console.log(res.data);
        setTimeout(() => {
          window.location.href = "/open-orders";
        }, 2000);
      });
    } catch (err) {
      Swal.fire({
        title: err.response.data.message,
        text: 'There was a problem with processing your transaction. Please try again later.',
        icon: 'error',
        showConfirmButton: false,
        timer: 2000,
        customClass: {
          popup: 'swal2-popup-failure'
        }
      });
      console.log(err.response.data.message);
    }
  };



  // =====================Best selling====================

  const BestSelling = async () => {
    try{
      await apiGet('products/best-selling').then((res) => {
          setBestSelling(res.data)
          console.log(res.data);
      })
    }catch(err){
      console.log(err.response.message)
    }
  }


  // ================New Arrival======================

  const NewArrival = async () => {
    try{
      await apiGet('products/new-arrival').then((res) => {
        setNewArrival(res.data)
      })
    }catch(err){
      console.log(err.response.message)
    }
  }

  return (
    <dataContext.Provider
      value={{
        registerConfig,
        updateUserConfig,
        updatePasswordConfig,
        ForgottenConfig,
        LoginConfig,
        Logout,
        GetUser,
        GetWallet,
        getUser,
        getWallet,
        setGetUser,
        setGetWallet,
        GetAddressbook,
        getAddressbook,
        CreateAddress,
        newAddress,
        AddToCartConfig,
        IncreaseItemQuantityConfig,
        ReduceFromItemQuantityConfig,
        cartItems,
        GetAllCartItems,
        RemoveItemFromCartConfig, 
        ClearCartConfig,
        verifyReg,
        VerifyReg,
        GetAddress,
        setGetAddress,
        getAddress,
        DeleteAddress,
        UpdateAddress,
        SetDefault,
        getIsDefault,
        ProcessPayment,
        FinalizePayment,
        getTransDetail,
        getWalletDetails,
        WalletDetails,
        // GetTransactions,
        getTransactions,
        getTrx,
        FetchTrx,
        pageElementSize, 
          totalPages, 
          pageNumber, 
          setPageNumber, 
          totalElements,
          numOfElements,
          WalletDetails,
          GetPickUpCentersByStateConfig,
          pickupCentersInState,
          GetAllStatesConfig,
          allStates,
          GetPickupCenterByEmailConfig,
          pickupCenterByEmail,
          OrderConfig,
          localStorageValue,
          GetAllOpenOrders,
          openOrders,
          openOrdersPageNumber,
          openOrdersPageElementSize,
          openOrdersTotalPages,
          openOrdersTotalElements,
          openOrdersNumOfElements,
          orderStatus,
          setOpenOrdersPageNumber,
          GetAllClosedOrders,
          closedOrders,
          closedOrdersPageNumber, 
          setClosedOrdersPageNumber,
          closedOrdersPageElementSize,
          closedOrdersTotalPages,
          closedOrdersTotalElements,
          closedOrdersNumOfElements,
          localStorageValue,
          BestSelling,
          bestSelling,
          NewArrival,
          newArrival,
          setShowNavbar,
          showNavbar,
          itemCount,
          isLoading,
          setIsLoading,
      }}
    >
      {children}
    </dataContext.Provider>
  );
};

export const useAuth = () => {
  const context = React.useContext(dataContext);
  if (context === "undefined") {
    throw new Error("useAuth must be used within the auth provider");
  }
  return context;
};

export default DataProvider;
