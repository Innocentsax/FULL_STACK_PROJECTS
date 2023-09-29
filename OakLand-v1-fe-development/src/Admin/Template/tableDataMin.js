import { Avatar, Button, message } from "antd";
import {
  UserOutlined,
  DeleteOutlined,
  EditOutlined,
} from '@ant-design/icons';

import PopupConfirm from "../../../components/PopupNotification/PopupConfirm";

const CustomAvatar = ({name, style}) => {
  let trim = name.trim();
  if(trim.length === 0) return <Avatar style={style} icon={<UserOutlined />}/>

  const split = trim.split(" ");
  if(split.length === 1) return <Avatar style={style}>{name.charAt(0).toUpperCase()}</Avatar>

  else if(split.length > 1) return <Avatar style={style}>{`${name.charAt(0)}${name.charAt(name.indexOf(' ') + 1)}`}</Avatar>
}

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
  setShowDrawer(false)
  message.error('Click on No');
};


const productColumns = [
  {
    title : "Image",
    dataIndex: 'imageUrl',
    key: 'imageUrl',
    render: (_, product) => <CustomAvatar  key={product.id}
    style={{ 
      color: '#f56a00', backgroundColor: '#fde3cf' }} 
      name={product.name} />
  },
  {
    title: 'Id',
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: 'Name',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: 'Price',
    dataIndex: 'price',
    key: 'price',
  },
  {
    title: 'Quantity',
    dataIndex: 'availableQty',
    key: 'availableQty',
  },
  {
    title: 'Color',
    dataIndex: 'color',
    key: 'color',
  },
  {
    title: 'Description',
    dataIndex: 'description',
    key: 'description',
  },
  {
    title: "Actions",
    dataIndex: 'actions',
    key: 'actions',
    render: (_, product) => <HandleAddNewProductDetails />
  },
];

export { productColumns, }