import React from 'react'
import FormRow from './FormRow'

const UpdatePassword = () => {
  
  return (
    <>
      <FormRow
        type='password'
        name='oldPassword'
        // value={values.password}
        placeholder="Enter your password"
        labelText="old password"
        // handleChange={handleChange}
      />

      <FormRow
        type='password'
        name='newPassword'
        // value={values.password}
        placeholder="Enter your password"
        labelText="new password"
        // handleChange={handleChange}
      />

      <FormRow
        type='password'
        name='confirmPassword'
        // value={values.password}
        placeholder="Enter your password"
        labelText="confirm new password"
        // handleChange={handleChange}
      />
    </>
  )
}

export default UpdatePassword