import React from "react";

import { BiFilterAlt } from "react-icons/bi";
import { Link } from "react-router-dom";
import { RxPerson } from "react-icons/rx";
import { FiMail, FiPhone } from "react-icons/fi";
import { BsCheckCircle } from "react-icons/bs";
import axios from "axios";
import ReactPaginate from "react-paginate";
import { useState, useEffect } from "react";
import { useStateContext } from "../../context/ContextProvider";

const StaffEmployees = () => {

  const [users, setUsers] = useState({});
  const [pageNo, setPageNo] = useState(0);
  const context = useStateContext();
  const token = "Bearer "+context.token;
  const headers =  {
  'Content-Type': 'application/json',
 'Authorization': token
};


  const fetchUsers = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/v1/hr/view-all-staff?pageNo=" + pageNo , { headers }
      );
      setUsers(response.data);
      console.log(response.data);
    } catch (error) {
      console.error("Error fetching users", error);
    }
  };
  useEffect(() => {
    fetchUsers();
  }, [pageNo]);
  console.log(pageNo);
  const changePage = ({ selected }) => {
    setPageNo(selected);
  };
  const userss = users.content ? users.content : [];
  const paginatioNo = users.pageNo+1

  return (
    <div className="p-4 relative">
      <div className="flex justify-between mb-16">
        <h1 className="text-4xl font-bold">Employees</h1>
        <div>
          <button className="flex items-center bg-white rounded-md justify-center relative h-12 w-28 min-w-250">
            <BiFilterAlt className="h-full  flex items-center justify-center left-1" />
            <select className="border-none  appearance-none p-2 ">
              <option>Filter by</option>
              <option>Newest</option>
              <option>Oldest</option>
              <option>Department</option>
            </select>
          </button>
        </div>
      </div>


        <div>
          <table className="table-fixed border-collapse border-spacing-2 w-full">
            <thead className="">
              <tr>
                <th>
                  <div className="flex items-center px-4">
                    <RxPerson />
                    <p className="m-2"></p>
                    Employee
                  </div>
                </th>

                <th>
                  <div className="flex items-center px-4">
                    <FiMail />
                    <p className="m-2"></p>
                    Email
                  </div>
                </th>

                <th className="px-4">
                  <div className="flex items-center px-4">
                    <FiPhone />
                    <p className="m-2"></p>
                    Mobile
                  </div>
                </th>

                <th className="px-4">
                  <div className="flex items-center px-4">
                    <BsCheckCircle />
                    <p className="m-2"></p>
                    Status
                  </div>
                </th>
              </tr>
            </thead>
            {userss.map((user, index) => (


            <tbody className="bg-white">
              <tr className="border-4 border-mainBackground">
                <td className="py-2 px-4">
                  <div>
                    <h1>{user.fullName}</h1>
                    <p className="text-lightFont text-sm">{user.position}</p>
                  </div>
                </td>

                <td className="px-4">{user.email}</td>

                <td className="px-4">{user.phoneNo}</td>

                <td className="px-4">
                  <div className="flex ">
                    <button className="bg-green-300 h-8 rounded-md text-white w-14">
                      {user.status}
                    </button>
                    <div className="px-4">
                      <Link to="/view-empployees-profile">
                        <span>View Profile</span>
                      </Link>
                    </div>
                  </div>
                </td>
              </tr>
            </tbody>
       ))}

          </table>
        </div>

      <div className="bottom-0 w-full p-4 flex items-center justify-center">

        <ReactPaginate
            previousLabel={"Previous"}
            nextLabel={"Next"}
            pageCount={paginatioNo}
            onPageChange={changePage}
            containerClassName={"flex items-center justify-center space-x-4 mt-10"} // Added mt-10 for top margin
            previousLinkClassName={"bg-white border border-gray-300 shadow-xs rounded px-3 py-1 hover:bg-blue-500 hover:text-white"}
            nextLinkClassName={"bg-white border border-gray-300 shadow-xs rounded px-3 py-1 hover:bg-blue-500 hover:text-white"}
            disabledClassName={"paginationDisabled"}
            activeClassName={"paginationActive bg-blue-500 rounded"} // Added rounded background
            pageLinkClassName={"text-sm"} // Added text-sm for smaller font size
        />
      </div>

      
    </div>
  );
};

export default StaffEmployees;

