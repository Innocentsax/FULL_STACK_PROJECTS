
import { Button, message } from "antd";
import {
  DeleteOutlined,
  EditOutlined,
} from '@ant-design/icons';

import PopupConfirm from "../../../components/PopupNotification/PopupConfirm";
import CustomAvatar from '../Layout/CustomAvatar'

const HandleAddNewProductDetails = () => {
  
  return(
    <div style={{ display: "flex", alignItems: "center", justifyContent: "center", gap: "20px"}}>
      <PopupConfirm 
        description={"description"} 
        confirm={() => confirmDeleteProduct()} 
        cancel={() => cancelDeleteProduct()}
        component={ <Button type="primary default" 
        danger ghost icon={<DeleteOutlined />}></Button> }/>

      <PopupConfirm description={"description"} 
        confirm={() => confirmEditProduct()}
        cancel={() => cancelEditProduct()}
        component={ <Button type="primary danger" 
        ghost icon={<EditOutlined />}></Button> }/>
    </div>
  );
}

const confirmDeleteProduct = () => {
};

const cancelDeleteProduct = (e) => {
  message.error('Click on No');
};


const confirmEditProduct = () => {

};

const cancelEditProduct = () => {
  message.error('Click on No');
};


const productColumns = [
  {
    title : "Image",
    dataIndex: 'imageUrl',
    key: 'imageUrl',
    width: '50px',
    render: (_, person) => <CustomAvatar  key={person.id} style={{ 
      color: '#f56a00', backgroundColor: '#fde3cf' }} 
      firstName={person.firstName} lastName={person.lastName}/>
  },
  {
    title: 'Id',
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: 'First Name',
    dataIndex: 'firstName',
    key: 'firstName',
  },
  {
    title: 'Last Name',
    dataIndex: 'lastName',
    key: 'lastName',
  },
  {
    title: 'Email',
    dataIndex: 'email',
    key: 'email',
  },
  {
    title: 'Gender',
    dataIndex: 'gender',
    key: 'gender',
  },
  {
    title: 'Data of Birth',
    dataIndex: 'date_of_birth',
    key: 'date_of_birth',
  },
  {
    title: 'Phone Number',
    dataIndex: 'phone',
    key: 'phone',
  },  
  {
    title: 'Verified',
    dataIndex: 'verificationStatus',
    key: 'verificationStatus',
  },
  {
    title: 'Address',
    dataIndex: 'address',
    key: 'address',
  },
  {
    title: "Actions",
    dataIndex: 'actions',
    key: 'actions',
    render: (_, person) => <HandleAddNewProductDetails />
  },
];

export { productColumns, }