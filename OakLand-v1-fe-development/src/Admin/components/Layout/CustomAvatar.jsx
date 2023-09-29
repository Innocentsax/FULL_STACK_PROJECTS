import { Avatar } from "antd";
import {
    UserOutlined,
  } from '@ant-design/icons';

const CustomAvatar = ({name, style, url}) => {
  if(url !== undefined || url !== null) {
    return <img src={url} alt={""}/>
  }else{
      let trim = name?.trim();
      if(trim?.length === 0) return <Avatar style={style} icon={<UserOutlined />}/>

      const split = trim.split(" ");
      if(split.length === 1) return <Avatar style={style}>{name.charAt(0).toUpperCase()}</Avatar>
      else return <Avatar style={style}>{`${name.charAt(0)}${name.charAt(name.indexOf(' ') + 1)}`}</Avatar>
    

  }
}

export default CustomAvatar