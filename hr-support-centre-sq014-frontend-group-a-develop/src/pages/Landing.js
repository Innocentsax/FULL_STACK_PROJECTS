import React from "react";
import human from "../assets/images/Iconpng.png";
import star from "../assets/images/star.jpg";
import welcome from "../assets/images/landingimage.png";
import { Link } from "react-router-dom";
import { AiFillTwitterCircle } from "react-icons/ai";
import { BsYoutube } from "react-icons/bs";
import { FaInstagramSquare } from "react-icons/fa";

const Landing = () => {
  return (
    <>
    <div className="bg-white">
      {/* Header section */}
      <nav>
        <div className="flex flex-row justify-between h-16 top-0 left-0 right-0 md:px-28 md:py-4 items-center px-2 bg-white">
          <div className="font-bold md:text-2xl">HR Suport Center</div>
          <Link to="/login">
            <button className="bg-primaryColor rounded-md h-44 w-135 text-white font-bold">
              Log in
            </button>
          </Link>
        </div>
      </nav>
      {/* Hero section */}
      <div className="bg-slate-100 px-8 py-6 h-580">
        <div className="container">
          <div className="row">
            <div className="col-lg-12">
              <div className="row">
                <div className="col-sm align-self-center">
                  <div className="">
                    <div className="row md:px-14">
                      <div className="col-lg-12">
                        <h1 className="font-bold text-4xl text-text-color">
                          Get expert HR guidance and <br></br> support when you
                          need it <br></br>with our{" "}
                          <span className="text-primaryColor">
                            HR Support Center
                          </span>
                        </h1>
                      </div>
                      <div className="col-lg-12 pb-4">
                        <p className="py-6">
                          Say goodbye to HR headaches and focus on what you do{" "}
                          <br></br> best - growing your business
                        </p>
                        <Link to="/login">
                          <button className="rounded-md h-44 px-6 font-bold text-white bg-primaryColor">
                            Log in Here
                          </button>
                        </Link>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="col-sm">
                  <img className="rounded-lg h-443 w-678" src={welcome} alt="" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Body section */}
      <div className="mt-20">
        <div className="text-center py-8">
          <img
            style={{ height: "70px", width: "70px", display: "inline" }}
            src={star}
            alt=""
          />
          <h2 className="font-bold text-xl">
            Improve the recruitments with<br></br> HR Support Center
          </h2>
        </div>
        <div className="flex justify-between md:px-32 md:py-6 sm:px-1 sm:py-1">
          <div className="flex">
            <div className="px-8">
              <img
                style={{ height: "50px", width: "50px" }}
                src={human}
                alt="png"
              />
            </div>
            <div>
              <p>
                Equality, Quality <br></br>& Fairness
              </p>
            </div>
          </div>

          <div className="flex">
            <div className="px-2">
              <img
                style={{ height: "50px", width: "50px" }}
                src={human}
                alt="png"
              />
            </div>
            <div>
              <p>
                Candedate & Recruiter <br></br>Experience
              </p>
            </div>
          </div>

          <div className="flex">
            <div className="px-2">
              <img
                style={{ height: "50px", width: "50px" }}
                src={human}
                alt="png"
              />
            </div>
            <div>
              <p>
                Better ROI for <br></br>Every Hire
              </p>
            </div>
          </div>
        </div>
      </div>
      {/* Footer section */}
      <footer className="flex justify-between md:px-28 py-6 bg-landingFooter items-center text-white mt-32">
        <div>
          <p className="text-xs">All rights reserved 2023 Hr Support Center</p>
        </div>
        <div>
          <h2 className="font-bold md:text-xl">HR Support Center</h2>
        </div>
        <div>
          <div className="flex justify-between px-8 py-2">
            <div className="px-2">
              <FaInstagramSquare />
            </div>
            <div className="px-2">
              <AiFillTwitterCircle />
            </div>
            <div className="px-2">
              <BsYoutube />
            </div>
          </div>
          <div>
            <p className="text-xs">info@hrsupportcenter.com</p>
          </div>
        </div>
      </footer>
    </div>
    </>
  )
}

export default Landing
