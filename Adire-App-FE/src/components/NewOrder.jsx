import React from 'react'
import { Link } from 'react-router-dom'
import axios from 'axios'
import { useSelector, useDispatch } from 'react-redux';
import { IconFormRow } from '.'
import Wrapper from '../assets/wrappers/NewOrder'
import { CalendarInputIcon } from '../utils/SVGIcons'
import FormRow from './FormRow'
import Measurement from './Measurement'
import { materialTypes, statusOptions } from '../utils/enumData'
import { getDueDate } from '../utils/localDateTime'
import { handleChange, createOrder, editOrder } from '../features/order/orderSlice'
import { toast } from 'react-toastify'
import { convertMeasurementsToMeasurementList, formatDate } from '../utils/helpers'
import AddPhotoAlternateOutlinedIcon from '@mui/icons-material/AddPhotoAlternateOutlined';

const NewOrder = () => {
  const {
    isLoading,
    customerEmail,
    designImage,
    designImageUrl,
    dueDate,
    duration,
    materialType,
    measurementList,
    numberOfOrders,
    orderPrice,
    orderStatus,
    isEditing,
    editOrderId,
  } = useSelector((store) => store.order);
  const dispatch = useDispatch();

  const handleSubmit = (e) => {
    e.preventDefault();

    if (isEditing && (!customerEmail || !duration || !materialType || !orderPrice || !dueDate  || !orderStatus)) {
      toast.error('Please Fill Out All Fields');
      return;
    }

    if (!customerEmail || !duration || !materialType || !orderPrice || !dueDate) {
      toast.error('Please Fill Out All Fields');
      return;
    }
    

    if(isEditing) {
      dispatch(
        editOrder({ 
          orderId: editOrderId, order: { 
            customerEmail,
            designImageUrl,
            dueDate,
            duration,
            materialType,
            measurementList: convertMeasurementsToMeasurementList(measurementList),
            orderPrice,
            orderStatus 
          }
        })
      );
      return;
    }

    dispatch(createOrder({
      customerEmail,
      designImageUrl,
      dueDate,
      duration,
      materialType,
      measurementList: convertMeasurementsToMeasurementList(measurementList),
      orderPrice
    }));
  };

const handleOrderInput = (e) => {
  const name = e.target.name;
  const value = e.target.value;
  console.log(name, value);
  if((name === 'duration') && !isNaN(value)) {
    const dueDateValue = getDueDate(value)
    console.log(dueDateValue);
    dispatch(handleChange({ name, value }));
    dispatch(handleChange({ name: 'dueDate', value: dueDateValue }));
  }
  dispatch(handleChange({ name, value }));
};

console.log({
  isLoading,
  customerEmail,
  designImage,
  designImageUrl,
  dueDate,
  duration,
  materialType,
  measurementList,
  measurementLists: convertMeasurementsToMeasurementList(measurementList),
  numberOfOrders,
  orderPrice,
  orderStatus,
  isEditing,
  editOrderId,
});

  
  const cloud_name = "dlvzbmq1o";
  const preset_key = "j2h0xeav";

  const uploadImage = (event) => {
    const file = event.target.files[0];
    console.log(file);
    const formData = new FormData();
    formData.append("file", file);
    formData.append("upload_preset", preset_key);

    axios.post(
      `https://api.cloudinary.com/v1_1/${cloud_name}/image/upload`, 
      formData
    ).then((response) => {
      console.log(response);
      console.log(response.data.secure_url);
      dispatch(handleChange({ name : 'designImageUrl', value: response.data.secure_url }));
      // response.data.secure_url
    }).catch(err => console.log(err));
  }

  return (
    
    <Wrapper>
      <form >
      <div className="new-order-container">
        <div className='new-order-form'>
          {/* <header>
          <div className='main-icon'>{company.charAt(0)}</div>
          <div className='info'>
            <h5>{position}</h5>
            <p>{company}</p>
          </div>
          </header> */}
          <div className='header'>
            <div className='info'>
              <h3>create new order</h3>
              <p className='sub-heading'>empower your business with seamless order management</p>
            </div>
            {/* <button className='view-all-btn'> */}
            <Link to="/all-orders" className='view-all-btn'>
              View all
            </Link>
          </div>

          {/* Order details form */}
          <div className='form'>
            <FormRow
              type='text'
              name='customerEmail'
              value={customerEmail}
              labelText="customer email"
              handleChange={handleOrderInput}
            />

            <div className='form-grid'>
              <FormRow
                type='text'
                name='duration'
                value={duration}
                labelText="project duration"
                handleChange={handleOrderInput}
              />
              <IconFormRow
                type='text'
                name='dueDate'
                isReadOnly={true}
                value={dueDate && formatDate(dueDate)}
                labelText="due date"
                // handleChange={handleOrderInput}
              >
                <CalendarInputIcon />
              </IconFormRow>
            </div>


            <div className='form-grid'>
              <FormRow
                type='text'
                name='orderPrice'
                value={orderPrice}
                handleChange={handleOrderInput}
              />

              {/* <div className="form-row">
                <label className="form-label">MaterialType</label>
                <button 
                type='button'
                onClick={() => console.log("sogo")}
                  className="form-input form-input-default material-btn"
                >
                  select material(s)
                </button>
              </div> */}

              <div className='form-row'>
                <label htmlFor="materialType" className='form-label'>
                  material type
                </label>
                <select
                  name="materialType"
                  value={materialType}
                  onChange={handleOrderInput}
                  id="materialType"
                  className='form-select form-input-default'
                >
                  <option value="">--Select--</option>
                  {Object.entries(materialTypes).map(([key, name]) => (
                    <option key={key} value={key}>
                      {name}
                    </option>
                  ))}
                </select>
              </div>
            </div>
              
              {/* <FormRow
                type='file'
                name='image'
                // value={values.password}
                placeholder="Select Image"
                labelText="add image"
                // handleChange={handleChange}
              /> */}
            {/* <div className='form-grid'> */}
              {isEditing && (
                <div className='form-row'>
                  <label htmlFor="status" className='form-label'>
                    status
                  </label>
                  <select
                    name="orderStatus"
                    value={orderStatus}
                    onChange={handleOrderInput}
                    className='form-select form-input-default'
                  >
                    <option value="">--Select--</option>
                    {Object.entries(statusOptions).map(([key, name]) => (
                    <option className='form-option' key={key} value={key}>{name}</option>
                  ))}
                  </select>
                </div>
              )}


              <div className='form-row'>
                <label htmlFor='designImageUrl' className='form-label'>
                  add image
                </label>
                <div className='file-inputs'>
                  <input
                    className='image-input' 
                    type="file" 
                    name="designImageUrl" 
                    id="designImageUrl" 
                    onChange={uploadImage} 
                  />
                  <button type='button' className='image-btn'>
                    <i>
                      <AddPhotoAlternateOutlinedIcon />
                    </i>
                    Upload
                  </button>
                </div>
                {/* <label htmlFor={name} className='form-label'>
                  {labelText || name}
                </label>
                {children} */}
                {/* <input
                  min={min}
                  id={name}
                  type={type}
                  name={name}
                  value={value}
                  readOnly={isReadOnly}
                  onChange={handleChange}
                  className='form-input form-input-default'
                /> */}
              </div>

            {/* </div> */}
            

            {/* <div>
              <button 
                type='button'
                className='material-img-btn'
                onClick={() => handleButtonClick()}
              >
                add image
              </button>
              <div className='image-grid'>
              {renderComponents()}
                <input type="file" name="" id="" onChange={handleFileChange} />
                {selectedFile.length > 0 && (
                  renderImages()
                  <img src={selectedFile} alt="Selected file" style={{ maxWidth: '100%' }} />
                )}
              </div>
            </div> */}
            

            <button 
              disabled={isLoading}
              onClick={handleSubmit}
              className='btn btn-block bg-screen'
            >
              {isLoading ? 'Please Wait...' : isEditing ? 'save changes' : 'create order'}
            </button>
          </div>
          {/* Order details form  end*/}

        </div>
        
        {/* measurement form */}
        <div className='measurement-container'>
          <header>
            <h4>measurements in : <span>inches</span></h4>
          </header>
          <hr />
          <Measurement />
        </div>
      </div>
      
        <button 
          disabled={isLoading}
          onClick={handleSubmit} className='btn btn-block sm-screen'
        >
          {isLoading ? 'Please Wait...' : isEditing ? 'save changes' : 'create order'}
        </button>
      </form>

    </Wrapper>
  )
}

export default NewOrder