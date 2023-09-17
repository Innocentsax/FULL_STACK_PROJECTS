import React from 'react';
import {CheckBox, InputField, Label} from '../../../components/form-fields';


const Form = ({method, handleSubmit, fields, handleChange}) => {

    if (method === 'PUT') {
        return (
            <form method={method} onSubmit={handleSubmit}>
                <Label elementId='fullName' text='Full Name'/>
                <InputField
                    type='text'
                    value={fields.fullName}
                    id='fullName'
                    name='fullName'
                    changeHandler={handleChange}
                />
                <Label elementId='email' text='Email'/>
                <InputField
                    type='text'
                    value={fields.email}
                    id='email'
                    name='email'
                    changeHandler={handleChange}
                />
                <Label elementId='address' text='Address'/>
                <InputField
                    type='text'
                    value={fields.address}
                    id='address'
                    name='address'
                    changeHandler={handleChange}
                />
                <Label elementId='city' text='City'/>
                <InputField
                    type='text'
                    value={fields.city}
                    id='city'
                    name='city'
                    changeHandler={handleChange}
                />
    
    
                <Label elementId='state' text='State'/>
                <InputField
                    type='text'
                    value={fields.state}
                    id='state'
                    name='state'
                    changeHandler={handleChange}
                />
    
                <Label elementId='phone' text='Phone Number'/>
                <InputField
                    type='text'
                    value={fields.phone}
                    name='phone'
                    id='phone'
                    changeHandler={handleChange}
                />
    
                <CheckBox name='isDefaultShippingAddress' id='isDefaultShippingAddress' checked={fields.isDefaultShippingAddress} changeHandler={handleChange}/>
                <Label elementId='isDefaultShippingAddress' text='Default Shipping Address'/>
                <div>
                <button className='btn-address' type='submit' >UPDATE</button>
                </div>
                
            </form>
        )
    } else {
        return (
            <form method={method} onSubmit={handleSubmit}>
                <Label elementId='fullName' text='Full Name'/>
                <InputField
                    type='text'
                    value={fields.fullName}
                    id='fullName'
                    name='fullName'
                    changeHandler={handleChange}
                />
                <Label elementId='email' text='Email'/>
                <InputField
                    type='text'
                    value={fields.email}
                    placeholder='example@gmail.com'
                    id='email'
                    name='email'
                    changeHandler={handleChange}
                />
                <Label elementId='address' text='Address'/>
                <InputField
                    type='text'
                    value={fields.address}
                    placeholder='Add a unique address'
                    id='address'
                    name='address'
                    changeHandler={handleChange}
                />
                <Label elementId='city' text='City'/>
                <InputField
                    type='text'
                    value={fields.city}
                    id='city'
                    name='city'
                    changeHandler={handleChange}
                />
    
    
                <Label elementId='state' text='State'/>
                <InputField
                    type='text'
                    value={fields.state}
                    id='state'
                    name='state'
                    changeHandler={handleChange}
                />
    
                <Label elementId='phone' text='Phone Number'/>
                <InputField
                    type='text'
                    value={fields.phone}
                    name='phone'
                    id='phone'
                    changeHandler={handleChange}
                />
    
                <CheckBox name='isDefaultShippingAddress' id='isDefaultShippingAddress' checked={fields.isDefaultShippingAddress} changeHandler={handleChange}/>
                <Label elementId='isDefaultShippingAddress' text='Default Shipping Address'/>
                <div>
                <button className='btn-address' type='submit' >SUBMIT</button>
                </div>
                
            </form>
        )
    }
}

export default Form;