import Profile from '../../Dashboard/Profile/Profile';
import { Account, Button, HeaderStyle, Headers, LogoDiv } from '../../Styled/Styled';
import '../css/HomePage.css'
import { Link } from 'react-router-dom';

export default function Header(){
    const TOKEN = localStorage.getItem("TOKEN");
   
    return (
        <>


        <HeaderStyle>
            <Headers>
            <LogoDiv><Link to="/" style={{textDecoration:"none"}} ><h1 className='eventLogo'>Event</h1></Link></LogoDiv>
            <Account>
                
                {(TOKEN==null) ? <Link className='link-login' to="/login">Login</Link> :<Profile /> }
                

                <Link to="../app/event" ><Button>Create Event</Button></Link>

           

            </Account>
            </Headers>
        </HeaderStyle>
        </>
    )
}


