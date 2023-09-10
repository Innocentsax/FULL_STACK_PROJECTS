import React from "react";
import { HiPencilSquare } from "react-icons/hi2";
import profilePic from "../../assets/images/Defaultpic.png";
import profilePic1 from "../../assets/images/Defaultpic.png";
import profilePic2 from "../../assets/images/Defaultpic.png";
import { ImAttachment } from "react-icons/im";
import { IoImageOutline } from "react-icons/io5";
import { BsEmojiSmile } from "react-icons/bs";

import "./StaffChat.css"

const StaffChat = () => {
  return (
    <div className="p-4">
      <div className="cont">
        <div className="leftSide">
          <div className="header bg-white">
            <div className="">
              <h1 className="font-bold text-2xl">Message</h1>
            </div>
            <ul className="nav_icons">
              <li>
                <div className="ml-2"> ... </div>
              </li>
              <li>
                <HiPencilSquare />
              </li>
            </ul>
          </div>
          {/* <!-- Search Chat --> */}
          <div className="search_chat bg-white">
            <div>
              <input
                type="text"
                className=" !border-mainBackground"
                placeholder="Search or start new chat"
              />
            </div>
          </div>
          {/* <!-- CHAT LIST --> */}
          <div className="chatlist">
            <div className="block active">
              <div className="imgBox">
                <img src={profilePic} className="cover" alt="" />
              </div>
              <div className="details">
                <div className="listHead">
                  <h4>Yemi Johnson</h4>
                  <p className="time">08:15am</p>
                </div>
                <div className="message_p">
                  <p>
                    Lorem ipsum dolor sit amet consect Commodo nibh ultricies
                    pulvinar{" "}
                  </p>
                </div>
              </div>
            </div>

            <div className="block unread">
              <div className="imgBox">
                <img src={profilePic1} className="cover" alt="" />
              </div>
              <div className="details">
                <div className="listHead">
                  <h4>John</h4>
                  <p className="time">12:34</p>
                </div>
                <div className="message_p">
                  <p>Studying for java</p>
                  <b>1</b>
                </div>
              </div>
            </div>

            <div className="block unread">
              <div className="imgBox">
                <img src={profilePic2} className="cover" alt="" />
              </div>
              <div className="details">
                <div className="listHead">
                  <h4>Adenike</h4>
                  <p className="time">Yesterday</p>
                </div>
                <div className="message_p">
                  <p>Call me when you get home</p>
                  <b>2</b>
                </div>
              </div>
            </div>
            <div className="block">
              <div className="imgBox">
                <img src={profilePic} className="cover" alt="" />
              </div>
              <div className="details">
                <div className="listHead">
                  <h4>Susan</h4>
                  <p className="time">Yesterday</p>
                </div>
                <div className="message_p">
                  <p>Hello</p>
                </div>
              </div>
            </div>
            <div className="block">
              <div className="imgBox">
                <img src={profilePic1} className="cover" alt="" />
              </div>
              <div className="details">
                <div className="listHead">
                  <h4>Billonier</h4>
                  <p className="time">18/01/2022</p>
                </div>
                <div className="message_p">
                  <p>I'll get back to you</p>
                </div>
              </div>
            </div>
            <div className="block">
              <div className="imgBox">
                <img src={profilePic1} className="cover" alt="" />
              </div>
              <div className="details">
                <div className="listHead">
                  <h4>Ibrahim</h4>
                  <p className="time">17/01/2022</p>
                </div>
                <div className="message_p">
                  <p>Congratulations</p>
                </div>
              </div>
            </div>
            <div className="block">
              <div className="imgBox">
                <img src={profilePic2} className="cover" alt="" />
              </div>
              <div className="details">
                <div className="listHead">
                  <h4>Wendy</h4>
                  <p className="time">15/01/2022</p>
                </div>
                <div className="message_p">
                  <p>Thanks alot</p>
                </div>
              </div>
            </div>
            <div className="block">
              <div className="imgBox">
                <img src={profilePic} className="cover" alt="" />
              </div>
              <div className="details">
                <div className="listHead">
                  <h4>Susan</h4>
                  <p className="time">Yesterday</p>
                </div>
                <div className="message_p">
                  <p>Did you finish the project?</p>
                </div>
              </div>
            </div>
            <div className="block">
              <div className="imgBox">
                <img src={profilePic1} className="cover" alt="" />
              </div>
              <div className="details">
                <div className="listHead">
                  <h4>Simba</h4>
                  <p className="time">18/01/2022</p>
                </div>
                <div className="message_p">
                  <p>Nice course</p>
                </div>
              </div>
            </div>
            <div className="block">
              <div className="imgBox">
                <img src={profilePic} className="cover" alt="" />
              </div>
              <div className="details">
                <div className="listHead">
                  <h4>Joy</h4>
                  <p className="time">18/01/2022</p>
                </div>
                <div className="message_p">
                  <p>I'll get back to you</p>
                </div>
              </div>
            </div>
            <div className="block">
              <div className="imgBox">
                <img src={profilePic1} className="cover" alt="" />
              </div>
              <div className="details">
                <div className="listHead">
                  <h4>Timi</h4>
                  <p className="time">17/01/2022</p>
                </div>
                <div className="message_p">
                  <p>Congratulations</p>
                </div>
              </div>
            </div>
            <div className="block">
              <div className="imgBox">
                <img src={profilePic2} className="cover" alt="" />
              </div>
              <div className="details">
                <div className="listHead">
                  <h4>Dian</h4>
                  <p className="time">15/01/2022</p>
                </div>
                <div className="message_p">
                  <p>Thanks alot</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="rightSide">
          <div className="bg-white pt-2 px-2 border-2 border-mainBackground">
            <h1 className="font-bold ml-2">Yemi Johnson</h1>
            <div className="flex justify-between  ">
              <div className="flex items-center">
                <span className="bg-green-600 h-2 w-2 rounded-full mx-2"></span>
                <span className="text-lightFont text-sm">Active now</span>
              </div>
              <span className="font-bold text-lightFont">. . .</span>
            </div>
          </div>

          {/* <!-- CHAT-BOX --> */}
          <div className="chatbox bg-white">
            <div className="message my_msg">
              <p>
                Hi <br />
                <span>12:18</span>
              </p>
            </div>

            <div className="flex items-center active">
              <div className="mr-4">
                <img src={profilePic} className=" h-12 w-12" alt="" />
              </div>
              <div className="details">
                <div className="listHead flex items-center">
                  <h4 className="mr-2">Yemi Johnson</h4>
                  <span className="bg-green-600 h-2 w-2 rounded-full mx-2"></span>
                  <p className="time">08:15am</p>
                </div>
                <div className="message_f w-96">
                  <p>
                    Lorem ipsum dolor sit amet consect Commodo nibh ultricies
                    pulvinar{" "}
                  </p>
                </div>
              </div>
            </div>

            <div className="message my_msg">
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit. <br />
                <span>12:15</span>
              </p>
            </div>

            <div className="flex items-center justify-center py-4">
              <hr className=" w-full " />
              <p className="px-4 text-center whitespace-nowrap">April 8</p>
              <hr className=" w-full" />
            </div>

            <div className="flex items-center active">
              <div className="mr-4">
                <img src={profilePic} className=" h-12 w-12" alt="" />
              </div>
              <div className="details">
                <div className="listHead flex items-center">
                  <h4 className="mr-2">Yemi Johnson</h4>
                  <span className="bg-green-600 h-2 w-2 rounded-full mx-2"></span>
                  <p className="time">08:15am</p>
                </div>
                <div className="message_f w-96">
                  <p>
                    Lorem ipsum dolor sit amet consect Commodo nibh ultricies
                    pulvinar{" "}
                  </p>
                </div>
              </div>
            </div>

            <div className="message my_msg">
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit. Lorem
                ipsum dolor sit amet consectetur adipisicing elit. Eaque aliquid
                fugiat accusamus dolore qui vitae ratione optio sunt <br />
                <span>12:15</span>
              </p>
            </div>
            <div className="flex items-center active">
              <div className="mr-4">
                <img src={profilePic} className=" h-12 w-12" alt="" />
              </div>
              <div className="details">
                <div className="listHead flex items-center">
                  <h4 className="mr-2">Yemi Johnson</h4>
                  <span className="bg-green-600 h-2 w-2 rounded-full mx-2"></span>
                  <p className="time">08:15am</p>
                </div>
                <div className="message_f w-96">
                  <p>
                    Lorem ipsum dolor sit amet consect Commodo nibh ultricies
                    pulvinar{" "}
                  </p>
                </div>
              </div>
            </div>
            <div className="message my_msg">
              <p>
                Lorem ipsum dolor sit amet consectetur <br />
                <span>12:15</span>
              </p>
            </div>
            <div className="flex items-center active">
              <div className="mr-4">
                <img src={profilePic} className=" h-12 w-12" alt="" />
              </div>
              <div className="details">
                <div className="listHead flex items-center">
                  <h4 className="mr-2">Yemi Johnson</h4>
                  <span className="bg-green-600 h-2 w-2 rounded-full mx-2"></span>
                  <p className="time">08:15am</p>
                </div>
                <div className="message_f w-96">
                  <p>
                    Lorem ipsum dolor sit amet consect Commodo nibh ultricies
                    pulvinar{" "}
                  </p>
                </div>
              </div>
            </div>
            <div className="message my_msg">
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit.
                <br />
                <span>12:15</span>
              </p>
            </div>
            <div className="flex items-center active">
              <div className="mr-4">
                <img src={profilePic} className=" h-12 w-12" alt="" />
              </div>
              <div className="details">
                <div className="listHead flex items-center">
                  <h4 className="mr-2">Yemi Johnson</h4>
                  <span className="bg-green-600 h-2 w-2 rounded-full mx-2"></span>
                  <p className="time">08:15am</p>
                </div>
                <div className="message_f w-96">
                  <p>
                    Lorem ipsum dolor sit amet consect Commodo nibh ultricies
                    pulvinar{" "}
                  </p>
                </div>
              </div>
            </div>
            <div className="flex items-center justify-center py-4">
              <hr className=" w-full " />
              <p className="px-4 text-center whitespace-nowrap">Yesterday</p>
              <hr className=" w-full" />
            </div>
            <div className="message my_msg">
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit.
                <br />
                <span>12:15</span>
              </p>
            </div>
            <div className="flex items-center active">
              <div className="mr-4">
                <img src={profilePic} className=" h-12 w-12" alt="" />
              </div>
              <div className="details">
                <div className="listHead flex items-center">
                  <h4 className="mr-2">Yemi Johnson</h4>
                  <span className="bg-green-600 h-2 w-2 rounded-full mx-2"></span>
                  <p className="time">08:15am</p>
                </div>
                <div className="message_f w-96">
                  <p>
                    Lorem ipsum dolor sit amet consect Commodo nibh ultricies
                    pulvinar{" "}
                  </p>
                </div>
              </div>
            </div>
            <div className="flex items-center active">
              <div className="mr-4">
                <img src={profilePic} className=" h-12 w-12" alt="" />
              </div>
              <div className="details">
                <div className="listHead flex items-center">
                  <h4 className="mr-2">Yemi Johnson</h4>
                  <span className="bg-green-600 h-2 w-2 rounded-full mx-2"></span>
                  <p className="time">08:15am</p>
                </div>
                <div className="message_f w-96">
                  <p>
                    Lorem ipsum dolor sit amet consect Commodo nibh ultricies
                    pulvinar{" "}
                  </p>
                </div>
              </div>
            </div>
            <div className="flex items-center active">
              <div className="mr-4">
                <img src={profilePic} className=" h-12 w-12" alt="" />
              </div>
              <div className="details">
                <div className="listHead flex items-center">
                  <h4 className="mr-2">Yemi Johnson</h4>
                  <span className="bg-green-600 h-2 w-2 rounded-full mx-2"></span>
                  <p className="time">08:15am</p>
                </div>
                <div className="message_f w-96">
                  <p>
                    Lorem ipsum dolor sit amet consect Commodo nibh ultricies
                    pulvinar{" "}
                  </p>
                </div>
              </div>
            </div>
            <div className="flex items-center active">
              <div className="mr-4">
                <img src={profilePic} className=" h-12 w-12" alt="" />
              </div>
              <div className="details">
                <div className="listHead flex items-center">
                  <h4 className="mr-2">Yemi Johnson</h4>
                  <span className="bg-green-600 h-2 w-2 rounded-full mx-2"></span>
                  <p className="time">08:15am</p>
                </div>
                <div className="message_f w-96">
                  <p>
                    Lorem ipsum dolor sit amet consect Commodo nibh ultricies
                    pulvinar{" "}
                  </p>
                </div>
              </div>
            </div>
          </div>

          {/* <!-- CHAT INPUT --> */}
          <div className="chat_input">
            <div className="flex">
              <button  className="mx-2">
                <IoImageOutline />
              </button>
              <button className="mx-2">
                <ImAttachment />
              </button>
              <button className="mx-2">
                <BsEmojiSmile />
              </button>
            </div>
            {/* <!-- <ion-icon name="happy-outline"></ion-icon> --> */}
            <input type="text" placeholder="Type a message" />
            <button>Send</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default StaffChat;
