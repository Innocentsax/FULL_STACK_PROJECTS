import { Button, message } from "antd";
import {
  DeleteOutlined,
  EditOutlined,
} from '@ant-design/icons';
import PopupConfirm from "../../../components/PopupNotification/PopupConfirm";
import useCategory from "../../../hooks/useCategory"
import useProduct from "../../../hooks/useProduct"
import CustomAvatar from '../Layout/CustomAvatar'


const HandleColumnEvents = ({ category, setShowDrawer }) => {
  const { setSingleCategory, deleteCategory } = useCategory()
  const { setHeaderTitle } = useProduct()
  return(
    <div style={{ display: "flex", alignItems: "center", justifyContent: "center", gap: "20px"}}>
      <PopupConfirm 
        description={`Delete ${category.name.toUpperCase()}?`} 
        confirm={() => confirmDelete(category, deleteCategory)} 
        cancel={() => cancelDelete(category)}
        component={ <Button type="primary default" 
        danger ghost icon={<DeleteOutlined />}></Button> }/>

      <PopupConfirm description={`Do you want to edit product ${ category.name }?`} 
        confirm={() => confirmEdit(category, setShowDrawer, setSingleCategory, setHeaderTitle)}
        cancel={() => cancelEdit(setShowDrawer)}
        component={ <Button type="primary danger" 
        ghost icon={<EditOutlined />}></Button> }/>
    </div>
  );
}

const confirmDelete= (category, deleteCategory) => {
  deleteCategory(category.id)
};

const cancelDelete = (e) => {
  console.log(e);
  message.error('Click on No');
};


const confirmEdit = (category, setShowDrawer, setSingleCategory, setHeaderTitle) => {
  setHeaderTitle(`Update category: ${category.name}`)
  setSingleCategory(category)
  setShowDrawer(true)
  message.success('Click on Yes');
};

const cancelEdit = (setShowDrawer) => {
  setShowDrawer(false)
  message.error('Click on No');
};


const categoryColumns = (setShowDrawer) => [
  {
    title : "Image",
    dataIndex: 'imageUrl',
    key: 'imageUrl',
    width: '100px',
    render: (_, category) => {
    <CustomAvatar  key={category.id}
    style={{ 
      color: '#f56a00', backgroundColor: '#fde3cf' }} 
      name={category.name} url={category.imageUrl}/>
    }
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
    title: 'Size',
    dataIndex: 'size',
    key: 'size',
  },
  {
    title: "Actions",
    dataIndex: 'actions',
    key: 'actions',
    render: (_, category) => <HandleColumnEvents setShowDrawer={setShowDrawer} category={category}/>
  },
];

export { categoryColumns, }