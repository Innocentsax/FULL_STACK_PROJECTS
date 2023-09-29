import { Avatar, Button, message } from "antd";
import {
  UserOutlined,
  DeleteOutlined,
  EditOutlined,
} from '@ant-design/icons';
import PopupConfirm from "../../../components/PopupNotification/PopupConfirm";
import useCategory from "../../../hooks/useCategory";
import useProduct from "../../../hooks/useProduct";

const CustomAvatar = ({name, style}) => {
  let trim = name.trim();
  if(trim.length === 0) return <Avatar style={style} icon={<UserOutlined />}/>

  const split = trim.split(" ");
  if(split.length === 1) return <Avatar style={style}>{name.charAt(0).toUpperCase()}</Avatar>

  else if(split.length > 1) return <Avatar style={style}>{`${name.charAt(0)}${name.charAt(name.indexOf(' ') + 1)}`}</Avatar>
}


const stateColumns = (setShowDrawer) => [
  {
    title : "Image",
    dataIndex: 'imageUrl',
    key: 'imageUrl',
    width: '100px',
    render: (_, state) => <CustomAvatar  key={state.id}
    style={{ 
      color: '#f56a00', backgroundColor: '#fde3cf' }} 
      name={state.name} />
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
];

export { stateColumns, }