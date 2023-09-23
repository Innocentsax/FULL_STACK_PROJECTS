import React, {useState, useEffect} from 'react';
import ProductRow from './ProductRow'

const ProductsList = () => {
    const [productData, setProductData] = useState({});
    const productUrl = 'https://fitnesso-app-new.herokuapp.com/';

    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch(productUrl);
            const data = response.json();
            console.log(data);
            setProductData(data);
        }
    })
  return (
    <div className="admin-dashboard-board">
        <table width="100%">
            <thead>
                <tr>
                    <td>Name</td>
                    <td>Price</td>
                    <td>Description</td>
                    <td>Category</td>
                    <td>Date Created</td>
                    <td>Date Updated</td>
                    <td>Action</td>
                  
                </tr>
            </thead>
            <tbody>
                {
                    productData?.map(item=>(
                        <ProductRow
                        id={item.id} 
                        image={item.image}
                        name={item.productName}
                        category={item.category}
                        price={item.price} 
                        description={item.description} 
                        date={item.date} 
                        />
                        
                    ))

                }

            </tbody>

        </table>

    </div>
  )
}

export default ProductsList;