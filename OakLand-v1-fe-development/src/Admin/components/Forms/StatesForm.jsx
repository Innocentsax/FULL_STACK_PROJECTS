import { useState } from 'react';
import { Input, Col, Select, Form, Row, Spin, } from 'antd';
import { errorNotification, successNotification } from '../../../components/Notification'
import {
    LoadingOutlined,
  } from '@ant-design/icons';
  
import useProduct from '../../../hooks/useProduct'
import useCategory from '../../../hooks/useCategory'


const rowGutterSize = 10
const spanSize = 20;
const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

const StatesForm = ({ setShowDrawer }) => {
    const [ form ] = Form.useForm() 
    const[submitting, setSubmitting] = useState(false);
    const { headerTitle } = useProduct()
    const { createNewState, updateState, singleState } = useCategory()

    const onClose = () => setShowDrawer(false);

    const onFinish = newState => {
        if(headerTitle === "Add New State")
            createNewState(setSubmitting, onClose, newState)
        else
            updateState(onClose, newState)
    };

    const onFinishFailed = errorInfo => {
        alert(JSON.stringify(errorInfo, null, 2))
        errorNotification(`Operation Unsuccessful.`, "", "topLeft")
    };


    
  return (
    <Form layout="vertical"
            form={form}
            onFinishFailed={onFinishFailed}
            fields={form.setFieldsValue({nameOfState: singleState.name})}
            onFinish={onFinish}>
        <Row gutter={rowGutterSize}>
            <Col span={spanSize}>
                <Form.Item
                    name="nameOfState"
                    label="Name"
                    rules={[{required: true, message: 'Name  of State Required'}]}
                >
                    <Input placeholder="State"/>
                </Form.Item>
            </Col>
        </Row>

        <Row>
            <Col span={spanSize}>
                <Form.Item >
                    <button className="primary home-btn" type="submit" style={{margin: "auto"}}>Submit</button>
                </Form.Item>
            </Col>
        </Row>
        <Row>
            { submitting && <Spin indicator={antIcon}/> }
        </Row>
    </Form>
  )
}

export default StatesForm