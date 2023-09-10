import React from 'react'
import { useStateContext } from '../../context/ContextProvider';
import { Link, NavLink } from 'react-router-dom';
import { TooltipComponent } from "@syncfusion/ej2-react-popups";
import { MdOutlineCancel } from "react-icons/md";

import { links } from "../../data/AdminHrResources";

const AdminSidebar = () => {
    const { activeMenu, setActiveMenu, screenSize } = useStateContext();

    const handleCloseSideBar = () => {
      if (activeMenu && screenSize <= 900) {
        setActiveMenu(false);
      }
    };
  
    const activeLink =
      "flex items-center gap-3 pl-4 pt-3 pb-2.5 rounded-lg text-white  text-md m-2";
    const normalLink =
      "flex items-center gap-3 pl-4 pt-3 pb-2.5 rounded-lg text-md text-gray-700 dark:text-gray-200 dark:hover:text-black hover:bg-light-gray m-2";
  
    return (
      <div className="ml-3 h-screen md:overflow-hidden overflow-auto md:hover:overflow-auto pb-10">
        {activeMenu && (
          <>
            <div className="flex justify-between items-center">
              <Link
                to="admin/dashboard"
                onClick={handleCloseSideBar}
                className="items-center gap-3 ml-3 mt-4 flex text-xl font-extrabold tracking-tight dark:text-white text-slate-900"
              >
                <span>HR Support Center</span>
              </Link>
              <TooltipComponent content="Menu" position="BottomCenter">
                <button
                  type="button"
                  onClick={() =>
                    setActiveMenu((prevActiveMenu) => !prevActiveMenu)
                  }
                  className="text-xl rounded-full p-3 hover:bg-light-gray mt-4 block md:hidden"
                >
                  <MdOutlineCancel />
                </button>
              </TooltipComponent>
            </div>
            <div className="mt-10">
              {links.map((item) => (
                <div key={item.title}>
                  <p className="text-gray-400 m-3  uppercase">{item.title}</p>
                  {item.links.map((link) => (
                    <NavLink
                      to={`/${link.location}`}
                      key={link.name}
                      onClick={handleCloseSideBar}
                      style={({ isActive }) => ({
                        backgroundColor: isActive ? "#0E82F6" : "",
                      })}
                      className={({ isActive }) =>
                        isActive ? activeLink : normalLink
                      }
                    >
                      {link.icon}
                      <span className="capitalize">{link.name}</span>
                    </NavLink>
                  ))}
                </div>
              ))}
            </div>
          </>
        )}
      </div>
    );
}

export default AdminSidebar
