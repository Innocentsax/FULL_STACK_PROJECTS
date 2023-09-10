import {MdOutlineDashboardCustomize} from "react-icons/md"
import {CgClipboard} from "react-icons/cg"
import {IoPersonSharp} from "react-icons/io5"
import {BsGift} from "react-icons/bs"
import {AiOutlineSetting} from "react-icons/ai"
import {RiLogoutBoxRLine} from "react-icons/ri"

export const links = [
    {
      title: 'Overview',
      links: [
        {
          name: 'Dashboard',
          location: 'admin/',
          icon: <MdOutlineDashboardCustomize />,
        },
        {
            name: 'Job board',
            location: 'admin/job-board',
            icon: <CgClipboard />,
          },
          {
            name: 'Employees',
            location: 'admin/employee-information',
            icon: <IoPersonSharp />,
          },
          {
            name: 'Rewards & Recognition',
            location: 'admin/reward-recognition',
            icon: <BsGift />,
          },
      ],
    },
  
    {
      title: 'Other',
      links: [
        {
          name: 'Settings',
          location: 'admin/setting',
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