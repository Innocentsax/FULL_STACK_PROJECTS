// eslint-disable-next-line no-unused-vars
import React from 'react'
import Header from '../HomePage/Header/Header'
import Footer from '../HomePage/Footer/Footer'
import FormRegister from './FormRegister'

const Register = () => {
  if(localStorage.getItem("TOKEN")!=null){
    window.location.replace("app/event")
}
  return (
     <>
      <Header />
      <FormRegister />
      <Footer />
    
      </>
  )
}

export default Register