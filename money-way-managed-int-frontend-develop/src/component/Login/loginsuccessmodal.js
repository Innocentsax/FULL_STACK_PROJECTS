import SuccessLogo from "../../asset/img/successful.png";



function LoginSuccess(){

    return(

        <div className="flex items-center flex-col" >
            
            <div className="w-1/3 py-8 px-2.5 bg-[#ffffff] rounded-lg">

                <div className="grid justify-items-center">
                    <img src={SuccessLogo}/>
                </div>

                <div class=" font-extrabold text-3xl grid justify-items-center mt-6">Successful</div>
                
                <div className="grid justify-items-center mt-6 text-base font-medium">
                    Your password has been changed
                    <div>successfully. Login to access your account</div>
                </div>

                <div className="mt-8 grid justify-items-center">
                    <button className="text-[#ffffff] bg-[#3538CD] font-semibold w-3/4 rounded-lg h-12">Continue to Login</button>
                </div>
            </div>

        </div>

    );

}

export default LoginSuccess;