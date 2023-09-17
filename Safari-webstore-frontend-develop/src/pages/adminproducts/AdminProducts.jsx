import React, {useState} from 'react';
import Select from 'react-select';
import makeAnimated from 'react-select/animated';
import './AdminProducts.css';
import AdminLayout from '../../components/adminlayout/AdminLayout';
import Footer from '../../components/Footer';
import {categories} from '../../data/categories';
import {subCategories} from '../../data/subCategories';
import {useForm} from 'react-hook-form';
import {yupResolver} from '@hookform/resolvers/yup';
import * as yup from 'yup';
import {colors} from '../../data/colors';
import {sizes} from "../../data/sizes";
import Axios from 'axios';
import BaseUrl from '../../apis/BaseUrl'


const schema = yup.object().shape({
  title: yup.string().required(),
  price: yup.string().required(),
  description: yup.string().required,
})

const token = localStorage.getItem('token');
  const AdminProducts = (props) => {

    const {
      register,
      handleSubmit,
      errors
    } = useForm({
      resolver: yupResolver(schema),
    });
    const [selectedImages, setSelectedImages] = useState([]);
    const [imagesToUpload, setImagesToUpload] = useState([]);
    const [uploadedImagesUrl, setUploadedImagesUrl] = useState([]);
    const [category, setCategory] = useState([]);
    const [subCategory, setSubCategory] = useState([]);
    const [color, setColor] = useState([]);
    const [size, setSize] = useState([]);
    const [name, setName] = useState("");
    const [price, setPrice] = useState("");
    const [description, setDescription] = useState("");

    const handleImageChange = (e) => {
      if (e.target.files) {
        const filesArray = Array.from(e.target.files).map((file) => URL.createObjectURL(file));
        const imagesArray = Array.from(e.target.files).map((file) => file);

        setImagesToUpload((prevImages) => prevImages.concat(imagesArray));
        setSelectedImages((prevImages) => prevImages.concat(filesArray));
        Array.from(e.target.files).map(
           (file) => URL.revokeObjectURL(file) // avoid memory leak
        );
      }
    };


    const renderPhotos = (source) => {
      return source.map((photo) => {
        return <img className="product-image"
                    src={photo}
                    alt=""
                    key={photo}
        />;
      });
    };

    const customTheme = (theme) => {
      return {
        ...theme,
        colors: {
          ...theme.colors,
          primary25: '#F5F5F5',
          primary: '#ED165F',
        }
      }
    }

    let imagesArray = [];

    const modifyContent = (content) => {
      const newContent = [];

      content.forEach(data => {
        newContent.push(data.value);
      })
      return newContent;
    }

    const submitHandler = (event) => {
      event.preventDefault();
      imagesToUpload.map((image) => {
        const formData = new FormData();
        formData.append("file", image)
        formData.append("upload_preset", "vgpe2ptd");

        Axios.post(
           "https://api.cloudinary.com/v1_1/dmtkcdiya/image/upload",
           formData
        ).then((response) => {
          console.log(response);
          imagesArray.push(response.data.secure_url);

          setUploadedImagesUrl(imagesArray);

          const productRequest = {
            name: name,
            price: price,
            description: description,
            category: modifyContent(category),
            subCategory: modifyContent(subCategory),
            sizes: modifyContent(size),
            colors: modifyContent(color),
            productImages: imagesArray
          }
          Axios.post(
             BaseUrl + "/api/admin/add-product",
             productRequest, {
               headers: {
                 Authorization: `Bearer ${token}`,
                 "content-type": "application/json",
               }
             }).then(response => {console.log("Product response " + response)})
        });

      });
    };

    return (
       <>
         <AdminLayout>
           <form onSubmit={submitHandler}>
             <div className="product-wrapper">
               <h1 className="title"> Add To Products </h1>
               <div className="product-list-wrapper">
                 <h3 className="info-title"> Add Products </h3>
                 <div className="product-details-wrapper">
                   <div className="product-details">
                     <div className="name-price">
                       <div>
                         <h4> Title: </h4>
                         <input name="name"
                                type="text"
                                placeholder="Title of Product"
                                onChange={(event) => setName(event.target.value)}
                         />
                       </div>
                       <div>
                         <h4> Price: </h4>
                         <input name="price"
                                type="number"
                                min="0"
                                step="0.01"
                                required pattern="^\d+(?:\.\d{1,2})?$"
                                placeholder="Price of Product"
                                onChange={(event) => setPrice(event.target.value)}
                         />
                       </div>
                     </div>

                     <div>
                       <h4> Add Image(s): </h4>
                       <input name="image"
                              type="file"
                              id="file"
                              multiple onChange={handleImageChange}
                       />
                       <div className="product-image-wrapper">
                         <div className="label-holder">
                           <label htmlFor="file"
                                  className="label">
                             <i className="material-icons"> add_a_photo </i>
                           </label>
                         </div>
                         <div className="result"> {renderPhotos(selectedImages)}
                         </div>
                       </div>
                     </div>
                   </div>

                   <div className="description-details">
                     <label>
                       <h4> Description: </h4>
                       <textarea name="description"
                                 placeholder="Please Enter A Description"
                                 className="description"
                                 onChange={(e) => setDescription(e.target.value)}
                       />
                     </label>
                   </div>
                 </div>

                 <div className="category-main-wrapper">
                   <div className="category-wrapper">
                     <h4> Category: </h4>
                     <Select name="category"
                             components={makeAnimated()}
                             theme={customTheme}
                             isMulti isSearchable options={categories}
                             placeholder="Select a category"
                             noOptionsMessage={() => "No matching category found!"}
                             onChange={setCategory}
                     />
                   </div>

                   <div className="category-wrapper">
                     <h4> Sub - Category: </h4>
                     <Select name="sub-category"
                             components={makeAnimated()}
                             theme={customTheme}
                             isMulti isSearchable options={subCategories}
                             placeholder="Select a sub-category"
                             noOptionsMessage={() => "No matching sub-category found!"}
                             onChange={setSubCategory}
                     />
                   </div>
                 </div>
                 <div className="category-main-wrapper">

                   <div className="category-wrapper">
                     <h4> Color: </h4>
                     <Select name="color"
                             components={makeAnimated()}
                             onChange={setColor}
                             theme={customTheme}
                             isMulti isSearchable options={colors}
                             placeholder="Select colors"
                             noOptionsMessage={() => "No matching color found!"}
                     />
                   </div>

                   <div className="category-wrapper">
                     <h4> Size: </h4>
                     <Select name="size"
                             components={makeAnimated()}
                             onChange={setSize}
                             theme={customTheme}
                             isMulti isSearchable options={sizes}
                             placeholder="Select sizes"
                             noOptionsMessage={() => "No matching size found!"}
                     />
                   </div>
                 </div>
               </div>
             </div>
             <input type="submit"
                    id="submit"
                    value="Add Product"
                    className="add-product-btn"/>
           </form>
         </AdminLayout>
         <Footer/>
       </>
    );
}
export default AdminProducts;
