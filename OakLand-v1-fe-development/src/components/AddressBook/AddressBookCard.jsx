import { useRef } from "react";
import { useEffect, useState } from "react";
import { BsPencilFill } from "react-icons/bs";
import { useAuth } from "../../context/authcontext";
import Loader from "../Loader/Loader";
import { RiDeleteBinLine } from "react-icons/ri";


export const ConfirmDelete = ({ closeModal, id }) => {
  const { DeleteAddress, GetAddressbook, GetAddress, getAddress } = useAuth();
  const ref = useRef(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    setIsLoading(true)
    DeleteAddress(id)
    closeModal()
    setIsLoading(false);
  };

  const handleClickOutside = (e) => {
    if (ref.current && !ref.current.contains(e.target)) {
      closeModal();
    }
  };

  useEffect(() => {
    document.addEventListener("click", handleClickOutside, true);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [handleClickOutside]);


  useEffect(() => {
    GetAddress(id)
  }, [])

  return (
    <div className="w-[100%] h-[88%] mt-[4rem] rounded-b-md top-0 left-0 flex justify-center items-center fixed">
      <div
        ref={ref}
        className="flex flex-col items-center gap-4 z-50 bg-[white] border p-5"
      >
        <div>
          <p className="text-[1.5rem]">Confirm delete? </p>
        </div>
        <div className="flex gap-4">
          <button
            type="button"
            className="bg-[#7e6a17] text-[white] text-[0.7rem] py-[0.5rem] px-[1rem] rounded-md"
            onClick={closeModal}
          >
            Cancel
          </button>
          <button
            type="submit"
            className="bg-[#7e6a17] text-[white] text-[0.7rem] py-[0.5rem] px-[1rem] rounded-md"
            onClick={() => DeleteAddress(id)}
          >
            Delete
          </button>
        </div>
      </div>
      {isLoading && <Loader />}
    </div>
  );
};

export const EditAddress = ({ id, closeModal }) => {
  const {
    UpdateAddress,
    GetAddressbook,
    getAddress,
    setGetAddress,
    GetAddress,
  } = useAuth();
  const ref = useRef(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    setGetAddress({ ...getAddress, [e.target.name]: e.target.value });
  };

  const handleUpdate = (e) => {
    e.preventDefault();
    setIsLoading(true)
    UpdateAddress(id, getAddress);
    setIsLoading(false)
  };

  

  // useEffect(() => {
  //   GetAddress(id);
  // }, [id]);

  useEffect(() => {
    GetAddressbook();
  }, []);

  const handleClickOutside = (e) => {
    if (ref.current && !ref.current.contains(e.target)) {
      closeModal();
    }
  };

  useEffect(() => {
    document.addEventListener("click", handleClickOutside, true);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [handleClickOutside]);

  return (
    <div
      ref={ref}
      className="md:w-[100%] w-[90%] md:h-[88%] h-[50%] flex justify-center items-center fixed md:top-[13%] top-[15%] md:left-0 bg-[white] rounded-b-md "
    >
      <div className="flex flex-col items-center justify-center gap-4 md:absolute w-[90%] h-[90%] bg-[white] border p-3 z-50">
        <p className="text-[2rem] self-end cursor-pointer" onClick={closeModal}>
          X
        </p>
        <form
          action=""
          className="flex flex-col justify-center items-center w-[90%] gap-4"
        >
          <div className="flex gap-4 w-[100%]">
            <div className="flex flex-col w-[45%]">
              <input
                className="border-b border-l px-2"
                type="text"
                name="fullName"
                onChange={handleChange}
                value={getAddress.fullName}
                id=""
                required
              />
              <label htmlFor="fullName">Full Name</label>
            </div>
            <div className="flex flex-col w-[45%]">
              <input
                className="border-b border-l px-2"
                type="text"
                name="phone"
                onChange={handleChange}
                value={getAddress.phone}
                id=""
                required
              />
              <label htmlFor="phone">Phone Number</label>
            </div>
          </div>
          <div className="flex gap-4 w-[100%]">
            <div className="flex flex-col w-[45%]">
              <input
                className="border-b border-l px-2"
                type="email"
                name="emailAddress"
                onChange={handleChange}
                value={getAddress.emailAddress}
                id=""
                required
              />
              <label htmlFor="fullName">Email Address</label>
            </div>
            <div className="flex flex-col w-[45%]">
              <input
                className="border-b border-l px-2"
                type="text"
                name="street"
                onChange={handleChange}
                value={getAddress.street}
                id=""
                required
              />
              <label htmlFor="street">Street</label>
            </div>
          </div>
          <div className="flex gap-4 w-[100%]">
            <div className="flex flex-col w-[45%]">
              <input
                className="border-b border-l px-2"
                type="text"
                name="state"
                onChange={handleChange}
                value={getAddress.state}
                id=""
                required
              />
              <label htmlFor="state">State</label>
            </div>
            <div className="flex flex-col w-[45%]">
              <input
                className="border-b border-l px-2"
                type="text"
                name="country"
                onChange={handleChange}
                value={getAddress.country}
                id=""
                required
              />
              <label htmlFor="country">Country</label>
            </div>
          </div>

          <div className="flex gap-4 justify-center w-[100%]">
            <button
              type="button"
              className="bg-[#7e6a17] text-[white] py-2 px-4 rounded-md"
              onClick={closeModal}
            >
              Cancel
            </button>
            <button
              type="button"
              className="bg-[#7e6a17] text-[white] py-2 px-4 rounded-md"
              onClick={handleUpdate}
            >
              Save
            </button>
          </div>
        </form>
        {isLoading && <Loader />}
      </div>
    </div>
  );
};






const AddressBookCard = ({ fullName, address, emailAddress, phoneNumber, id }) => {
  const [openConfirm, setOpenConfirm] = useState(false);
  const [openEdit, setOpenEdit] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const { SetDefault, getAddress, GetAddress, GetAddressbook, DeleteAddress } = useAuth();

  const [screenSize, setScreenSize] = useState(window.innerWidth);

  const handleResize = () => {
    setScreenSize(window.innerWidth);
  };
  
  useEffect(() => {
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  const handleDefault = (e) => {
    e.preventDefault();
    setIsLoading(true)
    SetDefault(id)
    setIsLoading(false)
  }

  useEffect(() => {
    GetAddress(id)
    console.log(getAddress)
  }, [])



  return (
    <div className="">
      {screenSize > 768 ? (
      <div className="p-3 shadow-md md:w-[330px] md:h-[180px] gap-2 border-[1px] rounded-md divide-y">
        <div className="flex flex-col gap-2 pb-2">
          <h5 className="text-[#7e6a17]">{fullName}</h5>
          <div className="h-[40px]">
            <p className="text-[0.8rem] line-clamp-2 ">{address}</p>
          </div>
          <p>{ phoneNumber }</p>
        </div>
        <div className="flex items-center justify-between pt-2">
          <div>
              {
                  getAddress.isDefault === true ? 
                  
                  <button  className="text-[#5151cc] text-[0.8rem] cursor-not-allowed">
                      SET AS DEFAULT
                  </button>
                  :
                  <button onClick={handleDefault} className="text-[#5151cc] text-[0.8rem] cursor-pointer">
                      SET AS DEFAULT
                  </button>
              }
            
          </div>
          <div className="flex items-center gap-3">
            <button
              type="button"
              onClick={() => setOpenEdit(!openEdit)}
              className="text-[#5151cc] text-1xl"
            >
              <BsPencilFill />
            </button>
              <button type="button" onClick={() => setOpenConfirm(!openConfirm)}>< RiDeleteBinLine className="text-[red]" /></button>
          </div>
        </div>
        {openConfirm && <ConfirmDelete id={id} closeModal={() => setOpenConfirm(false)} />}
        {openEdit && <EditAddress id={id} closeModal={() => setOpenEdit(false)} />}
        {isLoading && <Loader />}
      </div>
      ):(
        <div className="p-3 w-full shadow-md border-2 bg-[white] mt-2 gap-2 divide-y">
          <div className="flex flex-col gap-2 pb-2">
            <h5 className="text-[#7e6a17]">{fullName}</h5>
            <div className="h-[40px]">
              <p className="text-[0.8rem] line-clamp-2 ">{address}</p>
            </div>
            <p>{ phoneNumber }</p>
          </div>
          <div className="flex items-center justify-between pt-2">
            <div>
                {
                    getAddress.isDefault === true ? 
                    
                    <button  className="text-[#5151cc] text-[0.8rem] cursor-not-allowed">
                        SET AS DEFAULT
                    </button>
                    :
                    <button onClick={handleDefault} className="text-[#5151cc] text-[0.8rem] cursor-pointer">
                        SET AS DEFAULT
                    </button>
                }
              
            </div>
            <div className="flex items-center gap-3">
              <button
                type="button"
                onClick={() => setOpenEdit(!openEdit)}
                className="text-[#5151cc] text-1xl"
              >
                <BsPencilFill />
              </button>
                <button type="button" onClick={() => setOpenConfirm(!openConfirm)}>< RiDeleteBinLine className="text-[red]" /></button>
            </div>
          </div>
          {openConfirm && <ConfirmDelete id={id} closeModal={() => setOpenConfirm(false)} />}
          {openEdit && <EditAddress id={id} closeModal={() => setOpenEdit(false)} />}
          {isLoading && <Loader />}
        </div>
        )}
    </div>
  );
};

export default AddressBookCard;
