import { useState } from 'react';
import { Input, Col, Form, Row, Spin, } from 'antd';
import { errorNotification, } from '../../../components/Notification'
import {
    LoadingOutlined,
  } from '@ant-design/icons';
  
import useProduct from '../../../hooks/useProduct'
import useCategory from '../../../hooks/useCategory'
import UploadImageBtn from './UploadImageBtn';


const rowGutterSize = 10
const spanSize = 20;
const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

const CategorySubCategoryForm = ({ setShowDrawer }) => {
    const [ form ] = Form.useForm() 
    const[submitting, setSubmitting] = useState(false);
    const { headerTitle, productImgUrl } = useProduct()
    const { singleCategory, createCategory, updateCategory, 
        createSubcategory } = useCategory()

    const onClose = () => setShowDrawer(false);

    const onFinish = category => {
        const newCategory = { ...category, imageUrl: productImgUrl }
        if(headerTitle === "Add New Category")
            createCategory(setSubmitting, onClose, newCategory)
        else if(headerTitle === "Update Category")
            updateCategory(setSubmitting, onClose, newCategory)
        else if(headerTitle === "Create new subcategory")
            createSubcategory(setSubmitting, onClose, newCategory)
    };

    const onFinishFailed = errorInfo => {
        alert(JSON.stringify(errorInfo, null, 2))
        errorNotification(`Operation Unsuccessful.`, "", "topLeft")
    };


    
  return (
    <section className="form-section">
        <UploadImageBtn />
        <Form layout="vertical"
                form={form}
                onFinishFailed={onFinishFailed}
                fields={form.setFieldsValue({ ...singleCategory, imageUrl: productImgUrl})}
                onFinish={onFinish}>
                    
            {/* <Row gutter={rowGutterSize}>
                <Col span={spanSize}>
                    <Form.Item
                        name="imageUrl"
                        label="imageUrl"
                        rules={[{required: true, message: 'Pickup Center name required'}]}
                    >
                        <Input placeholder="Pickup center Address"/>
                    </Form.Item>
                </Col>
            </Row> */}

            <Row gutter={rowGutterSize}>
                <Col span={spanSize}>
                    <Form.Item
                        name="name"
                        label="Name"
                        rules={[{required: true, message: 'Category name required'}]}
                    >
                        <Input placeholder="Enter Name of Category"/>
                    </Form.Item>
                </Col>
            </Row>

            <Row gutter={rowGutterSize}>
                <Col span={spanSize}>
                    <Form.Item
                        name="imageUrl"
                        label="imageUrl"
                        rules={[{required: true, message: 'Please enter image url'}]}
                    >
                        <Input type="text" placeholder="Image URL"/>
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
        </section>
  )
}

export default CategorySubCategoryForm