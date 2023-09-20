import {
    Align,
    Form,
    LabelInput,
    LabelI,
    Input,
    Botton,


} from '../Styled/Styled';
import Header from "../Header/Header";
import {Link} from 'react-router-dom'
const Password =()=>{
    return (
    <> 
    <Header navbar ={[]}/>

 
        <Align>
    <Form>
             
                <h1>Reset Your Password</h1>
                <span style={{
                    textAlign:"center"
                }}>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Ipsa, ma fjddj ggdfgdgfd</span>
            
                <LabelInput>
              
                <LabelI>
                        Password
                        <Input type="password" placeholder="*********************"></Input>
                    </LabelI>
                   
                    <LabelI>
                        Confirm Password
                        <Input type="password" placeholder="*********************"></Input>
                    </LabelI>
                   
                  
                   <p></p>
                   
                     <Botton>Login</Botton>
                  


                </LabelInput>
                <p>Dont have an account ? <Link style={{color:"blue"}} to="/signup">Sign up here</Link></p>

                </Form>
                </Align>


    </>
    )
}

export default Password;