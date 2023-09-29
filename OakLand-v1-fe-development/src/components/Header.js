import React from "react";
import { Link } from "react-router-dom";
import './NavBar/NavBar';


const HeaderComponent = () =>{

    const HEADER_PRIMARY_TEXT = "SYLISH FURNITURE IN UNIQUE STYLE";
    const HEADER_SECONDARY_TEXT = "Far far away, behind the word mountains, far from the countires Vokalia and Consonantia";
    

    return(
        <header className="bg-[#f5f5f5] px-[3rem] lg:pr-0 md:pl-[5rem] pb-[8rem] md:pt-[3rem] pt-[5rem] ">
            <div className="flex items-center justify-between gap-8 pt-5 lg:pt-4 ">
                <div className=" p-2 gap-2 md:text-center lg:text-left">
                    <h3 className="lg:text-3xl text-2xl font-bold">{HEADER_PRIMARY_TEXT}</h3>
                    <p className="mb-5 font-sans lg:text-2xl text-1xl">{HEADER_SECONDARY_TEXT}</p>
                    
                    <Link to="/shop" className=" bg-black p-[1.2rem] text-white rounded text-1xl shop-now">SHOP NOW</Link>
                </div>
                <div className=" hidden lg:block ">
                    <div className="flex bg-[#eeeeee] items-center rounded-full h-[550px] w-[550px] right-0">
                        <img className="w-[350px] justify-self-center ml-[-1rem] mb-[-8rem]" src="../images/hero-sitter.png" alt="#" />
                        <img className="w-[200px] ml-[-4rem] " src="../images/hero-lamp.png" alt="#" />
                    </div>
                </div>
            </div>
        </header>
    );
}

export default HeaderComponent;