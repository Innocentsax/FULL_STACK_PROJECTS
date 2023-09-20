import Footer from "../Footer/Footer";
import Header from "../Header/Header";
import Main from "./Main.JSX";

export default function HomePage(){
    const navbar =[
        "Home","About Us","Login","Get Started"
    ]


 
    return (
        <>
        <Header navbar ={navbar} />
        <Main />
        <Footer />
        
        </>
    )
}

