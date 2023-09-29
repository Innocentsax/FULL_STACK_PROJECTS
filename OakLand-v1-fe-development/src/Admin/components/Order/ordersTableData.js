
import { Button, message } from "antd";
import {
  DeleteOutlined,
} from '@ant-design/icons';

import PopupConfirm from "../../../components/PopupNotification/PopupConfirm";

const HandleAddNewProductDetails = () => {
  
  return(
    <div style={{ display: "flex", alignItems: "center", justifyContent: "center", gap: "20px"}}>
      <PopupConfirm 
        description={"description"} 
        confirm={() => confirmDeleteProduct()} 
        cancel={() => cancelDeleteProduct()}
        component={ <Button type="primary default" 
        danger ghost icon={<DeleteOutlined />}></Button> }/>

      {/* <PopupConfirm description={"description"} 
        confirm={() => confirmEditProduct()}
        cancel={() => cancelEditProduct()}
        component={ <Button type="primary danger" 
        ghost icon={<EditOutlined />}></Button> }/> */}
    </div>
  );
}

const confirmDeleteProduct = () => {
};

const cancelDeleteProduct = (e) => {
  message.error('Click on No');
};




const productColumns = [
  {
    title: "FirstName",
    key: "firstName",
    dataIndex: "firstName"
  },
  {
    title: "LastName",
    key: "lastName",
    dataIndex: "lastName"
  },
  {
    title: "Phone Number",
    key: "phone",
    dataIndex: "phone"
  },
  {
    title: "Grand Total",
    key: "grandTotal",
    dataIndex: "grandTotal",
  },
  {
    title: "Transaction",
    key: "status",
    dataIndex: "status"
  },
  {
    title: "Pickup Center",
    key: "pickupCenterName",
    dataIndex: "pickupCenterName"
  },
  {
    title: "Pickup Address",
    key: "pickupCenterAddress",
    dataIndex: "pickupCenterAddress",
  },
  {
    title: "Status",
    key: "pickupStatus",
    dataIndex: "pickupStatus",
  },
  // {
  //   title: "Actions",
  //   dataIndex: 'actions',
  //   key: 'actions',
  //   render: (_, order) => <HandleAddNewProductDetails />
  // },
];

export { productColumns, }