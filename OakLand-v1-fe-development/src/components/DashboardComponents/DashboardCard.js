import React from 'react';
import { Link } from "react-router-dom";

const DashBoardCard = (props) => {
  return (
    <>
        <div className="border-gray-100 border rounded-md divide-y">
          <div className='flex justify-between p-3'>
            <h2 className="text-xl">{props.title}</h2>
            <Link to="/" className="text-[#917307] text-3xl font-bold-1000">{props.icon}</Link>
          </div>
          <div class="pb-3 text-xl">
              <h5 class="p-3 pb-1">{props.subtitle}</h5>
              <p class="p-3 text-gray-500 flex align-middle">{props.icon2}{props.content}</p>
          </div>
        </div>
    </>
  )
}

export default DashBoardCard;