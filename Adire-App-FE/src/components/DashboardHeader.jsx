import React from 'react'
import Wrapper from '../assets/wrappers/DashboardHeader'

const DashboardHeader = ({ name, title }) => {
  return (
    <Wrapper>
        <h3>welcome {name}</h3>
        <p className='sub-heading dashboard-title'>{title}</p>
    </Wrapper>
  )
}

export default DashboardHeader