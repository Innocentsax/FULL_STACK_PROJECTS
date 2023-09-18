import React from 'react';
import "./resetpassword.css";
import {Link} from "react-router-dom";
import Loader from "../globalresources/Loader";

function PasswordResetForm(props) {
    return (
        <form onSubmit={props.handlePasswordResetSubmit}>
            <div className="login-decapay-3rZ">
                <div className="frame-8671-N89">
                    <div className="frame-8670-7Ld">
                        <img className="pay-Eg9" src="/assets/pay-iFT.png"/>
                        <Link to="/">
                            <p className="decapay-mg5">DecaPay</p>{" "}
                            <p className="decapay-mg5">DecaPay</p>{" "}
                        </Link>
                    </div>
                    <div className="frame-8669-JAD">
                        <p className="login-Dny">Password Reset </p>
                        <div className="frame-8668-MeH">
                            <div className="frame-8667-K5K">
                                <div className="frame-8666-GmF">
                                    <div className="frame-8665-S9w">
                                        <div className="frame-6-oFP">
                                            <div className="frame-4-kRX">
                                                <p className="email-f2h">New Password</p>
                                                <input
                                                    className="frame-2-B13"
                                                    name="newPassword"
                                                    placeholder="Enter new password"
                                                    type="password"
                                                    onChange={props.handleChange}
                                                />
                                            </div>
                                            <div className="frame-5-44q">
                                                <p className="password-21f">Confirm Password</p>
                                                <input
                                                    className="frame-3-kyF"
                                                    name="confirmPassword"
                                                    placeholder="Confirm Password"
                                                    type="password" onChange={props.handleChange}
                                                />
                                            </div>
                                        </div>
                                    </div>
                                    <button className="frame-8754-GcH action-button" type="submit">Reset
                                        <Loader status={props.spinner}/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    );
}

export default PasswordResetForm;