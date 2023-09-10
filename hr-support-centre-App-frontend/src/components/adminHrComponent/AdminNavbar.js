import React, { useEffect } from "react";
import {AiOutlineSearch, AiOutlineMenu} from "react-icons/ai"
import { RiNotification3Line } from "react-icons/ri";
import { TooltipComponent } from "@syncfusion/ej2-react-popups";
import { useStateContext } from "../../context/ContextProvider";

const NavButton = ({ title, customFunc, icon, color, dotColor }) => (
  <TooltipComponent content={title} position="BottomCenter">
    <button
      type="button"
      onClick={customFunc}
      style={{ color }}
      className="relative text-xl rounded-full p-3 hover:bg-light-gray"
    >
      <span
        style={{ background: dotColor }}
        className="absolute inline-flex rounded-full h-2 w-2 right-2 top-2"
      />
      {icon}
    </button>
  </TooltipComponent>
);

const AdminNavbar = () => {
  const {
    setActiveMenu,
    screenSize,
    setScreenSize,
    employee,
  } = useStateContext();

  useEffect(() => {
    const handleResize = () => setScreenSize(window.innerWidth);
    window.addEventListener("resize", handleResize);
    handleResize();
    return () => window.removeEventListener("resize", handleResize);
  }, [setScreenSize]);
  useEffect(() => {
    if (screenSize <= 1020) {
      setActiveMenu(false);
    } else {
      setActiveMenu(true);
    }
  }, [screenSize, setActiveMenu]);

  return (
    <div className="flex justify-between p-2 md:mx-6 relative bg-white w-full mt-0 mx-0 px-4 pt-4">
      <div className="flex">
      <NavButton
      title="" 
        customFunc={() => setActiveMenu((prevActiveMenu) => !prevActiveMenu)}
        color="#0e82f6"
        icon={<AiOutlineMenu />}
      />
        <form className="flex items-center rounded border-2 px-4">
          <input
            type="text"
            className="px-3 w-full py-2 outline-none font-semibold placeholder-gray-500 text-black "
            name="search"
            placeholder="search"
          />
          <AiOutlineSearch className="" />
        </form>
      </div>

      <div className="flex flex-end">
        <NavButton
          title="Notifications"
          dotColor="#0E82F6"
          color="#0E82F6"
          icon={<RiNotification3Line />}
        />
        <TooltipComponent content="profile" position="BottomCenter">
          <div
            className="md:flex item-center gap-2 cursor-pointer p-1 hover:bg-light-gray rounded-lg sm:block"
          >
            <img className="rounded-full w-8 h-8 md:block hidden" alt="" />
            <p>
              <span className="text-gray-400 text-14">Hi, </span>{" "}
              <span className="text-gray-400 font-bold ml-1 text-14">
                {employee.firstName}
              </span>
            </p>
          </div>
        </TooltipComponent>
      </div>
    </div>
  );
};

export default AdminNavbar;
