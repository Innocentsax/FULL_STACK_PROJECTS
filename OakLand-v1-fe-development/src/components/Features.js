import {CiDeliveryTruck} from "react-icons/ci"
import {MdOutlinePayments} from "react-icons/md"
import {GiReceiveMoney} from "react-icons/gi"
import {BiSupport} from "react-icons/bi"
import FeatureCard from "./FeatureCard/FeatureCard";


const Features = () => {
    return(
        <div className="mt-[8rem] flex flex-wrap gap-16 justify-center">
            <FeatureCard icon={<CiDeliveryTruck />} title="FREE SHIPPING" details="Order over $500" />
            <FeatureCard icon={<MdOutlinePayments />} title="QUICK PAYMENT" details="100% secured payment" />
            <FeatureCard icon={<GiReceiveMoney />} title="MONEY RETURN" details="Back under 7 days" />
            <FeatureCard icon={<BiSupport />} title="24/7 SUPPORT" details="Ready for you" />
        </div>
    );
}


export default Features;