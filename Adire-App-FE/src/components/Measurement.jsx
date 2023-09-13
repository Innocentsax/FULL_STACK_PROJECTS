import React from 'react'
import Wrapper from '../assets/wrappers/Measurement'
import { measurements } from '../utils/enumData'
import MeasurementFormRow from './MeasurementFormRow'
import { handleMeasurementsChange, createOrder, editOrder } from '../features/order/orderSlice'
import { useSelector, useDispatch } from 'react-redux';

const Measurement = () => {
    const {
        isLoading,
        measurementList,
        isEditing,
        editOrderId,
      } = useSelector((store) => store.order);
      const dispatch = useDispatch();

    const handleMeasurementOrderInput = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        console.log(name, value);
        dispatch(handleMeasurementsChange({ name, value }));
    };

  return (
    <>
        <Wrapper className='measurement-form'>
            {/* <div className='measurement-grid'> */}
           
            {Object.entries(measurements).map(([key, name], index) => {
                const measurementName = `${index + 1}. ${name}`;
                return (
                <MeasurementFormRow
                    id={key}
                    type="text"
                    key={key}
                    name={key}
                    labelText={measurementName}
                    value={measurementList[key]}
                    handleChange={handleMeasurementOrderInput}
                />
                );
            })}
            {/* </div> */}
        </Wrapper>
    </>
  )
}

export default Measurement