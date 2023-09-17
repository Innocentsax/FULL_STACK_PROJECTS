import React, {useMemo, useEffect, useState} from 'react';
import './AdminProductList.css';
import AdminLayout from "../../components/adminlayout/AdminLayout";
import {Table, SelectColumnFilter} from '../../components/table/Table';
import ProductApi from '../../apis/ProductApi';

const token = localStorage.getItem('token');
const AdminProductList = (props) => {
  const [data, setdata] = useState([]);  

   useEffect(() => {

    (async()=>{
      const result = await ProductApi.getAllProductsAdmin();   
    
      setdata(result.content);
    })();
   }, [])

  const columns = useMemo(
    () => [
      {
        Header: 'List of Products',
        columns: [
          {
            Header: 'Product Name',
            accessor: 'name',
          },
          {
            Header: 'Price',
            accessor: 'price',
          },
          {
            Header: 'Category',
            accessor: 'category[0].name',
            
          },
          {
            Header: 'Sub Category',
            accessor: 'subCategory[0].name',
          },
          {
            Header: 'Size',
            accessor: 'sizes[0].size'
            
          },
          {
            Header: 'Colour',
            accessor: 'colors[0].color',
        }
          
        ],
      },
    
    ],
    []
  )
  return (
     <AdminLayout>
      <div className="orders-wrapper">
        <Table columns={columns} data={data}/>
      </div>
     </AdminLayout>
  );
}

export default AdminProductList;