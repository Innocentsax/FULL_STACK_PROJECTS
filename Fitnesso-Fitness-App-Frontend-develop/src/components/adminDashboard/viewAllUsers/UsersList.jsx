import React, {useState, useEffect} from 'react';
import ViewAllUsersRow from './ViewAllUsersRow';

const UsersList = () => {
    const [usersData, setUsersData] = useState({});
    const usersListUrl = "";

    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch(usersListUrl);
            const data = response.json();
            console.log(data);
            setUsersData(data);
        }
    })
  return (
    <div className="admin-dashboard-board">
        <table width="100%">
            <thead>
                <tr>
                    <td>S/N</td>
                    <td>fName</td>
                    <td>lName</td>
                    <td>DOB</td>
                    <td>Wallet ID</td>
                    <td>Gender</td>
                    <td>Action</td>
                  
                </tr>
            </thead>
            <tbody>
                {
                    usersData?.map(user=>(
                        <ViewAllUsersRow
                        id={user.id} 
                        first_name={user.firstName}
                        last_name={user.user}
                        email={user.email}
                        gender={user.gender}
                        dateOfBirth={user.dateOfBirth} 
                        walletId={user.walletId}
                        />
                           
                    ))

                }
                

            </tbody>

        </table>


    </div>
  )
}

export default UsersList