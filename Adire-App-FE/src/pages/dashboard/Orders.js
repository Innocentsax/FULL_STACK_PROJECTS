import React, { useState } from 'react'
import { NewOrder } from '../../components'

const Orders = () => {
  const [showOrdersList, setShowOrdersList] = useState(true);
  return (
    <>
      <NewOrder />
    </>
  )
}

export default Orders