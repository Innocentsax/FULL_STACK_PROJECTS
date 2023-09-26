import {useState} from "react";
import { ReactComponent as ArrowDown } from '../../asset/paybills/svg/arrow-down.svg';
import { ReactComponent as MobileSignalOut } from '../../asset/paybills/svg/mobile-signal-out.svg';
import { ReactComponent as TvAlt } from '../../asset/paybills/svg/tv-alt.svg';
import { Link } from 'react-router-dom';

const Payments = () => {
    const [activePaymentsPage, setActivePaymentsPage] = useState('Pay Bills');

    // Event handler for button click
    const handlePaymentTypeButtonClick = (page) => {
        setActivePaymentsPage(page);
    };

    return (
        <div className = "bg-gray-100 h-[100%] w-[100%] flex justify-center pt-[40px]">
            <div className = "max-w-[580px] w-[90%] bg-gray-100 ">
                <h2 className = "mb-8 text-2xl font-bold text-center">Pay Bills</h2>
                <div className = "py-6 px-8 bg-white h-[335px]">
                    {/*div to allow arranging icons on internet button*/}
                    <div className = "flex justify-between">
                        {/* Link to navigate to Internet payment form */}
                        <Link className = "flex justify-between w-[100%] items-center" to="/user/payment/airtime" onClick = {() => handlePaymentTypeButtonClick('Airtime')}>
                            <div className = "flex items-center">
                                <MobileSignalOut />
                                <span className = "inline-block pl-4">
                                            Airtime
                                </span>
                            </div>
                            <div>
                                <ArrowDown />
                            </div>
                        </Link>
                    </div>
                    <div className = "flex justify-between mt-10">
                        {/* Link to navigate to Internet payment form */}
                        <Link className = "flex justify-between w-[100%] items-center" to="/user/payment/internet" onClick = {() => handlePaymentTypeButtonClick('Internet')}>
                            <div className = "flex items-center">
                                <MobileSignalOut />
                                <span className = "inline-block pl-4">
                                            Internet
                                </span>
                            </div>
                            <div>
                                <ArrowDown />
                            </div>
                        </Link>
                    </div>

                    <div className = "flex justify-between mt-10">
                        {/* Link to navigate to TV payment form */}
                        <Link className = "flex justify-between w-[100%] items-center" to="/tv" onClick = {() => handlePaymentTypeButtonClick('TV')}>
                            <div className = "flex items-center">
                                <TvAlt />
                                <span className = "inline-block pl-4">
                                            TV
                                        </span>
                            </div>
                            <div>
                                <ArrowDown />
                            </div>
                        </Link>
                    </div>

                    <div className = "flex justify-between mt-10">
                        {/* Link to navigate to Electricity payment form */}
                        <Link className = "flex justify-between w-[100%] items-center" to="/electricity" onClick = {() => handlePaymentTypeButtonClick('Electricity')}>
                            <div className = "flex items-center">
                                <TvAlt />
                                <span className = "inline-block pl-4">
                                            Electricity
                                        </span>
                            </div>
                            <div>
                                <ArrowDown />
                            </div>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Payments;