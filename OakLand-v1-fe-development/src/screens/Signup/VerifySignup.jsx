import { useEffect, useState } from "react";
import { BsFillCheckCircleFill } from "react-icons/bs";
import { FaTimes } from "react-icons/fa";
import { GiCheckMark } from "react-icons/gi";
import { ImCancelCircle } from "react-icons/im";
import { Link, useSearchParams } from "react-router-dom";
import Loader from "../../components/Loader/Loader";
import { useAuth } from "../../context/authcontext"


const VerifyRegistration = () => {

    const { VerifyReg, verifyReg, isLoading, setIsLoading } = useAuth();
    const [queryParams] = useSearchParams();

    useEffect(() => {
        // setIsLoading(true)
        VerifyReg(queryParams);
        console.log(queryParams)
        // setIsLoading(false)
    }, [queryParams])

    return(
        <div className="pt-8 h-[100vh] w-full flex justify-center items-center bg-[#f5f5f5]">          
          {
            verifyReg.message !== "Request Successful" && verifyReg.message !== "Token Not Found"               
             && verifyReg.message !== "Token already used" ?           
             <div>                <p>Please wait while we verify your email........</p>               
              {isLoading && <Loader />}
         </div>            :
            verifyReg.message === "Request Successful" ?           
             <div className="flex flex-col gap-6 bg-[#e8e8e8] border justify-center items-center w-[50%] md:w-[30%] h-[400px] rounded-[2rem] ">                
             < BsFillCheckCircleFill size={80} className='text-[green]' />                
             <h1 className="text-[1rem]">                   
             {verifyReg.data}
            </h1>                
            <Link className="bg-[#7e6a17] text-[white] py-2 px-3 rounded-md" to='/login'>Login</Link>            
            </div>            :
         <div className="flex flex-col gap-6 bg-[#e8e8e8] border justify-center items-center w-[50%] md:w-[30%] h-[400px] rounded-[2rem] ">            
             < ImCancelCircle size={80} className='text-[red]' />                
             <h1 className="text-[1rem]">                   Verification failed!                </h1> 
            <Link className="bg-[#7e6a17] text-[white] py-2 px-3 rounded-md" to='/'>Resend Token</Link> 
        </div>            }
    </div> 

    )
}

export default VerifyRegistration;