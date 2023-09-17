import React from 'react';
import { Modal, Confirm } from 'semantic-ui-react';
import AddressApi from '../../../apis/AddressApi';
import DashboardLayout from "../../../components/userdashboard/DashboardLayout";
import './AddressBook.scss';
import Alert from "../../../components/Alert";
import Pagination from './Pagination';
import Form from './Form';

function addressReducer(state, action) {
  switch (action.type) {
    case 'OPEN_MODAL':
      return { open: true, dimmer: action.dimmer, size: action.size, method: action.method  }
    case 'CLOSE_MODAL':
      return { open: false }
    default:
      throw new Error()
  }
}

const AddressBook = (props) => {
  const [defaultAddress, setDefaultAddress] = React.useState({});
  const [addresses, setAddresses] = React.useState([]);
  const [fields, setFields] = React.useState({
    fullName: '',
    email: '',
    address: '',
    city: '',
    state: '',
    phone: '',
    isDefaultShippingAddress: false
  });
  const [alertBox, setAlertBox] = React.useState({state:false, message:'',type:'error'});

  const [confirm, setConfirm] = React.useState({ open: false });
  const openConfirm = () => setConfirm({open : true});
  const closeConfirm = () => setConfirm({open : false});
  
  const [currentPage, setCurrentPage] = React.useState(1);
  const [addressesPerPage] = React.useState(2);
  const indexOfLastAddress = currentPage * addressesPerPage;
  const indexOfFirstAddress = indexOfLastAddress - addressesPerPage;
  const currentAddresses = addresses.slice(indexOfFirstAddress, indexOfLastAddress);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);
  const handleChange = (event) => {
      const {name, value, checked} = event.target;
      setFields((fields) => ({...fields, [name]: name !== 'isDefaultShippingAddress' ? value : checked }));
  };

  const handleSubmit = (e) => {
    AddressApi.addAddress(e, fields, setAlertBox);
    setFields({...fields, fullName: '', email: '', address: '', city: '', state: '', phone: ''});
  };

  const handleUpdate = (e) => {
    AddressApi.editAddress(e, fields, setAlertBox);
    setFields({...fields, fullName: '', email: '', address: '', city: '', state: '', phone: ''});
  }

  const [state, dispatch] = React.useReducer(addressReducer, {
    open: false,
    dimmer: undefined,
    size: undefined,
    method: 'POST',
  })
  const { open, dimmer, size, method } = state

  React.useEffect(() => {
    (async()=>{
      const defaultAddress = await AddressApi.getUserDefaultAddress();    
      setDefaultAddress(defaultAddress);
    })();
   }, [])

   React.useEffect(() => {
    (async()=>{
      const allAddresses = await AddressApi.getAllUserAddresses();
      const addressesExceptDefault = allAddresses.filter(address => address.id !== defaultAddress.id);
      setAddresses(addressesExceptDefault);
    })();
   })

  return (
     <DashboardLayout>
       <div className="wrapper">
         <h1 className="title">Address Book</h1>
         {
          
          defaultAddress.id !== undefined ? 
            (
              <div className="shell-wrapper">
                <h3 className="shell-title"> Default Shipping Address </h3>
                <p> { defaultAddress.fullName } </p>
                <p> { defaultAddress.email } </p>
                <p> { defaultAddress.address } </p>
                <p> { defaultAddress.city }, { defaultAddress.state } </p>
                <p> { defaultAddress.phone } </p>
                <div className="self-shell">
                  <a style={{color:"#ED165F"}} href="/account/addressbook#" onClick={() => {
                    setFields({fullName: defaultAddress.fullName,
                              email: defaultAddress.email,
                              address: defaultAddress.address,
                              city: defaultAddress.city,
                              phone: defaultAddress.phone,
                              state: defaultAddress.state,
                              isDefaultShippingAddress: defaultAddress.isDefaultShippingAddress,
                              id: defaultAddress.id
                              })
                    dispatch({ type: 'OPEN_MODAL', dimmer: 'inverted', size: 'tiny', method: 'PUT' })}}>Edit</a>              
                  <a style={{color:"#ED165F"}} href="/account/addressbook#" onClick={openConfirm}>Delete</a>
                  <Confirm
                      open={confirm.open}
                      onCancel={closeConfirm}
                      onConfirm={() => AddressApi.deleteAddress(defaultAddress.id)}
                      size='tiny'
                      dimmer='inverted'
                    />
                </div>
              </div>        
            )
           :
           (<div className="shell-wrapper">
              <h3>DEFAULT ADDRESS NOT FOUND!</h3>
            </div>)
         }
         <button className="btn-address" onClick={() => {
          setFields({
            fullName: '',
            email: '',
            address: '',
            city: '',
            state: '',
            phone: '',
            isDefaultShippingAddress: false
          })
           dispatch({ type: 'OPEN_MODAL', dimmer: 'inverted', size: 'tiny', method: 'POST' })}}>ADD NEW ADDRESS</button>
         
         
         <Modal
          size={size}
          dimmer={dimmer}
          open={open}
          method={method}
          onClose={() => dispatch({ type: 'CLOSE_MODAL' })}>
          <Modal.Header>ADD NEW ADDRESS</Modal.Header>
          <Modal.Content>
          {alertBox.state && <Alert text={alertBox.message} variant={alertBox.type}/>}
            {
              method === 'POST' ? <Form method={method} handleSubmit={handleSubmit} fields={fields} handleChange={handleChange} /> :
                <Form data={defaultAddress} method={method} handleSubmit={handleUpdate} fields={fields} handleChange={handleChange} />
            }
            
          </Modal.Content>
        </Modal>
        
          {
            currentAddresses.map(address => 
              <div className="shell-wrapper">
                <h3 className="shell-title"> Shipping Address </h3>
                <p> { address.fullName } </p>
                <p> { address.email } </p>
                <p> { address.address } </p>
                <p> { address.city }, { address.state } </p>
                <p> { address.phone } </p>
                <div className="self-shell">
                  <a style={{color:"#ED165F"}} href="/account/addressbook#" onClick={() =>{ 
                    setFields({
                      fullName: address.fullName,
                      email: address.email,
                      address: address.address,
                      city: address.city,
                      phone: address.phone,
                      state: address.state,
                      isDefaultShippingAddress: address.isDefaultShippingAddress,
                      id: address.id
                      })
                    dispatch({ type: 'OPEN_MODAL', dimmer: 'inverted', size: 'tiny', method: 'PUT' })}}>Edit</a> 
                  <a style={{color:"#ED165F"}} href="/account/addressbook#" onClick={openConfirm}>Delete</a>
                  <Confirm
                      open={confirm.open}
                      onCancel={closeConfirm}
                      onConfirm={() => {AddressApi.deleteAddress(address.id)}}
                      size='tiny'
                      dimmer='inverted'
                    />
                </div>
              </div>
            )
          }
          <Pagination itemsPerPage = {addressesPerPage} totalPages={addresses.length} paginate={paginate} currentPage={currentPage} />
        
       </div>
     </DashboardLayout>
  );
}

export default AddressBook;