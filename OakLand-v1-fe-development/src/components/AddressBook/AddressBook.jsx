import { useEffect, useState } from "react";
import { useAuth } from "../../context/authcontext";
import AddressBookCard from "./AddressBookCard"






const AddressBook = () => {

    const {GetAddressbook, getAddressbook, getAddress, GetAddress} = useAuth();
    const [screenSize, setScreenSize] = useState(window.innerWidth);

    const handleResize = () => {
      setScreenSize(window.innerWidth);
    };
    
    useEffect(() => {
      window.addEventListener("resize", handleResize);
      return () => window.removeEventListener("resize", handleResize);
    }, []);
  
    useEffect(() => {
        GetAddressbook();
    }, [getAddress])

    // useEffect(() => {
    //     GetAddress()
    // }, [])

    return(
        <div>
            {screenSize > 768 ? (
                <div className="flex flex-wrap justify-start w-[95%] min-h-[85%] gap-4 bg-[white] p-1 fixed ">
                    {getAddressbook.map((address) =>
                            <AddressBookCard fullName={address.fullName} address={address.street + "," + address.state + " " + address.country} phoneNumber={address.phone} emailAddress={address.email} id={address.id} />
                    )}
                </div>
            ):(
                <div className="m-w-[100vw]">
                    {
                    getAddressbook.map((address) => 
                    <div key={address.id}>
                        <AddressBookCard fullName={address.fullName} address={address.street + "," + address.state + " " + address.country} phoneNumber={address.phone} emailAddress={address.email} id={address.id} />
                    </div>
                )}
                </div>
            )}
        </div>
        
    )
}

export default AddressBook;