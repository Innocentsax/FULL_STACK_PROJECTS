import React from 'react'
import { useNavigate } from 'react-router-dom';
import { BackButton, OrdersTable } from '../../components'

const AllOrders = () => {
  const navigate = useNavigate();

  function handleGoBack() {
    navigate(-1);
  }

  return (
    <div>
      <BackButton handleGoBack={handleGoBack} />
      <OrdersTable />


    </div>
  )
}

export default AllOrders