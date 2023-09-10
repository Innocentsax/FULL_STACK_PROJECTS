import {MdOutlineDashboardCustomize} from "react-icons/md"
import {CgClipboard, CgProfile} from "react-icons/cg"
import {IoPersonSharp} from "react-icons/io5"
import {HiOutlineChatBubbleLeftRight} from "react-icons/hi2"
import {BsGift} from "react-icons/bs"
import {AiOutlineSetting} from "react-icons/ai"
import {RiLogoutBoxRLine} from "react-icons/ri"

export const links = [
    {
      title: 'Overview',
      links: [
        {
          name: 'Dashboard',
          location: 'staff/',
          icon: <MdOutlineDashboardCustomize />,
        },
        {
            name: 'Job board',
            location: 'staff/job-board',
            icon: <CgClipboard />,
          },
          {
            name: 'Profile',
            location: 'staff/profile',
            icon: <CgProfile />,
          },
          {
            name: 'Employees',
            location: 'staff/employees',
            icon: <IoPersonSharp />,
          },
          {
            name: 'Chat',
            location: 'staff/chat',
            icon: <HiOutlineChatBubbleLeftRight />,
          },
          {
            name: 'Rewards & Recognition',
            location: 'staff/reward-recognition',
            icon: <BsGift />,
          },
      ],
    },
  
    {
      title: 'Other',
      links: [
        {
          name: 'Settings',
          location: 'staff/setting',
          icon: <AiOutlineSetting />,
        },
        {
          name: 'Logout',
          location: 'login',
          icon: <RiLogoutBoxRLine />,
        },
        
      ],
    },
  ];