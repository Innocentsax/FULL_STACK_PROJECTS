import React, {useRef, useState, useEffect} from "react"
import {Link, useNavigate} from "react-router-dom";
import Loader from "../../globalresources/Loader";
import ResponseMessage from "../../globalresources/modals/ResponseMessage";
import axios from "axios";
import {baseEndpoint} from "../../globalresources/Config";
import "./EditProfile.css";
import expenseListModal from "../modals/ExpenseListModal";
import {elementSelector} from "../../globalresources/elementSelector";
import "./UploadPhoto.css"

function UploadPhoto(){
    const [image, setImage] = useState(null);
    const [imageUrl, setImageUrl]= useState('');



    const refreshPage = () => {
        window.location.reload(false);
      }

    useEffect(() => {

        const url = localStorage.getItem("imagePath");

        setImageUrl(url)
    }, []);

    const handleFile = (e)=>{
        const preview = document.getElementById("preview");
        preview.src = URL.createObjectURL(e.target.files[0]);
        setImage(e.target.files[0]);
        console.log(e.target.files[0])
    }

    const handleUpload = async (e)=>{
        e.preventDefault();
        const token = localStorage.getItem("token");
        const formData = new FormData;
        formData.append('file', image);

        fetch(
            baseEndpoint + "/api/v1/user/upload-profile-picture",
            {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`
                },
                body: formData
            }
        ).then((res) =>
            res.json()
        ).then((result) => {
            setImageUrl(result.imageUrl)
            localStorage.setItem("imagePath",result.imageUrl);
            refreshPage();
            console.log('success', result)
        }).catch((error)=>{
            console.log(error)
        });


    }

    return(
        <div className="sign-up-decapay-3kM">
            <div className="frame-8671-Yh7">
                <div className=" my-4"><h2>UPLOAD PHOTO</h2></div>
                <div id="frame" className="container">
                <div className="frame-8670-u1s">
                    <a
                        className="nav-link dropdown-toggle hidden-arrow d-flex align-items-center my-4"
                        href="#!"
                        id="navbarDropdownMenuLink"
                        role="button"
                        data-mdb-toggle="dropdown"
                        aria-expanded="false"
                    >
                        <img
                            id="preview"
                            src={imageUrl}
                            className="rounded-circle profile-img"
                            height={22}
                            alt=""
                            loading="lazy"
                        />
                    </a>
                </div>
                <div>
                    <form onSubmit={handleUpload}>
                        <button type="submit" className="btn btn-success  mb-3 mx-4"><input type="file" onChange={handleFile}/></button>
                        <button type="submit" className="btn btn-success btn-lg mb-3" > Upload</button>
                    </form>
                </div>
                </div>
                {/*=============================================================================*/}


                    <div className="frame-8668-vbB">
                        <div className="frame-8666-HAq">
                            <div className="frame-6-2eD">
                                <div className="frame-4-AkR">
                                </div>
                                <div className="frame-6-LYR">

                                </div>
                                <div className="frame-8-wAq">

                                </div>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
        // {responseMessage && <ResponseMessage message={responseMessage}/>}

    )

}
export default UploadPhoto;