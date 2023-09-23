import React from 'react'

const ViewOrder = ({id, user, status, order_date, paymentMethod,delivered_date, amount }) => {
  return (
    <>
    <tr>
              <td className="admin-dashboard-people">
                  <div className="admin-dashboard-people-de">
                      <p>{id}</p>
                  </div>
              </td>
              <td className="admin-dashboard-people-des">
                  <h5>{user}</h5>

                  {/* <p>web dev</p> */}
              </td>
              <td className="admin-dashboard-active">
                  <p>{status}</p>

              </td>
              <td className="admin-dashboard-role">
              {/* <p>{paymentMethod}</p> */}
              <h5>{amount}</h5>



              </td>
              <td className="admin-dashboard-role">
                    <p>{order_date}</p>
              </td>
              <td className="admin-dashboard-role">
              <p>{delivered_date}</p>

              </td> 
              <td className="admin-dashboard-edit">
                  <a href="#">
                      <i className="fa fa-pencil"></i>
                  </a> {"   "}
                  <a href="#">
                      <i className="fa fa-trash"></i>
                      
                      </a>
              </td>


          </tr>


</>
  )
}

export default ViewOrder