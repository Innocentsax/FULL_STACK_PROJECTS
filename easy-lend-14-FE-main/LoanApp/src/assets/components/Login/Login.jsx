import { validateUser } from "../../Utils/AppUtils";
import Header from "../Header/Header";
import UserFormsAuth from "./UserFormsAuth";

const Login = ()=>{
    validateUser()
    return (
        <>
        <Header navbar={[]} />
        <UserFormsAuth />
        </>
    )
}

export default Login;