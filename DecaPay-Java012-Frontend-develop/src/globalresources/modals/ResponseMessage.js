import React, {Component} from 'react';
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";

const ResponseMessage = (props) => {

    const [open, setOpen] = React.useState(true);
    const handleClose = () => setOpen(false);
    {
        return (
            <div>
                <Modal
                    open={open}
                    onClose={handleClose}
                    aria-labelledby="modal-modal-title"
                    aria-describedby="modal-modal-description"
                >
                    <Box className="modal-box">
                        <div><button className="btnClose" onClick={handleClose}>X</button></div>

                     <div>{props.message}</div>

                    </Box>
                </Modal>
            </div>
        );
    }
}

export default ResponseMessage;