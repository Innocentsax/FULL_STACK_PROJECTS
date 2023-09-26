import {useState} from "react";
import Select, { components } from 'react-select';
import SearchableSelect from "./SearchableSelect";
import Switch from "@mui/material/Switch";
// const { Option } = components;

const OtherBankTransfer = () => {
    const [selectedBankOption, setSelectedBankOption] = useState('');
    const bankOptions = [
        { value: 'first-bank', label: 'First Bank' },
        { value: 'guaranty-trust-bank', label: 'Guaranty Trust Bank' },
        { value: 'polaris-bank', label: 'Polaris Bank' },
    ];
    const [saveBeneficiary, setSaveBeneficiary] = useState(false);

    const handleBankOptionChange = (selectedBankOption) => {
        setSelectedBankOption(selectedBankOption);
    };
    const handleChange = (event) => {
        setSaveBeneficiary(event.target.checked);
    };

    return (
        <div>
            <form>
                {/*email*/}
                <div className = "flex flex-col">
                    <label className = "font-semibold text-sm" htmlFor="select-bank-dropdown-menu">
                        Bank
                    </label>
                    {/*<input id = "email-text-field" type = "text" placeholder = "Enter email address" className = "py-3 px-4 border mt-2 rounded-md"/>*/}
                    {/*<Select*/}
                    {/*    id="select-bank-dropdown"*/}
                    {/*    value={selectedBankOption}*/}
                    {/*    onChange={handleBankOptionChange}*/}
                    {/*    options={bankOptions}*/}
                    {/*    className = "py-3 px-4 border shadow-sm focus:outline-none mt-2 rounded-md"*/}
                    {/*/>*/}
                    <SearchableSelect />
                </div>

                {/*amount*/}
                <div className = "flex flex-col mt-4">
                    <label className = "font-semibold text-sm" htmlFor="amount-text-field">
                        Amount
                    </label>
                    <input id = "amount-text-field" type = "text" placeholder = "Enter an amount" className = "py-3 px-4 border mt-2 rounded-md"/>
                </div>

                {/*pin*/}
                <div className = "flex flex-col mt-4">
                    <label className = "font-semibold text-sm" htmlFor="pin-text-field">
                        Pin
                    </label>
                    <input id = "pin-text-field" type = "text" placeholder = "Enter pin" className = "py-3 px-4 border mt-2 rounded-md"/>
                </div>

                {/*description*/}
                <div className = "flex flex-col mt-4">
                    <label className = "font-semibold text-sm" htmlFor="description-text-area">
                        Description
                    </label>
                    <textarea id = "pin-text-field" type = "text" placeholder = "Write a short description" className = "py-3 px-4 border mt-2 rounded-md">
                    </textarea>
                </div>
                {/*save as beneficiary*/}
                <div className = "flex mt-4 items-center justify-between">
                    <div>
                        <p className = "text-indigo-700 text-sm">Save as Beneficiary</p>
                    </div>
                    <div>
                        <Switch
                            checked={saveBeneficiary}
                            onChange={handleChange}
                            inputProps={{ 'aria-label': 'controlled' }}
                        />
                    </div>
                </div>
                {/*continue button*/}
                <div className = "mt-10">
                    <button className = "text-center w-full py-3 px-4 rounded-md bg-indigo-700 text-sm font-semibold text-white">Continue</button>
                </div>
            </form>
        </div>
    )
}

export default OtherBankTransfer;