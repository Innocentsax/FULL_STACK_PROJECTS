import React from 'react'
import { Outlet } from 'react-router-dom';
import { BigSidebar, Navbar, SmallSidebar } from '../../components';
import Wrapper from '../../assets/wrappers/SharedLayout';
import Loader from '../../components/Loader';

const SharedLayout = () => {
    return (
        <Wrapper>
            
            <main className='dashboard'>
                {/* <SmallSidebar /> */}
                <BigSidebar />
                <div>
                    {/* { true && <Loader /> } */}
                    <Navbar />
                    <div className='dashboard-page'>
                        <Outlet />
                    </div>
                </div>
            </main>
        </Wrapper>
    );
}

export default SharedLayout