import {useEffect, useState} from "react";
import {useStateContext} from "../Context/ContextProvider";
import axios from "axios";

const Beneficiary = () => {
    const [beneficiaries, setBeneficiaries]= useState([])
    const context = useStateContext();
    useEffect(() =>{
            const getBeneficiaries = async () =>{

                const token = "Bearer "+context.token;

                const headers = {
                    'Content-Type': 'application/json',
                    'Authorization': token
                };

                await axios.get('http://localhost:9000/api/v1/transaction/beneficiaries?transaction-type=cash-transfer', { headers })
                    .then(response => {
                        setBeneficiaries(response.data)
                        console.log(response.data)
                    })
                    .catch(error => {
                        console.log(error);
                    });
            };
            getBeneficiaries();
        }, [context.token]
    );

    return (
        <div>
            {/*contains beneficiaries*/}
            <div className = "">
            {/*<div className = "pt-8">*/}
                {/*individual beneficiaries*/}
                {beneficiaries.map((beneficiary, index) => (
                    <div key={index} className="mb-4">
                        <div className = "text-sm flex justify-between">
                            <div>
                                <p>{beneficiary.name}</p>
                                <p>{beneficiary.accountNumber}</p>
                            </div>
                            <div>
                                <p>{beneficiary.accountBank}</p>
                            </div>
                        </div>
                    </div>
                ))}

            </div>
        </div>
    )
}

export default Beneficiary;