import React from 'react';
import { Link } from "react-router-dom";

const CheckoutCard = ({ title,children, to, linkName }) => {
  return (
    <div className='bg-white divide-y p-3 rounded-md mb-3'>
        <div className='py-2 font-bold-900'>
            <h1 className='text-xl font-extrabold uppercase pb-2'>{title}</h1>
            <Link to={to} className="text-[#917307] hover:p-3 hover:font-bold-900 hover:rounded-md hover:drop-shadow-lg hover:bg-[#e6d69d]">{linkName}</Link>
        </div>

        <div className=''>
            {children}
        </div>
    </div>
  )
}

export default CheckoutCard;