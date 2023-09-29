import { useEffect, useState } from 'react';
import { Input, Col, Select, Form, Row, Spin, } from 'antd';
import { errorNotification } from '../../../components/Notification'
import {
    LoadingOutlined,
  } from '@ant-design/icons';
  
import useProduct from '../../../hooks/useProduct'
import useCategory from '../../../hooks/useCategory'
import UploadImageBtn from './UploadImageBtn';
const {Option} = Select;
const { TextArea } = Input;


const rowGutterSize = 10
const spanSize = 20;
const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

const AddProductForm = ({ setShowDrawer }) => {
    const [ form ] = Form.useForm() 
    const[submitting, setSubmitting] = useState(false);
    const { singleProduct, headerTitle, addNewProduct, editProduct, productImgUrl, } = useProduct()
    const { subcategories, getSubcategories } = useCategory()

    const onClose = () => setShowDrawer(false);

    useEffect(() => {
        getSubcategories()
    }, [])
    

    const onFinish = product => {
        const newProduct = { ...product, imageUrl: productImgUrl }
        console.log(JSON.stringify(newProduct, null, 2));
        if(headerTitle === "Add New Product")
            addNewProduct(setSubmitting, onClose, newProduct)
        else
            editProduct(onClose, newProduct)
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
            fields={form.setFieldsValue({ ...singleProduct, imageUrl: productImgUrl})}
            onFinish={onFinish}>
        <Row gutter={rowGutterSize}>
            <Col span={spanSize}>
                <Form.Item
                    name="name"
                    label="Name"
                    rules={[{required: true, message: 'Product name required'}]}
                >
                    <Input placeholder="Product Name"/>
                </Form.Item>
            </Col>
        </Row>

        <Row gutter={rowGutterSize}>
            <Col span={spanSize}>
                <Form.Item
                    name="price"
                    label="Price"
                    rules={[{required: true, message: 'Price required'}]}
                >
                    <Input type="number" placeholder="Product Price"/>
                </Form.Item>
            </Col>
        </Row>

        <Row gutter={rowGutterSize}>
            <Col span={spanSize}>
                <Form.Item
                    name="availableQty"
                    label="Quantity"
                    rules={[{required: true, message: 'Please enter quantity'}]}
                >
                    <Input placeholder="Quantity" type="number"/>
                </Form.Item>
            </Col>
        </Row>

        <Row gutter={rowGutterSize}>
            <Col span={spanSize}>
                <Form.Item
                    name="subCategory"
                    label="Subcategory"
                    rules={[{required: true, message: 'Product SubCategory Must be Selected'}]}
                >
                    <Select placeholder="Please select a subcategory">
                        { 
                        subcategories?.map((subCat, index) => 
                            <Option 
                                key={index} 
                                value={subCat.name}>{subCat.name}
                            </Option>
                        )}
                    </Select>
                </Form.Item>
            </Col>
        </Row>

        <Row gutter={rowGutterSize}>
            <Col span={spanSize}>
                <Form.Item
                    name="color"
                    label="Color"
                    rules={[{required: true, message: 'Please enter product color'}]}
                >
                    <Input placeholder="Product Color"/>
                </Form.Item>
            </Col>
        </Row>


        <Row gutter={rowGutterSize}>
            <Col span={spanSize}>
                <Form.Item
                    name="description"
                    label="Description"
                    rules={[{required: true, message: 'Product Description Cannot Be Empty'}]}
                >
                <TextArea
                showCount
                maxLength={500}
                style={{ height: 120, marginBottom: 24 }}
                // onChange={onChange}
                placeholder="Enter Product Description" />
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

export default AddProductForm