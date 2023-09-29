import { useEffect, useState } from "react";
import ChangePassword from "./ChangePassword";
import { useAuth } from "../../context/authcontext";
import Loader from "../Loader/Loader";



const UserInformation = () =>{

    const[openChangePassoword, setOpenChangePassword] = useState(false);
    const { GetUser, getUser, setGetUser, updateUserConfig} = useAuth();
    const [isLoading, setIsLoading] = useState(false);

    console.log(getUser)
    

    useEffect(() => {
        GetUser();
    }, []);


    const onChange = (e) => {
        e.preventDefault();
        setGetUser({...getUser, [e.target.name]: e.target.value})
    }

    const handleUpdate = async (e) => {
        e.preventDefault();
        setIsLoading(true)
        await updateUserConfig(getUser);
        setIsLoading(false)
    }

    return(
        <div className="px-5 pt-2 w-[100%]">
            <h2 className="text-1xl mb-[2rem]">Edit account Information</h2>
            <form action="submit" className=" flex flex-col gap-3">
                {/* <Input type='text' placeholder='First Name' id='firstName' label='First Name'/>
                <Input type='text' placeholder='Last Name' id='lastName' label='Last Name'/>
                <Input type='email' placeholder='Email address' id='email' label='Email Address'/>
                <Input type='text' placeholder='Date of Birth' id='dob' label='Date of Birth'/>
                <Input type='text' placeholder='Address' id='address' label='Address'/>
                <Input type='text' placeholder='Phone' id='phone' label='Phone Number'/> */}

                <div className="flex gap-4">
                    <div className="flex flex-col g-3 w-[45%]">
                        <input className="border-b border-l px-1 " type="text" name="firstName" value={getUser.firstName} id=""  onChange={onChange} placeholder="First Name" />
                        <label htmlFor="firstName">First Name</label>
                    </div>
                    <div className="flex flex-col g-3 w-[45%]">
                        <input className="border-b border-l px-1 " type="text" name="lastName" value={getUser.lastName} id="" onChange={onChange} placeholder="Last Name" />
                        <label htmlFor="lastName">Last Name</label>
                    </div>
                </div>
               <div className="flex gap-4">
                    <div className="flex flex-col g-3 w-[45%]">
                            <input className="border-b border-l px-1 " type="email" name="email" value={getUser.email} id="" onChange={onChange} placeholder="Email address" readOnly />
                            <label htmlFor="email">Email Address</label>
                        </div>
                        <div className="flex flex-col g-3 w-[45%]">
                            <input className="border-b border-l px-1 " type="date" name="date_of_birth" value={getUser.date_of_birth} id=""  onChange={onChange} placeholder="DoB" />
                            <label htmlFor="dob">Date of Birth</label>
                        </div>
                    </div>
                <div>
                    <div className="flex gap-4">
                        <div className="flex flex-col g-3 w-[45%]">
                            <input className="border-b border-l px-1 " type="text" name="phone" value={getUser.phone} id="" onChange={onChange} placeholder="Phone Number" />
                            <label htmlFor="phone">Mobile</label>
                        </div>
                        <div className="w-[150px] mt-2">
                            <p className="text-[red] cursor-pointer" onClick={() => setOpenChangePassword(true)} >Change password</p>
                        </div>
                        
                        
                    </div>
                </div>
                <div className="flex flex-col g-3 w-full">
                    <input className="border-b border-l px-1 " type="text" name="address" value={getUser.address} id="" onChange={onChange} placeholder="Home Address" />
                    <label htmlFor="address">Home address</label>
                </div>
                
                
                {openChangePassoword && <ChangePassword closeModal={() => setOpenChangePassword(false)} />}
                <div className="flex justify-center">
                    <button type="button" className="bg-[#7e6a17] text-[white] py-2 px-4 rounded-md" onClick={handleUpdate}>Save</button>
                </div>
            </form>
            {isLoading && <Loader />}
        </div>
    )
}


export default UserInformation;