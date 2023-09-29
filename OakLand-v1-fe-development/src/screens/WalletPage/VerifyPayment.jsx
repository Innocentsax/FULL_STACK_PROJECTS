import { useEffect, useState } from "react";
import { BsFillCheckCircleFill } from "react-icons/bs";
import { ImCancelCircle } from "react-icons/im";
import { Link, useParams, useSearchParams } from "react-router-dom";
import Loader from "../../components/Loader/Loader";
import { useAuth } from "../../context/authcontext"


const VerifyPayment = () => {

    const [isLoading, setIsLoading] = useState(false);

    const { getTransDetail, FinalizePayment } = useAuth();
    const [queryParams] = useSearchParams();
    // const { reference, trxref } = useParams();


    const [reference, setReference] = useState('');

    useEffect(() => {
        const currentUrl = window.location.href;
        const match = currentUrl.match(/reference=([^&]+)/);
        if (match) {
            setReference(match[1]);
        }
    }, []);

    useEffect(() => {
        setIsLoading(true)
        FinalizePayment(reference).then(() => setIsLoading(false));
        console.log(reference)
    }, [reference])


    return(
        <div className="pt-8 h-[100vh] w-full flex justify-center items-center bg-[#f5f5f5]">

            { getTransDetail !== 'Transaction not successful' && getTransDetail !== "Transaction Completed"  ? (

                <div>
                <p>Please hold while we verify your transaction. This may take a few minute.</p>
                {isLoading && <Loader />}
                </div>
            
            
             ): getTransDetail === "Transaction Completed" ? (

                <div className="flex flex-col gap-6 bg-[#e8e8e8] border justify-center items-center w-[50%] md:w-[30%] h-[400px] rounded-[2rem] ">
                    < BsFillCheckCircleFill size={80} className='text-[green]' />
                    <h1 className="text-[1rem]">
                    {getTransDetail}
                    </h1>
                    <Link className="bg-[#7e6a17] text-[white] py-2 px-3 rounded-md" to='/wallet'>Continue to Wallet</Link>
                </div>
            
            
             ):(
                <div className="flex flex-col gap-6 bg-[#e8e8e8] border justify-center items-center w-[50%] md:w-[30%] h-[400px] rounded-[2rem] ">
                    < ImCancelCircle size={80} className='text-[red]' />
                    <h1 className="text-[1rem]">
                        Transaction failed
                    </h1>
                    <Link className="bg-[#7e6a17] text-[white] py-2 px-3 rounded-md" to='/wallet'>Retry</Link> 
                </div>
            )}
        </div>
    )
}

export default VerifyPayment;