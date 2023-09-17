import React, {useContext} from "react";
import {CheckBox, InputField, Label} from "./form-fields";
import {doLogin} from "../services/auth-service";
import Alert from "./Alert";
import { UserContext } from "../context/UserContext";

const SignIn = () => {
    const [fields, setFields] = React.useState({
        email: "",
        password: "",
        rememberMe: '',
    });
    const {login}  = useContext(UserContext);

    const [disabledButton, setDisabledButton] = React.useState("");
    const [alertBox, setAlertBox] = React.useState({state:false, message:'',type:'error'});
    const handleChange = (event) => {
        const {name, value, checked} = event.target;
        setFields((fields) => ({...fields, [name]: name !== 'rememberMe' ? value : checked}));
    };

    // React.useEffect(()=>{
    //     const rememberMe = JSON.parse(localStorage.getItem('rememberMe'));
    //     rememberMe !== null && setFields(fields => rememberMe);
    // },[]);
    return (
        <section className="sign-in">
            <h5 className="form-title">SIGN IN</h5>
            {alertBox.state && <Alert text={alertBox.message} variant={alertBox.type}/>}
            <form method="POST" onSubmit={(e) => doLogin(e, fields, setDisabledButton,setAlertBox, login)}>
                <Label elementId="user-email" text="email"/>
                <InputField
                    type="email"
                    value={fields.email}
                    placeholder=""
                    id="user-email"
                    required={true}
                    changeHandler={handleChange}
                    name="email"
                />

                <Label elementId="user-password" text="password"/>
                <InputField
                    type="password"
                    value={fields.password}
                    placeholder=""
                    name="password"
                    id="user-password"
                    changeHandler={handleChange}
                />
                <CheckBox name='rememberMe' id="remember-me" checked={fields.rememberMe} changeHandler={handleChange}/>
                <Label elementId="remember-me" text="remember my details"/>
                <button type="submit" className="btn-block btn-block--contained" disabled={disabledButton}>
                    Sign In
                </button>
            </form>
        </section>
    );
};

export default SignIn;
