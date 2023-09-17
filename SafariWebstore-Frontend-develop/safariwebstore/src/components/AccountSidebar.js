import React from 'react'
import '../stylesheet/accountsidebar.css'
import {Form, Button, ButtonGroup} from 'react-bootstrap'
export default function AccountSidebar() {
    return (
        <div className='acc-side-bar'> 
            <div className="top">
                <h5>ACCOUNT DASHBOARD</h5>
                <div className='row'>
                    <div className="line"><i className="fa fa-user"> <Button variant="light" className='btn-group-acc'> Account Information</Button></i></div>
                    <div className="line"><i class="fas fa-address-book"></i> <Button variant="light" className='btn-group-acc'> Address Book</Button></div>
                    <div className="line"><i class="fas fa-address-book"></i><Button variant="light" className='btn-group-acc'> My orders</Button></div>
                    <div className="line"><i class="fas fa-address-book"></i> <Button variant="light" className='btn-group-acc'>My Favourites</Button></div>  
                </div>
                </div>
            <div className ='link-acc-sidebar'>
                <a href='#'>SIGN OUT</a>
            </div>
           {/* <div className="side">
               <div className="account-dashboard"><h4>Account Dashboard</h4></div>
               <div className="account-tab"><h6>Account Information</h6></div>
               <div className="account-tab"><h6>Address Book</h6></div>
               <div className="account-tab"><h6>My Orders</h6></div>
               <div className="account-tab"><h6>My Favourites</h6></div>
            </div>  */}
        </div>
    )
}