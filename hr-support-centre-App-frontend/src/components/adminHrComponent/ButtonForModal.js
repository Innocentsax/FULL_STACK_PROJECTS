import { useState } from "react";

const ButtonForModal = ( { buttonForModalTitle, handleButtonForModalClick } ) => {


    return (
        <div>
            <button onClick = {handleButtonForModalClick}>
                <h1 className="text-blue-500 text-xs">{buttonForModalTitle}</h1>
            </button>

            <div className = "">
                <div className = "backdrop-opacity-100">

                </div>
            </div>
        </div>
    );
};

export default ButtonForModal;
