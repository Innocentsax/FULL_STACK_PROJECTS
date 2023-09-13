import React from 'react'

const ReceiptItem = ({ title, text, subText }) => {
  return (
    <div className='receipt-row'>
      <h5 className='receipt-title'>
        {title}
        {subText && <span className='text-small'>{` (${subText})`}</span>}
      </h5>
      <p className={`receipt-text ${title === 'email' ? '' : 'text-capitalize'}`}>{text}</p>
    </div>
  )
}

export default ReceiptItem