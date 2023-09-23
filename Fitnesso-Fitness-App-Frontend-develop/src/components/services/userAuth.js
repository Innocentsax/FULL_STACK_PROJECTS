import { config } from "./baseUrl";
// import axios from "axios";


const loginUser
 = async (event, formData, buttonState, setAlertBox) => {
  event.preventDefault();
  buttonState(true);
  handleSingleSignOn(formData);
  const url = config.baseURL + "/login";
  try {
    // const apiResponse = await axios.post(url, formData);
    // const { data } = apiResponse;
    setAlertBox({
      state: true,
      message: "Logged in successfully",
      type: "success",
    });
    // localStorage.setItem("token", data.token);
  } catch (e) {

    const status  = await e.response.status;
    if (status >= 400 && status !== undefined) {
      setAlertBox({
        state: true,
        message: "Incorrect username or password",
        type: "error",
      });
    }
    buttonState("");
  }
};

const handleSingleSignOn = (formData) => {
    const { rememberMe, email } = formData;
    if (formData.rememberMe) {
      localStorage.setItem("rememberMe", JSON.stringify({ email, rememberMe }));
    } else {
      if (localStorage.getItem("rememberMe")) {
        localStorage.removeItem("rememberMe");
      }
    }
};

export { loginUser
 };