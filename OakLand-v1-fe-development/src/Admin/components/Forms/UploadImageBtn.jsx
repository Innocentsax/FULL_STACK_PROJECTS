import React from "react";
import useProduct from "../../../hooks/useProduct";
import { UploadOutlined } from '@ant-design/icons';
import { Button, message, Upload } from 'antd';

const UploadImageBtn = () => {
    const { setProductImgUrl } = useProduct()

    const props = {
        name: 'productImage',
        action: `http://localhost:8080/api/v1/products/upload-image`,
        headers: {
          authorization: 'authorization-text',
        },
  
        onChange(info) {
            if (info.file.status !== 'uploading') {
                console.log(info.file, info.fileList);
            }
            if (info.file.status === 'done') {
                console.log(info)
                console.log(info.response)
                setProductImgUrl(info.file.response)
                message.success(`${info.file.name} file uploaded successfully`)
            } else if (info.file.status === 'error') {
                message.error(`${info.file.name} file upload failed.`);
            }
        },
};
    return (
        <Upload {...props} style={{marginBottom: "10px"}}>
            <Button icon={<UploadOutlined />}>Click to Upload</Button>
      </Upload>
    );
}

export default UploadImageBtn;