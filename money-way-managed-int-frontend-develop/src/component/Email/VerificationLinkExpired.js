import React from "react";


function VerificationLinkExpired(){

    return(
        <div className="flex items-center flex-col">

            <div className="w-2/5 text-lg mt-12 p-8 bg-[#ffffff] rounded-lg">
                <div className="text-orange-900 font-semibold text-4xl grid justify-items-center ">Verify your email</div>

                <div className="grid justify-items-center mt-12">
                    Hi there, the link you clicked has expired click the button below to resend the verification email
                    <div>then check your email and start enjoying MoneyWay.</div>
                </div>

                <div className="mt-8">
                    <button className="text-[#ffffff] bg-[#3538CD] font-semibold w-full rounded-lg h-12">Send Email Verification Link again</button>
                </div>
            </div>

        </div>
    );
}

export default VerificationLinkExpired;