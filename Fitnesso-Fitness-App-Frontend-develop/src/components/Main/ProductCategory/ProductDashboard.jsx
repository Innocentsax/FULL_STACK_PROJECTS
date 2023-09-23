import React from 'react'
import { Outlet } from 'react-router'
import FixedImage from '../FixedImage/FixedImage'
import Footer from '../Footer/Footer'
import CategoryBar from './CategoryBar'

const ProductDashboard = () => {
    return (
        <div>
            <CategoryBar/>
            <Outlet/>
            <Footer/>
        </div>
    )
}
export default ProductDashboard