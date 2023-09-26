import {useState} from "react";
import LocalTransfer from "./LocalTransfer";
import OtherBankTransfer from "./OtherBankTransfer";
import Beneficiary from "./Beneficiary";

const TransferTabs = () => {
    const [activeTab, setActiveTab] = useState('Transfer');
    const [tabDisplay, setTabDisplay] = useState(<LocalTransfer />);
    const [buttonStyles, setButtonStyles] = useState('');
    const buttonStyle = {
        borderBottom: '3px solid purple',
        color: '#3538CD',
    }

    const handleTransferTypeSelect = (selectedTransfer) => {
        if(selectedTransfer.target.id === "other-bank"){
            setActiveTab('Other Bank Transfer');
            setTabDisplay(<OtherBankTransfer />);
        }
        else if(selectedTransfer.target.id === "beneficiary"){
            setActiveTab('Beneficiary');
            setTabDisplay(<Beneficiary/>);
        }
        else {
            setActiveTab('Transfer');
            setTabDisplay(<LocalTransfer />);
        }
    }

    return (
        <div>
            {/*transfer card*/}
            <div className = "bg-gray-100">
                <h2 className = "mb-8 text-2xl font-bold text-center">{activeTab}</h2>
                <div className = "py-6 px-8 bg-white">
                    {/*active tab*/}
                    <div className = ''>
                        <div className = "flex justify-between  px-7">
                            {/*local*/}
                            <div style = {activeTab === 'Transfer' ? buttonStyle : {}} className = "pb-4 px-9">
                                <button id = 'local-transfer' className = {buttonStyles} onClick = {handleTransferTypeSelect}>Local Transfer</button>
                            </div>
                            {/*other*/}
                            <div style = {activeTab === 'Other Bank Transfer' ? buttonStyle : {}} className = "pb-4 px-9">
                                <button id = "other-bank" className = {buttonStyles} onClick = {handleTransferTypeSelect}>Other Bank Transfer</button>
                            </div>
                            {/*beneficiary*/}
                            <div style = {activeTab === 'Beneficiary' ? buttonStyle : {}} className = "pb-4 px-9">
                                <button id = 'beneficiary' className = {buttonStyles} onClick = {handleTransferTypeSelect}>Beneficiary</button>
                            </div>
                        </div>
                        <hr />
                    </div>
                    {/* transfer forms */}
                    <div className = "pt-10">
                        {tabDisplay}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default TransferTabs;