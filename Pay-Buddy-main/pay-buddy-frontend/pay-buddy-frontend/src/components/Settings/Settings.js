import React, {useState, useContext} from "react";
import {Link} from "react-router-dom";
import TransactionPin from '../BackendPages/TransactionPin';
import { ToastContainer } from "react-bootstrap";
import { MyContext } from "../../statemanagement/ComponentState";

function SettingsMenu() {
const [open, setOpen] = useState(false);
const handleClose = () => setOpen(false);
const handleOpen = () => setOpen(true);

let isPinUpdated = localStorage.getItem("isPinUpdated");
    if(isPinUpdated =="false"){
        isPinUpdated =false;
    }

const { pagename, setPageName } = useContext(MyContext);
    setPageName("Settings");

    return (
        <>

       <Link to="#"  className="list-group-item list-group-item-action py-2 ripple" onClick={handleOpen}><i className="fa fa-cog" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp; {isPinUpdated && <span>Update</span> }{!isPinUpdated && <span>Create</span> } Transaction Pin</Link>

          
      <TransactionPin open={open} handleClose={handleClose} handleOpen={handleOpen} />
          <ToastContainer />
        </>
    );
}

export default SettingsMenu;