import {useState} from "react";
import OtherBankTransfer from "./OtherBankTransfer";
import Beneficiary from "./Beneficiary";
import LocalTransfer from "./LocalTransfer";
import TransferTabs from "./TransferTabs";


const Transfer = () => {
    return (
        <div className = "bg-gray-100 h-[100%] w-[100%] flex justify-center pt-[40px] overflow-auto">
            <TransferTabs />
        </div>
    )
}

export default Transfer;