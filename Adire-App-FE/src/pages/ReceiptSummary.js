import React, { useEffect, useState } from 'react'
import { BackButton, Card, ReceiptItem } from '../components'
import Wrapper from '../assets/wrappers/ReceiptSummary'
import { useNavigate, useParams, Link } from 'react-router-dom'
import customFetch from '../utils/axios'
import { formatDate, formatMeasurements } from '../utils/helpers'


const ReceiptSummary = () => {
    const { id } = useParams()
    
    const [isLoading, setIsLoading] = useState(false)
    const [orderDetails, setOrderDetails] = useState(null)

    const navigate = useNavigate();

    function handleGoBack() {
        navigate(-1);
    }
    console.log(orderDetails);
    

    useEffect(() => {
        console.log('receipt summary');
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
        getOrderDetails();
    
    }, [id])

    if (isLoading) {
        return <p>loading...</p>
    }
    

    if (!orderDetails) {
        return <h2 className='section-title'>no order to display</h2>
    } 

    const {
        customer,
        designImageUrl,
        dueDate,
        duration,
        materialType,
        measurementList,
        orderPrice,
        orderStatus,
    } = orderDetails;

    console.log(measurementList, customer);

    const formattedPrice = Math.floor(orderPrice).toLocaleString("en-NG", {style: "currency", currency: "NGN", minimumFractionDigits: 0});
    
  return (
    <div>
        <BackButton handleGoBack={handleGoBack} />
        <div className='pd-top-3'>
            <Card>
                <Wrapper>
                    <div>
                        <div className="content">
                            <h4 className="title">receipt Summary</h4>
                        </div>
                        <div className="actions">
                            <ReceiptItem title={"name"} text={`${customer?.firstName} ${customer?.lastname}`} />
                            <ReceiptItem title={"phone number"} text={customer?.phoneNumber} />
                            <ReceiptItem title={"email"} text={customer?.email} />
                            <ReceiptItem title={"address"} text={customer?.address} />
                            <ReceiptItem title={"material type"} text={materialType} />
                            <ReceiptItem title={"measurements"} text={formatMeasurements(measurementList)} />
                            <ReceiptItem title={"price"} text={formattedPrice} />
                            <ReceiptItem title={"due date"} text={formatDate(dueDate)} />
                            <ReceiptItem title={"duration"} text={`${duration} ${duration > 1 ? 'Days' : 'Day'}`} />
                            <div className='btn-container'>
                                <button 
                                    className="btn" 
                                    type="button"
                                    onClick={() => {
                                        window.frames['frame1'].print()
                                    }}
                                >
                                    download PDF
                                </button>
                                <div>
                                    <iframe src={`/print-receipt/${id}`} name="frame1" className="hide-frame" title="invoice for orders"></iframe>
                                </div>
                                <button className="btn share-btn" type="button">share</button>

                                {/* <button
                                    className="btn btn-pry m-0 w-lg-75 w-md-100"
                                    onClick={() => window.frames['frame1'].print()}
                                >
                                    <i className="bi bi-receipt" />  Print Receipt
                                </button> */}

                                {/* Invoice Reciept */}
                                {/* NOTE: d-none is a class that means display-none, it hides the iframe */}
                                {/* NOTE: src will route to the page you want to print*/}

                                {/* <div className="mt-5">
                                    <iframe src={`/invoice/`} name="frame1" className="d-none w-100"></iframe>
                                </div> */}

                                {/* <button 
                                    onClick={handleDownloadPdf}
                                    class="btn" 
                                    type="button"
                                    disabled={!(isLoading === false)}
                                >
                                    {isLoading ? (
                                        <span>Downloading</span>
                                    ) : (
                                        "download PDF"
                                    )}
                                </button>
                                <button class="btn share-btn" type="button">share</button> */}

                                {/* npm i html2canvas jspdf */}
                            </div>
                        
                        </div>
                    </div>
                </Wrapper>
            </Card>
        </div>
  
    </div>
  )
}

export default ReceiptSummary