import styled from 'styled-components'
import money from './Images/money-bill.png'
import money2 from './Images/money-insert.png'
import {Link} from 'react-router-dom'
import  money3 from './Images/message-square-lines.png';
import  money4 from './Images/Profile.png';
import  money5 from './Images/lock-alt.png';
import  logout from './Images/logout.png'
import { SidedTfLX } from './DashboardStyled';
const SidebarD=()=>{
    return (
        <>
        <SideDashboard>
            <h1>Overview</h1>
           <Link to="/dashboard"><SidedTfLX><img src={money} width={30} /><span style={{color: "#2D00F7"}}>&nbsp;Loans</span></SidedTfLX></Link> 
            <Link to="/leading"><SidedTfLX><img src={money2} width={30} /><span >&nbsp;Leading Offers</span></SidedTfLX></Link> 
            <Link to="/message"><SidedTfLX><img src={money3} width={30} /><span>&nbsp;Message</span>
            </SidedTfLX></Link> 
            <Link to="/profile?task=profile"><SidedTfLX><img src={money4} width={30} /><span>&nbsp;Profile</span></SidedTfLX></Link> 
            <h1>Overview</h1>
            <Link><SidedTfLX><img src={money5} width={30} /><span >&nbsp;Change Password</span></SidedTfLX></Link> 
            <Link to="/logout"><SidedTfLX><img src={logout} width={30} /><span >&nbsp;Logout</span></SidedTfLX></Link> 
         
            </SideDashboard>
        
        </>
    )
}

export default SidebarD

const SideDashboard =styled.div`
width:15%;
height:80vh;
background:white;
border-top:1px solid #ccc;
padding:20px;
a{
    text-decoration:none
}
`

