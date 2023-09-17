import axios from 'axios'
import BaseUrl from './BaseUrl';
import setHeader from '../utilities/Header';


const customHeader = setHeader();

const AddressApi = {

    getUserDefaultAddress: async () => {
      const { data: defaultAddress } = await axios.get(
        `${BaseUrl}/api/shipping_address/default`, 
        customHeader
      );
      
      return defaultAddress;
    },

    getAllUserAddresses: async () => {
        const { data: addresses } = await axios.get(
          `${BaseUrl}/api/shipping_address/`, 
          customHeader
        );
        
        return addresses;
      },

    addAddress :  async (event, formData, setAlertBox) => {
        event.preventDefault();
        try {
            await axios.post( `${BaseUrl}/api/shipping_address/add`, formData, customHeader)
            .then((response) => {
                    const {status} = response;
                    if (status === 200){
                        setAlertBox({state:true, message:'Address has been added successfully!', type:'success'});
                    }
                }
            ); 
        }catch (e) {
            const {status}= e.response;
            if (status === 400 || status === 500){
                setAlertBox({state:true, message:'Something went wrong!!', type:'error'});
            }
        }
    },

    editAddress :  async (event, formData, setAlertBox) => {
        event.preventDefault();
        try{
            await axios.put( `${BaseUrl}/api/shipping_address/edit/${formData.id}`, formData, customHeader)
            .then((response) => {
                    const {status} = response;
                    console.log(status)
                    if (status === 200){
                        setAlertBox({state:true, message:'Address has been updated successfully!', type:'success'});
                    } 
                }
            );
        }catch (e) {
            const {status}= e.response;
            if (status === 400 || status === 500){
                setAlertBox({state:true, message:'Something went wrong!!', type:'error'});
            }
        }
    },

    deleteAddress: async (id) => {
        let { data: response } = await axios.delete(
          `${BaseUrl}/api/shipping_address/delete/${id}`,
          customHeader
        );
        
        return response;
      },

}

export default AddressApi;