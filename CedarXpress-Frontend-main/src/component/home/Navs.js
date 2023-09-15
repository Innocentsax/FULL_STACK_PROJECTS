import React from 'react'
import Nav from 'react-bootstrap/Nav'
import Button  from 'react-bootstrap/Button'
import {
  Link
} from "react-router-dom";
import {FaShoppingCart, FaBars, FaEnvelope} from "react-icons/fa"
import {TiSocialTwitterCircular, TiSocialFacebookCircular, TiSocialInstagramCircular} from 'react-icons/ti';


const Navs = (props) => {
    const {HomeCss, showSideNav} = props;
    return(
        <section>
            <main className={HomeCss.fixedPosition}>
                <div className={`${HomeCss.navTop} ${HomeCss.fontSm}`}><p> <FaEnvelope/> cedarxpress@gmail.com</p>
                <p>free shipping for all orders 500.00</p>
                <p className={`${HomeCss.navTopIcon}`}>
                    <TiSocialFacebookCircular className={`${HomeCss.navTopIcons}`}/>
                    <TiSocialTwitterCircular className={`${HomeCss.navTopIcons}`}/>
                    <TiSocialInstagramCircular className={`${HomeCss.navTopIcons}`}/>
                    </p></div>
                        <Nav className={`${HomeCss.customeNav}`} activeKey="/home">
                        <Nav.Item className={`${HomeCss.navItem}`}>
                            <Button 
                            className={`${HomeCss.noBg} ${HomeCss.primaryTextColor} ${HomeCss.navLeftBtn}`} 
                            variant='light'
                            onClick={showSideNav}
                            >
                                <FaBars  />
                            </Button>
                            </Nav.Item>
                            <Nav.Item className={`${HomeCss.navItem}`}>
                            <Link to="/" className={`${HomeCss.primaryTextColor} ${HomeCss.fontSm} ${HomeCss.active}`}>Home</Link>
                            </Nav.Item>
                            <Nav.Item className={`${HomeCss.navItem}`}>
                            <Link to="/about" className={`${HomeCss.primaryTextColor} ${HomeCss.fontSm}`}>About us</Link>
                            </Nav.Item>

                            <Nav.Item className={`${HomeCss.navItem}`}>
                            <Link to="/product-archive" className={`${HomeCss.primaryTextColor} ${HomeCss.fontSm}`}>Product Archive </Link>
                            </Nav.Item>

                            <Nav.Item className={`${HomeCss.navItem}`}>
                            <Nav.Link eventKey="link-2" className={`${HomeCss.primaryTextColor} ${HomeCss.fontSm}`}>blog</Nav.Link>
                            </Nav.Item>

                            <Nav.Item>
                            <h3 className={HomeCss.navH3}>funiture</h3>
                            </Nav.Item>

                            <Nav.Item className={`${HomeCss.navItem}`}>
                            <Nav.Link eventKey="link-2" className={`${HomeCss.primaryTextColor} ${HomeCss.fontSm}`}>shop</Nav.Link>
                            </Nav.Item>
                            
                            <Nav.Item className={`${HomeCss.navItem}`}>
                            <Nav.Link eventKey="link-2" className={`${HomeCss.primaryTextColor} ${HomeCss.fontSm}`}>Contact</Nav.Link>
                            </Nav.Item>

                            <Nav.Item className={`${HomeCss.navItem}`}>
                            <Link to="/login" className={`${HomeCss.primaryTextColor} ${HomeCss.fontSm}`}>Sign in</Link>
                            </Nav.Item>

                            <Nav.Item className={`${HomeCss.navItem}`}>
                            <Link to="/signup" className={`${HomeCss.primaryTextColor} ${HomeCss.fontSm}`}>Sign up</Link>
                            </Nav.Item>
                            
                            <Nav.Item className={`${HomeCss.navItem}`}>
                            <Link to="/cart"><Button className={`${HomeCss.noBg} ${HomeCss.primaryTextColor} ${HomeCss.navRightBtn}`} variant='light'><small className='shoppingcart'>&#x20A6;0.00</small><FaShoppingCart /></Button></Link>
                            </Nav.Item>

                    </Nav>
               
            </main>
        </section> 
)}

export default Navs