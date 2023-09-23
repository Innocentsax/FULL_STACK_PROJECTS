
import './dashboard.css'
import Emoji from './Emoji.svg'
// import jwt_decode from "jwt-decode";
//
//
// const token = localStorage.getItem("token");
// const decodedToken = jwt_decode(token);
// const fullName = decodedToken.fullName;

const search = () => {
    const username = localStorage.getItem("fullName");
    return <div className="search-menu">
        <div className="dashboard-content">
            <h2>Welcome {username.split(' ')[0]} <img src={Emoji} alt="Hive Emoji" /></h2>
            <div className="dashboard-search-component">
                <h2>New Job Listing</h2>
                <input type="text" placeholder="Search for a user" />
            </div>
        </div>
    </div>
}
 
export default search;