import React, {useEffect, useState} from "react";
import { BsArrowLeft} from 'react-icons/bs';
import { Link } from "react-router-dom";
import Topbar from '../dashboard/components/topbar/Topbar';

import axios from "axios";
import './profile_page.css';


const apiUrl = "http://localhost:3333/api/v1/user";
const accessToken = localStorage.getItem("token");

const authAxios = axios.create({
	baseURL : apiUrl,
	headers : {
		Authorization : `Bearer ${accessToken}`,
		"Content-Type" : "*"
	}
});



function ProfilePage () {

	const [user, setUser] = useState([]);
	useEffect(()=>{
		loadUser();
	},[]);

	const loadUser = async ()=> {
		try {
			const result = await authAxios.get(`/getUser`);
			setUser(result.data);
		} catch (err) {
			console.log(err.message);
		}
	}

	return(
		<>
		<Topbar/>
		
		<div className="container">
			<div className="previous-page">
				<span>
					<BsArrowLeft />
				</span>
				<Link to='/dashboard' className="goBack">Go back</Link>
			</div>
			<div className="profile-header">
				<h5>Profile</h5>
			</div>
			<form className="profileFormContainer">
			<div className="profile-container">
				<div className ="profile-div">
					<div className="profile-content">
						<label htmlFor="fiName">First Name</label>
						<input type="text"  placeholder={user.firstName} />
					</div>
					<div className="profile-content">
						<label htmlFor="laName">Last Name</label>
						<input type="text" placeholder= {user.lastName} />
					</div>
					<div className="profile-content">
						<label htmlFor="phNumber">Phone number</label>
						<input type="text" placeholder={user.phoneNumber} />
					</div>
					<div className="profile-content">
						<label htmlFor="bvn">BVN</label>
						<input type="text" placeholder={user.bvn} />
					</div>
					
					<div className="profile-content">
						<label htmlFor="email">Email</label>
						<input type="email" placeholder= {user.email} />
					</div>
					<div className="profile-content">
						<button type="submit" className="profileBtn" disabled>Done</button>
					</div>
				</div>
			</div>
				
			</form>
			
		</div>
		
		</>
	)

}

export default ProfilePage;