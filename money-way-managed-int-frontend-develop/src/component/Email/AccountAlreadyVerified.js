import React from "react";
import { Link } from 'react-router-dom';

function AccountAlreadyVerified(){

    return(
        <div className="relative top-0 left-0 flex items-center justify-center w-screen bg-blue-50 h-screen bg-opacity-75">

            <div className="w-2/5 text-lg shadow-md mt-12 bg-white p-8 rounded-lg">
                <div className="text-orange-900 font-semibold text-4xl grid justify-items-center ">Email Has Been Verified</div>

                <div className="grid justify-items-center mt-12">
                    Hi there, you have verified your email
                    <div>log into your account and start enjoying MoneyWay.</div>
                </div>
                <Link to="/login">
                    <div className="mt-8">
                        <button className="text-[#ffffff] bg-[#3538CD] font-semibold w-full rounded-lg h-12" >Login</button>
                    </div>
                </Link>

            </div>

        </div>
    );
}

export default AccountAlreadyVerified;