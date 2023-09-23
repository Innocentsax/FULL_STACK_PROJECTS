import React, {Component} from 'react';
import Logo from "../../Assets/logo.svg";


class LoginAndRegisterNavBar extends Component {
    render() {
        return (
                <nav>
                    <div className="nav-logo-container">
                        <img src={Logo} alt="Hive logo navebar"/>
                    </div>
                </nav>

        );
    }
}

export default LoginAndRegisterNavBar;