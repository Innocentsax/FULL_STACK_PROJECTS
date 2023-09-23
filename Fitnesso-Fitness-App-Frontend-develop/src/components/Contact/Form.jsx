// import React from 'react'

import './Form.css'
import Axios from 'axios'
import React, { useState, useEffect } from 'react'
import axios from 'axios'
import { notifyUser } from './utils.js'

function Form() {
  const [state, setState] = useState({
    fullName: '',
    email: '',
    currentWeight: '',
    goalWeight: '',
    goals: '',
  })

 const handleInput=(e)=>{
   const newObj = {...state}
   console.log(newObj, state, 'compare')
   newObj[e.target.name] = e.target.value
   setState(newObj)
 }


  const url = 'https://fitnesso-app-new.herokuapp.com/contact/save'

 const handleSubmit=(e)=>{
   e.preventDefault()
  const body = {
    fullName: state.fullName.trim(),
    email: state.email.trim(),
    currentWeight: state.currentWeight.trim(),
    goalWeight: state.goalWeight.trim(),
    goals: state.goals,
  }
 
  console.log(body)
  axios.post(url, body)
  .then((res)=>{
    console.log(res.data, 'backend response')
    console.log('this request is successful')
    notifyUser('Message Sent Successfully', 'Please Wait..', 'success', 'reload' )
  }).catch((err)=>{
    console.error(err)
    console.log(">>>>>>>>>>" + err.message)
  })
 }

  return (
    <div class='contact-form'>
      <span class='circle one'></span>
      <span class='circle two'></span>

      <form autocomplete='off' onSubmit={handleSubmit}>
        <h3 class='title'>Contact us</h3>
        <div class='input-contact focus'>
          <input type='text' name='fullName' placeholder='Full Name' class='input' value={state.fullName} onChange={handleInput} />
          {/* <label for=''>Full Name</label> */}
          {/* <span>Username</span> */}
        </div>
        <div class='input-contact focus'>
          {' '}
          <input type='email' name='email' placeholder='Email' class='input' value={state.email} onChange={handleInput} />
          {/* <label for=''>Email</label> */}
          {/* <span>Email</span> */}
        </div>
        <div class='input-contact focus'>
          {' '}
          <input type='tel' name='currentWeight' placeholder='Current Weight' class='input' value={state.currentWeight} onChange={handleInput} />
          {/* <label for=''>Current Weight</label> */}
          {/* <span>Phone</span> */}
        </div>
        <div class='input-contact focus'>
          {' '}
          <input type='tel' name='goalWeight' placeholder='Body Goals' class='input' value={state.goalWeight} onChange={handleInput} />
          {/* <label for=''>Goal Weight</label> */}
          {/* <span>Goal Weight</span> */}
        </div>
        <div class='input-contact textarea focus'>
          {' '}
          <textarea name='goals' class='input' value={state.goals} onChange={handleInput} ></textarea>
          <label for=''>Describe Your Goals</label>
          {/* <span>Message</span> */}
        </div>
        <input type='submit' value='Get In Touch' class='contact-page-btn' />
      </form>
    </div>
  )
}

export default Form;
