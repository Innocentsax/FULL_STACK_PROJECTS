import { config } from "./baseUrl";
import axios from "axios";

const { baseURL } = config;
const token = localStorage.getItem("token");
const requestHeaders = {
  headers: {
    Authorization: `Bearer ${token}`,
  },
};

const handleUserDetailsUpdate = async (userInfos, updatePassword) => {
  console.log("call to profile update");
  const { password, newPassword } = userInfos;
  if (updatePassword) updateUserPassword({ password, newPassword });
  try {
    const uri = baseURL + "/profile";
    const profileEditResponse = await axios.put(
      uri,
      userInfos,
      requestHeaders
    );
    // if(profileEditResponse) {
    //     const responseData = await profileEditResponse.data;
    // }
  } catch (requestError) {
    console.log(requestError);
  }
};

const updateUserPassword = async ({ newPassword, password }) => {
  const uri = baseURL + "/updatePassword";
  const requestBody = {
    confirmNewPassword: newPassword,
    newPassword: newPassword,
    oldPassword: password,
  };

  const passwordUpdateRequest = await axios.post(
    uri,
    requestBody,
    requestHeaders
  );
  const response = await passwordUpdateRequest.data;
  console.log(response);
};

export { handleUserDetailsUpdate };
