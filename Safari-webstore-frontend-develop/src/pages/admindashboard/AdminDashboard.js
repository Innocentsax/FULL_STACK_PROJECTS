import React, {useEffect, useState} from 'react';
import './AdminDashboard.css';
import AdminLayout from "../../components/adminlayout/AdminLayout";
import AdminApi from '../../apis/AdminApi';
import ProductApi from '../../apis/ProductApi';
import OrderApi from '../../apis/OrderApi';
import { InputField, Label } from '../../components/form-fields';


const AdminDashboard = (props) => {

  const [data, setdata] = useState({});
  const [products, setProducts] = useState([]);
  const [order, setOrder] = useState([]);
  const [total, setTotal] = useState(0);
  //me
  const [revenue, setRevenue] = useState([]);

  useEffect(() => {

   (async()=>{
     const result = await AdminApi.getAdminDetails();    
     setdata(result);

     const result2 = await OrderApi.adminGetAllOrders();   
      let total = 0;
      result2.map(each=>{
        total += each.totalCost;
      })
      setTotal(total);

   })();
  }, [])

  useEffect( () => {

    (async()=>{
      const result = await ProductApi.getAllProducts(); 
      const totalProduct = result.content.length;   
      setProducts(totalProduct);
    })();

}, [])


useEffect(() => {

  (async()=>{
    const result = await OrderApi.adminGetAllOrders();  
    const totalOrder = result;   
    setOrder(totalOrder);
  })();
 }, [])

  console.log(data);
  console.log(products);


  const{length} = order;
  const {firstName, lastName, email, gender } = data;
  console.log(length);

  return (
    
     <AdminLayout>
       <div className="wrapper">
        

         <div className="info-wrapper">
           <div className="info-content">
             <h3 className="info-title">Products</h3>
             <h2>{products}</h2>
           </div>
           <div className="info-content">
             <h3 className="info-title">Orders</h3>
             <h2>{length}</h2>
           </div>
           <div className="info-content">
             <h3 className="info-title">Revenue</h3>
             <h2>{total}</h2>
           </div>
         </div>


           <div className="info">
           <h3 style={{marginBottom:"3em", paddingTop:"1em"}} className="info-title">Account Info</h3>
           <div className="info-wrapper">

           <div className="details">
           <p>First Name</p>
           <div className="details-info"> 
             <input  value={firstName} />
            </div>
          
           </div>
           <div className="details">
             <p>Last Name</p>
             <div className="details-info">
             <input  value={lastName} />
             </div>
            
             
           </div>
           <div className="details">
           <p>Email</p>
           <div className="details-info" >
             <input value={email} />
            </div>
           
            
           </div>
           <div className="details">
           <p>Gender</p>
           <div className="details-info">
           <input  value={gender} /> 
           </div>
             
           </div>
           </div>
           </div>
         </div>
       
     </AdminLayout>
  );
}

export default AdminDashboard;