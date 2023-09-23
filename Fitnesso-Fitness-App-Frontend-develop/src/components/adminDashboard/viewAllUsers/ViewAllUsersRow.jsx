import React from 'react'

const ViewAllUsersRow = ({id, first_name, last_name, walletId, gender, dateOfBirth,email }) => {
  return (
    <>
          <tr>
                    <td className="admin-dashboard-people">
                        <div className="admin-dashboard-people-de">
                            <h5>{id}</h5>
                            <p>{first_name}</p>
                        </div>
                    </td>
                    <td className="admin-dashboard-people-des">
                        <h5>{last_name}</h5>
                        {/* <p>web dev</p> */}
                    </td>
                    <td className="admin-dashboard-active">
                        <span style={{fontWeight: '400', fontSize:'12px'}}>{email}</span>
                    </td>
                    <td className="admin-dashboard-role">
                        <p>{dateOfBirth}</p>
                    </td>
                    <td className="admin-dashboard-role">
                        <p>{walletId}</p>
                    </td>
                    <td className="admin-dashboard-role">
                        <p>{gender}</p>
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

export default ViewAllUsersRow