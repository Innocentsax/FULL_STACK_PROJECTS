import './local_transfer.css';
import React from 'react';
import Topbar from '../dashboard/components/topbar/Topbar';
import { BsArrowLeft} from 'react-icons/bs';
import { Link } from "react-router-dom";

function FormItem(props) {
    return (

        <div className="form-group form-item">
            <input type={props.type} className="form-control" id={props.name}  placeholder={props.placeHolder}/>
        </div>

    )

}


function TextItem(props) {
    return (

        <div className="form-group form-item">
            <input type={props.type} className="form-control" id={props.name} width="200" height="100" placeholder={props.placeHolder}/>
        </div>

    )

}




function LocalTransfer () {

   
    return(
        <>
            <Topbar />
            <div className="container">
                <div className="previous-page">
                    <span>
                        <BsArrowLeft />
                    </span>
                   <Link to="/dashboard" className="goBack">Go back</Link>
                </div>
                <div className="profile-header">
                    <h5>transfer</h5>
                </div>
                <div className="transferContainer">
                    <p className="first-link"><a href="/local">Local Bank Transfer</a></p>
                    <p className="second-link"><a href="/other-transfer">Other Bank Transfer</a></p>
                </div>
                <hr className="newHr" />
                <div className="formWrapper">
                    <div className="formContainer">
                        <form action="#">
                            <label htmlFor="anumber">Account Number</label> <br />
                            <input type="text" className="anumber" name="accountnumber" placeholder="Account number"/>
                            <label>Account Name</label> <br />
                            <input type="text" placeholder="Account name"/>
                            <label>Amount</label> <br />
                            <input type="text" placeholder="Amount"/>
                            <label>Pin</label>
                            <input type="text" placeholder="Pin"/>
                            <label>Narration</label>
                        </form>
                        <form>
                            <textarea placeholder="message"></textarea>
                        </form>
                        <div className="btnContainer"><button type="submit" className="transferBtn">Send Money</button></div>
                    </div>
                </div>
            </div>

        </>
    )

}

export default LocalTransfer;
