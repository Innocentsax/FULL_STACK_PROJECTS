import { useState } from 'react';
import { Input, Col, Select, Form, Row, Spin, } from 'antd';
import { errorNotification, successNotification } from '../../../components/Notification'
import {
    LoadingOutlined,
  } from '@ant-design/icons';
  
import useProduct from '../../../hooks/useProduct'
import useCategory from '../../../hooks/useCategory'
const {Option} = Select;


const rowGutterSize = 10
const spanSize = 20;
const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

const PickupCenterForm = ({ setShowDrawer }) => {
    const [ form ] = Form.useForm() 
    const[submitting, setSubmitting] = useState(false);
    const { headerTitle } = useProduct()
    const { states, singlePickupCenter, createNewPickupCenter, updatePickupCenter } = useCategory()

    const onClose = () => setShowDrawer(false);
    

    const onFinish = pickupCenter => {
        const newPickupCenter = { ...pickupCenter, state: pickupCenter.state.toUpperCase() }
        console.log(JSON.stringify(newPickupCenter, null, 2));
        if(headerTitle === "Add New Pickup Center")
            createNewPickupCenter(setSubmitting, onClose, newPickupCenter)
        else
            updatePickupCenter(onClose, newPickupCenter)
    };

    const onFinishFailed = errorInfo => {
        alert(JSON.stringify(errorInfo, null, 2))
        errorNotification(`Operation Unsuccessful.`, "", "topLeft")
    };


    
  return (
    <Form layout="vertical"
            form={form}
            onFinishFailed={onFinishFailed}
            fields={form.setFieldsValue(singlePickupCenter)}
            onFinish={onFinish}>
        <Row gutter={rowGutterSize}>
            <Col span={spanSize}>
                <Form.Item
                    name="name"
                    label="Name"
                    rules={[{required: true, message: 'Pickup Center name required'}]}
                >
                    <Input placeholder="Pickup center Address"/>
                </Form.Item>
            </Col>
        </Row>

        <Row gutter={rowGutterSize}>
            <Col span={spanSize}>
                <Form.Item
                    name="email"
                    label="Email"
                    rules={[{required: true, message: 'Please enter email'}]}
                >
                    <Input placeholder="Email" type="email"/>
                </Form.Item>
            </Col>
        </Row>

        <Row gutter={rowGutterSize}>
            <Col span={spanSize}>
                <Form.Item
                    name="phone"
                    label="Phone Number"
                    rules={[{required: true, message: 'Please enter phone number'}]}
                >
                    <Input type="number" placeholder="Phone Number"/>
                </Form.Item>
            </Col>
        </Row>

        <Row gutter={rowGutterSize}>
            <Col span={spanSize}>
                <Form.Item
                    name="location"
                    label="location"
                    rules={[{required: true, message: 'Location of Center required'}]}
                >
                    <Input type="text" placeholder="Location"/>
                </Form.Item>
            </Col>
        </Row>

        <Row gutter={rowGutterSize}>
            <Col span={spanSize}>
                <Form.Item
                    name="state"
                    label="State"
                    rules={[{required: true, message: 'State selection is required'}]}
                >
                    <Select placeholder="Please select a state">
                        { 
                        states?.map((state, index) => 
                            <Option 
                                key={index} 
                                value={state.name}>{state.name}
                            </Option>
                        )}
                    </Select>
                </Form.Item>
            </Col>
        </Row>


        <Row gutter={rowGutterSize}>
            <Col span={spanSize}>
                <Form.Item
                    name="delivery"
                    label="Delivery Fee"
                    rules={[{required: true, message: 'Please enter delivery fee'}]}
                >
                    <Input type="number" placeholder="Enter delivery fee"/>
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

export default PickupCenterForm