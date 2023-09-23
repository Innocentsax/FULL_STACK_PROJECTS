import axios from 'axios'
import React from 'react'
// import "./AdminDashboard.css";


const ProductRow = ({id, image, price, name, description, category,date}) => {
    const url = `https://fitnesso-app-new.herokuapp.com/product/delete/${id}`;
    const deleteItem = async () => {
        const response = await axios.delete(url, {
            headers: { Authorization: `Bearer ${token}` },
        });

        const data = response.data;
        alert("Item has been deleted!");
    }
  return (
    <>
          <tr>
                    <td className="admin-dashboard-people">
                        <img src={image} alt={name}/>
                        <div className="admin-dashboard-people-de">
                            <h5>{name}</h5>
                            {/* <p>ogbodouchenna@gmail.com</p> */}
                        </div>
                    </td>
                    <td className="admin-dashboard-people-des">
                        <h5>{price}</h5>
                        {/* <p>web dev</p> */}
                    </td>
                    <td className="admin-dashboard-active">
                        <span style={{fontWeight: '400', fontSize:'12px'}}>{description}</span>
                    </td>
                    <td className="admin-dashboard-role">
                        <p>{category}</p>
                    </td>
                    <td className="admin-dashboard-role">
                        <p>{date}</p>
                    </td>
                    <td className="admin-dashboard-role">
                        <p>{date}</p>
                    </td> 
                    <td className="admin-dashboard-edit">
                        <a href="#">
                            <i className="fa fa-pencil"></i>
                        </a> {"   "}
                        <a href="#" onClick={(deleteItem)}>
                            <i className="fa fa-trash"></i>
                            
                            </a>
                    </td>


                </tr>


    </>
  )
}

export default ProductRow