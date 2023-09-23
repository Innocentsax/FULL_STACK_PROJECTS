import React from 'react'
import { Link } from 'react-router-dom'
import HeaderBar from './HeaderBar'
import { Footer } from './Footer'
import homeImage from '../Assets/home-image.svg'

//new addition  hive/frontend-mvp/blessing
const LandingPage = () => {
    return <div className="home-container">
    {/* <HeaderBar /> */}
    <div className="home-banner-container">
        <div className="home-text-section">
            <div className="home-text-section-main">
                <div className="text-section">
                <h1 className="primay-heading">Leave the cleaning to us, and reclaim your time for what matters most</h1>
                <p className="primary-text">Clean spaces, happy faces - let us do the dirty work for you!</p>
                <div className="about-buttons-container">
                    <Link to={'/register'}>
                        <button className="secondary-button">
                            Get Started
                        </button>
                    </Link>
                    {/*<Link to={'/register'}>*/}
                    {/*    <button className="primary-button">*/}
                    {/*        Sign up as a Tasker*/}
                    {/*    </button>*/}
                    {/*</Link>*/}
                </div>
                </div>
            </div>
            
            
        </div>
        <div className="home-image-section">
            <img src={homeImage} alt="home image Hive" />
        </div>
    </div>
    <Footer />
  </div>;
}

export default LandingPage