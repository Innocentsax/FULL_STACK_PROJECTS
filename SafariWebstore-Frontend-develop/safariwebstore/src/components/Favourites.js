import React,{useState,useEffect,useRef} from 'react';
import "../stylesheets/favourites.css"
import FavouriteMain from './FavouriteMain';
export default function Favourites() {
    const sideBarButton = useRef();
    const handleSideBarButton = (e)=>{
        e.target.style.backgroundColor = "#ED165F";
        e.target.style.color = "white";
        // e.target.style.padding = "10px";
    }
    const changeBackground = (e)=>{
        e.target.style.backgroundColor = "#ED165F";
        e.target.style.color = "white";
    }
    const reverseChangeBackground = (e)=>{
        e.target.style.backgroundColor = "white";
        e.target.style.color = "black";
    }
    const favourites = [
        {
            id:1,
            productName: "Casual Flat Loafers",
            price:2000,
            size:"Size - EU : 36 US : 5.6"
        },{
            id:2,
            productName: "Casual Flat Loafers",
            price:2000,
            size:"Size - EU : 36 US : 5.6"
        },{
            id:3,
            productName: "Casual Flat Loafers",
            price:2000,
            size:"Size - EU : 36 US : 5.6"
        },{
            id:4,
            productName: "Casual Flat Loafers",
            price:2000,
            size:"Size - EU : 36 US : 5.6"
        }
    ]
    return (
        <>
        <div className="main-container">
        <div className="container">
                <div className="top">
                    <h6 className="header">ACOUNT DASHBOARD</h6>
                    <div className="buttons">
                        <div className="line" onMouseOver={changeBackground} onMouseOut={reverseChangeBackground} onClick={handleSideBarButton} ref={sideBarButton}><i className="fa fa-user"></i> Account Information</div>
                        <div className="line" onMouseOver={changeBackground} onMouseOut={reverseChangeBackground} onClick={handleSideBarButton} ref={sideBarButton}><i class="fas fa-address-book"></i> Address Book</div>
                        <div className="line" onMouseOver={changeBackground} onMouseOut={reverseChangeBackground} onClick={handleSideBarButton} ref={sideBarButton}><i class="fas fa-gift"></i> My Orders</div>
                        <div className="line" onMouseOver={changeBackground} onMouseOut={reverseChangeBackground} onClick={handleSideBarButton} ref={sideBarButton}><i class="fas fa-heart"></i> My Favourites</div>
                    </div>
                </div>
                <div className="line-last" onClick={event =>  window.location.href='/your-href'}><i class="fas fa-sign-out-alt" ></i> SIGN OUT</div>
            </div>
            <FavouriteMain favourites={favourites}/>
        </div>
        </>
    )
}
