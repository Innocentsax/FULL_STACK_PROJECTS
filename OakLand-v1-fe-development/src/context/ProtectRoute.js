import React, { Component } from 'react'

import { useLocation, Navigate, Outlet } from "react-router-dom";
import { errorNotification } from '../components/Notification';
import { isTokenValid } from '../utils/roleUrlRouter';
import { useAuth } from './authcontext';


export const ProtectAdminRoute = ({children}) => {
    const location = useLocation()
    const isAuthenticated = localStorage.getItem('signature')
    const userRole = localStorage.getItem('role')
      
    if(!isAuthenticated ||userRole ==="user" ||  userRole ==="vendor"){
        return (
            <Navigate to="/login" state={{from:location} }/>
        )
    }
    return children
}


export const ProtectCustomerRoute = ({children}) => {
    const { setShowNavbar } = useAuth()
    const location = useLocation()
    let isAuthenticated;
    const userRole = localStorage.getItem('role')

    const localStorageValue = localStorage.getItem('signature');
    
    if( localStorageValue !== null && localStorageValue.length > 4 ){
        isAuthenticated = true;
    }

    setShowNavbar(true)
    if(!isAuthenticated || userRole ==="ADMIN" ||  userRole ==="SUPER_ADMIN"){
        return (
            <Navigate to="/login" state={{from:location} }/>
        )
    }
    return children
}

export const IsAuthenticated = ({children}) => {
    const location = useLocation()
    let isAuthenticated;
    const localStorageValue = localStorage.getItem('signature');

     if(localStorageValue !== null && localStorageValue.length > 5){
        isAuthenticated = true;
     }else{
        isAuthenticated = false;
     }


    if(isAuthenticated){
        return (
            <Navigate to="/shop" state={{from:location} }/>
        )
    }
    return children
}

export const RequireAdminAuth = () => {
    const { setShowNavbar } = useAuth()
    const location = useLocation()
    const isAuthenticated = localStorage.getItem('signature')
    const userRole = localStorage.getItem('role')
    let tokenValid = null;

    setShowNavbar(false)

    if(isAuthenticated !== ''){
        tokenValid = isTokenValid(isAuthenticated)

        if(!tokenValid) {
            localStorage.setItem("signature", "")
            localStorage.setItem("role", "")
            errorNotification("Token expired!")
        }
    }else {
         errorNotification("Session expired!")

    }
  
    return (
        (userRole === "ADMIN" || userRole === "SUPERADMIN")  &&  tokenValid ? 
         <Outlet /> : <Navigate to="/login" state={ { from: location } } />
    )
  }
