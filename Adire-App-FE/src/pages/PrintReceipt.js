import React, { useEffect, useState } from 'react'
import { Logo, ReceiptItem } from '../components'
import Wrapper from '../assets/wrappers/PrintReceipt'
import { useParams, useNavigate } from 'react-router-dom'
import customFetch from '../utils/axios'
import { formatDate, formatMeasurements } from '../utils/helpers'

const PrintReceipt = () => {
    
    const { id } = useParams()
    console.log(id);
    
    const [isLoading, setIsLoading] = useState(false)
    const [orderDetails, setOrderDetails] = useState(null)

    const navigate = useNavigate();

    console.log(orderDetails);
    

    useEffect(() => {
        const getOrderDetails = async () => {
            setIsLoading(true)
            try {
            const response = await customFetch.get(`/api/order/get-a-single-order/${id}`);
                setOrderDetails(response.data.data);
                setIsLoading(false)
            } catch (error) {
                console.error(error);
                setIsLoading(false)
            }
        };
        getOrderDetails()
    }, [id])

    if (isLoading) {
        return <p>loading...</p>
    }

    if (!orderDetails) {
        return <h2 className='section-title'>no order to display</h2>
    }

    const {
        customer,
        customerEmail,
        designImageUrl,
        dueDate,
        duration,
        materialType,
        measurementList,
        orderPrice,
        orderStatus,
    } = orderDetails;

    // const day = ;
    const formattedPrice = Math.floor('50000').toLocaleString("en-NG", {style: "currency", currency: "NGN", minimumFractionDigits: 0});
    return (
        <Wrapper>
            <div className='container'>
                <div className="content">
                    <h5 className="receipt-header">invoice</h5>
                    <Logo />
                </div>
                <hr />
                <div className='header'>
                    <h4 className='title'>adire<span className='dot'>.</span></h4>
                </div>
                <div className="actions">
                    
                    <ReceiptItem title={"name"} text={`${customer?.firstName} ${customer?.lastname}`} />
                    <ReceiptItem title={"phone number"} text={customer?.phoneNumber} />
                    <ReceiptItem title={"email"} text={customerEmail} />
                    <ReceiptItem title={"address"} text={customer?.address} />
                    <ReceiptItem title={"material type"} text={materialType} />
                    <ReceiptItem title={"measurements"} text={formatMeasurements(measurementList)} />
                    <ReceiptItem title={"price"} text={formattedPrice} />
                    <ReceiptItem title={"due date"} text={formatDate(dueDate)} />
                    <ReceiptItem title={"duration"} text={`${duration} ${duration > 1 ? 'Days' : 'Day'}`} />
                </div>
            </div>
            <p className="copyright">
                copyright &copy; adire company <span>{new Date().getFullYear()}</span>. all rights reserved
            </p>
        </Wrapper>
    )
}

export default PrintReceipt