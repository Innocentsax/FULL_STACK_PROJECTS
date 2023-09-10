import React from 'react'
import { BsGift, BsBriefcase } from "react-icons/bs";
import { RiUserFollowLine } from "react-icons/ri";
import { SlCalender } from "react-icons/sl";
import awardIcon from "../../assets/images/reward.png";

const AdminHrRewardRecognition = () => {
  return (
    <div className="p-4">
      <div className="flex justify-between">
        <div>
          <span className="font-bold md:text-4xl text-lg">Rewards & Recognitions / </span>
          <span className="text-2xl">History</span>
        </div>
        <div className="flex items-center">
          <div className="flex">
            <h1>Sort by year: </h1>
            <select className="ml-2 mr-6">
              <option>2022</option>
              <option>2023</option>
              <option>2024</option>
              <option>2025</option>
            </select>
          </div>
          <button className="w-16 rounded-md text-white h-44 bg-primaryColor btn-block">
            Vote
          </button>
        </div>
      </div>
      <div className="mt-4  bg-white h-772">
        <div className="row mx-0 py-4 flex justify-between border-b-4">
          <div className="col-4 flex items-center">
            <BsGift className="text-primaryColor" />
            <p className="mx-2">Award Title</p>
          </div>
          <div className="col-2 flex items-center justify-center">
            <BsBriefcase className="text-primaryColor" />
            <p className="mx-2">Department</p>
          </div>
          <div className="col-3 flex items-center justify-center">
            <RiUserFollowLine className="text-primaryColor" />
            <p className="mx-2">Recipients</p>
          </div>
          <div className="col-3 flex items-center justify-center">
            <SlCalender className="text-primaryColor" />
            <p className="mx-2">Duration of Service</p>
          </div>
        </div>
        {/* body */}
        <div className="row mx-0 p-2">
          <div className="col-4 flex items-center">
            <img
            className="md:block hidden"
             src={awardIcon} />
            <div>
              <h1 className="font-bold md:text-2xl ">Loyal Award</h1>
              <p className="text-lightFont">Testing data are to be presented here</p>
            </div>
          </div>
          <div className="col-2 flex items-center justify-center">
            <h1>Design</h1>
          </div>
          <div className="col-3 flex flex-col items-center justify-center">
            <h1>Eleanor D. Quinnox</h1>
            <p className="text-lightFont">Snr Product Designer</p>
          </div>
          <div className="col-3 md:flex items-center  justify-center">
            <div className="">3y 6m</div>
            <button className="text-primaryColor font-bold px-4">
              View profile
            </button>
          </div>
        </div>

        <div className="row mx-0 p-2">
          <div className="col-4 flex items-center">
            <img src={awardIcon} />
            <div>
              <h1 className="font-bold text-2xl">Loyal Award</h1>
              <p className="text-lightFont">Testing data are to be presented here</p>
            </div>
          </div>
          <div className="col-2 flex items-center justify-center">
            <h1>Design</h1>
          </div>
          <div className="col-3 flex flex-col items-center justify-center">
            <h1>Eleanor D. Quinnox</h1>
            <p className="text-lightFont">Snr Product Designer</p>
          </div>
          <div className="col-3 flex items-center  justify-center">
            <div className="">3y 6m</div>
            <button className="text-primaryColor font-bold px-4">
              View profile
            </button>
          </div>
        </div>

        <div className="row mx-0 p-2">
          <div className="col-4 flex items-center">
            <img src={awardIcon} />
            <div>
              <h1 className="font-bold text-xl">Loyal Award</h1>
              <p className="text-lightFont">Testing data are to be presented here</p>
            </div>
          </div>
          <div className="col-2 flex items-center justify-center">
            <h1>Design</h1>
          </div>
          <div className="col-3 flex flex-col items-center justify-center">
            <h1>Eleanor D. Quinnox</h1>
            <p className="text-lightFont">Snr Product Designer</p>
          </div>
          <div className="col-3 flex items-center  justify-center">
            <div className="">3y 6m</div>
            <button className="text-primaryColor font-bold px-4">
              View profile
            </button>
          </div>
        </div>

        <div className="row mx-0 p-2">
          <div className="col-4 flex items-center">
            <img src={awardIcon} />
            <div>
              <h1 className="font-bold text-2xl">Loyal Award</h1>
              <p className="text-lightFont">Testing data are to be presented here</p>
            </div>
          </div>
          <div className="col-2 flex items-center justify-center">
            <h1>Design</h1>
          </div>
          <div className="col-3 flex flex-col items-center justify-center">
            <h1>Eleanor D. Quinnox</h1>
            <p className="text-lightFont">Snr Product Designer</p>
          </div>
          <div className="col-3 flex items-center  justify-center">
            <div className="">3y 6m</div>
            <button className="text-primaryColor font-bold px-4">
              View profile
            </button>
          </div>
        </div>

        <div className="row mx-0 p-2">
          <div className="col-4 flex items-center">
            <img src={awardIcon} />
            <div>
              <h1 className="font-bold text-2xl">Loyal Award</h1>
              <p className="text-lightFont">Testing data are to be presented here</p>
            </div>
          </div>
          <div className="col-2 flex items-center justify-center">
            <h1>Design</h1>
          </div>
          <div className="col-3 flex flex-col items-center justify-center">
            <h1>Eleanor D. Quinnox</h1>
            <p className="text-lightFont">Snr Product Designer</p>
          </div>
          <div className="col-3 flex items-center  justify-center">
            <div className="">3y 6m</div>
            <button className="text-primaryColor font-bold px-4">
              View profile
            </button>
          </div>
        </div>

      </div>
    </div>
  );
}

export default AdminHrRewardRecognition
