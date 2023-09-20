import { Headers ,LogoDiv,H1, PerfectLine, Img} from "../Styled/Styled";
import NavBar from './NavBar'
import logo from "../Images/logo_.png"
import {useState,useEffect} from 'react'
function Header(props){
    const [isScrolled, setIsScrolled] = useState(false);

    useEffect(() => {
      const handleScroll = () => {
        const scrollTop = window.pageYOffset;
        setIsScrolled(scrollTop > 0);
      };
  
      window.addEventListener("scroll", handleScroll);
      return () => {
        window.removeEventListener("scroll", handleScroll);
      };
    }, []);

    const home= ()=>{
      window.location.href="/"
    }
   
    return (
        <>
        
        <Headers  className={isScrolled ? "fixed-header" : ""}>
        <PerfectLine>
            <LogoDiv>
                <Img onclick={home} src={logo} alt="logo" width={50} />
                <H1>EasyLend</H1>
            </LogoDiv>
            {(props.navbar.length>0) ? <NavBar items={props.navbar} /> :""}
        </PerfectLine>
        </Headers>
        </>
    )
}

export default Header;