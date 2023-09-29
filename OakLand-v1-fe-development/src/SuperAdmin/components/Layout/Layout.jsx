import SideNav from "../SideNav/SideNav"
import './layout.css';
import { Outlet } from 'react-router-dom'

const Layout = () => {
  return (
    <section className="admin-dashboard-layout">
      <div className="container">
        <SideNav /> 
        <div className="children">
          <Outlet />
        </div>
      </div>
    </section>
  )
}

export default Layout