import React from 'react'
import Button  from 'react-bootstrap/Button'
import {IconContext} from 'react-icons'
import {HiOutlineX, HiOutlinePhone} from 'react-icons/hi'
import {FaEnvelope, FaRegClock} from 'react-icons/fa'
import {TiSocialTwitterCircular, TiSocialFacebookCircular, TiSocialInstagramCircular} from 'react-icons/ti';


const SideNav = (props) => {
    const {HomeCss, showSideNav, bestSales} = props;
    function CloseBtn() {
  return (
    <IconContext.Provider
      value={{ color: '#fff', size: '2rem'}}
    >
      <div>
        <HiOutlineX />
      </div>
    </IconContext.Provider>
  );
}

const gallary = bestSales.map(image =>  <div className={HomeCss.galaryItem} key={image.url}> <img src={image.url} style={{width:'100%', height: '100%'}} alt='logo'/></div>)
    return (
        <section className={HomeCss.sideNav}>
            <main>
                <div className={HomeCss.header}>
                     <Button 
                    className={`${HomeCss.noBg} 
                    ${HomeCss.primaryTextColor} 
                    ${HomeCss.navLeftBtn}`} 

                    variant='light'
                    onClick={showSideNav}
                    >
                        <CloseBtn className={HomeCss.closeBtn} />
                    </Button> 
                    </div>
                <h3 className={`${HomeCss.navH3} ${HomeCss.sideNavH3}`}>Funiture</h3>
                <p className={`${HomeCss.fontSm} ${HomeCss.centerText}`}>Far far away, behind the word mountains,
                     far from the countries Vokalia and Consonantia 
                     there live the blind texts.</p>
                <div className={HomeCss.galary}>
                    {gallary}
                </div>
                <div className={HomeCss.contact}>
                    <p> <FaRegClock/> Sun - Sat : 9:00 AM - 17:00 PM</p>
                    <p> <FaEnvelope/> cedarxpress@gmail.com</p>
                    <p> <HiOutlinePhone/> cedarxpress@gmail.com</p>

                </div>
                <div className={HomeCss.social}>
                     <p className={`${HomeCss.navTopIcon}`}>
                    <TiSocialFacebookCircular className={`${HomeCss.navTopIcons}`}/>
                    <TiSocialTwitterCircular className={`${HomeCss.navTopIcons}`}/>
                    <TiSocialInstagramCircular className={`${HomeCss.navTopIcons}`}/>
                    </p>
                </div>
            </main>
        </section>
    )
}

export default SideNav