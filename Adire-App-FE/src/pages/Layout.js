import React from 'react'
import { Link, Outlet } from 'react-router-dom'
import { Logo } from '../components'
import Wrapper from '../assets/wrappers/Layout'

const Layout = () => {
  return (
    <Wrapper>
        <nav className='mg-top'>
            <Link to={'/landing'}>
              <Logo />
            </Link>
        </nav>

        <div className="section">
            <Outlet />
        </div>
    </Wrapper>
  )
}

export default Layout