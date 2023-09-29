import { useState } from "react";
import { useRef, useEffect } from "react";
import { useAuth } from "../../context/authcontext";



const ChangePassword = ({openModal, closeModal}) =>{

    const modalRef = useRef(null);

    const {updatePasswordConfig} = useAuth();
    const [password, setPassword] = useState({
        currentPassword: '',
        newPassword: '',
        confirmNewPassword: ''
    });

    const handleClickOutside = (e) => {
        if(modalRef.current && !modalRef.current.contains(e.target)){
            closeModal();
        }
    }

    useEffect(() => {
        document.addEventListener("click", handleClickOutside, true);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        }

    }, [handleClickOutside]);

    const handleChange = (e) => {
        setPassword({...password, [e.target.name]: e.target.value})
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        updatePasswordConfig(password)
        console.log(password)
        setPassword({})
    }

    return(
        <div className=" w-[100%] h-[87.5%] mt-[4rem] flex flex-col justify-center items-center mx-auto p-4 shadow-md rounded-b-md fixed bg-[#21334f] bg-opacity-[0.3] bg-op z-10 top-0 left-0 ">
            <div ref={modalRef} className="flex flex-col w-[70%] md:w-[50%] bg-[white] py-3 rounded-md">
                <div className="flex justify-end">
                    <h1 onClick={closeModal} className="text-3xl mr-6 cursor-pointer">X</h1>
                </div>
                <form action="" className="flex flex-col gap-3 p-6">
                    <div className="flex flex-col g-3">
                        <input className="border-b border-l px-1 " type="password" name="currentPassword" onChange={handleChange} value={password.currentPassword} id="" placeholder="Current Password" />
                        <label htmlFor="currentPassword">Current Password</label>
                    </div>
                    <div className="flex flex-col g-3">
                        <input className="border-b border-l px-1 " type="password" name="newPassword" onChange={handleChange} value={password.newPassword} id=""  placeholder="New Password" />
                        <label htmlFor="newPassword">New Password</label>
                    </div>
                    <div className="flex flex-col g-3">
                        <input className="border-b border-l px-1 " type="password" name="confirmNewPassword" onChange={handleChange} value={password.confirmNewPassword} id="" placeholder="Confirm New Password" />
                        <label htmlFor="confirmNewPassword">Confirm New Password</label>
                    </div>
                    <div className="flex justify-center">
                    <button type="submit" onClick={handleSubmit} className="bg-[#7e6a17] text-[white] py-2 px-4 rounded-md">Save</button>
                </div>
                </form>
            </div>
            
        </div>
    )
}

export default ChangePassword;