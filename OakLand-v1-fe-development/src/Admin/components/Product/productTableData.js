import { Button, message } from "antd";
import {
  DeleteOutlined,
  EditOutlined,
} from '@ant-design/icons';
import PopupConfirm from "../../../components/PopupNotification/PopupConfirm";
import useProduct from "../../../hooks/useProduct";
import CustomAvatar from '../Layout/CustomAvatar'


const HandleAddNewProductDetails = ({ product, setShowDrawer }) => {
  const { getProducts, deleteProduct, setSingleProduct, setHeaderTitle } = useProduct()
  return(
    <div style={{ display: "flex", alignItems: "center", justifyContent: "center", gap: "20px"}}>
      <PopupConfirm 
        description={`Delete ${product.name.toUpperCase()}?`} 
        confirm={() => confirmDeleteProduct(product, deleteProduct, getProducts)} 
        cancel={() => cancelDeleteProduct(product)}
        component={ <Button type="primary default" 
        danger ghost icon={<DeleteOutlined />}></Button> }/>

      <PopupConfirm description={`Do you want to edit product ${product.name}?`} 
        confirm={() => confirmEditProduct(product, setShowDrawer, setSingleProduct, setHeaderTitle)}
        cancel={() => cancelEditProduct(setShowDrawer)}
        component={ <Button type="primary danger" 
        ghost icon={<EditOutlined />}></Button> }/>
    </div>
  );
}

const confirmDeleteProduct = (product, deleteProduct, getProducts) => {
  deleteProduct(product)
  getProducts()
};

const cancelDeleteProduct = (e) => {
  console.log(e);
  message.error('Click on No');
};


const confirmEditProduct = (product, setShowDrawer, setSingleProduct, setHeaderTitle) => {
  setHeaderTitle(`Update Product: ${product.name}`)
  setSingleProduct(product)
  setShowDrawer(true)
  message.success('Click on Yes');
};

const cancelEditProduct = (setShowDrawer) => {
  setShowDrawer(false)
  message.error('Click on No');
};


const productColumns = (setShowDrawer) => [
  {
    title : "Image",
    dataIndex: 'imageUrl',
    key: 'imageUrl',
    render: (_, product) => <CustomAvatar  key={product.id}
    style={{ 
      color: '#f56a00', backgroundColor: '#fde3cf' }} 
      name={product.name} url={product.imageUrl} />
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
    render: (_, product) => <HandleAddNewProductDetails key={product.id}
      product={product} setShowDrawer={setShowDrawer}/>
  },
];

export { productColumns, }