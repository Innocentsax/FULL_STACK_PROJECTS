import React,{useState} from "react";
    import signinStyle from "../stylesheets/signin.module.css";
    import axios from  'axios';
    import Alert from '@mui/material/Alert'

 
   const SignUp= () =>{
        
      const [firstName, setFirstName]= useState('')
        const [lastName, setLastName]= useState('')
        const [email, setEmail]= useState('')
        const [password, setPassword]= useState('')
        const [confirmPassword, setConfirmPassword]= useState('')
        const [dateOfBirth, setDateOfBirth]= useState('')
        const [gender, setGender]= useState('')
        const [errorMessage, setErrorMessage]= useState('')
        const [successMessage, setSuccessMessage]= useState('')
        const [newsLetterSub, setNewsLetterSub]= useState(false)

        function handleSubmit(e){
          e.preventDefault()
        

     
          if(firstName&&lastName&&email&&(password.length>=6)&&(confirmPassword===password)&&dateOfBirth&&gender){
            setErrorMessage(false)
            setFirstName('')
            setLastName('')
            setEmail('')
            setPassword('')
            setConfirmPassword('')
            setDateOfBirth('')
            setGender('')
            setNewsLetterSub(false)
            setSuccessMessage("Registration successful, check your email to verify your account")
            setTimeout(()=>{
              setSuccessMessage('')
            },5000)

            // axios.post('https://cors-anywhere.herokuapp.com/https://safari-webstore-008.herokuapp.com/signup',{email:email,firstName:firstName,
            // lastName:lastName, password: password, confirmPassword:confirmPassword, dateOfBirth: dateOfBirth, gender: gender
           
            axios.post('http://localhost:8080/signup',{email:email,firstName:firstName,
            lastName:lastName, password: password, confirmPassword:confirmPassword, dateOfBirth: dateOfBirth, gender: gender
           
          
          
          
          })

            
            
          }

           if(!firstName&&!lastName&&!email&&!password&&!confirmPassword&&!dateOfBirth&&!gender){
              setErrorMessage('All fields required')
            

          }
          else if(!firstName){
            setErrorMessage('First name required')
          
          }

          else if(!lastName){
            setErrorMessage('Last name required')
          
          }

          else if(!email){
            setErrorMessage('Email required')
          
          }


          else if(!password){
            setErrorMessage('Password required')
          
          }
          else if(password.length<6){
            setErrorMessage('Password must be at least 6 characters')
          
          }

          else if(!confirmPassword){
            setErrorMessage('Confirm password required')
          
          }

          else if(confirmPassword!==password){
            setErrorMessage('Passwords must match')
          
          }

          else if(!dateOfBirth){
            setErrorMessage('Date of birth required')
          
          }

          else if(!gender){
            setErrorMessage('Gender required')
          
          }
         


          }
      
      const current = new Date().toISOString().split("T")[0]
      return (
        <>
        
          <div
            className={signinStyle.container}
            id={signinStyle["signUp"]}
            style={{ width: "80%", minWidth: "300px" }}
          >
            <div className={signinStyle.top} style={{height:'70px'}}>
            </div>
            <div className={signinStyle.bottom}>
              <h5>CREATE ACCOUNT</h5>
              <form className={signinStyle.form} onSubmit={handleSubmit} >
              { errorMessage ?(<Alert style={{marginBottom:30}} severity="error">{errorMessage}</Alert>): ""}
              
              
               {successMessage?<Alert style={{marginBottom:30}} severity="success">{successMessage}</Alert>: ""}
              
             
               
                <div className={signinStyle.formGroup}>
                  <label>First name</label>
                  <input  name='firstName'  value={firstName} onChange={(e)=>setFirstName(e.target.value)}  type='text'/>
          
                </div>
                <div className={signinStyle.formGroup}>
                  <label>Last name</label>
                  <input  name='lastName' value={lastName} onChange={(e)=>setLastName(e.target.value)}type='text'/>
                      </div>
                <div className={signinStyle.formGroup}>
                  <label>Email</label>
                  <input id="email"  name='email'  value={email} onChange={(e)=>setEmail(e.target.value)}   type='email'/>
                
                
                </div>
                <div className={signinStyle.formGroup}>
                  <label>Create password</label>
                  <input  name='password' value={password} onChange={(e)=>setPassword(e.target.value)}  type='password'/>
                
                
                </div>
                <div className={signinStyle.formGroup}>
                  <label>Confirm password</label>
                  <input name='confirmPassword' value={confirmPassword} onChange={(e)=>setConfirmPassword(e.target.value)} type='password'/>
          
                </div>
                <div className={signinStyle.formGroup}>
                  <label>Date Of Birth</label>
                  <input type='date' label='Date of Birth' name='dateOfBirth' value={dateOfBirth} onChange={(e)=>setDateOfBirth(e.target.value)}   max={current} />
             
                </div>

            <label style={{fontFamily:'Mulish',fontSize:15,marginTop:10}}>
                Gender
                  </label>
                <div>
                <input   type='radio'  name="gender" value={'Male'} onChange={(e)=>setGender(e.target.value)} />  Male 
                <input  type='radio'   name="gender" value={'Female'} onChange={(e)=>setGender(e.target.value)} /> Female
    
                </div> 

                <div className={signinStyle.formGroup}>
                <input  id='checkbox' type='checkbox' style={{marginTop:20}} name="newsLetterSub" checked={newsLetterSub} onChange={(e)=>setNewsLetterSub(e.target.checked)} />
              <label htmlFor='checkbox' ><div style={{marginTop:20}}>I want to receive safari newsletters with the best deals and offers</div></label>
                </div>
                
          
                <div className={signinStyle.formGroup}>
          
                  <button id='button' type='submit' >CREATE ACCOUNT</button>
                </div>

               
              </form>
            </div>
          </div>
        </>
      );
    }

    export default SignUp
