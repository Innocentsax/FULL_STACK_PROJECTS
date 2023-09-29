import { MdMarkEmailRead } from "react-icons/md";
import { RiMailCheckLine } from "react-icons/ri";
import { Link } from "react-router-dom";



const CheckMail = () => {

    return(
        <div className="w-[100%] h-[100vh] flex justify-center items-center mt-[4rem] p-5">
            <div className="w-[700px] h-[500px] p-5 bg-[#ebebeb] text-justify text-[black] flex flex-col justify-center item-center gap-4">
                < MdMarkEmailRead className="text-[15rem] self-center text-[green]" />
                <div className="flex flex-col gap-4">
                    <p className="text-center text-[1.5rem] font-bold">Verification link has been sent to you email, 
                        Check your email to comfirm registration
                    </p>
                    <div className="flex justify-center items-center">
                        <span>Did not recieve Verification link? <Link to='/' className="text-[#9b7d0f]">Resend token</Link> </span>
                    </div>
                    <div className="flex justify-center items-center">
                        <span> Verified? </span> <Link to='/login'>Proceed to Login</Link>
                    </div>
                </div>
            </div>
        </div>
    )
}


export default CheckMail;