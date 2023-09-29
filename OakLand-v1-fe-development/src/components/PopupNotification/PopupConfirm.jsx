import { Popconfirm, } from 'antd';

// const confirm = (e) => {
//   console.log(e);
//   message.success('Click on Yes');
// };
// const cancel = (e) => {
//   console.log(e);
//   message.error('Click on No');
// };

const PopupConfirm = ({title, description, confirm, cancel, component}) => (
  <Popconfirm
    placement="topLeft"
    title={title}
    description={description}
    onConfirm={confirm}
    onCancel={cancel}
    okText="Yes"
    okType="primary"
    ghost
    cancelText="No"
  >
    {component}
  </Popconfirm>
);
export default PopupConfirm;