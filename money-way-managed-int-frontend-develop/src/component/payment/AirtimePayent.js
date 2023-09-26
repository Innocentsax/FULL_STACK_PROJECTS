import Select from 'react-select';
import {useState} from "react";
const { Option } = [
    { value: 'heading', label: 'Select Option', isHeading: true },
    { value: 'mtn-data', label: 'MTN Data' },
    { value: 'glo-data', label: 'Glo Data' },
    { value: 'airtel-data', label: 'Airtel Data' },
    { value: '9mobile-data', label: '9Mobile Data' }
];
const AirtimePayment = () => {
    const [plan, setPlan] = useState("");

    const handleOptionChange =(option)=>{
        setPlan(option);
    }

    return (
        <div className = "bg-gray-100 h-[100%] flex justify-center w-fit">
            <div className = "h-full w-full bg-gray-100 pt-10">
                <h2 className="text-2xl text-main text-center font-bold">Buy Airtime on-the-go!</h2>
                <div className="bg-white p-8 mt-5 mx-24">
                    <div className=" mb-4">
                        <button className="text-indigo-700 text-sm mb-8">Beneficiaries</button>
                        <div className="mb-4">
                            <label className="block text-sm font-semibold text-gray-700 mb-1">Amount</label>
                            <input
                                type="text"
                                placeholder={"Type amount here"}
                                className="border border-gray-300 rounded-md px-3 py-2 w-full"
                            />
                        </div>

                        <div className="mb-4">
                            <label className="block text-sm font-semibold  text-main mb-1">Network</label>
                            <Select
                                id="select-provider-dropdown"
                                value={plan} onChange={handleOptionChange} options={Option}
                            />
                        </div>

                        <div className="mb-8">
                            <label className="block text-sm font-semibold  text-gray-700 mb-1">Phone Number</label>
                            <input
                                type="text"
                                placeholder={"Type number here"}
                                className="border border-gray-300 rounded-md px-3 py-2 w-full"
                            />
                        </div>

                        <button className="text-indigo-700 text-sm mb-8">Save as beneficiary</button>
                        <div className="mb-4">
                            <button className="bg-indigo-700 hover:bg-indigo-800 text-white rounded-md px-4 py-2 w-full"> Continue </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AirtimePayment
