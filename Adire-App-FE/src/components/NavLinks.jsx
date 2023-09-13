import { Link } from 'react-router-dom';
import links from '../utils/links';
import { clearStore } from '../features/user/userSlice';
import { useDispatch } from 'react-redux';
import { googleLogout } from '@react-oauth/google';

const NavLinks = ({ toggleSidebar, setActive, active }) => {
  const dispatch = useDispatch();

  return (
    <div className='nav-links'>
      {links.map((link) => {
        const { text, path, id, icon } = link;
        if (!icon && !path) {
          if (id === 1) {
            return (
              <div key={id}>
                <p className='sub-heading'>{text}</p>
              </div>
            )
          } else {
            return (
              <div key={id}>
                <hr />
                <p className='sub-heading'>{text}</p>
              </div>
            )
          }
        }

        if (!icon) {
          return (
            <div 
              key={id}
              className='nav-links other-nav-links'
            >
              <Link
                key={id}
                to={path}
                className={ active === path ? 'nav-link other-active' : 'nav-link' }
                onClick={toggleSidebar}
                
              >
              {text}
              </Link>
            </div> 
          )
        }
        if (!path) {
          return (
            <div key={id} className="logout-btn">
              <Link
                to={'#'}
                className='nav-link'
                onClick={() => {
                  dispatch(clearStore('Logging out...'))
                  googleLogout();
                }}
                
              >
                <span className='icon'>{icon}</span>
                logout
              </Link>
            </div>
          )
        }
        return (
          <Link
            to={path}
            className={ active === path ? 'nav-link active' : 'nav-link' }
            // className={ `nav-link ${active === path ? 'active' : ''} ` }
            // className={({ isActive }) => {
            //   return isActive ? 'nav-link active' : 'nav-link';
            // }}
            key={id}
            // onClick={setActive(text)}
            
          >
            <span className='icon'>{icon}</span>
            {text}
          </Link>
        );
      })}
    </div>
  );
};
export default NavLinks;