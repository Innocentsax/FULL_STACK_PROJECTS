import React from 'react'

import { Collapse } from 'antd';

const Collapsible = ({ children }) => {
  const onChange = (key) => {
    console.log(key);
  }

  return (
    <Collapse bordered={false} ghost accordion defaultActiveKey={['1']} 
    className="site-collapse-custom-collapse"
    onChange={onChange}>
      { children }
    </Collapse>
  )
}

export default Collapsible