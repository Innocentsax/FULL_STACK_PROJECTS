import navbar from '../Assets/navbar.svg'
import { Link, useLocation } from 'react-router-dom'
import { NotificationIcon } from '../utils/Icons'


const NavBar = () => {
    const username = "Omotayo";
    const { pathname } = useLocation();
    console.log(pathname);
  return (
    <>
      <nav className='navbar navbar-expand-lg'>
        <div className='nav-center'>
           
           { <Link className="site-title" to="/Home">Hive.</Link>}
            <div className="collapse navbar-collapse">
                {pathname === "/view" &&
                    <ul>
                        <li><Link to='/ViewAllTasks'>View all tasks</Link></li>
                        <div className='nav-container'>
                            <Link to="/NotificationModal">
                                <NotificationIcon />
                            </Link>    
                            <div className='notification-alert'></div>
                        </div>
                        <li><img src={navbar} alt="user image Hive" /></li>
                        <li><Link to="/ProfileModal">Hi, {username}</Link></li>
                    </ul>  
                }  
            </div>
            
                {pathname === "/Home" &&
                    <ul>
                        <li><Link to="/About">About</Link></li>
                        <li className="vl"></li>
                        <li><button className='btn btn-primary'><Link to="/Login">Login</Link></button></li>   
                    </ul>
                } 
            
         
        </div>
      </nav>
    </>
  );
};
export default NavBar;