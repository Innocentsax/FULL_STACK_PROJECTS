import { validateUser } from "../../Utils/AppUtils";
import Header from "../Header/Header";
import Userforms from "./UserForms";

const Register = ()=>{
    validateUser()
    return (
        <>
        <Header navbar={[]} />
        <Userforms />

        </>
    )
}

export default Register;

