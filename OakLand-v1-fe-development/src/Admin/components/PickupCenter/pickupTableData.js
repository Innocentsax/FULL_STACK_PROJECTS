import { Avatar, Button, message } from "antd";
import {
  DeleteOutlined,
  EditOutlined,
} from '@ant-design/icons';
import PopupConfirm from "../../../components/PopupNotification/PopupConfirm";
import useProduct from "../../../hooks/useProduct";
import useCategory from "../../../hooks/useCategory";
import CustomAvatar from '../Layout/CustomAvatar'

const HandleColumnEvents = ({ pickupCenter, setShowDrawer }) => {
  const { setHeaderTitle } = useProduct()
  const { deletePickupCenter, setSinglePickupCenter } = useCategory()
  
  return(
    <div style={{ display: "flex", alignItems: "center", justifyContent: "center", gap: "20px"}}>
      <PopupConfirm 
        description={`Delete ${pickupCenter.name.toUpperCase()}?`} 
        confirm={() => confirmDeleteProduct(pickupCenter, deletePickupCenter)} 
        cancel={() => cancelDeleteProduct(pickupCenter)}
        component={ <Button type="primary default" 
        danger ghost icon={<DeleteOutlined />}></Button> }/>

      <PopupConfirm description={`Do you want to edit pickup center ${pickupCenter.name}?`} 
        confirm={() => confirmEditProduct(pickupCenter, setShowDrawer, setSinglePickupCenter, setHeaderTitle)}
        cancel={() => cancelEditProduct(setShowDrawer)}
        component={ <Button type="primary danger" 
        ghost icon={<EditOutlined />}></Button> }/>
    </div>
  );
}

const confirmDeleteProduct = (pickupCenter, deletePickupCenter) => {
  deletePickupCenter(pickupCenter)
};

const cancelDeleteProduct = (e) => {
  console.log(e);
  message.error('Click on No');
};


const confirmEditProduct = (pickupCenter, setShowDrawer, setSinglePickupCenter, setHeaderTitle) => {
  setHeaderTitle(`Update PickupCenter: ${pickupCenter.name}`)
  setSinglePickupCenter(pickupCenter)
  setShowDrawer(true)
  message.success('Click on Yes');
};

const cancelEditProduct = (setShowDrawer) => {
  setShowDrawer(false)
  message.error('Click on No');
};


const pickupColumns = (setShowDrawer) => [
  // {
  //   title : "Image",
  //   dataIndex: 'imageUrl',
  //   key: 'imageUrl',
  //   width: '80px',
  //   render: (_, product) => <CustomAvatar  key={product.id}
  //   style={{ 
  //     color: '#f56a00', backgroundColor: '#fde3cf' }} 
  //     name={product.name} />
  // },
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
    title: 'Location',
    dataIndex: 'location',
    key: 'location',
  },
  {
    title: 'State',
    dataIndex: 'state',
    key: 'state',
  },
  {
    title: 'Email',
    dataIndex: 'email',
    key: 'email',
  },
  {
    title: 'Phone',
    dataIndex: 'phone',
    key: 'phone',
  },

  {
    title: 'Delivery',
    dataIndex: 'delivery',
    key: 'delivery'
  },
  {
    title: "Actions",
    dataIndex: 'actions',
    key: 'actions',
    render: (_, pickupCenter) => <HandleColumnEvents setShowDrawer={setShowDrawer}
      pickupCenter={pickupCenter}/>
  },
];

export { pickupColumns, }