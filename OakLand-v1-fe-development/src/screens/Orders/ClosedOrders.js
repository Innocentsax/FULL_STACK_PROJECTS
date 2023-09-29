import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import ReactPaginate from "react-paginate";
import { FaShoppingCart } from "react-icons/fa";
import { ArrowLeftTwoTone, ArrowRightTwoTone } from "@mui/icons-material";
import SideBar from '../../components/SideBar/SideBar';
import CheckoutCard from '../../components/CheckoutCard/CheckoutCard';
import { useAuth } from '../../context/authcontext';

const ClosedOrders = () => {
  const [screenSize, setScreenSize] = useState(window.innerWidth);
  const {  GetAllClosedOrders,
    closedOrders,
    closedOrdersPageNumber,
    closedOrdersPageElementSize,
    closedOrdersTotalPages,
    closedOrdersTotalElements,
    closedOrdersNumOfElements,
    orderStatus,
    setClosedOrdersPageNumber } = useAuth();
  const changePage = ({ selected }) => setClosedOrdersPageNumber(selected)

  const handleResize = () => {
    setScreenSize(window.innerWidth);
  }

  useEffect(() => {
    GetAllClosedOrders();
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  return (
    <div className='min-h-[100vh] md:px-[5%] lg:py-[5%] pl-2 pr-2 '>
      {screenSize > 768 ? (
        <div className='row mx-auto'>
          {/* Desktop layout */}
          <div className='col-md-4'>
            <SideBar />
            <p className='text-20xl text-red-700'></p>
          </div>

          <div className='col-md-8 bg-white drop-shadow-md rounded-md pb-5 divide-y'>
            <div className='divide-y'>
              <div className="border-gray-300 border-b-1 py-3">
                <h1 className='text-2xl font-bold-900'>Orders</h1>
                <div className='mt-3'>
                    <Link to="/open-orders" className="border-r border-black pr-3 hover:text-red-600">OPEN ORDERS</Link>
                    <Link to="/closed-orders" className='ml-3'>CLOSED ORDERS </Link>
                </div> 
              </div>

          {(closedOrders.length === 0) &&
              <div className='mx-2'>
                <div className='flex justify-center align-middle pb-2 pt-4'>
                  <FaShoppingCart className='text-5xl rounded-full text-[#917307]'/>
                </div>
                <div className='flex justify-center align-middle py-2 font-bold-900 text-2xl'>
                  <h1>You have no orders yet!</h1>
                </div>
                <div className='flex justify-center align-middle py-2 text-xl text-center'>
                  <h3>All your closed orders will be saved here for you to access their state anytime.</h3>
                </div>  
                <div className='flex justify-center align-middle py-3  text-xl'>
                  <Link to="/shop" className='bg-[#917307] text-white px-3 py-3 rounded-md drop-shadow-md hover:text-xl hover:bg-[#796006]'>CONTINUE SHOPPING</Link>
                </div>
              </div>
            }
            </div> 
{/* ==============Closed Orders Details***************===== */}
{ (closedOrders !== null) && closedOrders.map((order, index) =>  {
                const createdAt = order.transaction.createdAt;
                const updatedAt = order.transaction.updatedAt;

                const dateCreated = new Date(createdAt);
                const dateUpdated = new Date(updatedAt);

                const dateOptions = { year: 'numeric', month: 'short', day: 'numeric' };
                const dateCreatedFormatted = dateCreated.toLocaleDateString('en-US', dateOptions); 
                const dateUpdatedFormatted = dateUpdated.toLocaleDateString('en-US', dateOptions); 

                const timeOptions = { hour: 'numeric', minute: '2-digit' };
                const timeCreatedFormatted = dateCreated.toLocaleTimeString('en-US', timeOptions); 
                const timeUpdatedFormatted = dateUpdated.toLocaleTimeString('en-US', timeOptions); 
                return(
            <div className="row border-gray-300 border-b" key={index}>
              <h1 className="my-3 text-3xl font-extrabold ">Order Details</h1>
                  <div>           
                    <p>Date Ordered: {dateCreatedFormatted}</p>
                    <p>Time Ordered: {timeCreatedFormatted}</p>
                    <p>Date Pickedup: {dateUpdatedFormatted}</p>
                    <p>Time Pickedup: {timeUpdatedFormatted}</p>
                    <h1>Status : <span className='text-xl font-extrabold text-red-700'>Closed</span></h1>
                    <p>Transaction Id: {order.transaction.reference} <small className="text-red-700">(Please take note of this)</small></p>
                  </div> 
              <div className='col-md-6'>
                  <CheckoutCard title={`1. Your Order(${order.items.length} items)`} >
                    <div className='divide-y'>
                    {order.items.map((item, index) => {
                        return(
                          <div key={index} className="py-2 flex justify-between">
                            <div>
                              <p>{item.productName}</p>
                              <p className='text-gray-400'>Qty : {item.orderQty}</p>
                            </div>  

                            <p className='text-[#917307]'>₦{item.unitPrice * item.orderQty}</p>
                          </div>
                        )})} 
                      <div className="flex justify-between my-3 font-extrabold pt-3 border-b-2 border-gray-200">
                        <h1 className='text-2xl text-gray-800'>Total</h1>
                        <p>₦{order.grandTotal}</p>
                      </div>
                    </div>
                  </CheckoutCard>
              </div> 

              <div className='col-md-6'>
                <CheckoutCard title="2. PICKUP CENTER DETAILS">
                    <ul className="text-lg mt-0 px-3">
                      <li className="text-lg font-medium">- Name: {order.pickupCenter.name}</li>
                      <li className="text-lg font-medium">- Address: {order.pickupCenter.address}</li>
                      <li className="text-lg font-medium">- State: {order.pickupCenter.state.name}</li>
                      <li className="text-lg font-medium">- Phone-number: {order.pickupCenter.phone}</li>
                      <li className="text-lg font-medium">- Email: {order.pickupCenter.email}</li>
                    </ul>  
                </CheckoutCard>
              </div>

              <ReactPaginate 
                previousLabel={<ArrowLeftTwoTone />}
                nextLabel={<ArrowRightTwoTone />}
                pageCount={closedOrdersTotalPages} 
                onPageChange={changePage}
                containerClassName={"paginationBtns"}
                previousLinkClassName={"prevBtn"}
                nextLinkClassName={"nextBtn"}
                disabledClassName={"paginationDisabled"}
                activeClassName={"paginationActive"}
              /> 
            </div>
              )
            })} 
{/* ==============End of Closed Orders Details***************===== */}
          </div>
          
        </div>
      ) : (
        <div>
          {/* Mobile layout */}
        </div>
      )}

      
</div>
  );
}

export default ClosedOrders;
