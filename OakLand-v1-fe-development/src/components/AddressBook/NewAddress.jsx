import { useState } from "react"
import { Link } from "react-router-dom";
import { useAuth } from "../../context/authcontext"
import Loader from "../Loader/Loader";
import SideBar from "../SideBar/SideBar";



const NewAddress = () => {

    const {CreateAddress} = useAuth();
    const [isLoading, setIsLoading] = useState(false);
    const [screenSize, setScreenSize] = useState(window.innerWidth);


    const [address, setAddress] = useState({
        fullName: '',
        phone: '',
        email: '',
        street: '',
        state: '',
        country: ''

    })

    const handleChange = (e) => {
        setAddress({...address, [e.target.name]: e.target.value})
    }


    const handleCancel = () => {
        setAddress({
            fullName: '',
            phone: '',
            email: '',
            street: '',
            state: '',
            country: ''
        })
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        await CreateAddress(address);
        setIsLoading(false);
        setAddress({
            fullName: '',
            phone: '',
            email: '',
            street: '',
            state: '',
            country: ''
        })
    }

    return(
        <div className='md:px-[5%] py-[5%] pb-0'>
        {screenSize > 768 ? (
          <div className='row mx-auto'>
            {/* Desktop layout */}
            <div className='col-md-4'>
              <SideBar />
              <p className='text-20xl text-red-700'></p>
            </div>
  
            <div className='col-md-8 bg-[white] drop-shadow-md rounded-md pb-2 divide-y'>
                <div className="border-gray-300 border-b-1 py-3 flex justify-between">
                  <h1 className='text-2xl font-bold-900 '>Address Book</h1>
                  <button className='bg-[#7e6a17] text-[white] p-2 rounded-md'>New Address</button>
                </div>
  
                <div className='bg-[white]'>
                    <div className="w-[100%] min-h-[88%] flex justify-center items-center">
                        <form action="" onSubmit={handleSubmit} className='flex flex-col gap-4 w-[80%] py-[5%]'>
                        <div className="flex gap-4">
                            <div className='flex flex-col w-[45%]'>
                                <input className="border-b border-l px-2" type="text" name="fullName" onChange={handleChange} value={address.fullName} id="" required />
                                <label htmlFor="fullName">Full Name</label>
                            </div>
                            <div className='flex flex-col w-[45%]'>
                                <input className="border-b border-l px-2" type="text" name="phone" onChange={handleChange} value={address.phone} id="" required />
                                <label htmlFor="phone">Phone Number</label>
                            </div>
                        </div>
                        <div className="flex gap-4">
                            <div className='flex flex-col w-[45%]'>
                                <input className="border-b border-l px-2" type="email" name="email" onChange={handleChange} value={address.email} id=""  required/>
                                <label htmlFor="fullName">Email Address</label>
                            </div>
                            <div className='flex flex-col w-[45%]'>
                                <input className="border-b border-l px-2" type="text" name="street" onChange={handleChange} value={address.street} id="" required />
                                <label htmlFor="street">Street</label>
                            </div>
                        </div>
                        <div className="flex gap-4">
                            <div className='flex flex-col w-[45%]'>
                                <input className="border-b border-l px-2" type="text" name="state" onChange={handleChange} value={address.state} id="" required />
                                <label htmlFor="state">State</label>
                            </div>
                            <div className='flex flex-col w-[45%]'>
                                <input className="border-b border-l px-2" type="text" name="country" onChange={handleChange} value={address.country} id="" required />
                                <label htmlFor="country">Country</label>
                            </div>
                        </div>
                        <div className='flex gap-4 justify-center mt-6'>
                            <Link to='/addressbook' onClick={handleCancel} className="bg-[#7e6a17] text-[white] py-2 px-4 rounded-md">Cancel</Link>
                            <button type="submit" className="bg-[#7e6a17] text-[white] py-2 px-4 rounded-md">Save</button>
                        </div>

                        </form>
                        {isLoading && <Loader />}
                    </div>
                </div>
            </div>
           </div>
        ) : (
            <div className='flex justify-center items-center'>
                    <div className=" bg-[white] border-2 p-5">
                        <form action="" onSubmit={handleSubmit} className='flex flex-col gap-4 '>
                        <div className="flex gap-4">
                            <div className='flex flex-col w-[45%]'>
                                <input className="border-b border-l px-2" type="text" name="fullName" onChange={handleChange} value={address.fullName} id="" required />
                                <label htmlFor="fullName">Full Name</label>
                            </div>
                            <div className='flex flex-col w-[45%]'>
                                <input className="border-b border-l px-2" type="text" name="phone" onChange={handleChange} value={address.phone} id="" required />
                                <label htmlFor="phone">Phone Number</label>
                            </div>
                        </div>
                        <div className="flex gap-4">
                            <div className='flex flex-col w-[45%]'>
                                <input className="border-b border-l px-2" type="email" name="email" onChange={handleChange} value={address.email} id=""  required/>
                                <label htmlFor="fullName">Email Address</label>
                            </div>
                            <div className='flex flex-col w-[45%]'>
                                <input className="border-b border-l px-2" type="text" name="street" onChange={handleChange} value={address.street} id="" required />
                                <label htmlFor="street">Street</label>
                            </div>
                        </div>
                        <div className="flex gap-4">
                            <div className='flex flex-col w-[45%]'>
                                <input className="border-b border-l px-2" type="text" name="state" onChange={handleChange} value={address.state} id="" required />
                                <label htmlFor="state">State</label>
                            </div>
                            <div className='flex flex-col w-[45%]'>
                                <input className="border-b border-l px-2" type="text" name="country" onChange={handleChange} value={address.country} id="" required />
                                <label htmlFor="country">Country</label>
                            </div>
                        </div>
                        <div className='flex gap-4 justify-center mt-6'>
                            <Link to='/addressbook' onClick={handleCancel} className="bg-[#7e6a17] text-[white] py-2 px-4 rounded-md">Cancel</Link>
                            <button type="submit" className="bg-[#7e6a17] text-[white] py-2 px-4 rounded-md">Save</button>
                        </div>

                        </form>
                        {isLoading && <Loader />}
                    </div>
                </div>
        )}
      </div>
    )
}

export default NewAddress;