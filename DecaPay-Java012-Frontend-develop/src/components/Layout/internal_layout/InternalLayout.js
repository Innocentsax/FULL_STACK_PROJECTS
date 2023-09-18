import React from 'react';
import {Link, Outlet} from 'react-router-dom';
import InternalHeader from "./InternalHeader";

function InternalLayout(){
    return (
        <div>
            <InternalHeader />
            <div className="container-fluid">
                <Outlet/>
            </div>
        </div>
    );
}

export default InternalLayout;