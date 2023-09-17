import axios from 'axios'
import BaseUrl from './BaseUrl';
import setHeader from '../utilities/Header';

const AdminDetailsApi = {

    getAdminDetails: async () => {
      const { data: adminDetails } = await axios.get(
        `${BaseUrl}/api/user-details`, 
       setHeader()
      );
      console.log(adminDetails);
      
      return adminDetails;
    }

}

export default AdminDetailsApi;