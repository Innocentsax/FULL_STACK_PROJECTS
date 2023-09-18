
import React from 'react'
import Logo from '../common/Logo'
import Banner from '../components/Home/homebanner/banner'
import Content from '../components/Home/homecontent/content'
import Footer from '../components/Home/homefooter/footer'
import NavBar from '../components/Home/NavBar/NavBar'

export default function LandingPage() {
  return (
    <>
   <NavBar/>
   <Banner/>
   <Content />
   <Footer />
   </>
  )
}
