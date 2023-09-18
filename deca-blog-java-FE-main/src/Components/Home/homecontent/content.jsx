import React from 'react'
import './content.style.jsx'
import bodyImg1 from '../../../assets/bodyimg1.png'
import bodyImg2 from '../../../assets/Rectangle 2.png'
import bodyImg3 from '../../../assets/Rectangle 3.png'
import bodyImg4 from '../../../assets/Rectangle 4.png'
import bodyImg5 from '../../../assets/Rectangle 5.png'
import bodyImg6 from '../../../assets/Rectangle 8.png'
import bodyImg7 from '../../../assets/Rectangle 9.png'
import bodyImg8 from '../../../assets/Rectangle 10.png'
import { ContentStyle } from './content.style.jsx'


export default function Content() {
  return (
    <ContentStyle>
       <div class="contentText">
           <p className='contentHeader'>Tips and recommendations on Tech Stacks</p>
           <p className='contentSubtitle'>Read Articles, join spaces hosted by different Decadevs just like you.</p>
       </div>
       <div className='contentFlex'>
    <div className='story'><img src={bodyImg1} alt="user story1"/></div>
    <div className='story'><img src={bodyImg2} alt="user story1"/></div>
    <div className='story'><img src={bodyImg3} alt="user story1"/></div>
    <div className='story hide'><img src={bodyImg4} alt="user story1"/></div>
    <div className='story hide'><img src={bodyImg5} alt="user story1"/></div>
    <div className='story hide'><img src={bodyImg6} alt="user story1"/></div>
    <div className='story hide'><img src={bodyImg7} alt="user story1"/></div>
    <div className='story hide'><img src={bodyImg8} alt="user story1"/></div>
       </div>

       </ContentStyle>
  )
}


