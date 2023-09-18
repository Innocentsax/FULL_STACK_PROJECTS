import React from 'react';

function Loader(props) {
    return (
        <>
        {props.status &&
        <div className="spinner-border m-5" role="status">
             <span className="visually-hidden">Loading...</span>
        </div>}
        </>
    );
}

export default Loader;