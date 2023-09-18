import React, {useRef, useState, useEffect} from "react"
import {Link, useNavigate} from "react-router-dom";
import Loader from "../../globalresources/Loader";
import ResponseMessage from "../../globalresources/modals/ResponseMessage";
import axios from "axios";
import {baseEndpoint} from "../../globalresources/Config";
import "./EditProfile.css";
import expenseListModal from "../modals/ExpenseListModal";
import {elementSelector} from "../../globalresources/elementSelector";


function EditProfile(){

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");


    const navigate = useNavigate();
    const END_POINT_URL = baseEndpoint+"/api/v1/user/upload-profile-picture";

    const [isSpinning, setisSpinning]=useState(false);
    const [responseMessage, setResponseMessage] =useState(null);



    useEffect(() => {
        const phone = localStorage.getItem("phoneNumber");
        const lastname = localStorage.getItem("lastName");
        const firstname = localStorage.getItem("firstName");

        setFirstName(firstname);
        setLastName(lastname);
        setPhoneNumber(phone);

    }, []);

    // const [image, setImage] = useState(null);
    // const [imageUrl, setImageUrl]= useState('');

    const handleSubmit = async (e) => {
        setisSpinning(true);
        setResponseMessage(null);


        e.preventDefault();
        const updateUser = {
            firstName:firstName,
            lastName: lastName,
            phoneNumber: phoneNumber
        }

        console.log(updateUser);
        const token = localStorage.getItem("token");
        const res = await fetch(baseEndpoint+'/api/v1/user/edit', {
            method: 'PUT',
            headers: {
                "content-type":"application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(updateUser)

        });

        const result = await res.json();
        console.log(result);
        localStorage.setItem("phoneNumber", result.phoneNumber);
        localStorage.setItem("lastName", result.lastName);
        localStorage.setItem("firstName", result.firstName);
        setFirstName(result.firstName);
        setLastName(result.lastName);

        setisSpinning(false);
        setResponseMessage("Update successful");
        window.location.reload();

       // navigate("")
    };

    //Handle File
    // const handleFile = (e)=>{
    //     setImage(e.target.files[0]);
    //     console.log(e.target.files[0])
    // }

    // const handleUpload = async (e)=>{
    //     e.preventDefault();
    //     const token = localStorage.getItem("token");
    //     const formData = new FormData;
    //     formData.append('file', image);

    //     fetch(
    //         baseEndpoint + "/api/v1/user/upload-profile-picture",
    //         {
    //             method: "POST",
    //             headers: {
    //                 Authorization: `Bearer ${token}`
    //             },
    //             body: formData
    //         }
    //     ).then((res) =>
    //         res.json()
    //     ).then((result) => {
    //         setImageUrl(result.imageUrl)
    //         localStorage.setItem("imagePath",result.imageUrl);
    //         console.log('success', result)
    //     }).catch((error)=>{
    //         console.log(error)
    //     });
    //
    //
    // }

    return(
        <div className="sign-up-decapay-3kM">
            <div className="frame-8671-Yh7">
                <div className="frame-8670-u1s">
                </div>
                <div className="frame-8669-UjB">
                    <p className="create-an-account-1z1">Edit Profile</p>
                    <div className="frame-8668-vbB">
                        <div className="frame-8666-HAq">
                            <div className="frame-6-2eD">
                                <div className="frame-4-AkR">
                                    <p className="first-name-vzV">First Name</p>
                                    <input
                                        className="frame-2-fBP"
                                        placeholder="Enter your first name"
                                        name="firstName"
                                        type="text"
                                        value={firstName}
                                        onChange={e=>{setFirstName(e.target.value)}}
                                    />
                                </div>
                                <div className="frame-6-LYR">
                                    <p className="last-name-JVF">Last Name</p>
                                    <input
                                        className="frame-2-3Sq"
                                        placeholder="Enter your last name"
                                        name="lastName"
                                        type="text"
                                        value={lastName}
                                        onChange={e=>{setLastName(e.target.value)}}
                                    />
                                </div>
                                <div className="frame-8-wAq">
                                    <p className="phone-number-6pR">Phone Number</p>
                                    <input
                                        className="frame-2-Efj"
                                        placeholder="Enter your phone number"
                                        name="phoneNumber"
                                        type="number"
                                        value={phoneNumber}
                                        onChange={e=>{setPhoneNumber(e.target.value)}}
                                    />
                                </div>
                            </div>
                            <button onClick={(e) => handleSubmit(e)} className="frame-3-jku" value="Sign Up"
                                    type="submit">
                                Save Changes <Loader status={isSpinning}/>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            {responseMessage && <ResponseMessage message={responseMessage}  />}
        </div>
        // {responseMessage && <ResponseMessage message={responseMessage}/>}

    )

}
export default EditProfile;