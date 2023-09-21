import styled from "styled-components";
import profile from '../../HomePage/image/event_1.png';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronDown, faChevronUp } from "@fortawesome/free-solid-svg-icons";
import { useState ,useEffect} from "react";
import UserProfileModal from "../../Profile-modal/UserProfileModal";


const Profile = () => {
  const userdetails = JSON.parse(localStorage.getItem("userDetails"));
  const [isShown, setIsShown] = useState(false);
  const [username, setUser] = useState("");
  const [arrs, setUsers] = useState([]);

  useEffect(() => {

      console.log(userdetails)
      const Name = userdetails.userFullName;
      const namesArray = Name.split(" ");
      setUser(namesArray[0]);
      setUsers(namesArray);
    
  }, []);

  const handleClick = () => {
    setIsShown(!isShown);
  };


  return (
    <>
      <ProfileRow onClick={handleClick}>
        <Round>
          <Img src={userdetails.imageUrl} alt="logo" />
        </Round>
        <span>

       
          {username} &nbsp;
          <FontAwesomeIcon icon={isShown ? faChevronUp : faChevronDown} />
        </span>
      </ProfileRow>
      {isShown && <UserProfileModal name={userdetails.userFullName} email={userdetails.userEmail} profile={userdetails.imageUrl} />}
    </>
  );
};

export default Profile;

const ProfileRow = styled.div`
  width: 40%;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-around;
  cursor: pointer;
`;

const Round = styled.div`
  width: 40px;
  height: 40px;
  border-radius: 100px;
`;

const Img = styled.img`
  width: 100%;
  height: 100%;
  border-radius: 100px;
`;
