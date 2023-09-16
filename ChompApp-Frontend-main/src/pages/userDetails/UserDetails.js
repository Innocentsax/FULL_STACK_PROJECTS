import React, { useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import CartService from '../../services/CartService'
import "./UserDetails.css"



const ViewUsersDetails = () => {
    const [user, setUser] = useState([])
    const currentUser = useSelector((state) => state.user);


    useEffect(() => {
        CartService.viewUsersDetails().then((resp) => {
            setUser(resp.data)
        }) 
        console.log(user);
    }, [])

  return (
    <div className="userDetails mt-5">
      {currentUser ? (
        <div className="container card ms-auto me-auto p-3 custom-card">
          <div className="userDetails__header">
            <h2>{user.firstName}</h2>
            <h2>{user.lastName}</h2>
          </div>
          <hr />
          <div className="userDetails__body">
            <div className="userDetails__contentent">
              <label>Email:</label>
              <h5>{user.email}</h5>
            </div>
            <div className="userDetails__contentent">
              <label>Gender:</label>
              <h5>{user.gender}</h5>
            </div>
            <div className="userDetails__contentent">
              <label>Role:</label>
              <h5>{currentUser.roles[0].name}</h5>
            </div>
            <div className="userDetails__contentent">
              <label>User Id:</label>
              <h5>{user.userId}</h5>
            </div>
          </div>
        </div>
      ) : (
        <h2>You need to login</h2>
      )}
    </div>
  );
}

export default ViewUsersDetails