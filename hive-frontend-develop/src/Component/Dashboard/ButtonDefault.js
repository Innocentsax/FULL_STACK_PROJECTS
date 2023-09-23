import './ButtonDefault.css'
import { useState } from 'react'
import Modal from '../Modal/ViewJob'

function ButtonDefault() {

    
    const [openModal, setOpenModal] = useState(false)

    return (
    <div className="button-default-button-default">
        <button 
            className="dashboard-button" 
            onClick={() => {
                setOpenModal(true)
            }}
        >
                View Task
        </button>

        {/* <Modal open={openModal} onClose={() => setOpenModal(false)}/> */}

        {/* {openModal && <Modal closeModel={() => setOpenModal(false)}/>} */}

        {/* {openModal && <Modal closeModal={setOpenModal}/>} */}
        {openModal && <Modal setOpenModal={setOpenModal} />}
    </div>
    );
    }
    
    
    export default ButtonDefault;