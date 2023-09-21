import Footer from "../HomePage/Footer/Footer";
import Header from "../HomePage/Header/Header";
import LoginForm from "./LoginForm";

const Login = ()=>{
    if(localStorage.getItem("TOKEN")!=null){
        window.location.replace("app/event")
    }
    return(
        <>
        <Header />
        <LoginForm />
        <Footer />
        </>
    )

}
export default Login;