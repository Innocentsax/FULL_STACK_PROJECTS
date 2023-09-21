import wordWibeWEb from '../image/svg/word.svg'
import facebook from '../image/svg/facebook.svg'
import twitter from '../image/svg/twitter.svg'
import youtube from '../image/svg/youtube.svg'
import './Footer.css'
import { Link } from 'react-router-dom'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPaperPlane } from '@fortawesome/free-solid-svg-icons'
import { DivFooter, 
    DivInsideRight, 
    FooterDiv, 
    LetfDivEvent, 
    RightDivEvent } from '../../Styled/Styled'
export default function Footer(){
    return (
        <>
        <FooterDiv>
            <LetfDivEvent>
                <p className='name'>Events</p>
                <p className='copyright'>Copyrights events</p>
                 <p className='reserved'>All rights reserved</p>
                    <DivFooter>
                    <Link to="/"><img src={facebook} width={20} /></Link>
                    <Link to="/"><img src={wordWibeWEb} width={20} />  </Link>
                    <Link to="/"><img src={twitter} width={20} />  </Link>
                    <Link to="/"><img src={youtube} width={20} />  </Link>
                </DivFooter>
            </LetfDivEvent>
            <RightDivEvent>
                <DivInsideRight>
                    <p style={{color:"white"}}>Company</p>
                    <p className='about'>About us</p>
                    <p>Blog</p>
                    <p>Contact us</p>
                    <p>Testimonials</p>
                </DivInsideRight>
            <DivInsideRight>
                <p style={{color:"white"}}>Support</p>
                 <p className='about'>Help center</p>
                <p>Terms of service</p>
                 <p>Legal</p>
                <p>Privacy policy</p>
            </DivInsideRight>
                <DivInsideRight>
                <p style={{color:"white"}}>Stay up to date</p>
                    <label className='email'>
                    <input style={{color:"white"}} type='text' placeholder='Your email address' />
                <FontAwesomeIcon icon={faPaperPlane} className='red'/>
                </label>
            </DivInsideRight>
        </RightDivEvent>
        </FooterDiv>
        </>
    )
}


